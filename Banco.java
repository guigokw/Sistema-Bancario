import java.util.*;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Banco {

    List<Funcionario> listaDeFuncionarios = new ArrayList<>();
    Set<Integer> idDoFuncioanrio = new HashSet<>();
    List<Cliente> clientesNoBanco = new ArrayList<>();
    Set<Integer> idDoCliente = new HashSet<>();
    Map<Integer, CaixaEletronico> caixasNoBanco = new HashMap<>();
    Set<Integer> numeroDoCaixa = new HashSet<>();
    Scanner input = new Scanner(System.in);

    public void adicionarFuncionario() {
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
            System.out.println("funcionario " +funcionario.getNomePessoa()+ " adicionado a lista de funcionarios do banco");
        }
    }

    public void removerFuncionario() {
        System.out.print("qual o id do funcioanrio que vc deseja remover?");
        int id = input.nextInt();

        Funcionario funcionario = listaDeFuncionarios.stream()
                .filter(a -> a.getIdPessoa() == id)
                .findFirst()
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("nao foi possivel remover o funcioanrio pois ele nao foi encontrado"));

        listaDeFuncionarios.remove(funcionario);
        System.out.println("funcionario " +funcionario.getNomePessoa()+ " removido da lista de funcionarios do banco");
    }

    public void adicionarCliente() throws IdDoClienteDuplicadoException {
        System.out.print("qual o id do cliente?");
        int idCliente = input.nextInt();

        input.nextLine();

        System.out.print("qual o nome do cliente?");
        String nomeCliente = input.nextLine();

        System.out.print("qual o endereco do cliente?");
        String enderecoCliente = input.nextLine();

        System.out.print("qual a data de nascimento do cliente?");
        String dataNascimento = input.nextLine();

        DateTimeFormatter formatado = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNasciemntoCliente = LocalDate.parse(dataNascimento, formatado);

        Cliente cliente = new Cliente(idCliente, nomeCliente, enderecoCliente, dataNasciemntoCliente);

        if (!idDoCliente.add(cliente.getIdCliente())) {
            throw new IdDoClienteDuplicadoException("nao foi possivel adicionar o cliente ao banco pois o id esta duplicado");
        } else {
            clientesNoBanco.add(cliente);
            System.out.println("cliente " + cliente.getNomeCliente() + " adicionado no banco");
        }
    }

    public void removerCliente() throws ClienteNaoEncontradoException {
        System.out.print("qual o id do cliente que vc deseja remover?");
        int id = input.nextInt();

        Cliente cliente = clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                                .findFirst()
                                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel remover o cliente pois o id nao foi encontrado?"));

        clientesNoBanco.remove(cliente);
        System.out.println("cliente " +cliente.getNomeCliente()+ " removido do banco");
    }

    public void adicionarContaDoCliente() throws ClienteNaoEncontradoException, IllegalArgumentException {
        System.out.print("qual o id do cliente que vc deseja adicionar conta?");
        int id = input.nextInt();

       Cliente cliente = clientesNoBanco.stream()
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


       Conta conta = new Conta(cliente, numeroConta.replaceAll("[/d]", ""), saldoConta, StatusConta.ATIVA, tipoConta, LocalDate.now());
       cliente.adicionarConta(conta);
    }

    public void bloquearConta() throws ClienteNaoEncontradoException, ContaNaoEncontradaException {
        System.out.print("qual o id do cliente que vc deseja bloquear a conta?");
        int id = input.nextInt();

        input.nextLine();

        Cliente cliente = clientesNoBanco.stream()
                        .filter(a -> a.getIdCliente() == id)
                                .findFirst()
                                        .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel bloquear a conta pois o id do cliente nao foi encontrado"));

        System.out.print("qual o numero da conta que vc deseja bloquear?");
        String numero = input.nextLine();

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
                System.out.println("conta " +conta.getTipoConta()+ " de " +conta.getProprietarioConta().getNomeCliente()+ " foi bloqueada");
                break;
            case 2:
                System.out.println("se deseja bloquear a conta de um outro ususario, por favor faça a operação novamente");
                break;
            default:
                System.out.println("opcao invalida, por favor digite novamente");
        }
    }

    public void encerrarConta() throws ClienteNaoEncontradoException, ContaNaoEncontradaException {
        System.out.print("qual o id do cliente que vc deseja encerrar a conta?");
        int id = input.nextInt();

        input.nextLine();

        Cliente cliente = clientesNoBanco.stream()
                .filter(a -> a.getIdCliente() == id)
                .findFirst()
                .orElseThrow(() -> new ClienteNaoEncontradoException("nao foi possivel encerrar a conta pois o id do cliente nao foi encontrado"));

        System.out.print("qual o numero da conta que vc deseja encerrar?");
        String numero = input.nextLine();

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
                System.out.println("conta " +conta.getTipoConta()+ " de " +conta.getProprietarioConta().getNomeCliente()+ " foi encerrada");
                break;
            case 2:
                System.out.println("se deseja bloquear a conta de um outro ususario, por favor faça a operação novamente");
                break;
            default:
                System.out.println("opcao invalida, por favor digite novamente");
        }
    }

    public void adicionarCaixaEletronico() throws CaixaDuplicadoException, IllegalArgumentException {
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

    public void removerCaixaEletronico() throws CaixaEletronicoNaoEncontradoException {
        System.out.print("qual o numero do caixa eletronico que vc deseja remover do sistema do banco");
        int numero = input.nextInt();

        CaixaEletronico caixa = caixasNoBanco.values().stream()
                        .filter(a -> a.getNumeroCaixa() == numero)
                                .findFirst()
                                        .orElseThrow(() -> new CaixaEletronicoNaoEncontradoException("nao foi possivel remover o caixa eletronico pois o numero do caixa nao foi encontardo"));

        caixasNoBanco.remove(caixa.getNumeroCaixa(), caixa);
        System.out.println("caixa numero " +caixa.getNumeroCaixa()+ " removido do banco");
    }
}
