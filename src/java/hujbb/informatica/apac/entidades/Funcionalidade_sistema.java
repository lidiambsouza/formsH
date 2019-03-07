package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Funcionalidade_sistema {

    private int id_funcionalidade_sistema;
    private String funcionalidade;

    
    public Funcionalidade_sistema(int id_funcionalidade_sistema, String funcionalidade) {
        this.id_funcionalidade_sistema = id_funcionalidade_sistema;
        this.funcionalidade = funcionalidade;
    }

    public Funcionalidade_sistema(){
        
    }

    public int getId_funcionalidade_sistema() {
        return id_funcionalidade_sistema;
    }

    public void setId_funcionalidade_sistema(int id_funcionalidade_sistema) {
        this.id_funcionalidade_sistema = id_funcionalidade_sistema;
    }

    public String getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(String funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id_funcionalidade_sistema;
        hash = 37 * hash + Objects.hashCode(this.funcionalidade);
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
        final Funcionalidade_sistema other = (Funcionalidade_sistema) obj;
        if (this.id_funcionalidade_sistema != other.id_funcionalidade_sistema) {
            return false;
        }
        if (!Objects.equals(this.funcionalidade, other.funcionalidade)) {
            return false;
        }
        return true;
    }
    
    
    
}
