package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.AutorizacaoDAO;
import hujbb.informatica.apac.entidades.Autorizacao;
import hujbb.informatica.apac.entidades.Formulario;
import hujbb.informatica.apac.entidades.Status;
import hujbb.informatica.apac.entidades.imprimir.Pdf_apacEmitidas;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import hujbb.informatica.apac.util.relatorio.Relatorios;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class EmitidosBean extends FormularioBean {

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
    private boolean rendereBtEnviarSus;
    private ArrayList<SelectItem> status_item;
    private Date dtIni;
    private Date dtFim;
    private String tituloTabela;
    private String filtroSituacao;

    @PostConstruct
    public void init() {
        nomeAutorizador = "";
        codOrgEmissor = "";
        docAutorizador = "";
        dtAutorizacao = new Date();
        dtIniValidade = new Date();
        dtFimValidade = new Date();

        selectBooleanCheckBox_periodo = true;
        dtIni = new Date();
        dtFim = new Date();
        filtroSituacao = "";
        try {
            status_item = Status.item("");
            FabricaDeConexoes.fecharConecxao();
        } catch (ErroSistema e) {
        }
    }

    public void buscarApac() throws ErroSistema {
        String condicao = "WHERE (formulario.`data` between '" + F.dataStringBanco(dtIni) + "' AND '" + F.dataStringBanco(dtFim) + "') ";
        if (!(filtroSituacao.isEmpty() || filtroSituacao.equals("0"))) {
            condicao += " AND (formulario.`status_id_status` ='" + filtroSituacao + "' )";
        }
        List<Formulario> aux = new ArrayList<>();
        super.buscar(condicao);
        for (Formulario f : super.getEntidades()) {
            super.setEntidade(f);
            super.buscaProcedimentosForm();
            aux.add(getEntidade());
        }
        Collections.sort(aux);
        setEntidades(aux);
        FabricaDeConexoes.fecharConecxao();
    }
//botoes

    public void btEnviarSus() throws ErroSistema {

        for (int i = 0; i < getEntidades().size(); i++) {
            if (getEntidades().get(i).isChekBoxEnviar() && getEntidades().get(i).getStatus().getId_status() == 3) {// 3 impresso
                getEntidades().get(i).setStatus(new Status(4, "Enviado para o SUS"));
                getEntidades().get(i).setChekBoxEnviar(false);
                editar(getEntidades().get(i));
            }
        }
        FabricaDeConexoes.fecharConecxao();
    }

    public void btSalvar(Formulario f) throws ErroSistema {

        if (f.getAutorizacao().getNum_autorizacao().isEmpty()) {
            F.mensagem("", "Informe o número da autorização", FacesMessage.SEVERITY_ERROR);
        } else {
            //adiciona uma autorizacao

            Autorizacao autorizacao = new AutorizacaoDAO().salvar(f.getAutorizacao());
            //atualiza o formulario com a nova autorizacao
            f.setAutorizacao(autorizacao);
            f.setStatus(new Status(5, "autorizado"));
            f = editar(f);
//            log(usuario, 6);
            if (f != null) {
                for (int i = 0; i < getEntidades().size(); i++) {
                    if (f.equals(getEntidades().get(i))) {
                        getEntidades().set(i, f);
                        F.mensagem("", "Autorização registrada com sucesso!", FacesMessage.SEVERITY_INFO);
                        break;
                    }
                }
            }
        }

        FabricaDeConexoes.fecharConecxao();
    }

    public void btImprimir() throws ErroSistema {
        if (!getEntidades().isEmpty()) {
            //cria uma lista de objetos para gerar a  pdf
            List<Object> l = new ArrayList<>();
            Pdf_apacEmitidas pdf_apacEmitidas;
            int i = 0;
            for (Formulario f : getEntidades()) {
                pdf_apacEmitidas = new Pdf_apacEmitidas(
                        ++i + "",
                        f.getPaciente().getNome(),
                        f.getPaciente().getCns() + "",
                        f.getP1().getNome(),
                        mascaraCodProdedimento(f.getP1().getCodigo()),
                        "APACs emitidas no período de " + F.dataString(dtIni) + " à " + F.dataString(dtFim)
                );
                l.add(pdf_apacEmitidas);
            }
            Relatorios r = new Relatorios("/hujbb/informatica/apac/util/relatorio/cabecalhoebserh.jpg");

            r.gerarPDF(l, "apacEmitidas");
        } else {
            F.mensagem("Sem dados para imprimir!", "", FacesMessage.SEVERITY_INFO);
        }

    }
// fim botoes
    //auxiliares

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
    public String getTituloTabela() {
        tituloTabela = getEntidades().size() + " APACs período de " + F.dataString(dtIni) + " à " + F.dataString(dtFim);
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
        if (dtIni.after(dtFim)) {
            dtFim = dtIni;
        }
        this.dtIni = dtIni;
    }

    public Date getDtFim() {

        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        selectBooleanCheckBox_periodo = false;
        if (dtFim.before(dtIni)) {
            dtIni = dtFim;
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
        return filtroSituacao;
    }

    public void setFiltroSituacao(String filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }

    public String getNomeAutorizador() {
        return nomeAutorizador;
    }

    public void setNomeAutorizador(String nomeAutorizador) {
        this.nomeAutorizador = nomeAutorizador;
    }

    public String getCodOrgEmissor() {
        return codOrgEmissor;
    }

    public void setCodOrgEmissor(String codOrgEmissor) {
        this.codOrgEmissor = codOrgEmissor;
    }

    public String getDocAutorizador() {
        return docAutorizador;
    }

    public void setDocAutorizador(String docAutorizador) {
        this.docAutorizador = docAutorizador;
    }

    public Date getDtAutorizacao() {
        return dtAutorizacao;
    }

    public void setDtAutorizacao(Date dtAutorizacao) {
        this.dtAutorizacao = dtAutorizacao;
    }

    public Date getDtIniValidade() {
        return dtIniValidade;
    }

    public void setDtIniValidade(Date dtIniValidade) {
        this.dtIniValidade = dtIniValidade;
    }

    public Date getDtFimValidade() {
        return dtFimValidade;
    }

    public void setDtFimValidade(Date dtFimValidade) {
        this.dtFimValidade = dtFimValidade;
    }

    public boolean isRendereDadosDaAutorizacao() {
        if (filtroSituacao.equals("4") && !getEntidades().isEmpty()) {
            rendereDadosDaAutorizacao = true;
        } else {
            rendereDadosDaAutorizacao = false;
        }
        return rendereDadosDaAutorizacao;
    }

    public void setRendereDadosDaAutorizacao(boolean rendereDadosDaAutorizacao) {
        this.rendereDadosDaAutorizacao = rendereDadosDaAutorizacao;
    }

    public boolean isRendereBtEnviarSus() {
        if (filtroSituacao.equals("3") && !getEntidades().isEmpty()) {
            rendereBtEnviarSus = true;
        } else {
            rendereBtEnviarSus = false;
        }

        return rendereBtEnviarSus;
    }

    public void setRendereChekBoxEnviar(boolean rendereChekBoxEnviar) {
        this.rendereBtEnviarSus = rendereChekBoxEnviar;
    }

}
