package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;


public class Perfil implements Serializable{
   private int id_perfil;
   private String perfil;

    public Perfil(int id_perfil, String perfil) {
        this.id_perfil = id_perfil;
        this.perfil = perfil;
    }
   
   public Perfil(){
       
   }

    public int getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id_perfil;
        hash = 37 * hash + Objects.hashCode(this.perfil);
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
        final Perfil other = (Perfil) obj;
        if (this.id_perfil != other.id_perfil) {
            return false;
        }
        if (!Objects.equals(this.perfil, other.perfil)) {
            return false;
        }
        return true;
    }
   
   
   
   
}
