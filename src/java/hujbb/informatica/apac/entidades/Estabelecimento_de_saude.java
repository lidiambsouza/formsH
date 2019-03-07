package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Estabelecimento_de_saude implements Serializable {
    
    private Integer id_estabelecimento_saude = -1;
    private String cnes = "";
    private String nome = "";

    public Estabelecimento_de_saude(Integer id_estabelecimento_saude, String cnes, String nome) {
        this.id_estabelecimento_saude = id_estabelecimento_saude;
        this.cnes = cnes;
        this.nome = nome;
    }

    public Estabelecimento_de_saude() {
    }
    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 6;
    }

    public Integer getId_estabelecimento_saude() {
        return id_estabelecimento_saude;
    }

    public void setId_estabelecimento_saude(Integer id_estabelecimento_saude) {
        this.id_estabelecimento_saude = id_estabelecimento_saude;
    }

    public String getCnes() {
        return cnes.toUpperCase();
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id_estabelecimento_saude);
        hash = 13 * hash + Objects.hashCode(this.cnes);
        hash = 13 * hash + Objects.hashCode(this.nome);
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
        final Estabelecimento_de_saude other = (Estabelecimento_de_saude) obj;
        if (!Objects.equals(this.cnes, other.cnes)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.id_estabelecimento_saude, other.id_estabelecimento_saude)) {
            return false;
        }
        return true;
    }
    


}
