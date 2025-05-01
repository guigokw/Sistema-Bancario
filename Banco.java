import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Banco {

    List<Cliente> clientesDoBanco = new ArrayList<>();
    Set<Integer> idDoCliente = new HashSet<>();
    Scanner input = new Scanner(System.in);

    public void adicionarCliente(Cliente cliente) throws IdDoClienteDuplicadoException {
        if (!idDoCliente.add(cliente.getIdCliente())) {
            throw new IdDoClienteDuplicadoException("nao foi possivel adicionar o cliente ao banco pois o id esta duplicado");
        } else {
            clientesDoBanco.add(cliente);
            System.out.println("cliente " + cliente.getNomeCliente() + " adicionado no banco");
        }
    }

    public void removerCliente(Cliente cliente) {
        clientesDoBanco.remove(cliente);
        System.out.println("cliente " +cliente.getNomeCliente()+ " removido do banco");
    }

    public void adicionarContaDoCliente() throws ClienteNaoEncontradoException, IllegalArgumentException {
        System.out.print("qual o id do cliente que vc deseja adicionar conta?");
        int id = input.nextInt();

       Cliente cliente = clientesDoBanco.stream()
                .filter(a -> a.getIdCliente() == id)
                .findFirst()
                .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel adicionar uma conta pro cliente, pois o cliente " +id+ " nao foi registrado"));

       System.out.print("qual o numero da conta que vc deseja definir?");
       String numeroConta = input.nextLine();

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
           default -> throw new IllegalArgumentException("opcao invalida, por favor se quiser realizar a operacao insira novamente");
       };

       Conta conta = new Conta(cliente, numeroConta, saldoConta, StatusConta.ATIVA, tipoConta);
       cliente.adicionarConta(conta);
       
    }
}
