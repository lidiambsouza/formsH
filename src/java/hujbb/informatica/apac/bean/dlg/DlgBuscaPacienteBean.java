package hujbb.informatica.apac.bean.dlg;

import hujbb.informatica.apac.bean.CrudBean;
import hujbb.informatica.apac.dao.PacienteDAO;
import hujbb.informatica.apac.entidades.Paciente;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class DlgBuscaPacienteBean extends CrudBean<Paciente, PacienteDAO> implements Serializable {

    private PacienteDAO dao;
    Paciente p;

    
    
    @PostConstruct
    public void init() {

    }

    public void btBuscarPacienteDlg(String busca) throws ErroSistema {
        
        busca = busca.trim();
        setEntidade(null);
        int aux;
        if (!busca.isEmpty()) {
            String condicao = "";

            try {
                aux = Integer.parseInt(busca);
                condicao += "(p.prontuario = " + aux + ") and ep.ind_padrao = 'S' ORDER BY p.nome, ep.seqp desc LIMIT 100 ";
            } catch (NumberFormatException e) {
                condicao += "(p.nome ILIKE '" + busca + "%') and ep.ind_padrao = 'S' ORDER BY p.nome, ep.seqp desc LIMIT 100 ";
            }
            getEntidades().clear();
            List <Paciente> auxi = getDao().buscarAghuBarros(condicao);
            for(Paciente p :auxi){
               if(!getEntidades().contains(p)){
                   getEntidades().add(p);
               }
            }
              
        } else {
            getEntidades().clear();
        }
        if (getEntidades() != null) {
            if (getEntidades().size() > 0) {
                setEntidade(getEntidades().get(0));
            }
        }

    }

    public void rowSelectEvent(SelectEvent e) {

        selecionarPacienteDlg((Paciente) e.getObject());
    }

    public void selecionarPacienteDlg(Paciente entidade) {
        
        if (entidade != null) {
            if (!entidade.equals(new Paciente())) {

                if (entidade.getData_obito() == null) {
                    RequestContext.getCurrentInstance().closeDialog(entidade);
                } else {
                    F.mensagem("Paciente consta como morto!", "", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
    }

    @Override
    public PacienteDAO getDao() {
        if (dao == null) {
            dao = new PacienteDAO();
        }
        return dao;
    }

    @Override
    public Paciente criarNovaEntidade() {
        return new Paciente();
    }

    public Paciente getP() {
        return p;
    }

    public void setP(Paciente p) {
        this.p = p;
    }
    
    

}
