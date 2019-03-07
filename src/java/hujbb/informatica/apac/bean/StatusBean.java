package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.StatusDAO;
import hujbb.informatica.apac.entidades.Status;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class StatusBean extends CrudBean<Status, StatusDAO>{
    private StatusDAO dao;

    @Override
    public StatusDAO getDao() {
        if(dao ==  null){
            dao =  new StatusDAO();
        }
        return dao;
    }

    @Override
    public Status criarNovaEntidade() {
        return new Status();
    }
    
}
