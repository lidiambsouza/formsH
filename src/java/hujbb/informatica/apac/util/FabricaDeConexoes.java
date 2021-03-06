package hujbb.informatica.apac.util;

import hujbb.informatica.apac.entidades.Solicitante;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

public class FabricaDeConexoes {

    private static Connection conexao;
   


    //produção
//    private static final String URL_CONEXAO = "jdbc:mariadb://hhhhhhh/formsh_hujbb";
//    private static final String USUARIO = "user";
//    private static final String SENHA = "senha";
    
<<<<<<< src/java/hujbb/informatica/apac/util/FabricaDeConexoes.java
    //homologação no 100
=======
  //  homologação no 100
>>>>>>> src/java/hujbb/informatica/apac/util/FabricaDeConexoes.java
    private static final String URL_CONEXAO = "jdbc:mariadb://xxxx/formsh_hujbb_homologacao";
    private static final String USUARIO = "Algum";
    private static final String SENHA = "senha*";
    
    
<<<<<<< src/java/hujbb/informatica/apac/util/FabricaDeConexoes.java
    //desenvolvimento no 110
//    private static final String URL_CONEXAO = "jdbc:mariadb://yyyyyy/formsh_hujbb_desenvolvimento";
//    private static final String USUARIO = "algum";
//    private static final String SENHA = "senha";
    
=======
//    //desenvolvimento no 110
//    private static final String URL_CONEXAO = "jdbc:mariadb://zzzzzz/formsh_hujbb_desenvolvimento";
//    private static final String USUARIO = "algum";
//    private static final String SENHA = "senha";
//    
>>>>>>> src/java/hujbb/informatica/apac/util/FabricaDeConexoes.java
    
// 
//    
    private static Connection conexaoAghuBarros;
    private static final String URL_CONEXAO_AGHU_BARROS = "jdbc:postgresql://gggggg:6544/dbALgum";
    private static final String USUARIO_AGHU_BARROS = "user";
    private static final String SENHA_AGHU_BARROS = "senha";
    


    public static Connection getConexao() throws ErroSistema {
       
        if (conexao == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
                System.out.println("ABRIR**********");
            } catch (ClassNotFoundException ex) {
                F.mensagem("", "Driver do banco não foi encontrado", FacesMessage.SEVERITY_ERROR);
                throw new ErroSistema("Driver do banco não foi encontrado!", ex);

            } catch (SQLException ex) {
                F.mensagem("", "Não foi possivel conectar ao banco de dados !", FacesMessage.SEVERITY_ERROR);
                throw new ErroSistema("Não foi possivel conectar ao banco de dados", ex);
            }
        }else {//if1
            try {
                if(conexao.isClosed()){
                    fecharConecxao();
                    conexao =  getConexao();
                }
            } catch (SQLException ex) {
              //  System.out.println();
                F.setMsgErro(ex.toString()+":fabrica de conexoes getConexao()");
            }
        }
        
