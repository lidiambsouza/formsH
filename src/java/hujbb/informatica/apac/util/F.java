package hujbb.informatica.apac.util;

import hujbb.informatica.apac.dao.AuditoriaDAO;
import hujbb.informatica.apac.dao.CompetenciaDAO;
import hujbb.informatica.apac.entidades.Auditoria;
import hujbb.informatica.apac.entidades.Competencia;
import hujbb.informatica.apac.entidades.Entidade;
import hujbb.informatica.apac.entidades.Log_procedimento;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

public class F {

//    public static Usuario logado;
    public static String stringAux;
    public static int intAux;
    private static Competencia competencia;
    public static String msgErro = "";

    public static void mensagem(String titulo, String msg, FacesMessage.Severity tipoMsg) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage mensagem = new FacesMessage(tipoMsg, titulo, msg);
        context.addMessage(null, mensagem);

    }

    public static void redirecionarPagina(String pagina) {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
        } catch (IOException ex) {
            mensagem("util.util", "34:" + ex, FacesMessage.SEVERITY_FATAL);
            Logger.getLogger(F.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Date StringParaUtilDate(String data) {

        Date dt;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            dt = df.parse(data);

        } catch (ParseException e) {
            F.setMsgErro(e + ":" + data);
            return null;

        }
        return dt;

    }

    public static String dataString(Date data) {

        if (data == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return df.format(data);
    }

    public static String dataStringBanco(Date data) {
        if (data == null) {
            return "1900-01-01";
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(data);
        }
    }

    public static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -999;
        }
    }

    public static float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return -999;
        }
    }

    //calcula o fator da barra de Processo
    public static int indBarraProcesso(int valor, int total) {
        if (total == 0) {
            total = 1;
        }
        int i = valor * 100 / total;
        return i;
    }

    //recebe um util DATE e converte para sql DATE
    public static java.sql.Date sqlDate(Date data) {
        if (data == null) {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                data = dateformat.parse("01/01/1900");
            } catch (ParseException ex) {
                F.setMsgErro(ex.toString() + "F:120");
            }

        }
        return new java.sql.Date(data.getTime());
    }

    public static void log(Usuario logado, int tipo_entidade, int alvo, int procedimento, String antigo, String novo) throws ErroSistema {
        new AuditoriaDAO().salvar(
                new Auditoria(-1, null, logado, new Entidade(tipo_entidade, ""), alvo, new Log_procedimento(procedimento, ""), antigo, novo));
    }

    public static String tratarCondicaoSQL(String condicao) {
        if (condicao.isEmpty()) {
            condicao = " WHERE 1 ";
        } else {
            condicao = " WHERE " + condicao.replace("WHERE", "");
        }
        return condicao;
    }

    //EXTRAI UM ARQUIVO DE UM ZIP
    public static File getSingleFile(String filename, String zip) {

        ZipFile zipFile;

        byte[] buffer = new byte[1024];

        try {

            zipFile = new ZipFile(new File(zip));

            ZipEntry zipEntry = zipFile.getEntry(filename);

            InputStream is = zipFile.getInputStream(zipEntry);
            F.setMsgErro("6");
            //FileOutputStream outstream = new FileOutputStream(zipEntry.getName());
            FileOutputStream outstream = new FileOutputStream(new File(zipEntry.getName()));
            F.setMsgErro("7");
            int n;
            F.setMsgErro("8");
            while ((n = is.read(buffer)) > -1) {
                F.setMsgErro("9");
                outstream.write(buffer, 0, n);
                F.setMsgErro("10");
            }
            F.setMsgErro("11");
            outstream.close();
            is.close();
            zipFile.close();
            F.setMsgErro("12");
            return new File(zipEntry.getName());

        } catch (IOException ex) {
            F.setMsgErro(ex.toString());
            return null;
        } catch (NullPointerException ex) {
            F.setMsgErro(ex.toString());
            return null;
        }
    }

    public static Competencia getCompetencia() {
        if (competencia == null) {
            competencia = new Competencia();
        }
        if (competencia.equals(new Competencia())) {
            try {
                List<Competencia> competencias = new CompetenciaDAO().buscar("WHERE 1");
                if (competencias.isEmpty()) {
                    F.setCompetencia(new Competencia());
                } else {
                    F.setCompetencia(competencias.get(0));
                }
            } catch (ErroSistema ex) {
                F.setMsgErro("tempatebean 38: " + ex.toString());
            }

        }
       
        return competencia;
    }

    public static void setCompetencia(Competencia competencia) {
        
        F.competencia = competencia;
    }

    public static void setMsgErro(String msgErro) {
        F.msgErro += "\n" + hora() + msgErro + " \n";
    }

    public static String hora() {
        Date hora = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss \n");

        return sdf.format(hora);
    }

    //trata caracterers exemplo ç, á, õ, 
    public static String tratarStringBanco(String s) {

        return s.replace("ç", "c").
                replace("Ç", "C").
                replace("á", "a").
                replace("â", "a").
                replace("ã", "a").
                replace("Á", "A").
                replace("Ã", "A").
                replace("Â", "A").
                replace("é", "e").
                replace("É", "E").
                replace("Ê", "E").
                replace("í", "i").
                replace("Í", "I").
                replace("ó", "o").
                replace("õ", "o").
                replace("Ó", "O").
                replace("Ô", "O").
                replace("Õ", "O").
                replace("ú", "u").
                replace("Ú", "U").
                replace("ç", "c");
    }

    //validar cpf
    public static boolean isCPF(String cpf) {
        int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

//funcao auxiliar validar cpf
    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    //executar sql generico
    public static void executSql(String sql) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            //System.out.println(F.intAux);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            System.gc();
        } catch (SQLException ex) {
            F.setMsgErro("F.executSql ! " + ex.toString());
//            System.out.println(ex.toString());
//            System.out.println(sql);
//            System.out.println(F.intAux);
        }

    }

    public static int anoDaData(Date d) {
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        return Integer.parseInt(ano.format(d));
    }

    public static Date somarDiasData(Date data, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, dias);

        return calendar.getTime();
    }

    public static Date somarMesData(Date data, int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, m);

        return calendar.getTime();
    }
    
    public static void abrirDlgPreenchimentoRapido(String jsf, int largura, int altura, boolean moverDlg ){
         Map<String,Object> opcoes =  new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth",largura);
        opcoes.put("contentHeight", altura);
        opcoes.put("draggable", moverDlg);
        
        RequestContext.getCurrentInstance().openDialog(jsf, opcoes, null);
    }
    
    public static void fecharDlg(Object o) throws ErroSistema{
        FabricaDeConexoes.fecharConecxao();
        RequestContext.getCurrentInstance().closeDialog(o);
    }
}
