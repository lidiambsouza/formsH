package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Setor implements Serializable{

    private Integer id_setor;
    private String nome;
    private String sigla;

    public Setor() {
        id_setor =-1;
        nome ="";
        sigla = "";
    }

    public Setor(Integer id_setor, String nome, String sigla) {
        this.id_setor = id_setor;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Integer getId_setor() {
        return id_setor;
    }

    public void setId_setor(Integer id_setor) {
        this.id_setor = id_setor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id_setor);
        hash = 29 * hash + Objects.hashCode(this.nome);
        hash = 29 * hash + Objects.hashCode(this.sigla);
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
        final Setor other = (Setor) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.sigla, other.sigla)) {
            return false;
        }
        if (!Objects.equals(this.id_setor, other.id_setor)) {
            return false;
        }
        return true;
    }
    
    
    
}
