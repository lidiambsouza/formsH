package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.Proc_justificativaDAO;
import hujbb.informatica.apac.entidades.Proc_justificativa;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Proc_justificativaBean extends CrudBean<Proc_justificativa, Proc_justificativaDAO>{
    private Proc_justificativaDAO dao;

    @Override
    public Proc_justificativaDAO getDao() {
        if(dao ==  null){
            dao =  new Proc_justificativaDAO();
        }
        return dao;
    }

    @Override
    public Proc_justificativa criarNovaEntidade() {
        return new Proc_justificativa();
    }
    
}
