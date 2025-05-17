import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class CaixaEletronico {
    private int numeroCaixa;
    private double dinheiroNoCaixa;

    Scanner input = new Scanner(System.in);

    public CaixaEletronico(int numeroCaixa, double dinheiroNoCaixa) throws IllegalArgumentException {
        if (dinheiroNoCaixa < 0 || numeroCaixa < 0) {
            throw new IllegalArgumentException("nao há dinheiro no caixa |OU| numero do caixa invalido");
        } else {
            this.numeroCaixa = numeroCaixa;
            this.dinheiroNoCaixa = dinheiroNoCaixa;
        }
    }


    public int getNumeroCaixa() {
        return numeroCaixa;
    }

    public void setNumeroCaixa(int numeroCaixa) {
        this.numeroCaixa = numeroCaixa;
    }

    public double getDinheiroNoCaixa() {
        return dinheiroNoCaixa;
    }

    public void setDinheiroNoCaixa(double dinheiroNoCaixa) {
        this.dinheiroNoCaixa = dinheiroNoCaixa;
    }


    public void realizarSaque(Banco banco) throws ClienteNaoEncontradoException, ContaNaoEncontradaException, SaqueInvalidoException, java.util.InputMismatchException {
        try {
            if (banco.getCaixasNoBanco().isEmpty()) {
                System.out.println("nao foi possivel realizar a operacao pois nao há caixas eletronicos no banco");
            } else {
                System.out.print("qual o id do cliente que deseja realizar um saque");
                int id = input.nextInt();

                input.nextLine();

                Cliente cliente = banco.getClientesNoBanco().stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel realizar o saque pois o id do cliente nao foi encontrado"));


                System.out.print("qual o numero da conta que vc deseja realizar um saque?");
                String numero = input.nextLine().strip();

                Conta conta = cliente.getContasDoCliente().values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                        .filter(a -> a.getProprietarioConta().getIdCliente() == id)
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA)
                        .filter(a -> a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel realizar o saque pois o numero da conta nao foi encontrado |OU| cliente fornecido nao corresponde a essa conta" +
                                "|OU| a conta esta bloqueada ou encerrada"));

                System.out.print("quanto o valor que vc deseja sacar?");
                double valor = input.nextDouble();

                if (valor > conta.getSaldoConta()) {
                    throw new SaqueInvalidoException("nao foi possivel realizar o saque pois nao há saldo suficiente");
                } else if (valor < 0) {
                    throw new SaqueInvalidoException("nao foi possivel realizar o saque pois o valor registrado para retiro esta invalido");
                } else if (valor > this.dinheiroNoCaixa) {
                    throw new SaqueInvalidoException("não foi possivel realizar o saque pois nao há saldo suficiente no caixa bancario, por favor faça a operacao em outro caixa");
                } else {
                    conta.setSaldoConta(conta.getSaldoConta() - valor);
                    setDinheiroNoCaixa(this.dinheiroNoCaixa - valor);
                    conta.historicoDeOperacoes.put(TipoOperacao.SAQUE, valor);
                    System.out.println("saque de R$:" + valor + " da conta de " + conta.getProprietarioConta().getNomeCliente() + " realizado");
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException | SaqueInvalidoException |
                 IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void realizarDeposito(Banco banco) throws ClienteNaoEncontradoException, ContaNaoEncontradaException, IllegalArgumentException, java.util.InputMismatchException {
        try {
            if (banco.getCaixasNoBanco().isEmpty()) {
                System.out.println("nao foi possivel realizar a operacao pois nao há caixas eletronicos no banco");
            } else {
                System.out.print("qual o id do cliente que deseja realizar o deposito?");
                int id = input.nextInt();

                input.nextLine();

                Cliente cliente = banco.getClientesNoBanco().stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel realizar o deposito pois o id do cliente nao foi encontrado"));

                System.out.print("qual o numero da conta que vc deseja realizar deposito");
                String numero = input.nextLine().strip();

                Conta conta = cliente.getContasDoCliente().values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                        .filter(a -> a.getProprietarioConta().getIdCliente() == cliente.getIdCliente())
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA)
                        .filter(a -> a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel realizar o deposito pois nao foi encontrado o numero da conta |OU|" +
                                "o cliente fornecido nao corresponde a essa conta" +
                                "|OU| a conta esta bloqueada ou encerrada"));

                System.out.print("qual o valor que vc deseja depositar?");
                double valor = input.nextDouble();

                if (valor < 0) {
                    throw new IllegalArgumentException("nao foi possivel realizar o deposito pois o valor inserido esta invalido");
                } else {
                    conta.setSaldoConta(conta.getSaldoConta() + valor);
                    setDinheiroNoCaixa(this.dinheiroNoCaixa + valor);
                    conta.historicoDeOperacoes.put(TipoOperacao.DEPOSITO, valor);
                    System.out.println("deposito do valor de R$:" + valor + " realizado na conta de " + cliente.getNomeCliente());
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void realizarTransferencia(Banco banco) throws ClienteNaoEncontradoException, ContaNaoEncontradaException, TransferenciaInvalidaException, java.util.InputMismatchException {
        try {
            if (banco.getCaixasNoBanco().isEmpty()) {
                System.out.println("nao foi possivel realizar a operacao pois nao há caixas eletronicos no banco");
            } else {
                System.out.print("qual o id do cliente que deseja realizar a transferencia?");
                int id1 = input.nextInt();

                System.out.print("qual o id do cliente que ira receber a transferencia?");
                int id2 = input.nextInt();

                input.nextLine();

                Cliente cliente1 = banco.getClientesNoBanco().stream()
                        .filter(a -> a.getIdCliente() == id1)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel realizar a trasnferencia pois o id do cliente que iria transferir nao foi encontrado"));

                Cliente cliente2 = banco.getClientesNoBanco().stream()
                        .filter(a -> a.getIdCliente() == id2)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel realizar a transferencia pois o id co cliente que recebiria nao foi encontrado"));

                System.out.print("qual o numero da conta de origem que vc deseja realizar a trasnferencia?");
                String numero1 = input.nextLine().strip();

                System.out.print("qual o numero da conta que destino que vc deseja realizar a trasnferencia?");
                String numero2 = input.nextLine().strip();

                Conta conta1 = cliente1.getContasDoCliente().values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero1))
                        .filter(a -> a.getProprietarioConta().getIdCliente() == cliente1.getIdCliente())
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA)
                        .filter(a -> a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel realizar a trasnferencia pois nao foi encontrado o numero da conta de origem |OU|" +
                                "o cliente fornecido nao corresponde a essa conta" +
                                " |OU| a conta esta bloqueada ou encerrada"));

                Conta conta2 = cliente2.getContasDoCliente().values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero2))
                        .filter(a -> a.getProprietarioConta().getIdCliente() == cliente2.getIdCliente())
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA)
                        .filter(a -> a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel realizar a transferencia pois nao foi encontrado o numero da conta de destino |OU|" +
                                "o cliente fornecido nao corresponde a essa conta" +
                                "|OU| a conta esta bloqueada ou encerrada"));

                System.out.print("qual o valor da transferencia que vc deseja realizar?");
                double valor = input.nextDouble();

                if (valor < 0 || valor > conta1.getSaldoConta() || valor > this.dinheiroNoCaixa) {
                    throw new TransferenciaInvalidaException("nao foi possivel realizar a transferencia pois o valor esta invalido pois esta negativo |OU| esta maior que o saldo da conta de origem" +
                            " |OU| o valor esta maior que o saldo do caixa bancario, se for o caso realize a transfrencia em outro caixa eletronico");
                } else {
                    conta1.setSaldoConta(conta1.getSaldoConta() - valor);
                    conta2.setSaldoConta(conta2.getSaldoConta() + valor);
                    setDinheiroNoCaixa(this.dinheiroNoCaixa - valor);
                    conta1.historicoDeOperacoes.put(TipoOperacao.TRANSFERENCIA, valor);
                    conta2.historicoDeOperacoes.put(TipoOperacao.TRANSFERENCIA, valor);
                    System.out.println("transferencia de R$:" + valor + " enviada da conta de " + conta1.getProprietarioConta().getNomeCliente() + " para a conta de " + conta2.getProprietarioConta().getNomeCliente());
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException | TransferenciaInvalidaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tirarExtrato(Banco banco) throws ClienteNaoEncontradoException, ContaNaoEncontradaException, java.util.InputMismatchException {
        try {
            if (banco.getCaixasNoBanco().isEmpty()) {
                System.out.println("nao foi possivel realizar a operacao pois nao há caixas eletronicos no banco");
            } else {
                System.out.print("qual o id do cliente que deseja tirar o extrato?");
                int id = input.nextInt();

                input.nextLine();

                Cliente cliente = banco.getClientesNoBanco().stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel tirar o extrato pois o id do cliente nao foi encontrado?"));

                System.out.print("qual o numero da conta que vc deseja tirar o extrato");
                String numero = input.nextLine().strip();

                Conta conta = cliente.getContasDoCliente().values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                        .filter(a -> a.getProprietarioConta().getIdCliente() == cliente.getIdCliente())
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA)
                        .filter(a -> a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel tirar o extrato pois nao foi encontrado o numero da conta |OU|" +
                                "o cliente fornecido nao corresponde a essa conta" +
                                " |OU| a conta esta bloqueada ou encerrada"));


                if (conta.historicoDeOperacoes.isEmpty()) {
                    System.out.println("nao foi possivel tirar o extrato bancario, pois a conta de " + cliente.getNomeCliente().toLowerCase() + " nao realizou nenhuma operação");
                } else {
                    System.out.println("===== EXTRATO BANCARIO DE " + cliente.getNomeCliente().toUpperCase() + " =====");
                    for (Map.Entry<TipoOperacao, Double> operacoes : conta.historicoDeOperacoes.entrySet()) {
                        System.out.println("TIPO DE OPERAÇÃO: " + operacoes.getKey().name());
                        System.out.println("VALOR: " +operacoes.getValue());
                        System.out.println("---------------------");
                    }
                    System.out.println("SALDO ATUAL: " + conta.getSaldoConta());


                }
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void consultarSaldo(Banco banco) throws ClienteNaoEncontradoException, ContaNaoEncontradaException, java.util.InputMismatchException {
        try {
            if (banco.getCaixasNoBanco().isEmpty()) {
                System.out.println("nao foi possivel realizar a operacao pois nao há caixas eletronicos no banco");
            } else {
                System.out.print("qual o id do cliente que deseja consultar o saldo");
                int id = input.nextInt();

                input.nextLine();

                Cliente cliente = banco.getClientesNoBanco().stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel consultar o saldo pois o id do cliente nao foi encontrado"));


                System.out.print("qual o numero da conta que vc deseja consultar saldo?");
                String numero = input.nextLine().strip();

                Conta conta = cliente.getContasDoCliente().values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                        .filter(a -> a.getProprietarioConta().getIdCliente() == cliente.getIdCliente())
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA)
                        .filter(a -> a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel consultar saldo pois o numero da conta não foi encontrado |OU| a conta esta bloqueada ou encerrada" +
                                "|OU| o cliente fornecido nao corresponde a essa conta"));

                if (conta.getTipoConta() == TipoConta.POUPANCA) {
                    double saldoAtual = 0;
                    LocalDate dataLocal = LocalDate.now();
                    Month mes = conta.getDataCriacaoDaConta().getMonth();
                    Month mesAtual = dataLocal.getMonth();

                    if (mes != mesAtual) {
                        saldoAtual = conta.getSaldoConta() * 0.5;
                        System.out.println("SALDO DA CONTA: " + saldoAtual);
                        conta.historicoDeOperacoes.put(TipoOperacao.CONSULTA, saldoAtual);
                    } else {
                        System.out.println("SALDO DA CONTA " + conta.getSaldoConta());
                        conta.historicoDeOperacoes.put(TipoOperacao.CONSULTA, conta.getSaldoConta());
                    }
                } else {
                    conta.historicoDeOperacoes.put(TipoOperacao.CONSULTA, conta.getSaldoConta());
                    System.out.println("SALDO DA CONTA " + conta.getSaldoConta());
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

}
