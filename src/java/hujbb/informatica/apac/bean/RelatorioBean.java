package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.RelatorioDAO;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.entidades.relarotios.Relatorio;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RelatorioBean implements Serializable {

    //usuario
    private Usuario logado;

    private String legendaDataTable;
    private String legendaColuna0;
    private String busca;
    private int valueSomTipoRel;
    private Date dtIni;
    private Date dtFim;
    private String filtroSituacao;

    private List<Relatorio> rel;

    @PostConstruct
    private void init() {
        valueSomTipoRel = -1;
        dtIni =  new Date();
        dtFim =  new Date();
        rel = new ArrayList<>();
        filtroSituacao="";
<<<<<<< src/java/hujbb/informatica/apac/bean/RelatorioBean.java
        somatorioApacSituacao();
=======
>>>>>>> src/java/hujbb/informatica/apac/bean/RelatorioBean.java
    }

    public void pesquisarRel(String condicao) throws ErroSistema {
        switch (valueSomTipoRel) {
            case 0: {
                rel = new RelatorioDAO().quantitativoCID(condicao,dtIni,dtFim);
                break;
            }
            case 1: {
                rel = new RelatorioDAO().quantitativoSETOR(condicao,dtIni,dtFim);
                break;
            }
            case 2: {
                if(filtroSituacao.equals("")){
                  rel = new RelatorioDAO().quantitativoSOLICITANTE(condicao,dtIni,dtFim,0);
                break;  
                }else if (filtroSituacao.equals("0")){
                    rel = new RelatorioDAO().quantitativoSOLICITANTE(condicao,dtIni,dtFim,1);
                break; 
                }else if (filtroSituacao.equals("I")){
                    rel = new RelatorioDAO().quantitativoSOLICITANTE(condicao,dtIni,dtFim,2);
                break;}
                else{
                rel = new RelatorioDAO().quantitativoSOLICITANTE(condicao,dtIni,dtFim,0);
                break;
                }
            }
            case 3: {
                rel = new RelatorioDAO().quantitativopPROCEDIMENTO(condicao,dtIni,dtFim);
                break;
            }
            default: {
                rel = new ArrayList<>();
                break;
            }
        }

    }

    public String getLegendaDataTable() {
        switch (valueSomTipoRel) {
            case 0: {
                legendaDataTable = "Quantitativo por CID";
                break;
            }
            case 1: {
                legendaDataTable = "Quantitativo por SETOR";
                break;
            }
            case 2: {
                legendaDataTable = "Quantitativo por SOLICITANTE";
                System.out.println("Total Geral" + somatorioApacSituacao());
                break;
            }
            case 3: {
                legendaDataTable = "Quantitativo por PROCEDIMENTO";
                break;
            }
            default: {
                legendaDataTable = "";
                break;
            }

        }
        return legendaDataTable;
    }
    
<<<<<<< src/java/hujbb/informatica/apac/bean/RelatorioBean.java
    public String somatorioApacSituacao(){
        int cont=0;
        for(int i = 0; i<getRel().size() ;i++ ){
            cont+=getRel().get(i).getTotal();
        }
        System.out.println(""+cont);
        return Integer.toString(cont);
        
        
        
=======
    public int somatorioApacSituacao(){
        int cont=0;
        for(int i = 0; i<getRel().size() ;i++ ){
            cont=getRel().get(i).getTotal();
        }  
        return cont;
>>>>>>> src/java/hujbb/informatica/apac/bean/RelatorioBean.java
    }

    public void setLegendaDataTable(String legendaDataTable) {
        this.legendaDataTable = legendaDataTable;
    }

    public int getValueSomTipoRel() {
        return valueSomTipoRel;
    }

    public void setValueSomTipoRel(int valueSomTipoRel) {
        this.valueSomTipoRel = valueSomTipoRel;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public List<Relatorio> getRel() {
        return rel;
    }

    public void setRel(List<Relatorio> rel) {
        this.rel = rel;
    }
    
    public String getLegendaColuna0() {
        switch (valueSomTipoRel) {
            case 0: {
                legendaColuna0 = "CID";
                break;
            }
            case 1: {
                legendaColuna0 = "SETOR";
                break;
            }
            case 2: {
                legendaColuna0 = "SOLICITANTE";
                break;
            }
            case 3: {
                legendaColuna0 = "PROCEDIMENTO";
                break;
            }
            default: {
                legendaColuna0 = "";
                break;
            }

        }
        return legendaColuna0;
    }

    public void setLegendaColuna0(String legendaColuna0) {
        this.legendaColuna0 = legendaColuna0;
    }

    public Usuario getLogado() {
        if (logado == null) {
            logado = new Usuario();
        }
        return logado;
    }

    public void setLogado(Usuario logado) {
        this.logado = logado;
    }

    public Date getDtIni() {
        return dtIni;
    }

    public void setDtIni(Date dtIni) {
        if (dtIni != null && dtFim != null) {
            if (dtIni.after(dtFim)) {
                dtFim = dtIni;
            }
        }
        this.dtIni = dtIni;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public String getFiltroSituacao() {
        return filtroSituacao;
    }

    public void setFiltroSituacao(String filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }

    
    
    public void setDtFim(Date dtFim) {
        if (dtFim != null && dtIni != null) {
            if (dtFim.before(dtIni)) {
                dtIni = dtFim;
            }
        }
        this.dtFim = dtFim;
    }

}
