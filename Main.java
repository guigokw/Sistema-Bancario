import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1 - entrar como funcionario");
                System.out.println("2 - entrar como cliente");
                System.out.println("3 - sair do caixa eletronico");
                System.out.println("-----------------------");
                System.out.print("qual dessas opções vc escolhe?");
                int opcao = input.nextInt();

                switch (opcao) {
                    case 1 -> entrarComoFuncionario(input, banco);

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("entrada invalida, por favir digite novamente");
                input.nextLine();
            }
        }
    }


    private static void entrarComoFuncionario(Scanner input, Banco banco) {
        try {
            System.out.println("1 - adicionar funcionario");
            System.out.println("2 - remover funcioanrio");
            System.out.println("3 - adicionar cliente ao banco");
            System.out.println("4 - remover cliente do banco");
            System.out.println("5 - adicionar conta do cliente");
            System.out.println("6 - bloquear conta do cliente");
            System.out.println("7 - encerrar conta do cliente");
            System.out.println("8 - adicionar caixa eletronico");
            System.out.println("9 - remover caixa eletornico");
            System.out.println("---------------------");
            System.out.print("qual dessas opceos vc escolhe?");
            int opcao = input.nextInt();

            input.nextLine();

            switch (opcao) {
                case 1 -> banco.adicionarFuncionario();
                case 2 -> banco.removerFuncionario();
                case 3 -> banco.adicionarCliente();
                case 4 -> banco.removerCliente();
                case 5 -> banco.adicionarContaDoCliente();
                case 6 -> banco.bloquearConta();
                case 7 -> banco.encerrarConta();
                case 8 -> banco.adicionarCaixaEletronico();
                case 9 -> banco.removerCaixaEletronico();
                default -> System.out.println("opcao invalida, por favor digite novamente");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("entrada invalida, por favor digite novamente");
            input.nextLine();
        } catch (IllegalArgumentException | ClienteNaoEncontradoException | ContaNaoEncontradaException | IdDoClienteDuplicadoException | CaixaEletronicoNaoEncontradoException | CaixaDuplicadoException | ContaDuplicadaException | FuncionarioNaoEncontradoException | FuncionarioDuplicadoException e) {
            System.out.println(e.getMessage());
            input.nextLine();
        }
    }
}
