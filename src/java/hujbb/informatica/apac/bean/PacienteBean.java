package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.PacienteDAO;
import hujbb.informatica.apac.entidades.Paciente;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PacienteBean extends CrudBean<Paciente, PacienteDAO>{
    private PacienteDAO dao;

    @Override
    public PacienteDAO getDao() {
        if(dao ==  null){
            dao =  new PacienteDAO();
        }
        return dao;
    }

    @Override
    public Paciente criarNovaEntidade() {
        return new Paciente();
    }
    
}
