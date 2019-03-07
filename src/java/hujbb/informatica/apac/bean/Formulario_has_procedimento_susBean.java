package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.Formulario_has_procedimento_susDAO;
import hujbb.informatica.apac.entidades.Formulario_has_procedimento_sus;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class Formulario_has_procedimento_susBean extends CrudBean<Formulario_has_procedimento_sus, Formulario_has_procedimento_susDAO>{
    private Formulario_has_procedimento_susDAO dao;

    @Override
    public Formulario_has_procedimento_susDAO getDao() {
        if(dao ==  null){
            dao =  new Formulario_has_procedimento_susDAO();
        }
        return dao;
    }

    @Override
    public Formulario_has_procedimento_sus criarNovaEntidade() {
        return new Formulario_has_procedimento_sus();
    }
    
}
