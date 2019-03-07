package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author lidia.souza
 */
public class Ativ_desativ_usuario implements Serializable {

    private Integer id;
    private Date dt_motivo;
    private Motivo_ativ_desativ_usuario motivo;
    private Usuario usuario;
    private String obs; //pedro
    
    public Ativ_desativ_usuario(Integer id, Date dt_motivo, String obs, Motivo_ativ_desativ_usuario motivo, Usuario usuario) {//lidia
        this.id = id;
        this.dt_motivo = dt_motivo;
        this.obs=obs;//lidia
        this.motivo = motivo;
        this.usuario = usuario;
    }

    public Ativ_desativ_usuario() {
        this.id = -1;
        this.dt_motivo = null;
        this.obs="";// lidia
        this.motivo = new Motivo_ativ_desativ_usuario();
        this.usuario = new Usuario();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDt_motivo() {
        return dt_motivo;
    }

    public void setDt_motivo(Date dt_motivo) {
        this.dt_motivo = dt_motivo;
    }

    public Motivo_ativ_desativ_usuario getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo_ativ_desativ_usuario motivo) {
        this.motivo = motivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getObs() {// lidia
        return obs;
    }

    public void setObs(String obs) {// lidia
        this.obs = obs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.dt_motivo);
        hash = 61 * hash + Objects.hashCode(this.motivo);
        hash = 61 * hash + Objects.hashCode(this.usuario);
        hash = 61 * hash + Objects.hashCode(this.obs);//lidia
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
        final Ativ_desativ_usuario other = (Ativ_desativ_usuario) obj;
        if (!Objects.equals(this.obs, other.obs)) { // lidia
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dt_motivo, other.dt_motivo)) {
            return false;
        }
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }
    
  
    
    
}
