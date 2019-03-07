package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.AuditoriaDAO;
import hujbb.informatica.apac.entidades.Auditoria;
import hujbb.informatica.apac.util.F;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AuditoriaBean extends CrudBean<Auditoria, AuditoriaDAO> {
    private AuditoriaDAO dao;
    
    
    
     public void verificaPerfil(int perfil) {
        
        if (perfil != 3) {//administrador
            F.redirecionarPagina("index.jsf");
        }

    }
    
    @Override
    public AuditoriaDAO getDao() {
        if(dao ==  null){
            dao =  new AuditoriaDAO();
        }
        return dao;    
    }

    @Override
    public Auditoria criarNovaEntidade() {
        return new Auditoria();
    }
    
}
