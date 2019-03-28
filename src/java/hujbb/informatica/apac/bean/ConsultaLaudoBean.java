package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.ConsultaLaudoDAO;
import hujbb.informatica.apac.dao.FormularioDAO;
import hujbb.informatica.apac.dao.UsuarioDAO;
import hujbb.informatica.apac.dao.PacienteDAO;
import hujbb.informatica.apac.entidades.Formulario;
import hujbb.informatica.apac.entidades.Paciente;
import hujbb.informatica.apac.entidades.Status;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import hujbb.informatica.apac.util.relatorio.Relatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class ConsultaLaudoBean implements Serializable {

    //usuario
    private Usuario logado;

    //registro de autorizacao
    private String nomeAutorizador;
    private String codOrgEmissor;
    private String docAutorizador;
    private Date dtAutorizacao;
    private Date dtIniValidade;
    private Date dtFimValidade;

//controle tela
    private boolean selectBooleanCheckBox_periodo;
    private boolean rendereDadosDaAutorizacao;

    private List<Formulario> forms;
    private ArrayList<SelectItem> status_item;
    private Date dtIni;
    private Date dtFim;
    private Date dtIniCriacao;
    private Date dtFimCriacao;
    private String tituloTabela;
    private String filtroSituacao;
    private String nomePaciente;
    private String numProntuario;
    private String cartSus;
    private String solicitante;
    private String paciente;
    private Usuario usuario;

    private Integer idSolicitanteTemp;

    private FormularioDAO formDAO;

    @PostConstruct
    public void init() {

        selectBooleanCheckBox_periodo = true;
        dtIni = new Date();
        dtFim = new Date();
        dtIniCriacao = new Date();
        dtFimCriacao = new Date();
        filtroSituacao = "";
        solicitante = "";
        paciente = "";
        cartSus = "";
        numProntuario = "";

        setSolicitante(getLogado().getNome());

    }

    //itens
    public List<String> nomeSolicitantes(String condicao) throws ErroSistema {

        List<String> solicitantes = new ArrayList<>();// Array de nomes
        if (condicao.equals("") || condicao.isEmpty()) {
            solicitantes.clear();
        } else {
            List<Usuario> m = new UsuarioDAO().buscar("usuario.`nome` LIKE '" + condicao + "%' ORDER BY usuario.`nome`");
//        nomeSolicitantes.add(new String("0", "Todos"));

            for (int i = 0; i < m.size(); i++) {
                if (m.get(i).getPerfil().getId_perfil() > 1 && m.get(i).getPerfil().getId_perfil() < 5) {

                    solicitantes.add(m.get(i).getNome());
                }
            }
        }

        //FabricaDeConexoes.fecharConecxao();
        return solicitantes;
    }

    public List<String> nomePacientes(String condicao) throws ErroSistema {

        List<String> pacientes = new ArrayList<>();// Array de nomes
        if (condicao.equals("") || condicao.isEmpty()) {
            pacientes.clear();
        } else {
            List<Paciente> m = new PacienteDAO().buscar("WHERE paciente.`nome` LIKE '" + condicao + "%' GROUP BY paciente.`nome`");

            for (int i = 0; i < m.size(); i++) {
                pacientes.add(m.get(i).getNome());
            }

        }

        //FabricaDeConexoes.fecharConecxao();
        return pacientes;
    }

    public void verificaPerfil(int perfil) {

        if (perfil == 3) {//3 administrador
            try {
                status_item = Status.item("");
                // //FabricaDeConexoes.fecharConecxao();
            } catch (ErroSistema e) {

            }

        } else {
            try {
                // verifica se perfil logado é solicitante ou solicitante oncologico
                status_item = Status.item("id_status = 2 OR id_status = 3");

                ////FabricaDeConexoes.fecharConecxao();
            } catch (ErroSistema ex) {
                Logger.getLogger(EmitidosBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void buscarApac() throws ErroSistema {

        String condicao = "";

        //situacao
        switch (filtroSituacao) {
            case "0": {
                condicao = "status.`id_status` <> 0 ";
                break;
            }
            case "1": {
                condicao = "status.`id_status` = 1 ";
                break;
            }
            case "2": {
                condicao = "status.`id_status` = 2 ";
                break;
            }
            case "3": {
                condicao = "status.`id_status` = 3 ";
                break;
            }
            case "4": {
                condicao = "status.`id_status` = 4 ";
                break;
            }
            case "5": {
                condicao = "status.`id_status` = 5 ";
                break;
            }
            case "10": {
                condicao = "status.`id_status` = 10 ";
                break;
            }
            case "99": {
                condicao = "status.`id_status` = 99 ";
                break;
            }
            case "-1": {
                condicao = "status.`id_status` = -1 ";
                break;
            }
            case "-9": {
                condicao = "status.`id_status` = -9 ";
                break;
            }
            default: {
                condicao = "status.`id_status` <> 0 ";
                break;
            }
        }

        /*
        //data
        if (dtIni != null && dtFim != null) {
            if (dtIni.equals(dtFim)) {

                condicao += " AND (formulario.`data` between '" + F.dataStringBanco(dtIni) + " 00:00:00' AND '" + F.dataStringBanco(dtIni) + " 23:59:59') ";
            } else {//datas diferentes
                condicao += " AND (formulario.`data` between '" + F.dataStringBanco(dtIni) + "' AND '" + F.dataStringBanco(dtFim) + " 23:59:59') ";
            }
        } else if (dtIni == null && dtFim != null) {
            condicao += " AND (formulario.`data` between '1900-01-01' AND '" + F.dataStringBanco(dtFim) + " 23:59:59') ";
        } else if (dtIni != null && dtFim == null) {
            condicao += " AND (formulario.`data` between '" + F.dataStringBanco(dtIni) + "' AND '" + F.dataStringBanco(new Date()) + " 23:59:59') ";
        }*/

        //data solicitação
        if (dtIniCriacao != null && dtFimCriacao != null) {
            if (dtIniCriacao.equals(dtFimCriacao)) {

                condicao += " AND (formulario.`data_criacao` between '" + F.dataStringBanco(dtIniCriacao) + " 00:00:00' AND '" + F.dataStringBanco(dtIniCriacao) + " 23:59:59') ";
            } else {//datas diferentes
                condicao += " AND (formulario.`data_criacao` between '" + F.dataStringBanco(dtIniCriacao) + "' AND '" + F.dataStringBanco(dtFimCriacao) + " 23:59:59') ";
            }
        } else if (dtIniCriacao == null && dtFimCriacao != null) {
            condicao += " AND (formulario.`data_criacao` between '1900-01-01' AND '" + F.dataStringBanco(dtFimCriacao) + " 23:59:59') ";
        } else if (dtIniCriacao != null && dtFimCriacao == null) {
            condicao += " AND (formulario.`data_criacao` between '" + F.dataStringBanco(dtIniCriacao) + "' AND '" + F.dataStringBanco(new Date()) + " 23:59:59') ";
        }
        
        //solicitente
        if (!(solicitante.isEmpty() || solicitante == null)) {
            condicao += " AND (solicitante.`nome` = '" + solicitante + "') ";
        }

        //paciente
        if (!(paciente.isEmpty() || paciente == null)) {
            condicao += " AND (paciente.`nome` = '" + paciente + "') ";
        }

        //n prontuario
        if (!(numProntuario.isEmpty() || numProntuario == null)) {
            condicao += " AND (paciente.`num_prontuario` = '" + numProntuario + "') ";
        }
        //n cartao sus
        if (!(cartSus.isEmpty() || cartSus == null)) {
            condicao += " AND (paciente.`cns` = '" + cartSus + "') ";
        }
        
        setForms(new ConsultaLaudoDAO().buscarLaudos(condicao));
        //adiciona os procedimentos dos laudos
        for (int i = 0; i < getForms().size(); i++) {
            getForms().get(i).buscaProcedimentosForm();
        }

        //FabricaDeConexoes.fecharConecxao();
    }

//botoes
    public void btDlgVisualizaform() {
        F.redirecionarPagina("");
    }

    public void btImprimirForm(Formulario f) throws ErroSistema {
        f = getFormDAO().buscaId(f.getId_formulario() + "");
        if (f != null) {//if1
            f.buscaProcedimentosForm();
            if (f.getStatus().getId_status() == 2) {//atualiza o status para emitido caso o status seja salvo
                f.setStatus(new Status(3, "Emitido"));
                getFormDAO().atualizar(f);
                //atualiza na tabela na tela apos a alteracao
                for (int i = 0; i < getForms().size(); i++) {
                    if (getForms().get(i).getId_formulario().equals(f.getId_formulario())) {
                        getForms().set(i, f);
                    }
                }
            }

            f.setPag2(null);//null é a flag pra buscar dados no banco 
            printFolha1(f);

        }//if1

    }

// fim botoes
    public boolean bloqueioImpressao(Formulario f) {
        //((id do usuario logado != id  do usuario do solicitante da apac) ou se o status do formulario != 3(emitido) ou !(perfil 2 solicitante ou 4 oncologico)  e nao perfil 3 adm)
        return (((getLogado().getId_usuario() != f.getSolicitante().getUsuario().getId_usuario()) || (f.getStatus().getId_status() != 3) || !(getLogado().getPerfil().getId_perfil() == 2 || getLogado().getPerfil().getId_perfil() == 4)) && (getLogado().getPerfil().getId_perfil() != 3));
    }

    public boolean bloqueioEdicao(Formulario f) {
        // ((dono da apac == usuario logado) e status = 2(salvo)  e (perfil 2 solicitante ou 4 oncologico) )        
        return !(((getLogado().getId_usuario() == f.getSolicitante().getUsuario().getId_usuario()) && (f.getStatus().getId_status() == 2) && (getLogado().getPerfil().getId_perfil() == 2 || getLogado().getPerfil().getId_perfil() == 4)) || (getLogado().getPerfil().getId_perfil() == 3));

    }

    //auxiliares
    public void printFolha1(Formulario f) {

        //id da folha 1 maior que 0 e id da folha dois menor que 1
        if (f.getId_formulario() > 0 && f.getPag2().getFormulario_id() < 1) {
            //cria uma lista de objetos para gerar a  pdf
            List<Object> l = new ArrayList<>();
            l.add(f);
            Relatorios r = new Relatorios("/hujbb/informatica/apac/util/relatorio/apac.jpg");

            r.gerarPDF(l, "report1");
        } else if (f.getPag2().getFormulario_id() > 0) {

            printFolha1e2(f);
        }

    }

    public void printFolha1e2(Formulario f) {
        if (f.getId_formulario() > 0 && f.getPag2().getFormulario_id() > 0) {

            //cria uma lista de objetos para gerar a  pdf
            List<Object> l = new ArrayList<>();
            l.add(f);
            Relatorios r = new Relatorios("/hujbb/informatica/apac/util/relatorio/apac.jpg", "/hujbb/informatica/apac/util/relatorio/apac2.jpg");

            r.gerarPDF(l, "report2");
        }
    }

    public boolean renderBtAutrizar(int status) {
        if (isRendereDadosDaAutorizacao() && status == 4) {
            return true;
        } else {
            return false;
        }
    }

    public boolean rendereChekBoxEnviar(int status) {
        if (status == 3 && filtroSituacao.equals("3")) {
            return true;
        } else {
            return false;
        }
    }

    public String mascaraCodProdedimento(String codProced) {
        String retur = "";
        for (int i = 0; i < codProced.length(); i++) {
            retur += codProced.charAt(i);
            if (i == 1 || i == 3 || i == 5) {
                retur += ".";
            }
            if (i == 8) {
                retur += "-";
            }
        }
        return retur;
    }

    //fim auxiliares
    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomePaciente() {
        if (paciente == null) {
            paciente = "";
        }
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNumProntuario() {
        return numProntuario;
    }

    public void setNumProntuario(String numProntuario) {
        this.numProntuario = numProntuario;
    }

    public String getCartSus() {
        return cartSus;
    }

    public void setCartSus(String cartSus) {
        this.cartSus = cartSus;
    }

    public String getSolicitante() {
        solicitante = logado.getNome();
        if (solicitante == null) {
            solicitante = "";
        }
        return solicitante.toUpperCase();
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;

    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getTituloTabela() {
        tituloTabela = getForms().size() + " LAUDOS PARA PROCEDIMENTOS AMBULATORIAIS ";
        return tituloTabela;
    }

    public void setTituloTabela(String tituloTabela) {
        this.tituloTabela = tituloTabela;
    }

    public boolean isSelectBooleanCheckBox_periodo() {

        return selectBooleanCheckBox_periodo;
    }

    public void setSelectBooleanCheckBox_periodo(boolean selectBooleanCheckBox_periodo) {

        this.selectBooleanCheckBox_periodo = selectBooleanCheckBox_periodo;
    }

    public Date getDtIni() {
        return dtIni;
    }

    public void setDtIni(Date dtIni) {
        selectBooleanCheckBox_periodo = false;
        if (dtIni != null && dtFim != null) {
            if (dtIni.after(dtFim)) {
                dtFim = dtIni;
            }
        }
        this.dtIni = dtIni;
    }

    public Date getDtFim() {

        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        selectBooleanCheckBox_periodo = false;
        if (dtFim != null && dtIni != null) {
            if (dtFim.before(dtIni)) {
                dtIni = dtFim;
            }
        }
        this.dtFim = dtFim;
    }

    public ArrayList<SelectItem> getStatus_item() {
        return status_item;
    }

    public void setStatus_item(ArrayList<SelectItem> status_item) {
        this.status_item = status_item;
    }

    public String getFiltroSituacao() {
        if (filtroSituacao == null) {
            filtroSituacao = "";
        }

        return filtroSituacao;
    }

    public void setFiltroSituacao(String filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }

    public boolean isRendereDadosDaAutorizacao() {
        if (filtroSituacao.equals("4") && !getForms().isEmpty()) {
            rendereDadosDaAutorizacao = true;
        } else {
            rendereDadosDaAutorizacao = false;
        }
        return rendereDadosDaAutorizacao;
    }

    public void setRendereDadosDaAutorizacao(boolean rendereDadosDaAutorizacao) {
        this.rendereDadosDaAutorizacao = rendereDadosDaAutorizacao;
    }

    public List<Formulario> getForms() {
        if (forms == null) {
            forms = new ArrayList<>();
        }
        return forms;
    }

    public void setForms(List<Formulario> forms) {
        this.forms = forms;
    }

    public Date getDtIniCriacao() {
        return dtIniCriacao;
    }

    public void setDtIniCriacao(Date dtIniCriacao) {
        selectBooleanCheckBox_periodo = false;
        if (dtIniCriacao != null && dtFimCriacao != null) {
            if (dtIniCriacao.after(dtFimCriacao)) {
                dtFimCriacao = dtIniCriacao;
            }
        }
        this.dtIniCriacao = dtIniCriacao;
    }

    public Date getDtFimCriacao() {
        return dtFimCriacao;
    }

    public void getDtFimCriacao(Date dtFimCriacao) {
        this.dtFimCriacao = dtFimCriacao;
    }

    public void setDtFimCriacao(Date dtFimCriacao) {
        selectBooleanCheckBox_periodo = false;
        if (dtFimCriacao != null && dtIniCriacao != null) {
            if (dtFimCriacao.before(dtIniCriacao)) {
                dtIniCriacao = dtFimCriacao;
            }
        }
        this.dtFimCriacao = dtFimCriacao;
    }

    private FormularioDAO getFormDAO() {
        if (formDAO == null) {
            formDAO = new FormularioDAO();
        }
        return formDAO;
    }

    public Usuario getLogado() {
        if (logado == null) {
            logado = new Usuario();
        }
        return logado;
    }

    public void setLogado(Usuario logado) {
        this.logado = logado;
    }

}
