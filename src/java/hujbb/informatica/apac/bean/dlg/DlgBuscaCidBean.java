package hujbb.informatica.apac.bean.dlg;

import hujbb.informatica.apac.bean.CidBean;
import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class DlgBuscaCidBean extends CidBean {

    private Cid cid;

    public static List<Cid> listaCidStatica;
    private List<Cid> listaCidAux;

    public void btBuscarCidDlg(String cid) throws ErroSistema {
        cid = cid.trim();
        
        setEntidade(null);
        
        if (cid.length() > 1) {
            String sql = "WHERE (cid.`nome` LIKE '%" + cid + "%') OR (cid.`cid` LIKE '" + cid + "%') ORDER BY cid.`nome` LIMIT 100";
            getEntidades().clear();
            List<Cid> cd = new CidDAO().buscar(sql);
            if (cd != null && cd.size() > 0) {
                for (int i = 0; i < cd.size(); i++) {
                    getEntidades().add(cd.get(i));
                }
            }
            if (getEntidades() != null) {
                if (getEntidades().size() > 0) {
                    setEntidade(getEntidades().get(0));
                }

            }
        } else {
            getEntidades().clear();
        }

    }

    public void setarListaCidAux() {
        listaCidAux = listaCidStatica;
        listaCidStatica = null;
    }

    //verifica se o cid selecionado ja foi escolhido anteriormentoe
    public boolean verificaCidEscolido(Cid c) {

        for (Cid c2 : listaCidAux) {
            if (c2.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public void rowSelectEventCid(SelectEvent e) {
        selecionarCidDlg((Cid) e.getObject());

    }

    public void selecionarCidDlg(Cid entidade) {
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
}
