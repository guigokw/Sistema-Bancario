import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Pessoa implements ExibicaoDeDetalhes {
    private String enderecoCliente;
    private LocalDate dataNascimentoCliente;

    Map<String, Conta> contasDoCliente = new LinkedHashMap<>();
    Set<String> numeroDaConta = new HashSet<>();

    public Cliente(int idCliente, String nomeCliente, String enderecoCliente, LocalDate dataNascimentoCliente) throws IllegalArgumentException, DateTimeParseException {
        super(idCliente, nomeCliente);

        int idade = Period.between(dataNascimentoCliente, LocalDate.now()).getYears();
        if (enderecoCliente.isEmpty() || idade < 18) {
            throw new IllegalArgumentException("nao foi possivel adicionar o cliente pois, o endreco esta nulo |OU| a idade do cliente é menor do que 18");
        } else {
            this.idPessoa = idCliente;
            this.nomePessoa = nomeCliente;
            this.enderecoCliente = enderecoCliente;
            this.dataNascimentoCliente = dataNascimentoCliente;
        }
    }

    public int getIdCliente() {
        return idPessoa;
    }

    public void setIdCliente(int idCliente) throws IllegalArgumentException {
        if (idCliente < 0) {
            throw new IllegalArgumentException("nao foi possivel adicionar po cliente pois  id é invalido");
        } else {
            this.idPessoa = idCliente;
        }
    }

    public String getNomeCliente() {
        return nomePessoa;
    }

    public void setNomeCliente(String nomeCliente) throws IllegalArgumentException {
        if (nomeCliente.isEmpty()) {
            throw new IllegalArgumentException("nao foi possivel cadastrar o cliente pois o nome esta vazio");
        } else {
            this.nomePessoa = nomeCliente;
        }
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) throws IllegalArgumentException {
        if (enderecoCliente.isEmpty()) {
            throw new IllegalArgumentException("nao foi possivel cadastrar o cliente pois o endereco esta vazio");
        } else {
            this.enderecoCliente = enderecoCliente;
        }
    }
    public LocalDate getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    public void setDataNascimentoCliente(LocalDate dataNascimentoCliente) throws IllegalArgumentException {
        int idade = LocalDate.now().getYear() - dataNascimentoCliente.getYear();
        if (idade < 18) {
            throw new IllegalArgumentException("nao foi possivel cadastrar o cliente pois a idade é menor de 18 anos");
        } else {
            this.dataNascimentoCliente = dataNascimentoCliente;
        }
    }


    @Override
    public void exibirDetalhes() {
        System.out.println("ID DO CLIENTE: " +this.idPessoa);
        System.out.println("NOME DO CLIENTE: " +this.nomePessoa);
        System.out.println("ENDERECO DO CLIENTE: " +this.enderecoCliente);
        System.out.println("DATA DE NASCIMENTO DO CLIENTE: " +this.dataNascimentoCliente);
    }

    public void adicionarConta(Conta conta) throws ContaDuplicadaException {
        if (!numeroDaConta.add(conta.getNumeroConta())) {
            throw new ContaDuplicadaException("nao foi possivel registrar a conta, pois o numero da conta esta duplicado");
        } else {
            contasDoCliente.put(conta.getNumeroConta(), conta);
            System.out.println("conta de numero " + conta.getNumeroConta() + " do cliente " + this.nomePessoa + " adicionada");
        }
    }
}
