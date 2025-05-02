public abstract class Pessoa {
    protected int idPessoa;
    protected String nomePessoa;

    public Pessoa(int idPessoa, String nomePessoa) throws IllegalArgumentException {
        if (idPessoa < 0 || nomePessoa.isEmpty()) {
            throw new IllegalArgumentException("nao foi possivel adicionar pois argumentos estão invalidos");
        } else {
            this.idPessoa = idPessoa;
            this.nomePessoa = nomePessoa;
        }
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
}
