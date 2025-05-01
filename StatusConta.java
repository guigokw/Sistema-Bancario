public enum StatusConta {
    ATIVA("Uma conta bancária ativa é aquela que está em pleno funcionamento, permitindo que o titular efetue transações como depósitos, saques e transferências. Para o Banco Central do Brasil (BACEN), uma conta bancária é considerada ativa desde que não esteja encerrada, mesmo que tenha saldo baixo ou não registre movimentações por um período"),
    BLOQUEADA("Uma conta bancária bloqueada significa que a conta está temporariamente indisponível para realizar operações financeiras. Isso pode ocorrer por diversos motivos, incluindo inatividade, determinação judicial, atualização de dados e até mesmo por questões de segurança do banco"),
    ENCERRADA("Uma conta bancária encerrada significa que a conta foi desativada e não está mais em uso. Isso pode ser feito por iniciativa do cliente ou do banco, e impede que novas transações (créditos ou débitos) sejam realizadas na conta");


    private String descricao;

    StatusConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
