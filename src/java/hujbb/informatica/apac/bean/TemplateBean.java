package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.CompetenciaDAO;
import hujbb.informatica.apac.entidades.Competencia;
import hujbb.informatica.apac.entidades.Formulario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TemplateBean implements Serializable {
    
    private int acao = 3;//1 insere 2 edita 3 busca 4 autorizar
    private boolean rendere_formulario_novo;
    private boolean rendere_formulario_registrarAutorizacao;
    private boolean rendere_formulario_buscar;
    private boolean rendere_formulario_editar;
    private boolean rendere_usuario;
    private boolean rendere_log;
    private boolean rendere_procedimento_buscar;
    private boolean rendere_log_erro;

  
    
    private Competencia competencia;
    //variavel temporaria para a tela de busca e edicao 
    private Formulario formularioTemp;
    
    @PostConstruct
    public void init() {
        todosRendereFalse();
        setCompetencia(null);
    }
    
    private void todosRendereFalse() {
        rendere_formulario_novo = false;
        rendere_formulario_registrarAutorizacao = false;
        rendere_formulario_buscar = false;
        rendere_formulario_editar = false;
        rendere_usuario = false;
        rendere_log = false;
        rendere_procedimento_buscar=false;
        rendere_log_erro = false;
    }
    
    public void renderizarMenu(int perfil) {
        
        todosRendereFalse();
        
        switch (perfil) {
            case 0: {//ADMINISTRATIVO FINANCEIRO
                break;
            }
            case 1: {//ADMINISTRATIVO
                rendere_formulario_registrarAutorizacao = true;
                rendere_formulario_buscar = true;
                rendere_formulario_editar = true;
                
                break;
            }
            case 2: {//SOLICITANTE
                rendere_formulario_novo = true;
                rendere_formulario_buscar = true;
                rendere_formulario_editar = true;
                break;
            }
            case 3: {//ADMINISTRADOR
                rendere_formulario_novo = true;
                rendere_formulario_registrarAutorizacao = true;
                rendere_formulario_buscar = true;
                rendere_formulario_editar = true;
                rendere_usuario = true;
                rendere_log = true;
                rendere_procedimento_buscar = true;
                rendere_log_erro = true;
                break;
            }
            case 4: {//SOLICITANTE AC
                rendere_formulario_novo = true;
                rendere_formulario_buscar = true;
                rendere_formulario_editar = true;
                break;
            }
        }
    }

    //menu
    public void formulario_registrarAutorizacao() {
        acao = 4;
        F.redirecionarPagina("formulario.jsf");
    }
    
    public void formulario_buscar() {
        acao = 3;
        F.redirecionarPagina("formulario.jsf");
    }
    
    public void formulario_editar() {
        acao = 2;
        F.redirecionarPagina("formulario_editar.jsf");
    }
    
    public void formulario_novo() {
        acao = 1;
        F.redirecionarPagina("formulario.jsf");
    }
    
    public int getAcao() {
        return acao;
    }
    
    public void setAcao(int acao) {
        this.acao = acao;
    }
    
    public boolean isRendere_formulario_novo() {
        return rendere_formulario_novo;
    }
    
    public void setRendere_formulario_novo(boolean rendere_formulario_novo) {
        this.rendere_formulario_novo = rendere_formulario_novo;
    }
    
    public boolean isRendere_formulario_registrarAutorizacao() {
        return rendere_formulario_registrarAutorizacao;
    }
    
    public void setRendere_formulario_registrarAutorizacao(boolean rendere_formulario_registrarAutorizacao) {
        this.rendere_formulario_registrarAutorizacao = rendere_formulario_registrarAutorizacao;
    }
    
    public boolean isRendere_formulario_buscar() {
        return rendere_formulario_buscar;
    }
    
    public void setRendere_formulario_buscar(boolean rendere_formulario_buscar) {
        this.rendere_formulario_buscar = rendere_formulario_buscar;
    }
    
    public boolean isRendere_formulario_editar() {
        return rendere_formulario_editar;
    }
    
    public void setRendere_formulario_editar(boolean rendere_formulario_editar) {
        this.rendere_formulario_editar = rendere_formulario_editar;
    }
    
    public boolean isRendere_usuario() {
        return rendere_usuario;
    }
    
    public void setRendere_usuario(boolean rendere_usuario) {
        this.rendere_usuario = rendere_usuario;
    }
      public boolean isRendere_log_erro() {
        return rendere_log_erro;
    }

    public void setRendere_log_erro(boolean rendere_log_erro) {
        this.rendere_log_erro = rendere_log_erro;
    }
    
    public boolean isRendere_log() {
        return rendere_log;
    }
    
    public void setRendere_log(boolean rendere_log) {
        this.rendere_log = rendere_log;
    }

    public boolean isRendere_procedimento_buscar() {
        return rendere_procedimento_buscar;
    }

    public void setRendere_procedimento_buscar(boolean rendere_procedimento_buscar) {
        this.rendere_procedimento_buscar = rendere_procedimento_buscar;
    }

    public Formulario getFormularioTemp() {
        return formularioTemp;
    }

    public void setFormularioTemp(Formulario formularioTemp) throws ErroSistema {
        this.formularioTemp = formularioTemp;
    }
    
    
    
    
    public Competencia getCompetencia() {
        
        return competencia;
    }
    
    public void setCompetencia(Competencia competencia) {
         if(competencia ==  null){
             competencia =  F.getCompetencia();
         }
        
        this.competencia = competencia;
    }
//funcoes

//fim funcoes
}
