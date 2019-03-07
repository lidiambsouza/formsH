package hujbb.informatica.apac.entidades.imprimir;

public class Pdf_apacEmitidas {

    private String n;
    private String nome;
    private String cartaoSus;
    private String nomeProcedimento;
    private String codigoProcedimento;
    private String periodo;

    public Pdf_apacEmitidas(String n, String nome, String cartaoSus, String nomeProcedimento, String codigoProcedimento,String periodo) {
        this.n = n;
        this.nome = nome;
        this.cartaoSus = cartaoSus;
        this.nomeProcedimento = nomeProcedimento;
        this.codigoProcedimento = codigoProcedimento;
        this.periodo = periodo;
    }
        
    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public void setCartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    public String getNomeProcedimento() {
        return nomeProcedimento;
    }

    public void setNomeProcedimento(String nomeProcedimento) {
        this.nomeProcedimento = nomeProcedimento;
    }

    public String getCodigoProcedimento() {
        return codigoProcedimento;
    }

    public void setCodigoProcedimento(String codigoProcedimento) {
        this.codigoProcedimento = codigoProcedimento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
        
    
}
