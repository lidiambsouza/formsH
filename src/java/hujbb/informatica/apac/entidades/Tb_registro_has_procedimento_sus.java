package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Tb_registro_has_procedimento_sus {
     private int tb_registro_id;    
     private String procedimento_sus_codigo;    
     private int dt_competencia;

    public Tb_registro_has_procedimento_sus() {
       this.tb_registro_id = -1;
        this.procedimento_sus_codigo = "";
        this.dt_competencia = -1;
    }

    public Tb_registro_has_procedimento_sus(int tb_registro_id, String procedimento_sus_codigo, int dt_competencia) {
        this.tb_registro_id = tb_registro_id;
        this.procedimento_sus_codigo = procedimento_sus_codigo;
        this.dt_competencia = dt_competencia;
    }

    public int getTb_registro_id() {
        return tb_registro_id;
    }

    public void setTb_registro_id(int tb_registro_id) {
        this.tb_registro_id = tb_registro_id;
    }

    public String getProcedimento_sus_codigo() {
        return procedimento_sus_codigo;
    }

    public void setProcedimento_sus_codigo(String procedimento_sus_codigo) {
        this.procedimento_sus_codigo = procedimento_sus_codigo;
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
        hash = 71 * hash + this.tb_registro_id;
        hash = 71 * hash + Objects.hashCode(this.procedimento_sus_codigo);
        hash = 71 * hash + this.dt_competencia;
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
        final Tb_registro_has_procedimento_sus other = (Tb_registro_has_procedimento_sus) obj;
        if (this.tb_registro_id != other.tb_registro_id) {
            return false;
        }
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.procedimento_sus_codigo, other.procedimento_sus_codigo)) {
            return false;
        }
        return true;
    }
     
}
