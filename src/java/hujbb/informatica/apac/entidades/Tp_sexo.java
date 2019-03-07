
package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;


public class Tp_sexo implements Serializable{
    private String id;
    private String sexo;

    public Tp_sexo() {
        this.id = "";
        this.sexo = "";
    }

    public Tp_sexo(String id, String sexo) {
        this.id = id;
        this.sexo = sexo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.sexo);
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
        final Tp_sexo other = (Tp_sexo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tp_sexo{" + "id=" + id + ", sexo=" + sexo + '}';
    }
    
    
    
}
