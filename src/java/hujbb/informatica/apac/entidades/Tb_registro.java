package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Tb_registro {

    private int id;
    private String nome;
    private int dt_competencia;

    public Tb_registro() {
        this.id = -1;
        this.nome = "";
        this.dt_competencia = -1;
    }

    public Tb_registro(int id, String nome, int dt_competencia) {
        this.id = id;
        this.nome = nome;
        this.dt_competencia = dt_competencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        int hash = 5;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.nome);
        hash = 13 * hash + this.dt_competencia;
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
        final Tb_registro other = (Tb_registro) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    
}
