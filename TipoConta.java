public enum TipoConta {
    CORRENTE("Uma conta corrente é um tipo de conta bancária que permite a movimentação diária do dinheiro, como pagamentos, transferências e saques. É ideal para quem precisa de fácil acesso aos seus fundos para uso no dia a dia"),
    POUPANCA("Uma conta poupança, também conhecida como caderneta de poupança, é uma forma de investir dinheiro com baixo risco e alta liquidez, ou seja, pode ser resgatado a qualquer momento. É um dos investimentos mais tradicionais no Brasil, voltado para quem busca guardar dinheiro para objetivos futuros ou para emergências");


    private String descricao;

     TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
