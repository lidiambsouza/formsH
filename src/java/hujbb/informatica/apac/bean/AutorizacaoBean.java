package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.AutorizacaoDAO;
import hujbb.informatica.apac.entidades.Autorizacao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AutorizacaoBean extends CrudBean<Autorizacao, AutorizacaoDAO>{
    private AutorizacaoDAO dao;

    @Override
    public AutorizacaoDAO getDao() {
        if(dao ==  null){
            dao =  new AutorizacaoDAO();
        }
        return dao;
    }

    @Override
    public Autorizacao criarNovaEntidade() {
        return new Autorizacao();
    }
    
}
