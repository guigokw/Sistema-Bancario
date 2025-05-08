import java.time.LocalDate;

public class Conta implements ExibicaoDeDetalhes {
    private Cliente proprietarioConta;
    private String numeroConta;
    private double saldoConta;
    private StatusConta statusConta;
    private TipoConta tipoConta;
    private LocalDate dataCriacaoDaConta;

    public Conta(Cliente proprietarioConta, String numeroConta, double saldoConta, StatusConta statusConta, TipoConta tipoConta, LocalDate dataCriacaoDaConta) throws IllegalArgumentException {
        if (numeroConta.length() == 4 && saldoConta > 0) {
            this.proprietarioConta = proprietarioConta;
            this.numeroConta = numeroConta;
            this.saldoConta = saldoConta;
            this.statusConta = statusConta;
            this.tipoConta = tipoConta;
            this.dataCriacaoDaConta = LocalDate.now();
        } else {
            throw new IllegalArgumentException("nao foi possivel cadastrar conta pois o numero da conta nao tem 4 digitos |OU| o saldo da conta é negativo");
        }
    }

    public Cliente getProprietarioConta() {
        return proprietarioConta;
    }

    public void setProprietarioConta(Cliente proprietarioConta) {
        this.proprietarioConta = proprietarioConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) throws IllegalArgumentException {
        if (numeroConta.length() != 4) {
            throw new IllegalArgumentException("nao foi possivel registrar a conta pois o numero da conta esta invalido");
        } else {
            this.numeroConta = numeroConta;
        }
    }

    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) throws IllegalArgumentException {
        if (saldoConta < 0) {
            throw new IllegalArgumentException("nao foi possivel cadastrar conta pois o saldo nao pode ser negativo");
        }
        this.saldoConta = saldoConta;
    }

    public StatusConta getStatusConta() {
        return statusConta;
    }

    public void setStatusConta(StatusConta statusConta) {
        this.statusConta = statusConta;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public LocalDate getDataCriacaoDaConta() {
        return dataCriacaoDaConta;
    }

    public void setDataCriacaoDaConta(LocalDate dataCriacaoDaConta) {
        this.dataCriacaoDaConta = dataCriacaoDaConta;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("NUMERO DA CONTA: " +this.numeroConta);
        System.out.println("SALDO DA CONTA: " +this.saldoConta);
        System.out.println("STATUS DA CONTA: " +this.statusConta);
        System.out.println("TIPO DA CONTA: " +this.tipoConta);
    }
}
