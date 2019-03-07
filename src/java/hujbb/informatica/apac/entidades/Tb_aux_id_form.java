
package hujbb.informatica.apac.entidades;


public class Tb_aux_id_form {
    private int id;
    private int ano;

    public Tb_aux_id_form(int id, int ano) {
        this.id = id;
        this.ano = ano;
    }

    public Tb_aux_id_form() {
        this.id = -1;
        this.ano = -1;
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Tb_aux_id_form other = (Tb_aux_id_form) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.ano != other.ano) {
            return false;
        }
        return true;
    }
    
    
    
    
}
