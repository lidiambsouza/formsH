package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Proc_justificativa implements Serializable{
    
    private Integer id_proc_justificativa =1;
    private String observacoes = "";
    private Cid cid_principal = new Cid();
    private Cid cid_secundario = new Cid();
    private Cid cid_causas_assoc = new Cid();

    public Proc_justificativa(Integer id_proc_justificativa, String observacoes, Cid cid_principal, Cid cid_secundario, Cid cid_causas_assoc) {
        this.id_proc_justificativa = id_proc_justificativa;
        this.observacoes = observacoes;
        this.cid_principal = cid_principal;
        this.cid_secundario = cid_secundario;
        this.cid_causas_assoc = cid_causas_assoc;
    }

    public Proc_justificativa() {
        this.id_proc_justificativa = -1;
        this.observacoes = "";
        this.cid_principal = new Cid();
        this.cid_secundario = new Cid();
        this.cid_causas_assoc = new Cid();
    }
    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 11;
    }

    public Integer getId_proc_justificativa() {
        return id_proc_justificativa;
    }

    public void setId_proc_justificativa(Integer id_proc_justificativa) {
        this.id_proc_justificativa = id_proc_justificativa;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes.trim();
    }

    public Cid getCid_principal() {
        if(cid_principal == null){
            cid_principal = new Cid();
        }
        
        return cid_principal;
    }

    public void setCid_principal(Cid cid_principal) {
        this.cid_principal = cid_principal;
    }

    public Cid getCid_secundario() {
        if(cid_secundario == null){
            cid_secundario = new Cid();
        }
        return cid_secundario;
    }

    public void setCid_secundario(Cid cid_secundario) {
        this.cid_secundario = cid_secundario;
    }

    public Cid getCid_causas_assoc() {
        if(cid_causas_assoc == null){
            cid_causas_assoc = new Cid();
        }
        return cid_causas_assoc;
    }

    public void setCid_causas_assoc(Cid cid_causas_assoc) {
        this.cid_causas_assoc = cid_causas_assoc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id_proc_justificativa);
        hash = 11 * hash + Objects.hashCode(this.observacoes);
        hash = 11 * hash + Objects.hashCode(this.cid_principal);
        hash = 11 * hash + Objects.hashCode(this.cid_secundario);
        hash = 11 * hash + Objects.hashCode(this.cid_causas_assoc);
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
        final Proc_justificativa other = (Proc_justificativa) obj;
        if (!Objects.equals(this.observacoes, other.observacoes)) {
            return false;
        }
        if (!Objects.equals(this.id_proc_justificativa, other.id_proc_justificativa)) {
            return false;
        }
        if (!Objects.equals(this.cid_principal, other.cid_principal)) {
            return false;
        }
        if (!Objects.equals(this.cid_secundario, other.cid_secundario)) {
            return false;
        }
        if (!Objects.equals(this.cid_causas_assoc, other.cid_causas_assoc)) {
            return false;
        }
        return true;
    }

    
    
    
    
}
