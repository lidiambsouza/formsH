package hujbb.informatica.apac.bean.dlg;

import hujbb.informatica.apac.bean.CidBean;
import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Procedimento_sus;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class DlgBuscaCidBean extends CidBean {

    private String filtro;

    private Cid cid;

    //staic
    public static List<Cid> listaCidStatica;
    public static Procedimento_sus procedimentoStatic;
    //aux
    private List<Cid> listaCidAux;//guarda os cids escolhidos
    private List<Cid> listaCidAux2;//guarda os cids relacionados ao procedimento principal
    private Procedimento_sus procedimentoAux;

    @Override
    @PostConstruct
    public void init() {

        listaCidAux2 = new ArrayList<>();
        setarAuxStatic();
        try {
            btBuscarCidDlg();
        } catch (ErroSistema ex) {
            F.setMsgErro("dlbBuscaCidBean:init:" + ex.toString());
        }

    }

    public void btBuscarCidDlg() throws ErroSistema {
        if (procedimentoAux != null) {
            setEntidade(null);
            getEntidades().clear();

            listaCidAux2 = procedimentoAux.cids();
            setEntidades(listaCidAux2);

            if (getEntidades() != null) {
                if (getEntidades().size() > 0) {
                    setEntidade(getEntidades().get(0));
                }
            }

        }

    }

    public void setarAuxStatic() {
        setarListaCidAux();//seta a lista do cids escolidoa no formulario pag 1
        setarProcedimentoAux(); //seta o procedimento principal
    }

    private void setarListaCidAux() {
        listaCidAux = listaCidStatica;
        listaCidStatica = null;
    }

    private void setarProcedimentoAux() {
        this.procedimentoAux = procedimentoStatic;
        procedimentoStatic = null;
    }

    //verifica se o cid selecionado ja foi escolhido anteriormentoe
    public boolean verificaCidEscolido(Cid c) {

        for (Cid c2 : getListaCidAux()) {
            if (c2.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public void rowSelectEventCid(SelectEvent e) throws ErroSistema {
        selecionarCidDlg((Cid) e.getObject());

    }

    public void selecionarCidDlg(Cid entidade) throws ErroSistema {

        if (entidade != null) {
            if (!entidade.equals(new Cid())) {
                if (!verificaCidEscolido(entidade)) {
                    F.fecharDlg(entidade);
                } else {
                    F.mensagem("Cid 10 já inserido!", "Selecione um cid diferente", FacesMessage.SEVERITY_WARN);
                }
            }
        }
    }

//auxiliaes
    public void filtrar(String filtro) {
        setEntidade(null);
        filtro = filtro.trim();
        if (filtro.isEmpty()) {//if 1
            setEntidades(listaCidAux2);

        } else {//else if 1
            setEntidades(new ArrayList<>());
            for (Cid c : listaCidAux2) {
                if (c.getCid().toUpperCase().contains(filtro.toUpperCase()) || c.getNome().toUpperCase().contains(filtro.toUpperCase())) {//if 2
                    getEntidades().add(c);
                }//fim if2
            }//fim for

        }//fim else if 1
        if (getEntidades().isEmpty()) {
            setEntidade(null);
        } else {
            setEntidade(getEntidades().get(0));
        }
    }

    @Override
    public List<Cid> getEntidades() {
        if (getFiltro().isEmpty()) {
            setEntidades(listaCidAux2);
        }
        return super.getEntidades();
    }

    public String getFiltro() {
        if (filtro == null) {
            filtro = "";
        }
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Cid> getListaCidAux() {
        if (listaCidAux == null) {
            listaCidAux = new ArrayList<>();
        }
        return listaCidAux;
    }

    public void setListaCidAux(List<Cid> listaCidAux) {
        this.listaCidAux = listaCidAux;
    }

}
