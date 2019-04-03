package hujbb.informatica.apac.bean;


import hujbb.informatica.apac.dao.CrudDAO;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract  class CrudBean <E ,D extends CrudDAO> implements  Serializable{//d = DAO que vms receber
    
    
   private String estadoTela = "buscar"; //insere ,edita,Busca
   private Usuario logado;
    
    private E entidade ;
    private List<E> entidades ;
    
    public  void novo(){
        entidade =  criarNovaEntidade();
        mudarParaInserir();
    }
    public void salvar(){
        try {
            getDao().salvar(entidade);//chama o metodo ja implementado na interface crudDao
            entidade = criarNovaEntidade();//depois q salvar limpa a entidade criando uma nova
            mudarParaBuscar();
            
        } catch (ErroSistema ex) {
            
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);//mensagem na tela
        }
        
    }
    public E editar(E entidade) throws ErroSistema{
        
        if(getDao().atualizar(entidade)==null){
            return null;
        }else{
            return entidade;
        }
    }
    public void deletar(E entidade){
        try {
            getDao().deletar(entidade);
            entidades.remove(entidade);
            F.mensagem("","Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            F.mensagem("Erro!",ex.getMessage(), FacesMessage.SEVERITY_ERROR);
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void buscar(String condicao){
        if(!isBusca()){
            mudarParaBuscar();
        }
        try {
            entidades = getDao().buscar(condicao);
            
        } catch (ErroSistema ex) {
            F.setMsgErro(ex.toString()+"CrudBean:buscar");
            F.mensagem("Erro! ","CrudBean:Buscar()"+ex.toString(), FacesMessage.SEVERITY_ERROR);
        }
        
    }
    
    
    public void adicionarMensagem(String msg, FacesMessage.Severity tipoErro){
       //                                  tipo de erro,mensagem ,detalhes do erro
        FacesMessage fm =  new FacesMessage(tipoErro, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);//adiciona a msg no contexto do jfs
        
    }
    
    //gets e set

    public E getEntidade() {
        if(entidade == null){
            entidade = criarNovaEntidade();
        }
        return entidade;
    }

    public void setEntidade(E entidade) {
        
        
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        if(entidades == null){
            entidades =  new ArrayList<>();
        }
        
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }

    public Usuario getLogado() {
        if(logado ==  null){
            logado  =  new Usuario();
        }
        return logado;
    }

    public void setLogado(Usuario logado) {
        this.logado = logado;
    }
   
    
    

//Responsavel por criar metodos nas classes beam
public abstract D getDao();
public abstract E criarNovaEntidade();

//Metodos para controle do estado da tela
    public  boolean  isInseri(){
        return "inserir".equals(estadoTela);
    }
    public  boolean  isEdita(){
        return "editar".equals(estadoTela);
    }
    public  boolean  isBusca(){
        return "buscar".equals(estadoTela);
    } 
    public void mudarParaInserir(){
        estadoTela = "inserir";
    }
    public void mudarParaEditar(){
        estadoTela = "editar";
    }
    public void mudarParaBuscar(){
        estadoTela = "buscar";
    }
}
