package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.entidades.Cid;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CidBean extends CrudBean<Cid, CidDAO>{
    private CidDAO dao;
    
    
    @PostConstruct
    public void init(){
        
    }
    
    @Override
    public CidDAO getDao() {
        if(dao ==  null){
            dao =  new CidDAO();
        }
        return dao;
    }

    @Override
    public Cid criarNovaEntidade() {
        return new Cid();
    }
    
}
