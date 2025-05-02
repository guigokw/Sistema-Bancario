public class Funcionario extends Pessoa implements ExibicaoDeDetalhes {
    private double salarioFuncionario;

    public Funcionario(int idFuncionario, String nomeFuncionario, double salarioFuncionario) throws IllegalArgumentException {
        super(idFuncionario, nomeFuncionario);
        if (salarioFuncionario < 0) {
            throw new IllegalArgumentException("nao foi possivel adicionar o funcionario pois o salario esta invalido");
        } else {
            this.salarioFuncionario = salarioFuncionario;
        }
    }

    public double getSalarioFuncionario() {
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(double salarioFuncionario) {
        this.salarioFuncionario = salarioFuncionario;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("ID DO FUNCIONARIO: " +this.idPessoa);
        System.out.println("NOME DO FUNCIONARIO: " +this.nomePessoa);
        System.out.println("SALARIO DO FUNCIONARIO: " +this.salarioFuncionario);
    }
}
