package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.RelatorioDAO;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.entidades.relarotios.Relatorio;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RelatorioBean implements Serializable{
 
    //usuario
    private Usuario logado;
    
    
    private String legendaDataTable;
    private String legendaColuna0;
    private String busca;
    private int valueSomTipoRel;
    
    private List<Relatorio> rel;

    @PostConstruct
    private void init() {
        valueSomTipoRel = -1;
        rel =  new ArrayList<>();
    }
    
    public void pesquisarRel(String condicao) throws ErroSistema{
        switch (valueSomTipoRel) {
            case 0: {
                rel = new RelatorioDAO().quantitativoCID(condicao);
                break;
            }
            case 1: {
                rel = new RelatorioDAO().quantitativoSETOR(condicao);
                break;
            }
            case 2: {
                rel = new RelatorioDAO().quantitativoSOLICITANTE(condicao);
                break;
            }
            case 3: {
                rel = new RelatorioDAO().quantitativopPROCEDIMENTO(condicao);
                break;
            }
            default:{
                rel =  new ArrayList<>();
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
            default:{
                legendaDataTable ="";
                break;
            }

        }
        return legendaDataTable;
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
            default:{
                legendaColuna0 ="";
                break;
            }

        }
        return legendaColuna0;
    }

    public void setLegendaColuna0(String legendaColuna0) {
        this.legendaColuna0 = legendaColuna0;
    }

    public Usuario getLogado() {
        if(logado == null){
            logado =  new Usuario();
        }
        return logado;
    }

    public void setLogado(Usuario logado) {
        this.logado = logado;
    }
    
    

}
