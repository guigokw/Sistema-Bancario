import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Cliente implements ExibicaoDeDetalhes {
    private int idCliente;
    private String nomeCliente;
    private String enderecoCliente;
    private LocalDate dataNascimentoCliente;

    Map<String, Conta> contasDoCliente = new LinkedHashMap<>();
    Set<String> numeroDaConta = new HashSet<>();

    public Cliente(int idCliente, String nomeCliente, String enderecoCliente, LocalDate dataNascimentoCliente) throws IllegalArgumentException {
        int idade = dataNascimentoCliente.getYear() - LocalDate.now().getYear();
        if (nomeCliente.isEmpty() || enderecoCliente.isEmpty() || idade < 18 || idCliente < 0) {
            throw new IllegalArgumentException("nao foi possivel adicionar o cliente pois apresenta dados invalidos");
        } else {
            this.nomeCliente = nomeCliente;
            this.enderecoCliente = enderecoCliente;
            this.dataNascimentoCliente = dataNascimentoCliente;
        }
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) throws IllegalArgumentException {
        if (idCliente < 0) {
            throw new IllegalArgumentException("nao foi possivel adicionar po cliente pois  id é invalido");
        } else {
            this.idCliente = idCliente;
        }
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) throws IllegalArgumentException {
        if (nomeCliente.isEmpty()) {
            throw new IllegalArgumentException("nao foi possivel cadastrar o cliente pois o nome esta vazio");
        } else {
            this.nomeCliente = nomeCliente;
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
        int idade = dataNascimentoCliente.getDayOfYear() - LocalDate.now().getYear();
        if (idade < 18) {
            throw new IllegalArgumentException("nao foi possivel cadastrar o cliente pois a idade é menor de 18 anos");
        } else {
            this.dataNascimentoCliente = dataNascimentoCliente;
        }
    }


    @Override
    public void exibirDetalhes() {
        System.out.println("NOME DO CLIENTE: " +this.nomeCliente);
        System.out.println("ENDERECO DO CLIENTE: " +this.enderecoCliente);
        System.out.println("DATA DE NASCIMENTO DO CLIENTE: " +this.dataNascimentoCliente);
    }

    public void adicionarConta(Conta conta) throws ContaDuplicadaException {
        if (!numeroDaConta.add(conta.getNumeroConta())) {
            throw new ContaDuplicadaException("nao foi possivel registrar a conta, pois o numero da conta esta duplicado");
        } else {
            contasDoCliente.put(conta.getNumeroConta(), conta);
            System.out.println("conta de numero " + conta.getNumeroConta() + " do cliente " + this.nomeCliente + " adicionada");
        }
    }
}
