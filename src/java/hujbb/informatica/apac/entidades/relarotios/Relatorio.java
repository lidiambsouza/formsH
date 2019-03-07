package hujbb.informatica.apac.entidades.relarotios;

public class Relatorio {

    private String descricao;
    private int autorizado;
    private int naoAutorizado;
    private int total;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(int autorizado) {
        this.autorizado = autorizado;
    }

    public int getNaoAutorizado() {
        return naoAutorizado;
    }

    public void setNaoAutorizado(int naoAutorizado) {
        this.naoAutorizado = naoAutorizado;
    }

    public int getTotal() {
        total = autorizado+naoAutorizado;
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    

}
