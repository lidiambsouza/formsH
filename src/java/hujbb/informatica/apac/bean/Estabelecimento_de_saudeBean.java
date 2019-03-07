package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.Estabelecimento_de_saudeDAO;
import hujbb.informatica.apac.entidades.Estabelecimento_de_saude;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Estabelecimento_de_saudeBean extends CrudBean<Estabelecimento_de_saude, Estabelecimento_de_saudeDAO>{
    private Estabelecimento_de_saudeDAO dao;

    @Override
    public Estabelecimento_de_saudeDAO getDao() {
        if(dao ==  null){
            dao =  new Estabelecimento_de_saudeDAO();
        }
        return dao;
    }

    @Override
    public Estabelecimento_de_saude criarNovaEntidade() {
        return new Estabelecimento_de_saude();
    }
    
}
