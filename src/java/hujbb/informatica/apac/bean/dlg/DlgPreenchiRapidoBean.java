package hujbb.informatica.apac.bean.dlg;

import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.dao.Procedimento_susDAO;
import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Paciente;
import hujbb.informatica.apac.entidades.Procedimento_sus;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class DlgPreenchiRapidoBean implements Serializable {

    private Paciente pacienteTemp;

    private Procedimento_sus procedimento;
    private Procedimento_susDAO procedimentoDAO;
    private ArrayList<SelectItem> procedimentos_susItem;

    private Cid cid;
    private CidDAO cidDAO;
    private ArrayList<SelectItem> cidItem;

    private String txtBuscaNumPront;
    private String numProntuario;
    private String buscaProcedimentoDlgPreencherRapido;
    private String vsOMProcedimentoDlgPreencherRapido;
    private String cidPrincipalDlgPreencherRapido;
    private String vsOMCidPrincipalDlgPreencherRapido;

    @PostConstruct
    public void init() {

        pacienteTemp = new Paciente();

        procedimentoDAO = new Procedimento_susDAO();
        procedimento = new Procedimento_sus();
        procedimentos_susItem = new ArrayList<>();

        cid = new Cid();
        cidDAO = new CidDAO();
        cidItem = new ArrayList<>();

        txtBuscaNumPront = "";

    }

    public void buscaParciente(String busca) throws ErroSistema {
        txtBuscaNumPront = "";
        DlgBuscaPacienteBean beanBuscaPace = new DlgBuscaPacienteBean();
        beanBuscaPace.btBuscarPacienteDlg(busca);
        if (beanBuscaPace.getEntidades().isEmpty()) {
            txtBuscaNumPront = "Nenhum registro encontrado!";
            pacienteTemp = new Paciente();
        } else {//encontrou paciente
            pacienteTemp = beanBuscaPace.getEntidades().get(0);
//            if (pacienteTemp.getData_obito() != null) {            // verifica se o paciente esrá morto
//                txtBuscaNumPront = "Paciente consta como morto!";  // se o paciente estiver morto exibe esta msg ao lado do labael do prontuario
//            }

        }
    }

    public void buscaProcedimento(String busca) throws ErroSistema {

        if (!busca.isEmpty()) {
            procedimento = new Procedimento_sus();
            procedimentos_susItem = Procedimento_sus.item("WHERE (procedimento_sus.`nome` LIKE '%" + busca + "%' OR procedimento_sus.`codigo` LIKE '" + busca + "%') AND (procedimento_sus.`dt_competencia` = " + F.getCompetencia().getCompetencia() + ") GROUP BY procedimento_sus.`codigo` ORDER BY  procedimento_sus.`nome` LIMIT 100");
            if (!procedimentos_susItem.isEmpty()) {
                selecionarProcedimento((String) procedimentos_susItem.get(0).getValue());
                buscaCidItem(getProcedimento());
            }
        }

    }

    //buscar cids item relacionado com o procedimento
    private void buscaCidItem(Procedimento_sus p) throws ErroSistema {

        getCidItem().clear();
        boolean aux = true;
        for (Cid c : p.cids()) {
            if (aux) {
                cid = c;
                aux = false;
            }//fim if 1 

            cidItem.add(new SelectItem(c.getCid(), c.getCid() + " - " + c.getNome()));
        }
    }

    //evento da selecao do procedimento no combo box
    public void selecionarProcedimento(String selecao) throws ErroSistema {
        try {
            int i = Integer.parseInt(selecao);
            procedimento = new Procedimento_sus();
            if (i != -1) {//if1
                procedimento = procedimentoDAO.buscaIdComp(selecao + "", F.getCompetencia().getCompetencia());
            }//fim if 1
        } catch (NumberFormatException e) {
            F.setMsgErro("Formulariobean:selecionarProcedimento():" + e);
        }

    }

    public void buscarCid(String busca) throws ErroSistema {
        getCidItem().clear();
        cid = new Cid();
        busca = busca.trim().toUpperCase();
        if (busca.isEmpty()) {//if 3
            buscaCidItem(getProcedimento());//busca os cids relacionado com o procedimento principal
        } else {//else if 3
            if (getProcedimento().cids().isEmpty()) {//se nao tiver nenhum cid relacionado if 0

                if (!busca.isEmpty()) {//if 1
                    cidItem = Cid.item("WHERE (cid.`nome` LIKE '%" + busca + "%') OR (cid.`cid` LIKE '%" + busca + "%') ORDER BY cid.`nome` LIMIT 10");
                    if (!cidItem.isEmpty()) {//if 2
                        selecionarCid((String) cidItem.get(0).getValue());
                    }//fim if 2
                }//fim if1

            } else {//else if 0

                for (Cid c : getProcedimento().cids()) {
                    if (c.getCid().toUpperCase().contains(busca) || c.getNome().toUpperCase().contains(busca)) {
                        cidItem.add(new SelectItem(c.getCid(), c.getCid() + " - " + c.getNome()));
                    }
                }
            }//fim else if0
        }//else3
    }

    public void btConfirm() throws ErroSistema {
        if (pacienteTemp.getData_obito() == null) {

            if (pacienteTemp.equals(new Paciente()) && procedimento.equals(new Procedimento_sus()) && cid.equals(new Cid())) {
                if (numProntuario.isEmpty() && buscaProcedimentoDlgPreencherRapido.isEmpty() && cidPrincipalDlgPreencherRapido.isEmpty()) {
                    F.mensagem("", "Preencha o campo de busca!", FacesMessage.SEVERITY_WARN);
                } else {
                    F.mensagem("", "Nenhum registro encontrado!", FacesMessage.SEVERITY_WARN);

                }

            } else {
                List<Object> l = new ArrayList<>();
                l.add(pacienteTemp);
                l.add(procedimento);
                l.add(cid);
                F.fecharDlg(l);
            }
        } else {
            F.mensagem("Paciente consta como morto!", "", FacesMessage.SEVERITY_ERROR);
        }

    }

    public void selecionarCid(String selecao) throws ErroSistema {

        cid = cidDAO.buscaId(selecao);

    }

    public String getTxtBuscaNumPront() {
        return txtBuscaNumPront;
    }

    public void setTxtBuscaNumPront(String txtBuscaNumPront) {
        this.txtBuscaNumPront = txtBuscaNumPront;
    }

    public Paciente getPacienteTemp() {
        return pacienteTemp;
    }

    public void setPacienteTemp(Paciente pacienteTemp) {
        this.pacienteTemp = pacienteTemp;
    }

    public ArrayList<SelectItem> getProcedimentos_susItem() {
        return procedimentos_susItem;
    }

    public void setProcedimentos_susItem(ArrayList<SelectItem> procedimentos_susItem) {
        this.procedimentos_susItem = procedimentos_susItem;
    }

    public Procedimento_sus getProcedimento() {
        if (procedimento == null) {
            procedimento = new Procedimento_sus();
        }
        if (procedimento.getQtd() < 1) {
            procedimento.setQtd(1);
        }
        return procedimento;
    }

    public void setProcedimento(Procedimento_sus procedimento) {
        this.procedimento = procedimento;
    }

    public Cid getCid() {
        return cid;
    }

    public void setCid(Cid cid) {
        this.cid = cid;
    }

    public ArrayList<SelectItem> getCidItem() {
        if (cidItem == null) {
            cidItem = new ArrayList<>();
        }
        return cidItem;
    }

    public void setCidItem(ArrayList<SelectItem> cidItem) {
        this.cidItem = cidItem;
    }

    public String getNumProntuario() {
        return numProntuario;
    }

    public void setNumProntuario(String numProntuario) {
        this.numProntuario = numProntuario.trim();
    }

    public String getBuscaProcedimentoDlgPreencherRapido() {
        return buscaProcedimentoDlgPreencherRapido;
    }

    public void setBuscaProcedimentoDlgPreencherRapido(String buscaProcedimentoDlgPreencherRapido) {
        this.buscaProcedimentoDlgPreencherRapido = buscaProcedimentoDlgPreencherRapido.trim();
    }

    public String getVsOMProcedimentoDlgPreencherRapido() {
        return vsOMProcedimentoDlgPreencherRapido;
    }

    public void setVsOMProcedimentoDlgPreencherRapido(String vsOMProcedimentoDlgPreencherRapido) {
        this.vsOMProcedimentoDlgPreencherRapido = vsOMProcedimentoDlgPreencherRapido;
    }

    public String getCidPrincipalDlgPreencherRapido() {
        return cidPrincipalDlgPreencherRapido;
    }

    public void setCidPrincipalDlgPreencherRapido(String cidPrincipalDlgPreencherRapido) {
        this.cidPrincipalDlgPreencherRapido = cidPrincipalDlgPreencherRapido.trim();
    }

    public String getVsOMCidPrincipalDlgPreencherRapido() {
        return vsOMCidPrincipalDlgPreencherRapido;
    }

    public void setVsOMCidPrincipalDlgPreencherRapido(String vsOMCidPrincipalDlgPreencherRapido) {
        this.vsOMCidPrincipalDlgPreencherRapido = vsOMCidPrincipalDlgPreencherRapido;
    }

}
