package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Procedimento_qtd_contratualizado {
    private String procedimento_sus_codigo;
    private int qtd_contratualizado;
    private int qtd_executado;

    public Procedimento_qtd_contratualizado() {
        this.procedimento_sus_codigo = "";
        this.qtd_contratualizado = -1;
        this.qtd_executado = -1;
    }

    public Procedimento_qtd_contratualizado(String procedimento_sus_codigo, int qtd_contratualizado, int qtd_executado) {
        this.procedimento_sus_codigo = procedimento_sus_codigo;
        this.qtd_contratualizado = qtd_contratualizado;
        this.qtd_executado = qtd_executado;
    }

    public String getProcedimento_sus_codigo() {
        return procedimento_sus_codigo;
    }

    public void setProcedimento_sus_codigo(String procedimento_sus_codigo) {
        this.procedimento_sus_codigo = procedimento_sus_codigo;
    }

    public int getQtd_contratualizado() {
        return qtd_contratualizado;
    }

    public void setQtd_contratualizado(int qtd_contratualizado) {
        this.qtd_contratualizado = qtd_contratualizado;
    }

    public int getQtd_executado() {
        return qtd_executado;
    }

    public void setQtd_executado(int qtd_executado) {
        this.qtd_executado = qtd_executado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.procedimento_sus_codigo);
        hash = 19 * hash + this.qtd_contratualizado;
        hash = 19 * hash + this.qtd_executado;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Procedimento_qtd_contratualizado other = (Procedimento_qtd_contratualizado) obj;
        if (this.qtd_contratualizado != other.qtd_contratualizado) {
            return false;
        }
        if (this.qtd_executado != other.qtd_executado) {
            return false;
        }
        if (!Objects.equals(this.procedimento_sus_codigo, other.procedimento_sus_codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "procedimento_qtd_contratualizado{" + "procedimento_sus_codigo=" + procedimento_sus_codigo + ", qtd_contratualizado=" + qtd_contratualizado + ", qtd_executado=" + qtd_executado + '}';
    }
            
}
