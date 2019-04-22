package hujbb.informatica.apac.entidades.relarotios;

public class Relatorio {

    private String descricao;
    private int autorizado;
    private int naoAutorizado;
    private int salvo;
    private int emitido;
    private int enviadoDere;
    private int cancelado;
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
        total = autorizado+naoAutorizado+enviadoDere+salvo+emitido+cancelado;
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSalvo() {
        return salvo;
    }

    public void setSalvo(int salvo) {
        this.salvo = salvo;
    }

    public int getEmitido() {
        return emitido;
    }

    public void setEmitido(int emitido) {
        this.emitido = emitido;
    }

    public int getEnviadoDere() {
        return enviadoDere;
    }

    public void setEnviadoDere(int enviadoDere) {
        this.enviadoDere = enviadoDere;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }
    

}
