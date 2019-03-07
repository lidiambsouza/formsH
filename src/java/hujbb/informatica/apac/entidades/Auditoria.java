package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.util.F;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Auditoria implements Serializable{

private Integer id_auditoria;    
private Date data ;    
private Usuario usuario;
private Entidade tipo_entidade;
private Integer id_entidade_alvo;
private Log_procedimento log_procedimento ;
private String campo_antigo;
private String campo_novo;

//variaveis extras
private String dataString;
private String afetado;



    public Auditoria() {
      id_auditoria =  -1;
      data =  new Date();
      usuario =  new Usuario();
      log_procedimento =  new Log_procedimento();
      tipo_entidade = new Entidade();
    }

    public Auditoria(Integer id_auditoria, Date data, Usuario usuario, Entidade tipo_entidade_id, Integer id_entidade_alvo, Log_procedimento log_procedimento, String campo_antigo, String campo_novo) {
        this.id_auditoria = id_auditoria;
        this.data = data;
        this.usuario = usuario;
        this.tipo_entidade = tipo_entidade_id;
        this.id_entidade_alvo = id_entidade_alvo;
        this.log_procedimento = log_procedimento;
        this.campo_antigo = campo_antigo;
        this.campo_novo = campo_novo;
    }
    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 1;
    }

    public String getDataString() {
        if(id_auditoria == -1){
            dataString = "";
        }else{
            dataString =  F.dataString(data);
        }
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
    

    public Integer getId_auditoria() {
        return id_auditoria;
    }

    public void setId_auditoria(Integer id_auditoria) {
        this.id_auditoria = id_auditoria;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Entidade getTipo_entidade_id() {
        return tipo_entidade;
    }

    public void setTipo_entidade_id(Entidade tipo_entidade_id) {
        this.tipo_entidade = tipo_entidade_id;
    }

    public Integer getId_entidade_alvo() {
        return id_entidade_alvo;
    }

    public void setId_entidade_alvo(Integer id_entidade_alvo) {
        this.id_entidade_alvo = id_entidade_alvo;
    }

    public Log_procedimento getLog_procedimento() {
        return log_procedimento;
    }

    public void setLog_procedimento(Log_procedimento log_procedimento) {
        this.log_procedimento = log_procedimento;
    }

    public String getCampo_antigo() {
        return campo_antigo;
    }

    public void setCampo_antigo(String campo_antigo) {
        this.campo_antigo = campo_antigo;
    }

    public String getCampo_novo() {
        return campo_novo;
    }

    public void setCampo_novo(String campo_novo) {
        this.campo_novo = campo_novo;
    }

    public String getAfetado() {
        afetado = tipo_entidade.getNome()+":"+id_entidade_alvo; 
        return afetado;
    }

    public void setAfetado(String afetado) {
        this.afetado = afetado;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id_auditoria);
        hash = 47 * hash + Objects.hashCode(this.data);
        hash = 47 * hash + Objects.hashCode(this.usuario);
        hash = 47 * hash + Objects.hashCode(this.tipo_entidade);
        hash = 47 * hash + Objects.hashCode(this.id_entidade_alvo);
        hash = 47 * hash + Objects.hashCode(this.log_procedimento);
        hash = 47 * hash + Objects.hashCode(this.campo_antigo);
        hash = 47 * hash + Objects.hashCode(this.campo_novo);
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
        final Auditoria other = (Auditoria) obj;
        if (!Objects.equals(this.campo_antigo, other.campo_antigo)) {
            return false;
        }
        if (!Objects.equals(this.campo_novo, other.campo_novo)) {
            return false;
        }
        if (!Objects.equals(this.id_auditoria, other.id_auditoria)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.tipo_entidade, other.tipo_entidade)) {
            return false;
        }
        if (!Objects.equals(this.id_entidade_alvo, other.id_entidade_alvo)) {
            return false;
        }
        if (!Objects.equals(this.log_procedimento, other.log_procedimento)) {
            return false;
        }
        return true;
    }
    

   
}
