import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("====== MENU ======");
                System.out.println("[1] - entrar como funcionario");
                System.out.println("[2] - entrar como cliente");
                System.out.println("[3] - sair do programa");
                System.out.println("-----------------------");
                System.out.print("qual dessas opções vc escolhe?");
                int opcao = input.nextInt();

                input.nextLine();

                switch (opcao) {
                    case 1 -> entrarComoFuncionario(input, banco);
                    case 2 -> entrarComoCliente(input, banco);
                    case 3 -> {
                        System.out.println("saindo do programa.....");
                        input.close();
                        return;
                    }
                    default -> System.out.println("opcao invalida, por favor digite novamente");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("entrada invalida, por favir digite novamente");
                input.nextLine();
            }
        }
    }


    private static void entrarComoFuncionario(Scanner input, Banco banco) throws java.util.InputMismatchException, IllegalArgumentException, ClienteNaoEncontradoException, ContaNaoEncontradaException, IdDoClienteDuplicadoException, CaixaEletronicoNaoEncontradoException, CaixaDuplicadoException, ContaDuplicadaException, FuncionarioNaoEncontradoException, FuncionarioDuplicadoException, DateTimeParseException {
        try {
            System.out.println("====== SISTEMA DO BANCO ======");
            System.out.println("[1] - adicionar funcionario");
            System.out.println("[2] - remover funcionario");
            System.out.println("[3] - adicionar cliente ao banco");
            System.out.println("[4] - remover cliente do banco");
            System.out.println("[5] - adicionar conta do cliente");
            System.out.println("[6] - bloquear conta do cliente");
            System.out.println("[7] - encerrar conta do cliente");
            System.out.println("[8] - adicionar caixa eletronico");
            System.out.println("[9] - remover caixa eletornico");
            System.out.println("---------------------");
            System.out.print("qual dessas opcões vc escolhe?");
            int opcao = input.nextInt();

            input.nextLine();

            switch (opcao) {
                case 1 -> banco.adicionarFuncionario(); // excecoes conferem
                case 2 -> banco.removerFuncionario(); // excecoes conferem
                case 3 -> banco.adicionarCliente(); // excecoes conferem
                case 4 -> banco.removerCliente(); // excecoes conferem
                case 5 -> banco.adicionarContaDoCliente(); // excecoes conferem
                case 6 -> banco.bloquearConta(); // excecoes conferem
                case 7 -> banco.encerrarConta(); // excecoes conferem
                case 8 -> banco.adicionarCaixaEletronico(); // excecoes conferem
                case 9 -> banco.removerCaixaEletronico(); // excecoes conferem
                default -> System.out.println("opcao invalida, por favor digite novamente");
            }
        } catch (IllegalArgumentException | ClienteNaoEncontradoException | ContaNaoEncontradaException |
                 IdDoClienteDuplicadoException | CaixaEletronicoNaoEncontradoException | CaixaDuplicadoException |
                 ContaDuplicadaException | FuncionarioNaoEncontradoException | FuncionarioDuplicadoException |
                 DateTimeParseException | NumeroContaDuplicadoException e) {
            System.out.println(e.getMessage());
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        }
    }

    private static void entrarComoCliente(Scanner input, Banco banco) throws ClienteNaoEncontradoException, SaqueInvalidoException, ContaNaoEncontradaException, IllegalArgumentException, TransferenciaInvalidaException, CaixaEletronicoNaoEncontradoException {
        try {
            System.out.print("qual o numero do caixa eletronico que vc deseja utilizar?");
            int numero = input.nextInt();

            CaixaEletronico caixa = banco.caixasNoBanco.values().stream()
                    .filter(a -> a.getNumeroCaixa() == numero)
                    .findFirst()
                    .orElseThrow(() -> new CaixaEletronicoNaoEncontradoException("nao foi possivel realizar a operacao pois o numero do caixa eletronico fornecido nao foi encontrado no sistema"));

            System.out.println("====== CAIXA ELETRONICO ======");
            System.out.println("[1] - realizar saque");
            System.out.println("[2] - realizar deposito");
            System.out.println("[3] - realizar transferencia");
            System.out.println("[4] - tirar o extrato bancario");
            System.out.println("[5] - consultar saldo");
            System.out.println("-----------------------");
            System.out.print("qual a opção de operação bancaria que vc deseja fazer?");
            int opcao = input.nextInt();

            input.nextLine();

            switch (opcao) {
                case 1 -> caixa.realizarSaque(banco); // excecoes conferem
                case 2 -> caixa.realizarDeposito(banco); // excecoes conferem
                case 3 -> caixa.realizarTransferencia(banco); // excecoes conferem
                case 4 -> caixa.tirarExtrato(banco);
                case 5 -> caixa.consultarSaldo(banco); // excecoes conferem
                default -> System.out.println("opcao invalida, se deseja realizar alguma das operações por favor digite novamente");
            }
        } catch (ClienteNaoEncontradoException | SaqueInvalidoException | ContaNaoEncontradaException |
                 IllegalArgumentException | TransferenciaInvalidaException |
                 CaixaEletronicoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        }
    }
}