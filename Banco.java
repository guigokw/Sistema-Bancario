import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.HashSet;
import java.time.LocalDate;


public class Banco {

    List<Funcionario> listaDeFuncionarios = new ArrayList<>();
    Set<Integer> idDoFuncioanrio = new HashSet<>();
    List<Cliente> clientesNoBanco = new ArrayList<>();
    Set<Integer> idDoCliente = new HashSet<>();
    Map<Integer, CaixaEletronico> caixasNoBanco = new HashMap<>();
    Set<Integer> numeroDoCaixa = new HashSet<>();
    Scanner input = new Scanner(System.in);

    public List<Cliente> getClientesNoBanco() {
        return clientesNoBanco;
    }

    public Map<Integer, CaixaEletronico> getCaixasNoBanco() {
        return caixasNoBanco;
    }

    public void adicionarFuncionario() throws java.util.InputMismatchException {
        try {
            System.out.print("qual o id do funcionario?");
            int idFuncionario = input.nextInt();

            input.nextLine();

            System.out.print("qual o nome do funcionario?");
            String nomeFuncionario = input.nextLine();

            System.out.print("qual o salario do funcionario?");
            double salarioFuncionario = input.nextDouble();

            Funcionario funcionario = new Funcionario(idFuncionario, nomeFuncionario, salarioFuncionario);

            if (!idDoFuncioanrio.add(funcionario.getIdPessoa())) {
                throw new FuncionarioDuplicadoException("nao foi possivel adicionar o funcionario pois o id esta duplicado");
            } else {
                listaDeFuncionarios.add(funcionario);
                System.out.println("funcionario " + funcionario.getNomePessoa() + " adicionado a lista de funcionarios do banco");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        }
    }

    public void removerFuncionario() throws java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty()) {
                System.out.println("nao foi possivel remover um funcionario, pois nao há nenhum regsitrado");
            } else {
                System.out.print("qual o id do funcionario que vc deseja remover?");
                int id = input.nextInt();

                Funcionario funcionario = listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == id)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel remover o funcioanrio pois ele nao foi encontrado"));

