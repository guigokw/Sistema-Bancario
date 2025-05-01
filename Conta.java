public class Conta implements ExibicaoDeDetalhes {
    protected Cliente proprietarioConta;
    protected String numeroConta;
    protected double saldoConta;
    protected StatusConta statusConta;
    protected TipoConta tipoConta;

    public Conta(Cliente proprietarioConta, String numeroConta, double saldoConta, StatusConta statusConta, TipoConta tipoConta) throws IllegalArgumentException {
        if (numeroConta.length() != 4 || saldoConta < 0) {
            throw new IllegalArgumentException("nao foi possivel cadastrar conta pois os dados estao invalidos");
        } else {
            this.proprietarioConta = proprietarioConta;
            this.numeroConta = numeroConta;
            this.saldoConta = saldoConta;
            this.statusConta = statusConta;
            this.tipoConta = tipoConta;
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

    @Override
    public void exibirDetalhes() {
        System.out.println("NUMERO DA CONTA: " +this.numeroConta);
        System.out.println("SALDO DA CONTA: " +this.saldoConta);
        System.out.println("STATUS DA CONTA: " +this.statusConta);
        System.out.println("TIPO DA CONTA: " +this.tipoConta);
    }
}
