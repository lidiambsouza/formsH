package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.UsuarioDAO;
import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsuarioBean extends CrudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioLogado = new Usuario();
    private String labelLogin;
    
    

    public void verificaUsuLog() {
        if (usuarioLogado == null || !usuarioLogado.isLogado()) {//nao ta logado
            try {
                FabricaDeConexoes.fecharConecxao();
            } catch (ErroSistema ex) {
                F.setMsgErro(ex.toString() + "usuariobean:26");
            }
            F.redirecionarPagina("telalogin.jsf");
        } else {
            try {
                FabricaDeConexoes.fecharConecxao();
            } catch (ErroSistema ex) {
                F.setMsgErro(ex.toString() + "usuariobean:31");
            }
            setLogado(usuarioLogado) ;
        }

    }

    public void sair() {
        usuarioLogado = new Usuario();
        labelLogin = "";
        F.setCompetencia(null);
        try {
            FabricaDeConexoes.fecharConecxao();
        } catch (ErroSistema ex) {
            F.setMsgErro(ex.toString() + "usuariobean:Sair()");
        }
        F.redirecionarPagina("telalogin.jsf");

    }

    public void initTelaLogin() {
        usuarioLogado = new Usuario();
        labelLogin = "";
        F.setCompetencia(null);
        try {
            FabricaDeConexoes.fecharConecxao();
        } catch (ErroSistema ex) {
            F.setMsgErro(ex.toString() + "usuariobean:Sair()");
        }

    }

    //logar ad ebserh
    public void logar() throws ErroSistema {
       
        Usuario u;
        /// se é o usuario padrao admin
        if (usuarioLogado.getLogin().equals("admin")) {//if 1
            //verificas se existe pelo menos 1 usuario com perfil adm no banco
            if (getDao().buscar("usuario.`perfil` =3 AND usuario.`ativo` = 1 LIMIT 1").isEmpty()) {//if2
                
                u = new Usuario(0, "admin", "admin", "admin", new Setor(), new Perfil(3, ""), 1, new Cbo(), new Date());

                u.setLogado(true);
                setUsuarioLogado(u);
                F.redirecionarPagina("index.jsf");

            }else {//if2 c
                        usuarioLogado.setLogado(false);
                        usuarioLogado.setSenha("");
                        labelLogin = "Já existe um usuário com perfil adm ativo!";
                    }//fim if2
        } else {//else if 1

            labelLogin = "";
            buscar("WHERE login = '" + usuarioLogado.getLogin() + "'");
            try {
                FabricaDeConexoes.fecharConecxao();
            } catch (ErroSistema ex) {
                F.setMsgErro(ex.toString() + "usuariobean:51");
            }

            if (getEntidades().size() > 0) {//verifica se o usuario esta no banco local  if3
                
                u = getEntidades().get(0);
                //se o usuario for (!= 0 ->)ativo
                if (u.getAtivo() != 0) {

                    if (FabricaDeConexoes.conexaoLdapEbserh(usuarioLogado.getLogin(), usuarioLogado.getSenha())) {//verifica no ad if4
                        u.setLogado(true);
                        setUsuarioLogado(u);
                        F.redirecionarPagina("index.jsf");
                        
                    } else {
                        usuarioLogado.setLogado(false);
                        usuarioLogado.setSenha("");
                        labelLogin = "Usuário ou senha incorreto!";
                    }//fim if4
                } else {
                    labelLogin = "Usuário inativo!";

                }
            } else {

                labelLogin = "Usuário não cadastrado!";

            }//fim if 1
        }

    }

//    ad hujbb
//    public void logar() {
//        labelLogin = "";
//        buscar("WHERE login = '" + usuarioLogado.getLogin() + "'");
//        try {
//            FabricaDeConexoes.fecharConecxao();
//        } catch (ErroSistema ex) {
//            F.setMsgErro(ex.toString() + "usuariobean:51");
//        }
//        Usuario u;
//
//        if (getEntidades().size() > 0) {//verifica se o usuario esta no banco local  if1
//            u = getEntidades().get(0);
//            //se o usuario for (!= 0 ->)ativo
//            if (u.getAtivo() != 0) {
//
//                if (FabricaDeConexoes.conexaoLdapBarros(usuarioLogado.getLogin(), usuarioLogado.getSenha())) {//verifica no ad if2
//                    u.setLogado(true);
//                    setUsuarioLogado(u);
//                    F.redirecionarPagina("index.jsf");
//                } else {
//                    usuarioLogado.setLogado(false);
//                    usuarioLogado.setSenha("");
//                    labelLogin = "Usuário ou senha incorreto!";
//                }//fim if2
//            }else{
//            labelLogin = "Usuário inativo!";
//                
//            }
//        } else {
//
//            labelLogin = "Usuário não cadastrado!";
//
//        }//fim if 1
//
//    }
    public void logarBDLocal() {

        buscar("WHERE login = '" + usuarioLogado.getLogin() + "'");
        try {
            FabricaDeConexoes.fecharConecxao();
        } catch (ErroSistema ex) {
            F.setMsgErro(ex.toString() + "usuariobean:82");
        }
        Usuario u;
        if (getEntidades().size() > 0) {
            u = getEntidades().get(0);
            if (u.getSenha().equals(usuarioLogado.getSenha())) {//senhas correpondem
                u.setLogado(true);
                setUsuarioLogado(u);

                F.redirecionarPagina("index.jsf");
            } else {
                labelLogin = "Senha incorreta!";
                usuarioLogado.setSenha("");
            }
        } else {
            labelLogin = "Usuário nao cadastrado!";
        }
    }

    public String getLabelLogin() {
        return labelLogin;
    }

    public void setLabelLogin(String labelLogin) {
        this.labelLogin = labelLogin;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void fecharConexao() throws ErroSistema {
        FabricaDeConexoes.fecharConecxao();
    }

    @Override
    public UsuarioDAO getDao() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario criarNovaEntidade() {
        return new Usuario();
    }

}
