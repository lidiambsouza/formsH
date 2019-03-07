package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Config implements Serializable{

    private Integer id_config = -1;
    private String nome = "";
    private String descricao = "";
    private String paranmetro = "";

    public Config(Integer id_config, String nome, String descricao, String paranmetro) {
        this.id_config = id_config;
        this.nome = nome;
        this.descricao = descricao;
        this.paranmetro = paranmetro;
    }

    public Config() {
    }
    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 4;
    }

    public Integer getId_config() {
        return id_config;
    }

    public void setId_config(Integer id_config) {
        this.id_config = id_config;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getParanmetro() {
        return paranmetro;
    }

    public void setParanmetro(String paranmetro) {
        this.paranmetro = paranmetro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id_config);
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + Objects.hashCode(this.descricao);
        hash = 47 * hash + Objects.hashCode(this.paranmetro);
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
        final Config other = (Config) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.paranmetro, other.paranmetro)) {
            return false;
        }
        if (!Objects.equals(this.id_config, other.id_config)) {
            return false;
        }
        return true;
    }
    
    
}
