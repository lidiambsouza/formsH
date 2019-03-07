
package hujbb.informatica.apac.entidades;

import java.util.logging.Logger;


public class Funcionalidade_sistema_has_perfil {
    private int funcionalidade_sistema_id_funcionalidade_sistema;
    private int perfil_id_perfil;

    public Funcionalidade_sistema_has_perfil(int funcionalidade_sistema_id_funcionalidade_sistema, int perfil_id_perfil) {
        this.funcionalidade_sistema_id_funcionalidade_sistema = funcionalidade_sistema_id_funcionalidade_sistema;
        this.perfil_id_perfil = perfil_id_perfil;
    }

    public Funcionalidade_sistema_has_perfil() {
    }

    public int getFuncionalidade_sistema_id_funcionalidade_sistema() {
        return funcionalidade_sistema_id_funcionalidade_sistema;
    }

    public void setFuncionalidade_sistema_id_funcionalidade_sistema(int funcionalidade_sistema_id_funcionalidade_sistema) {
        this.funcionalidade_sistema_id_funcionalidade_sistema = funcionalidade_sistema_id_funcionalidade_sistema;
    }

    public int getPerfil_id_perfil() {
        return perfil_id_perfil;
    }

    public void setPerfil_id_perfil(int perfil_id_perfil) {
        this.perfil_id_perfil = perfil_id_perfil;
    }
    private static final Logger LOG = Logger.getLogger(Funcionalidade_sistema_has_perfil.class.getName());

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.funcionalidade_sistema_id_funcionalidade_sistema;
        hash = 83 * hash + this.perfil_id_perfil;
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
        final Funcionalidade_sistema_has_perfil other = (Funcionalidade_sistema_has_perfil) obj;
        if (this.funcionalidade_sistema_id_funcionalidade_sistema != other.funcionalidade_sistema_id_funcionalidade_sistema) {
            return false;
        }
        if (this.perfil_id_perfil != other.perfil_id_perfil) {
            return false;
        }
        return true;
    }
    
    
    
}
