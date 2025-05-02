import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class CaixaEletronico {
    private int numeroCaixa;
    private double dinheiroNoCaixa;

    Scanner input = new Scanner(System.in);
    Banco banco = new Banco();

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

    public void realizarSaque() throws ClienteNaoEncontradoException, ContaNaoEncontradaException, SaqueInvalidoException {
        System.out.print("qual o id do cliente que deseja realizar um saque");
        int id = input.nextInt();

        input.nextLine();

        Cliente cliente = banco.clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                                .findFirst()
                                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel realizar o saque pois o id do cliente nao foi encontrado"));


        System.out.print("qual o numero da conta que vc deseja realizar um saque?");
        String numero = input.nextLine();

        Conta conta = cliente.contasDoCliente.values().stream()
                .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                .filter(a -> a.getProprietarioConta().getNomeCliente().equalsIgnoreCase(cliente.getNomeCliente()))
                .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA || a.getStatusConta() != StatusConta.ENCERRADA)
                .findFirst()
                .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel realizar o saque pois o numero da conta nao foi encontrado |OU| cliente fornecido nao corresponde a essa conta" +
                        "|OU| a conta esta bloqueada ou encerrada"));

        System.out.print("quanto o valor que vc deseja sacar?");
        double valor = input.nextDouble();

        if (valor > conta.getSaldoConta()) {
            throw new SaqueInvalidoException("nao foi possivel realizar o saque pois nao há saldo suficiente");
        } else if (valor < 0) {
            throw new SaqueInvalidoException("nao foi possivel realizar o saque pois o valor registrado para retiro esta invalido");
        } else {
            conta.setSaldoConta(- valor);
            System.out.println("saque de R$:" +valor+ " da conta de " +conta.getProprietarioConta().getNomeCliente()+ " realizado");
        }
    }

    public void consultarSaldo() {
        System.out.print("qual o id do cliente que deseja realizar um saque");
        int id = input.nextInt();

        input.nextLine();

        Cliente cliente = banco.clientesNoBanco.stream()
                .filter(a -> a.getIdCliente() == id)
                .findFirst()
                .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel consultar o saldo pois o id do cliente nao foi encontrado"));


        System.out.print("qual o numero da conta que vc deseja consultar saldo?");
        String numero = input.nextLine();

        Conta conta = cliente.contasDoCliente.values().stream()
                .filter(a -> a.getNumeroConta().equalsIgnoreCase(numero))
                .filter(a -> a.getStatusConta() != StatusConta.BLOQUEADA || a.getStatusConta() != StatusConta.ENCERRADA)
                .findFirst()
                .orElseThrow(() -> new ContaNaoEncontradaException("nao foi possivel consultar saldo pois o numero da conta não foi encontrado |OU| a conta esta bloqueada ou encerrada"));

        
        if (conta.getTipoConta() == TipoConta.POUPANCA) {
            double saldoAtual = 0;
            LocalDate dataLocal = LocalDate.now();
            Month mes = conta.getDataCriacaoDaConta().getMonth();
            Month mesAtual = dataLocal.getMonth();

            if (mes != mesAtual) {
                saldoAtual = conta.getSaldoConta() * 0.5;
                mes = mesAtual;
                System.out.println("SALDO DA CONTA: " +saldoAtual);
            } else {
                System.out.println("SALDO DA CONTA " +conta.getSaldoConta());
            }
        } else {
            System.out.println("SALDO DA CONTA " +conta.getSaldoConta());
        }
    }
}
