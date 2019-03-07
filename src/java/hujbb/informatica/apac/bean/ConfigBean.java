package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.ConfigDAO;
import hujbb.informatica.apac.entidades.Config;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ConfigBean extends CrudBean<Config, ConfigDAO>{
    private ConfigDAO dao;

    @Override
    public ConfigDAO getDao() {
        if(dao ==  null){
            dao =  new ConfigDAO();
        }
        return dao;
    }

    @Override
    public Config criarNovaEntidade() {
        return new Config();
    }
    
}
