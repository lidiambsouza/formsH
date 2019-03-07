package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Procedimento_sus_has_cbo {

    private String procedimento_sus_codigo;
    private String cbo_id;
    private int dt_competencia;

    public Procedimento_sus_has_cbo() {
        this.procedimento_sus_codigo = "";
        
        this.cbo_id = "";
        this.dt_competencia = -1;
    
    }

    public Procedimento_sus_has_cbo(String procedimento_sus_codigo, String cbo_id, int dt_competencia) {
        this.procedimento_sus_codigo = procedimento_sus_codigo;
        this.cbo_id = cbo_id;
        this.dt_competencia = dt_competencia;
    }

   

    public String getProcedimento_sus_codigo() {
        return procedimento_sus_codigo;
    }

    public void setProcedimento_sus_codigo(String procedimento_sus_codigo) {
        this.procedimento_sus_codigo = procedimento_sus_codigo;
    }

    public String getCbo_id() {
        return cbo_id;
    }

    public void setCbo_id(String cbo_id) {
        this.cbo_id = cbo_id;
    }
  

    public int getDt_competencia() {
        return dt_competencia;
    }

    public void setDt_competencia(int dt_competencia) {
        this.dt_competencia = dt_competencia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.procedimento_sus_codigo);
        hash = 37 * hash + Objects.hashCode(this.cbo_id);
        hash = 37 * hash + this.dt_competencia;
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
        final Procedimento_sus_has_cbo other = (Procedimento_sus_has_cbo) obj;
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.procedimento_sus_codigo, other.procedimento_sus_codigo)) {
            return false;
        }
        if (!Objects.equals(this.cbo_id, other.cbo_id)) {
            return false;
        }
        return true;
    }

   
    
    
}
