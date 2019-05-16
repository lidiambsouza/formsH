package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.RelatorioDAO;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.entidades.relarotios.Relatorio;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int filtroSituacao;

    private List<Relatorio> rel;

    @PostConstruct
    private void init() {
        valueSomTipoRel = 2;
        dtIni =  new Date();
        dtFim =  new Date();
        rel = new ArrayList<>();
        try {
            pesquisarRel("");
        } catch (ErroSistema ex) {
            F.setMsgErro("relatorio Bean:init:"+ex.toString());
        }
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
                rel = new RelatorioDAO().quantitativoSOLICITANTE(condicao, dtIni, dtFim, filtroSituacao);
                if(rel!=null && !rel.isEmpty()){
                    rel.sort(new Comparator<Relatorio> () {
                        @Override
                        public int compare(Relatorio o1, Relatorio o2) {
                            if(o1.getTotal()<o2.getTotal()){
                                return 1;
                            }else if(o1.getTotal()==o2.getTotal()){
                                return 0;
                            }else{
                                return -1;
                            }
                        }
                    });
                }
                break;
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
    
    public int somatorioApacSituacao(){
        int cont=0;
        for(int i = 0; i<getRel().size() ;i++ ){
            cont+=getRel().get(i).getTotal();
        }  
        return cont;
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

    public int getFiltroSituacao() {
        return filtroSituacao;
    }

    public void setFiltroSituacao(int filtroSituacao) {
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
