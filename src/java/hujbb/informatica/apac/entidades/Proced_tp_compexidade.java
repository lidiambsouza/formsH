
package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;


public class Proced_tp_compexidade implements Serializable {
    private String id;
    private String complexidade;

    public Proced_tp_compexidade() {
        this.id = "";
        this.complexidade = "";
    }

    public Proced_tp_compexidade(String id, String complexidade) {
        this.id = id;
        this.complexidade = complexidade;        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String complexidade) {
        this.complexidade = complexidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.complexidade);
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
        final Proced_tp_compexidade other = (Proced_tp_compexidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.complexidade, other.complexidade)) {
            return false;
        }
        return true;
    }
    
    
    
}
