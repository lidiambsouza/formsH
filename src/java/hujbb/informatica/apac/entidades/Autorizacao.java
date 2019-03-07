package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.util.F;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Autorizacao implements Serializable {

    private Integer id_autorizacao = -1;
    private String autorizador = "";
    private String cod_org_emissor = "";
    private Integer tipo_doc = -1;
    private String num_doc = "";
    private Date data = new Date();
    private String num_autorizacao = "";
    private Date data_val_ini = new Date();
    private Date data_val_fim = new Date();

    //variaveis extras
    private String tipo_doc_string;
    private String dataString;
    private String periodo;

    public Autorizacao(Integer id_autorizacao, String autorizador, String cod_org_emissor, Integer tipo_doc, String num_doc, Date data, String num_autorizacao, Date data_val_ini, Date data_val_fim) {
        this.id_autorizacao = id_autorizacao;
        this.autorizador = autorizador;
        this.cod_org_emissor = cod_org_emissor;
        this.tipo_doc = tipo_doc;
        setTipo_doc(tipo_doc);
        this.data = data;
        this.num_autorizacao = num_autorizacao;
        this.data_val_ini = data_val_ini;
        this.data_val_fim = data_val_fim;
    }

    public Autorizacao() {
        

    }

    public String getTipo_doc_string() {
        if (id_autorizacao == -1) {
            tipo_doc_string = "";
        } else {
            if (tipo_doc == 0) {
                tipo_doc_string = "CNS";
            } else {
                tipo_doc_string = "CPF";
            }
        }
        return tipo_doc_string;
    }

    public String getDataString() {
        if (id_autorizacao == -1) {
            dataString = "";
        } else {
            dataString = F.dataString(data);
        }
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public void setTipo_doc_string(String tipo_doc_string) {
        this.tipo_doc_string = tipo_doc_string;
    }

    public Integer getId_autorizacao() {
        return id_autorizacao;
    }

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd() {
        return 2;
    }

    public void setId_autorizacao(Integer id_autorizacao) {
        this.id_autorizacao = id_autorizacao;
    }

    public String getAutorizador() {
        return autorizador;
    }

    public void setAutorizador(String autorizador) {
        
        this.autorizador = autorizador;
    }

    public String getCod_org_emissor() {
        return cod_org_emissor;
    }

    public void setCod_org_emissor(String cod_org_emissor) {
        this.cod_org_emissor = cod_org_emissor;
    }

    public int getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(Integer tipo_doc) {
        if (tipo_doc != 0) {
            this.tipo_doc = 1;
        }
        this.tipo_doc = tipo_doc;
    }

    public String getNum_doc() {
        return num_doc;
    }

    public void setNum_doc(String num_doc) {
        this.num_doc = num_doc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNum_autorizacao() {
        if(num_autorizacao.equals("-1")){
            num_autorizacao = "";
        }
        return num_autorizacao;
    }

    public void setNum_autorizacao(String nun_autorizacao) {
        this.num_autorizacao = nun_autorizacao;
    }

    public Date getData_val_ini() {
        return data_val_ini;
    }

    public void setData_val_ini(Date data_val_ini) {
        this.data_val_ini = data_val_ini;
    }

    public Date getData_val_fim() {
        return data_val_fim;
    }

    public void setData_val_fim(Date data_val_fim) {
        this.data_val_fim = data_val_fim;
    }

    public String getPeriodo() {
        if (id_autorizacao == -1) {
            dataString = "";
        } else {
            periodo = F.dataString(data_val_ini) + " a " + F.dataString(data_val_fim);
        }
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id_autorizacao);
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
        final Autorizacao other = (Autorizacao) obj;
        if (!Objects.equals(this.id_autorizacao, other.id_autorizacao)) {
            return false;
        }
        return true;
    }

}