        return conexao;
    }

    public static Connection getConexaoAghuBarros() throws ErroSistema {
        if (conexaoAghuBarros == null) {
            try {
                Class.forName("org.postgresql.Driver");
                conexaoAghuBarros = DriverManager.getConnection(URL_CONEXAO_AGHU_BARROS, USUARIO_AGHU_BARROS, SENHA_AGHU_BARROS);
                System.out.println("ABRIR AGHU**********");
            } catch (ClassNotFoundException ex) {
                F.mensagem("", "Driver do banco não foi encontrado", FacesMessage.SEVERITY_ERROR);
                fecharConecxaoAghuBarros();
                return null;
            } catch (SQLException ex) {
                F.mensagem("", "Não foi possivel conectar ao banco de dados AGHU! ", FacesMessage.SEVERITY_ERROR);
                F.setMsgErro(ex.toString() + " fabrica de conexao getConectionaghu");
                fecharConecxaoAghuBarros();
                return null;
            }

        }
        return conexaoAghuBarros;
    }

    public static void fecharConecxao() throws ErroSistema {
        if (conexao != null) {
            try {
                System.out.println("FECHAR**********");
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar a conexao com o banco de dados", ex);
            }
        }
    }

    public static void fecharConecxaoAghuBarros() throws ErroSistema {
        if (conexaoAghuBarros != null) {
            try {
                System.out.println("FECHAR AGHU**********");
                conexaoAghuBarros.close();
                conexaoAghuBarros = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar a conexao com o banco de dados", ex);
            }
        }
    }

    //conexao com ad
    public static boolean conexaoLdapBarros(String login, String senha) {//

        String userName = login + "@algum.br";
        String newPassword = senha;
        boolean retorno = false;
        Hashtable authEnv = new Hashtable();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, "ldap://ffffff:pppp");
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        authEnv.put(Context.SECURITY_PRINCIPAL, userName);
        authEnv.put(Context.SECURITY_CREDENTIALS, newPassword);
        try {
            DirContext authContext = new InitialDirContext(authEnv);

            retorno = true;
        } catch (AuthenticationException authEx) {
            F.mensagem("Falha na autenticação!", "Usuário ou senha incorreto", FacesMessage.SEVERITY_ERROR);
        } catch (NamingException namEx) {
            F.mensagem("", "Falha na conexão com AD!", FacesMessage.SEVERITY_ERROR);

        }
        return retorno;
    }

    //conexao com ad EBSERH
    public static boolean conexaoLdapEbserh(String login, String senha) {//

        String userName = login + "@algum.ebserh.algo.br";
        String newPassword = senha;
        boolean retorno = false;
        Hashtable authEnv = new Hashtable();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, "ldap://kkkkk:pppp");
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        authEnv.put(Context.SECURITY_PRINCIPAL, userName);
        authEnv.put(Context.SECURITY_CREDENTIALS, newPassword);
        try {
            DirContext authContext = new InitialDirContext(authEnv);

            retorno = true;
        } catch (AuthenticationException authEx) {
            F.mensagem("Falha na autenticação!", "Usuário ou senha incorreto", FacesMessage.SEVERITY_ERROR);
        } catch (NamingException namEx) {
            F.mensagem("", "Falha na conexão com AD!", FacesMessage.SEVERITY_ERROR);

        }
        return retorno;
    }

    public static boolean conexaoLdapBettina(String login, String senha) {//

        String userName = login + "@balgum.ufpa.fim";
        String newPassword = senha;
        boolean retorno = false;
        Hashtable authEnv = new Hashtable();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, "ldap://vvvvvv:pppp");
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        authEnv.put(Context.SECURITY_PRINCIPAL, userName);
        authEnv.put(Context.SECURITY_CREDENTIALS, newPassword);
        try {
            DirContext authContext = new InitialDirContext(authEnv);

            retorno = true;
        } catch (AuthenticationException authEx) {
            F.mensagem("Falha na autenticação!", "Usuário ou senha incorreto", FacesMessage.SEVERITY_ERROR);
        } catch (NamingException namEx) {
            F.mensagem("", "Falha na conexão com AD!", FacesMessage.SEVERITY_ERROR);

        }
        return retorno;
    }

    //conexao com ad
    public static boolean LoginLdapBarros(String login) {//

        String userName = login + "@algo.br";
        // String newPassword = senha;
        boolean retorno = false;
        Hashtable authEnv = new Hashtable();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, "ldap://bbbbb:pppp");
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        authEnv.put(Context.SECURITY_PRINCIPAL, userName);
        //  authEnv.put(Context.SECURITY_CREDENTIALS, "senhaqualquer");
        try {
            DirContext authContext = new InitialDirContext(authEnv);

            retorno = true;
        } catch (AuthenticationException authEx) {
            F.mensagem("Falha na autenticação!" + authEx.toString(), "Usuário ou senha incorreto", FacesMessage.SEVERITY_ERROR);
        } catch (NamingException namEx) {
            F.mensagem("", "Falha na conexão com AD!", FacesMessage.SEVERITY_ERROR);

        }

        return retorno;
    }

    //busca os dados do usuario no ad
    public static Solicitante buscarLdap(String login, String ou) {//ou==pasta dentro do ad
        String userName = login + "@algo.br";
        String senha = "******";
        String SEARCHBASE = "ou=base,dc=base, dc=algo";
        DirContext dircontext = connect();

        Solicitante solicitante = new Solicitante();
        boolean encontrou = false;
        if (!ou.isEmpty()) {
            SEARCHBASE = ou + "," + SEARCHBASE;
        }

        Attributes attributes = new BasicAttributes(Boolean.FALSE);

        // attributes.put(new BasicAttribute("sAMAccountName", login));
        // attributes.put(new BasicAttribute("userPassword", senha));
        String atributosRetorno[] = new String[]{"cn", "cpf", "sAMAccountName"};

        try {

            NamingEnumeration resultado = dircontext.search(SEARCHBASE, attributes, atributosRetorno);

            while (resultado.hasMore()) {

                SearchResult sr = (SearchResult) resultado.next();
                if (sr.getName().contains("OU=")) {

                    solicitante = buscarLdap(login, sr.getName());
                    if (solicitante != null) {
                        return solicitante;
                    }
                }

                Attributes atributos = sr.getAttributes();

                NamingEnumeration todosAtributos = atributos.getAll();

                while (todosAtributos.hasMore()) {

                    Attribute attrib = (Attribute) todosAtributos.next();
                    String nomeAtributo = attrib.getID();

                    for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
                        String valor = e.next().toString();

                        if (nomeAtributo.equals("cn")) {
                            solicitante.setNome(valor);
                            solicitante.getUsuario().setNome(valor);
                        }
                        if (nomeAtributo.equals("cpf")) {
                            solicitante.setCpf(valor);

                        }
                        if (nomeAtributo.equals("sAMAccountName")) {
                            if (login.equals(valor)) {
                                solicitante.getUsuario().setLogin(valor);
                                encontrou = true;
                            }
                        }

                    }
                }

                if (encontrou) {

                    return solicitante;
                }
            }
            return null;
        } catch (NamingException e) {
            F.setMsgErro(e.toString() + "buscarLdap()");
            return null;
        }
    }

    //busca os dados do usuario no ad ebserh hujbb
    public static Solicitante buscarLdapEbserh(String login, String ou) {//ou==pasta dentro do ad

        String userName = login + "@algo.ebserh.fim.br";
        String senha = "******";
        String SEARCHBASE = "OU=Usuarios,OU=base,OU=base2,OU=base3,DC=fim1,DC=fim2,DC=fim3,DC=br";

        DirContext dircontext = connectEbserh();
        Solicitante solicitante = new Solicitante();
        boolean encontrou = false;
        if (!ou.isEmpty()) {
            SEARCHBASE = ou + "," + SEARCHBASE;
        }

        Attributes attributes = new BasicAttributes(Boolean.FALSE);

        // attributes.put(new BasicAttribute("sAMAccountName", login));
        // attributes.put(new BasicAttribute("userPassword", senha));
        String atributosRetorno[] = new String[]{"cn", "employeeID", "sAMAccountName"};

        try {

            NamingEnumeration resultado = dircontext.search(SEARCHBASE, attributes, atributosRetorno);

            while (resultado.hasMore()) {

                SearchResult sr = (SearchResult) resultado.next();
                if (sr.getName().contains("OU=")) {
                    solicitante = buscarLdapEbserh(login, sr.getName());
                    if (solicitante != null) {
                        return solicitante;
                    }
                }

                Attributes atributos = sr.getAttributes();

                NamingEnumeration todosAtributos = atributos.getAll();

                while (todosAtributos.hasMore()) {

                    Attribute attrib = (Attribute) todosAtributos.next();
                    String nomeAtributo = attrib.getID();

                    for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
                        String valor = e.next().toString();

                        if (nomeAtributo.equals("cn")) {
                            solicitante.setNome(valor);
                            solicitante.getUsuario().setNome(valor);
                        }
                        if (nomeAtributo.equals("employeeID")) {
                            solicitante.setCpf(valor);
                        }
                        if (nomeAtributo.equals("sAMAccountName")) {
                            if (login.equals(valor)) {
                                solicitante.getUsuario().setLogin(valor);
                                //System.out.println(encontrou+"********");
                                encontrou = true;
                            }
                        }
//                        System.out.println("***"+solicitante);
                    }
                }

                if (encontrou) {
                    return solicitante;
                } 
            }
            return null;
        } catch (NamingException e) {
            F.setMsgErro(e.toString() + "buscarLdapEbserh()");
            return null;
        }
    }

    //busca os dados do usuario no ad ebserh hubfs
    public static Solicitante buscarLdapEbserhHubfs(String login, String ou) {//ou==pasta dentro do ad

        String userName = login + "@base.fim.br";
        String senha = "******";
        String SEARCHBASE = "OU=Usuarios,OU=base,OU=base2,OU=base3,DC=fim1,DC=fim2,DC=fim3,DC=br";

        DirContext dircontext = connectEbserh();
        Solicitante solicitante = new Solicitante();
        boolean encontrou = false;
        if (!ou.isEmpty()) {
            SEARCHBASE = ou + "," + SEARCHBASE;
        }

        Attributes attributes = new BasicAttributes(Boolean.FALSE);

        // attributes.put(new BasicAttribute("sAMAccountName", login));
        // attributes.put(new BasicAttribute("userPassword", senha));
        String atributosRetorno[] = new String[]{"cn", "employeeID", "sAMAccountName"/*,"title"*/};

        try {

            NamingEnumeration resultado = dircontext.search(SEARCHBASE, attributes, atributosRetorno);

            while (resultado.hasMore()) {

                SearchResult sr = (SearchResult) resultado.next();
                if (sr.getName().contains("OU=")) {

                    solicitante = buscarLdapEbserhHubfs(login, sr.getName());
                    if (solicitante != null) {
                        return solicitante;
                    }
                }

                Attributes atributos = sr.getAttributes();

                NamingEnumeration todosAtributos = atributos.getAll();

                while (todosAtributos.hasMore()) {

                    Attribute attrib = (Attribute) todosAtributos.next();
                    String nomeAtributo = attrib.getID();

                    for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
                        String valor = e.next().toString();

                        if (nomeAtributo.equals("cn")) {
                            solicitante.setNome(valor);
                            solicitante.getUsuario().setNome(valor);
                        }
                        if (nomeAtributo.equals("employeeID")) {
                            solicitante.setCpf(valor);
                        }
                        if (nomeAtributo.equals("sAMAccountName")) {
                            if (login.equals(valor)) {
                                solicitante.getUsuario().setLogin(valor);
                                //System.out.println(encontrou+"********");
                                encontrou = true;
                            }
                        }
                        /*if (nomeAtributo.equals("title")) {//cbo
                                solicitante.getUsuario().getCbo().setNome(valor);
                        }*/
                                                
//                        System.out.println("***"+solicitante);
                    }
                }

                if (encontrou) {

                    return solicitante;
                }
            }
            return null;
        } catch (NamingException e) {
            F.setMsgErro(e.toString() + "buscarLdapEbserh()");
            return null;
        }
    }

    //ad hujbb
    private static DirContext connect() {
        String USER = "user@base.br";
        String PASSWD = "senha";
        String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
        String LDAP_SERVIDOR = "ldap://yyyyyyy:pppp";
        String TYPE_CONNECTION = "simple";

        Hashtable authEnv = new Hashtable(11);
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
        authEnv.put(Context.PROVIDER_URL, LDAP_SERVIDOR);
        authEnv.put(Context.SECURITY_AUTHENTICATION, TYPE_CONNECTION);
        authEnv.put(Context.SECURITY_PRINCIPAL, USER);
        authEnv.put(Context.SECURITY_CREDENTIALS, PASSWD);
        try {
            DirContext context = new InitialDirContext(authEnv);

            return context;
        } catch (Exception e) {
            F.setMsgErro("falha conexão LDAP " + e.toString());
            return null;
        }
    }

    //ad ebserh
    private static DirContext connectEbserh() {
        String USER = "user@base.fim.br";
        String PASSWD = "senha";
        String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
        String LDAP_SERVIDOR = "ldap://xxxxxxxx:pppp";
        String TYPE_CONNECTION = "simple";

        Hashtable authEnv = new Hashtable(11);
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
        authEnv.put(Context.PROVIDER_URL, LDAP_SERVIDOR);
        authEnv.put(Context.SECURITY_AUTHENTICATION, TYPE_CONNECTION);
        authEnv.put(Context.SECURITY_PRINCIPAL, USER);
        authEnv.put(Context.SECURITY_CREDENTIALS, PASSWD);
        try {
            DirContext context = new InitialDirContext(authEnv);

            return context;
        } catch (Exception e) {
           F.setMsgErro("falha conexão LDAP " + e.toString());
            return null;
        }
    }

    public static void commitFalse() throws ErroSistema {
        try {
            getConexao().setAutoCommit(false);
        } catch (SQLException ex) {
            F.setMsgErro("Fabrica:143" + ex.toString());
        }
    }

    public static void commit() throws ErroSistema {
        try {
            getConexao().commit();
            getConexao().setAutoCommit(true);
        } catch (SQLException ex) {
            F.setMsgErro("Fabrica:151" + ex.toString());
        }
    }

}
