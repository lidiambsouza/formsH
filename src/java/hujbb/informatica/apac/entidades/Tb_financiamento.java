
package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;


public class Tb_financiamento implements Serializable{
    private String id; 
    private String nome;
    int dt_competencia;

    public Tb_financiamento() {
        this.id = "";
        this.nome = "";
        this.dt_competencia = -1;
    }

    public Tb_financiamento(String id, String nome, int dt_competencia) {
        this.id = id;
        this.nome = nome;
        this.dt_competencia = dt_competencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDt_competencia() {
        return dt_competencia;
    }

    public void setDt_competencia(int dt_competencia) {
        this.dt_competencia = dt_competencia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + this.dt_competencia;
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
        final Tb_financiamento other = (Tb_financiamento) obj;
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    
    
}
