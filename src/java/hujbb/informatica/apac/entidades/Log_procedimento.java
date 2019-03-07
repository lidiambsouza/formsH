package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Log_procedimento {

    private Integer id;
    private String descricao;

    public Log_procedimento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Log_procedimento() {
    }
    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 15;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.descricao);
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
        final Log_procedimento other = (Log_procedimento) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
