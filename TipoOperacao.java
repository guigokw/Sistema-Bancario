public enum TipoOperacao {
    SAQUE("Uma operação bancária de saque é a retirada de dinheiro em espécie de uma conta corrente, poupança ou outro serviço financeiro. Pode ser feita em caixas eletrônicos, agências bancárias ou pontos autorizados, como correspondentes bancários. A operação envolve a transferência de fundos da conta para o cliente em forma de dinheiro físico"),
    DEPOSITO("Uma operação bancária de depósito é uma transação em que uma pessoa ou empresa entrega dinheiro, cheques ou outros valores a uma instituição de crédito, que se compromete a devolvê-los de acordo com os termos e condições acordados. Em outras palavras, é o ato de colocar dinheiro numa conta bancária ou instituição financeira"),
    TRANSFERENCIA("Uma operação bancária de transferência é a ação de movimentar dinheiro de uma conta bancária para outra, seja dentro do mesmo banco ou para um banco diferente"),
    CONSULTA("Uma operação bancária de consulta refere-se a uma ação realizada por um cliente para obter informações sobre sua conta bancária, transações, produtos ou serviços oferecidos pelo banco. Essa consulta pode ser feita através de diversos canais, como o internet banking, aplicativo do banco, atendimento ao cliente por telefone ou em agência");


    private String descricao;

    TipoOperacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