                listaDeFuncionarios.remove(funcionario);
                idDoFuncioanrio.remove(funcionario.getIdPessoa());
                System.out.println("funcionario " + funcionario.getNomePessoa() + " removido da lista de funcionarios do banco");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        }
    }

    public void adicionarCliente() throws IdDoClienteDuplicadoException, FuncionarioNaoEncontradoException, DateTimeParseException, java.util.InputMismatchException  {
        try {
            if (listaDeFuncionarios.isEmpty()) {
                System.out.println("não foi possivel adicionar um cliente ao banco, pois não há nenhum funcionario para registra-lo");
            } else {
                System.out.print("qual o id do funcionario que ira adicionar o cliente?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel adicionar o cliente no banco, pois o id do funcionario que iria registralo-lo nao foi encontrado"));


                System.out.print("qual o id do cliente?");
                int idCliente = input.nextInt();

                input.nextLine();

                System.out.print("qual o nome do cliente?");
                String nomeCliente = input.nextLine();

                System.out.print("qual o endereco do cliente?");
                String enderecoCliente = input.nextLine();

                System.out.print("qual a data de nascimento do cliente?");
                String dataNascimentoCliente = input.nextLine().trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate dataConvertida = LocalDate.parse(dataNascimentoCliente, formatter);

                Cliente cliente = new Cliente(idCliente, nomeCliente, enderecoCliente, dataConvertida);

                if (!idDoCliente.add(cliente.getIdCliente())) {
                    throw new IdDoClienteDuplicadoException("nao foi possivel adicionar o cliente ao banco pois o id esta duplicado");
                } else {
                    clientesNoBanco.add(cliente);
                    System.out.println("cliente " + cliente.getNomeCliente() + " adicionado no banco");
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (IdDoClienteDuplicadoException | FuncionarioNaoEncontradoException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerCliente() throws ClienteNaoEncontradoException, FuncionarioNaoEncontradoException, java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty() || clientesNoBanco.isEmpty()) {
                System.out.println("nao foi possivel remover um cliente, pois não há funcionarios registrados, para remove-lo" +
                        "|OU| nao foi possivel remover um cliente pois não há nenhum registrado");
            } else {
                System.out.print("qual o id do funcionario que ira remover o cliente?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel remover o cliente do banco, pois o id do funcionario que iria remove-lo nao foi encontrado"));


                System.out.print("qual o id do cliente que vc deseja remover?");
                int id = input.nextInt();

                Cliente cliente = clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel remover o cliente pois o id nao foi encontrado"));

                clientesNoBanco.remove(cliente);
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | FuncionarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
    public void adicionarContaDoCliente() throws ClienteNaoEncontradoException, IllegalArgumentException, FuncionarioNaoEncontradoException, java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty()) {
                System.out.println("nao foi possivel realizar a operacao pois não há nenhum funcionario registrado no banco");
            } else {
                System.out.print("qual o id do funcionario que ira adicionar a conta do cliente?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel adicionar a conta do cliente no banco, pois o id do funcionario que iria registralo-lo nao foi encontrado"));


                System.out.print("qual o id do cliente que vc deseja adicionar conta?");
                int id = input.nextInt();

                Cliente cliente = clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel adicionar uma conta pro cliente, pois o cliente com id" + id + " nao foi registrado"));

                input.nextLine();

                System.out.print("qual o numero da conta que vc deseja definir?");
                String numeroConta = input.nextLine();

                input.nextLine();

                System.out.print("qual o saldo da conta que vc deseja definir?");
                double saldoConta = input.nextDouble();

                System.out.println("1 - conta corrente");
                System.out.println("2 - conta poupanca");
                System.out.println("---------------------");
                System.out.print("quais desses tipos de conta vc deseja definir");
                int opcao = input.nextInt();

                TipoConta tipoConta = switch (opcao) {
                    case 1 -> TipoConta.CORRENTE;
                    case 2 -> TipoConta.POUPANCA;
                    default ->
                            throw new IllegalArgumentException("opcao invalida, por favor se quiser realizar a operacao insira novamente");
                };


                Conta conta = new Conta(cliente, numeroConta.replaceAll("[^\\d]", "").strip(), saldoConta, StatusConta.ATIVA, tipoConta, LocalDate.now());
                cliente.adicionarConta(conta);
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | IllegalArgumentException | FuncionarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }



    public void bloquearConta() throws ClienteNaoEncontradoException, ContaNaoEncontradaException, FuncionarioNaoEncontradoException, java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty()) {
                System.out.println("nao foi possivel bloquear uma conta, pois não há nenhum funcioanrio registrado para realizar a ação");
            } else {
                System.out.print("qual o id do funcionario que ira bloquear a conta do cliente?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel bloquear a conta do cliente, pois o id do funcionario que iria bloquea-lo nao foi encontrado"));


                System.out.print("qual o id do cliente que vc deseja bloquear a conta?");
                int id = input.nextInt();

                input.nextLine();

                Cliente cliente = clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel bloquear a conta pois o id do cliente nao foi encontrado"));

                System.out.print("qual o numero da conta que vc deseja bloquear?");
                String numero = input.nextLine().strip();

                Conta conta = cliente.contasDoCliente.values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                        .filter(a -> a.getProprietarioConta().getNomeCliente().equalsIgnoreCase(cliente.getNomeCliente()))
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA || a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel bloquear a conta pois o numero da conta nao foi encontrado |OU| cliente fornecido nao corresponde a essa conta" +
                                "|OU| status da conta ja esta bloqueada ou encerrada"));

                System.out.println("====== INFORMACOES DO CLIENTE ======");
                cliente.exibirDetalhes();
                System.out.println("-----------------------------");
                System.out.println("====== DETALHES DA CONTA ======");
                conta.exibirDetalhes();
                System.out.println("-----------------------------");
                System.out.println("1 - sim");
                System.out.println("2 - não");
                System.out.print("essas são as informacoes corretas da conta que vc deseja bloqueada?");
                int opcao = input.nextInt();

                switch (opcao) {
                    case 1:
                        conta.setStatusConta(StatusConta.BLOQUEADA);
                        System.out.println("conta " + conta.getTipoConta() + " de " + conta.getProprietarioConta().getNomeCliente() + " foi bloqueada");
                        break;
                    case 2:
                        System.out.println("se deseja bloquear a conta de um outro ususario, por favor faça a operação novamente");
                        break;
                    default:
                        System.out.println("opcao invalida, por favor digite novamente");
                }
            }
        }  catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException | FuncionarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void encerrarConta() throws ClienteNaoEncontradoException, ContaNaoEncontradaException, FuncionarioNaoEncontradoException, java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty()) {
                System.out.println("nao foi possivel encerrar uma conta, pois não há nenhum funcioanrio registrado para realizar a ação");
            } else {

                System.out.print("qual o id do funcionario que ira encerrar a conta do cliente?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel encerrar a conta do cliente, pois o id do funcionario que iria encerra-lo nao foi encontrado"));

                System.out.print("qual o id do cliente que vc deseja encerrar a conta?");
                int id = input.nextInt();

                input.nextLine();

                Cliente cliente = clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                        .findFirst()
                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel encerrar a conta pois o id do cliente nao foi encontrado"));

                System.out.print("qual o numero da conta que vc deseja encerrar?");
                String numero = input.nextLine().strip();

                Conta conta = cliente.contasDoCliente.values().stream()
                        .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                        .filter(a -> a.getProprietarioConta().getNomeCliente().equalsIgnoreCase(cliente.getNomeCliente()))
                        .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA || a.getStatusConta() != StatusConta.ENCERRADA)
                        .findFirst()
                        .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel encerrar a conta pois o numero da conta nao foi encontrado |OU| cliente fornecido nao corresponde a essa conta" +
                                "|OU| a conta ja esta bloqueada ou encerrada"));

                System.out.println("====== INFORMACOES DO CLIENTE ======");
                cliente.exibirDetalhes();
                System.out.println("-----------------------------");
                System.out.println("====== DETALHES DA CONTA ======");
                conta.exibirDetalhes();
                System.out.println("-----------------------------");
                System.out.println("1 - sim");
                System.out.println("2 - não");
                System.out.print("essas são as informacoes corretas da conta que vc deseja encerrar?");
                int opcao = input.nextInt();

                switch (opcao) {
                    case 1:
                        conta.setStatusConta(StatusConta.ENCERRADA);
                        System.out.println("conta " + conta.getTipoConta() + " de " + conta.getProprietarioConta().getNomeCliente() + " foi encerrada");
                        break;
                    case 2:
                        System.out.println("se deseja bloquear a conta de um outro ususario, por favor faça a operação novamente");
                        break;
                    default:
                        System.out.println("opcao invalida, por favor digite novamente");
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (ClienteNaoEncontradoException | ContaNaoEncontradaException | FuncionarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }


    public void adicionarCaixaEletronico() throws CaixaDuplicadoException, IllegalArgumentException, FuncionarioNaoEncontradoException, java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty()) {
                System.out.println("nao foi possivel adicionar o caixa eletronico, pois não há nenhum funcioanrio registrado para realizar a ação");
            } else {

                System.out.print("qual o id do funcionario que ira adicionar um caixa eletronico?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel adicionar um caixa eletronico, pois o id do funcionario que iria adiciona-lo nao foi encontrado"));

                System.out.print("qual o numero do caixa?");
                int numeroCaixa = input.nextInt();

                System.out.print("qual o saldo de dinheiro no caixa eletronico?");
                double dinheiroNoCaixa = input.nextDouble();

                CaixaEletronico caixa = new CaixaEletronico(numeroCaixa, dinheiroNoCaixa);

                if (!numeroDoCaixa.add(caixa.getNumeroCaixa())) {
                    throw new CaixaDuplicadoException("nao foi possivel adicionar o caixa eletronico ao banco pois o numero do esta duplicado");
                } else {
                    caixasNoBanco.put(caixa.getNumeroCaixa(), caixa);
                    System.out.println("caixa numero " + caixa.getNumeroCaixa() + " adicionado ao banco");
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (CaixaDuplicadoException | IllegalArgumentException | FuncionarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerCaixaEletronico() throws CaixaEletronicoNaoEncontradoException, FuncionarioNaoEncontradoException, java.util.InputMismatchException {
        try {
            if (listaDeFuncionarios.isEmpty() || caixasNoBanco.isEmpty()) {
                System.out.println("nao foi possivel remover o caixa do banco, pois não há nenhum funcioanrio registrado para realizar a ação" +
                        "|OU| não há nenhum caixa registrado para remover");
            } else {
                System.out.print("qual o id do funcionario que ira encerrar a conta do cliente?");
                int idDoFuncionario = input.nextInt();

                listaDeFuncionarios.stream()
                        .filter(a -> a.getIdPessoa() == idDoFuncionario)
                        .findFirst()
                        .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel remover o caixa do banco, pois o id do funcionario que iria remove-lo nao foi encontrado"));

                System.out.print("qual o numero do caixa eletronico que vc deseja remover do sistema do banco");
                int numero = input.nextInt();

                CaixaEletronico caixa = caixasNoBanco.values().stream()
                        .filter(a -> a.getNumeroCaixa() == numero)
                        .findFirst()
                        .orElseThrow(() -> new CaixaEletronicoNaoEncontradoException("nao foi possivel remover o caixa eletronico pois o numero do caixa nao foi encontardo"));

                caixasNoBanco.remove(caixa.getNumeroCaixa(), caixa);
                System.out.println("caixa numero " + caixa.getNumeroCaixa() + " removido do banco");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (CaixaEletronicoNaoEncontradoException | FuncionarioNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}
