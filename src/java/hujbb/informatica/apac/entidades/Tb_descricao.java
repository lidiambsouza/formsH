
package hujbb.informatica.apac.entidades;

import java.util.Objects;


public class Tb_descricao {
    private String cod_procedimento; 
    private String descricao;
    private int dt_competencia;

    public Tb_descricao() {
        this.cod_procedimento ="";
        this.descricao = "";
        this.dt_competencia = -1;
    }

    public Tb_descricao(String cod_procedimento, String descricao, int dt_competencia) {
        this.cod_procedimento ="";
        this.descricao = descricao;
        this.dt_competencia = dt_competencia;
    }

    public String getCod_procedimento() {
        return cod_procedimento;
    }

    public void setCod_procedimento(String cod_procedimento) {
        this.cod_procedimento = cod_procedimento;
    }

    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDt_competencia() {
        return dt_competencia;
    }

    public void setDt_competencia(int dt_competencia) {
        this.dt_competencia = dt_competencia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.cod_procedimento);
        hash = 67 * hash + Objects.hashCode(this.descricao);
        hash = 67 * hash + this.dt_competencia;
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
        final Tb_descricao other = (Tb_descricao) obj;
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.cod_procedimento, other.cod_procedimento)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        return true;
    }

       
}
