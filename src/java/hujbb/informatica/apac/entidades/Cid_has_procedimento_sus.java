package hujbb.informatica.apac.entidades;

import java.util.Objects;

public class Cid_has_procedimento_sus {
 private String cod_cid;    
 private String cod_procedimento;    
 private boolean principal;    
 private int dt_competencia;

    public Cid_has_procedimento_sus() {
        this.cod_cid = "";
        this.cod_procedimento = "";
        this.principal = false;
        this.dt_competencia = -1;
    }

    public Cid_has_procedimento_sus(String cod_cid, String cod_procedimento, boolean principal, int dt_competencia) {
        this.cod_cid = cod_cid;
        this.cod_procedimento = cod_procedimento;
        this.principal = principal;
        this.dt_competencia = dt_competencia;
    }

    public String getCod_cid() {
        return cod_cid;
    }

    public void setCod_cid(String cod_cid) {
        this.cod_cid = cod_cid;
    }

    public String getCod_procedimento() {
        return cod_procedimento;
    }

    public void setCod_procedimento(String cod_procedimento) {
        this.cod_procedimento = cod_procedimento;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public int getDt_competencia() {
        return dt_competencia;
    }

    public void setDt_competencia(int dt_competencia) {
        this.dt_competencia = dt_competencia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.cod_cid);
        hash = 97 * hash + Objects.hashCode(this.cod_procedimento);
        hash = 97 * hash + (this.principal ? 1 : 0);
        hash = 97 * hash + this.dt_competencia;
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
        final Cid_has_procedimento_sus other = (Cid_has_procedimento_sus) obj;
        if (this.principal != other.principal) {
            return false;
        }
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.cod_cid, other.cod_cid)) {
            return false;
        }
        if (!Objects.equals(this.cod_procedimento, other.cod_procedimento)) {
            return false;
        }
        return true;
    }
    
    
 
 
}
