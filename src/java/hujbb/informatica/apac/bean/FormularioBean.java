package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.bean.dlg.DlgBuscaCidBean;
import hujbb.informatica.apac.dao.AutorizacaoDAO;
import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.dao.Estabelecimento_de_saudeDAO;
import hujbb.informatica.apac.dao.FormularioDAO;
import hujbb.informatica.apac.dao.Formulario_f2DAO;
import hujbb.informatica.apac.dao.Formulario_has_procedimento_susDAO;
import hujbb.informatica.apac.dao.PacienteDAO;
import hujbb.informatica.apac.dao.Procedimento_susDAO;
import hujbb.informatica.apac.dao.SolicitanteDAO;
import hujbb.informatica.apac.entidades.Autorizacao;
import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Estabelecimento_de_saude;
import hujbb.informatica.apac.entidades.Finalidade_radio_f2;
import hujbb.informatica.apac.entidades.Formulario;
import hujbb.informatica.apac.entidades.Formulario_f2;
import hujbb.informatica.apac.entidades.Formulario_has_procedimento_sus;
import hujbb.informatica.apac.entidades.Paciente;
import hujbb.informatica.apac.entidades.Procedimento_sus;
import hujbb.informatica.apac.entidades.Solicitante;
import hujbb.informatica.apac.entidades.Status;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import hujbb.informatica.apac.util.relatorio.Relatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class FormularioBean extends CrudBean<Formulario, FormularioDAO> implements Serializable {

    private Usuario logado;
    private FormularioDAO formularioDAO;

    private Autorizacao autorizacao;

    private String estabelecimentoSelecionado = "-999";
    private ArrayList<SelectItem> estabelecimentos = new ArrayList<>();
    private String pacienteSelecionado = "-999";
    private ArrayList<SelectItem> pacientesAghuBarros = new ArrayList<>();

    private ArrayList<SelectItem> procedimentos_susItem = new ArrayList<>();
    private ArrayList<SelectItem> tipoDocItem = new ArrayList<>();
    private List<Procedimento_sus> procedimentos_sus = new ArrayList<>();

    private ArrayList<SelectItem> cids = new ArrayList<>();

    private Paciente pacienteTemp;

    //variaveis para tela
    private String txtBuscaNumPront;
    private String valueBtAddCid;
    private String valueBtAddProcedimento;
    private String valueBtAddPaciente;
    private String valueBtPreencPag2;
    private String legend_fsMaster;

    private String vsOMBuscaCidPag2;
    private String vitBuscaProcedimento;
    private String somProcedDlgPreencRapido;
    private String vsomProcedimento;///dlg add procedimento
    private String somVarPaciente;///dlg busca paciente nome

    private String scbmItem0;
    private String scbmItemI;
    private String scbmItemII;
    private String scbmItemIII;
    private String scbmItemVI;

    private String redblackcamp18;
    private String redblackcamp36;
    private String redblackcamp40;
    private String redblackcamp56;
    private String redblackcamp58;
    private String redblackcamp59;
    private String redblackcamp60;
    private String redblackcamp61;
    private String redblackcamp62;
    private String redblackcamp63;
    private String redblackcamp64;
    private String redblackcamp66;
    private String redblackcamp67;
    private String redblackcamp68;
    private String redblackcamp69;
    private String redblackcamp70;
    private String redblackcamp71;
    private String redblackcamp74;
    private String redblackcamp75;
    private String redblackcamp77;
    private String redblackcamp78;
    private String redblackcamp79;
    private String redblackcamp80;
    private String redblackcamp81;
    private String redblackcamp82;
    private String redblackcamp83;

    private boolean dlgBuscaPaciVisible;
    private boolean disableBtSalvar;
    private boolean renderePag1;
    private boolean rendereBtPreencPag2;
    private boolean rendereBtInformAutorizacao;
    private boolean renderebtEditarFsAutorizacao;
    private boolean renderebtAddCid;
    private boolean renderebtAlterarData;
    private boolean renderebtImprimir;
    private boolean renderebtImprimir2;
    private boolean renderebtBtSalvar;
    private boolean renderebtBtSalvar2;
    private boolean renderebtBtPreenchimentoRapido;
    private boolean rendereMsgNaoEncontrado;
    private boolean rendereFsRadioterapiapag2;
    private boolean rendereBtSalvarAlteracao;
    private boolean renderMsgAddProcBlank;
    private boolean somenteLeituraPag2;
    private boolean disableBtSalvarDlgAutorizacao;
    private boolean disabledBtPreencPag2;
    private boolean flagSalvarApenasPag1;

    private int itQuantidadeDlgPreencherRapido_qtd;
    private int itQuantidadeDlgAddProcedimento_qtd;
    private Procedimento_sus procedimentosTemp;

    //variaveis para adicionar cids
    private Cid cidTemp;

    private String qtdProcedimentoSelecionado;

    //variaveis extras
    private int acao;

    private Boolean IsPreenchimentoRapido;
    private Date hJ = new Date(); /// data de hoje
    private Date hJtil120 = new Date();

    @PostConstruct
    private void init() {

        //seta o staus do novo formulario como aberto
        this.IsPreenchimentoRapido = false;
        Status status = new Status();
        status.setId_status(1);
        getEntidade().setStatus(status);
        autorizacao = new Autorizacao();
        getEntidade().setAutorizacao(autorizacao);//seta a autorizacao

        Estabelecimento_de_saude es = new Estabelecimento_de_saude();
        getEntidade().setEstabelecimento_de_saude_executante(es);//seta o estabelecimento de saude executande com o padrao nao informado
        try {//busca os dados do barros barreto pra setar como estabelecimento de saude solicitante padrao no formulario
            List<Estabelecimento_de_saude> les = new Estabelecimento_de_saudeDAO().buscar("WHERE `id_estabelecimento_de_saude` = 1");

            if (les.size() > 0) {
                es = les.get(0);
            } else {
                F.mensagem("Atenção", "Não foram encontrados dados dos estabelecimentos de saúde no banco de dados!", FacesMessage.SEVERITY_WARN);
            }
            setEstabelecimentos(null);
        } catch (ErroSistema ex) {
            F.setMsgErro(ex.toString());
            es = new Estabelecimento_de_saude();
        }
        getEntidade().setEstabelecimento_de_saude_solicitante(es);
        procedimentosTemp = new Procedimento_sus();

        cidTemp = new Cid();

        //solicitante
        //seta a data da solicitacao com a data atual por padrão
        getEntidade().setData(new Date());
        dlgBuscaPaciVisible = false;

        disableBtSalvar = false;
        rendereBtInformAutorizacao = false;
        rendereMsgNaoEncontrado = false;
        rendereBtInformAutorizacao = true;
        rendereFsRadioterapiapag2 = false;
        renderePag1 = true;
        acao = -1;
        itQuantidadeDlgPreencherRapido_qtd = 1;
        itQuantidadeDlgAddProcedimento_qtd = 1;

        pacienteTemp = new Paciente();
        renderMsgAddProcBlank = false;

        todosBlackFolha1();//seta a cor dos labels da folha 1
        todosBlackFolha2();//seta a cor dos labels da folha 2

    }

    public void verificaPerfil(int perfil) {

        if (perfil != 0 && perfil != 1 && perfil != 2 && perfil != 3 && perfil != 4) {

            F.redirecionarPagina("index.jsf");
        }

    }

    //seta os dados do solicitante de acordo com o codigo do usuario
    public void setSolicitante(String codUsuario) throws ErroSistema {

        List<Solicitante> l = new SolicitanteDAO().buscar("WHERE `usuario_id_usuario` = " + codUsuario);
        Solicitante s = new Solicitante();
        if (l.size() > 0) {
            s = l.get(0);
        }

        getEntidade().setSolicitante(s);
        //FabricaDeConexoes.fecharConecxao();
    }

    //seleciona a acao de entre insere edita busca
    public void selectAcao(int acao) {
        this.acao = acao;
        switch (acao) {
            case 1://formulario/novo
                getEntidade().setId_formulario(-2);//pra poder exibir a tata
                mudarParaInserir();
                break;
            case 2://edita
                setEntidade(new Formulario());
                mudarParaEditar();

                break;
            default:
                setEntidade(new Formulario());
                mudarParaBuscar();
                break;
        }

    }

    public void selecionarEstabelecimentoSolicitante(String s) {
        Estabelecimento_de_saude es = getEntidade().getEstabelecimento_de_saude_solicitante();
        try {//busca os dados do barros barreto pra setar como estabelecimento de saude solicitante padrao no formulario
            if (!estabelecimentoSelecionado.equals(-999)) {
                es = new Estabelecimento_de_saudeDAO().buscar("WHERE `id_estabelecimento_de_saude` = " + s).get(0);
            }
        } catch (ErroSistema ex) {
            F.setMsgErro(ex.toString());
        }
        getEntidade().setEstabelecimento_de_saude_solicitante(es);
    }
//funcoes de botoes

    public void btPgBtImprimir() {

        init();
    }

    public void btSimdlgConfirmeSalvarF1() throws ErroSistema {
        flagSalvarApenasPag1 = true;
        btSalvar(getLogado());
        closeDlgConfirmeSalvarF1();
    }

    public void btSimdlgConfirmeEditF1() throws ErroSistema {
        flagSalvarApenasPag1 = true;
        btSalvarAlteracoes(getLogado());
        closeDlgConfirmeEditF1();
    }

    public void closeDlgBuscarCidPag2() throws ErroSistema {
        //FabricaDeConexoes.fecharConecxao();

    }

    public void closeDlgConfirmeSalvarF1() throws ErroSistema {
        //FabricaDeConexoes.fecharConecxao();

    }

    public void closeDlgConfirmeEditF1() throws ErroSistema {
        //FabricaDeConexoes.fecharConecxao();

    }

    private int selecaoCidPag2;//variavel pra indicar qual cid esta selecionando na pag2 ja que a funcoar de busca é generica para varias buscas de cid

    public void selecionarCidPag2(String cid) {
        RequestContext request = RequestContext.getCurrentInstance();
        Boolean tempT = true;
        if (cid.isEmpty()) {
            cid = cidTemp.getCid();
            F.mensagem("", "Preencher os campos de busca!", FacesMessage.SEVERITY_WARN);
            tempT = false;
        } //        if ((cid.equals(getEntidade().getProc_justificativa().getCid_principal().getCid())
        //                || cid.equals(getEntidade().getPag2().getCid_topografico1_radio())
        //                || cid.equals(getEntidade().getPag2().getCid_topografico2_radio())
        //                || cid.equals(getEntidade().getPag2().getCid_topografico3_radio()))
        //                && (!cid.isEmpty())) {
        //
        //            tempT = false;
        //            F.mensagem("CID 10 já inserido!", "Selecione um CID 10 diferente!", FacesMessage.SEVERITY_WARN);
        //
        //        } 
        else {
            switch (selecaoCidPag2) {
                case 1: {//selecionar cid topogragico onco
                    getEntidade().getPag2().setCid_topografia_onco(cid);
                    break;
                }
                case 2: {//selecionar cid topogragico radio1
                    getEntidade().getPag2().setCid_topografico1_radio(cid);

                    break;
                }
                case 3: {//selecionar cid topogragico radio2
                    getEntidade().getPag2().setCid_topografico2_radio(cid);

                    break;
                }
                case 4: {//selecionar cid topogragico radio3
                    getEntidade().getPag2().setCid_topografico3_radio(cid);

                    break;
                }
                default: {
                    break;
                }
            }
        }
        setCidTemp(new Cid());
        vsOMBuscaCidPag2 = "";
        cids.clear();
        request.addCallbackParam("FCD2", tempT);
    }

    public void btPreencPag2() {
        setRenderePag1(!renderePag1);
    }

    public void btSalvar(Usuario logado) throws ErroSistema {

        todosBlackFolha1();
        todosBlackFolha2();
        //salva folha 1
        switch (getEntidade().getStatus().getId_status()) {
            case 1://novo
                //status = 1 = novo
                Formulario f = new Formulario();
                List<Formulario_has_procedimento_sus> fhpdLista = new ArrayList<>();
                Formulario_has_procedimento_susDAO fhpd = new Formulario_has_procedimento_susDAO();
                try {
                    if (verificarPreenchimento()) {//if1 

                        getEntidade().getStatus().setId_status(2);//salvo

                        f = getDao().salvar(getEntidade());

                        setEntidade(f);
                        //se f1 for salvo e (solicitente onco ou adm) e flagSalvarApenaspag1 for falso

                        if (f.getId_formulario() > 0 && (getLogado().getPerfil().getId_perfil() == 3 || getLogado().getPerfil().getId_perfil() == 4) && !flagSalvarApenasPag1) {//salvar folha 2
                            f.getPag2().setFormulario_id(f.getId_formulario());//copia o id do formulario para o id da pag2

                            new Formulario_f2DAO().salvar(f.getPag2());

                        }
                        fhpdLista.add(fhpd.salvar(new Formulario_has_procedimento_sus(f, getEntidade().getP1(), getEntidade().getP1().getQtd(), 1)));
                        if (!getEntidade().getP2().getCodigo().isEmpty()) {//if2

                            Formulario_has_procedimento_sus fhp = fhpd.salvar(new Formulario_has_procedimento_sus(f, getEntidade().getP2(), getEntidade().getP2().getQtd(), 2));
                            if (fhp == null) {
                                throw new ErroSistema("testeando esse erro!! formulariobean123");
                            } else {
                                fhpdLista.add(fhp);
                            }
                        }//if2
                        if (!getEntidade().getP3().getCodigo().isEmpty()) {//if3
                            fhpdLista.add(fhpd.salvar(new Formulario_has_procedimento_sus(f, getEntidade().getP3(), getEntidade().getP3().getQtd(), 3)));
                        }//if3
                        if (!getEntidade().getP4().getCodigo().isEmpty()) {//if4
                            fhpdLista.add(fhpd.salvar(new Formulario_has_procedimento_sus(f, getEntidade().getP4(), getEntidade().getP4().getQtd(), 4)));
                        }//if4
                        if (!getEntidade().getP5().getCodigo().isEmpty()) {//if5
                            fhpdLista.add(fhpd.salvar(new Formulario_has_procedimento_sus(f, getEntidade().getP5(), getEntidade().getP5().getQtd(), 5)));
                        }//if5
                        if (!getEntidade().getP6().getCodigo().isEmpty()) {//if6
                            fhpdLista.add(fhpd.salvar(new Formulario_has_procedimento_sus(f, getEntidade().getP6(), getEntidade().getP6().getQtd(), 6)));
                        }//if6

                        setEntidade(f);
                        F.mensagem("Formulário salvo com sucesso!", "Nº de identificação: " + getEntidade().getMascaraId(), FacesMessage.SEVERITY_INFO);
                        log(logado, 1);
                    } else {
                        flagSalvarApenasPag1 = false;
                    }
                } catch (ErroSistema e) {

                    //desfaz o processo de salvar
                    try {
                        if (f.getId_formulario() != -1) {
                            getDao().deletar(f);
                        }
                        for (int i = 0; i < fhpdLista.size(); i++) {
                            if (fhpdLista.get(i).getFormulario().getId_formulario() != -1) {
                                fhpd.deletar(fhpdLista.get(i));
                            }
                        }
                    } catch (ErroSistema ex) {
                        F.setMsgErro("formulario dao 200:" + ex.toString());
                    }

                    F.mensagem("Erro ao salvar o formulário!", e.toString(), FacesMessage.SEVERITY_WARN);
                }

                break;
            case 2:
                //flagSalvarApenasPag1 = renderePag1;
//                if (!renderePag1) {  // se pagina1 nao estiver renderizada
//                    flagSalvarApenasPag1 = false;
//                }
                //2 formulario ja salvo entao sera atualizado segundo orientações da francy
                if (verificarPreenchimento()) { // if1
                    btSalvarAlteracoes(logado);
                } //fim if1
                break;
            default:
                F.setMsgErro(getEntidade().getStatus().getId_status().toString());
                break;
        }
        //FabricaDeConexoes.fecharConecxao();
    }

    public void btFormLimpar() throws ErroSistema {

        novo();
        btCidLimpar();
        btPacienteLimpar();
        btProcedimentoLimpar();
        limpDlgPreencherRapido();
        init();

    }

    public void limpDlgPreencherRapido() throws ErroSistema {

        pacienteTemp = new Paciente();
        cidTemp = new Cid();
        procedimentosTemp = new Procedimento_sus();
        pacientesAghuBarros.clear();
        procedimentos_susItem.clear();
        cids.clear();
        txtBuscaNumPront = "";
        somProcedDlgPreencRapido = "";

        FabricaDeConexoes.fecharConecxaoAghuBarros();
        //FabricaDeConexoes.fecharConecxao();

    }

    public boolean verificCamposPaciente() {
        boolean retorno = true;
        Paciente p = new Paciente();
        RequestContext request = RequestContext.getCurrentInstance();
        if (getEntidade().getPaciente().getNum_prontuario().isEmpty()) {
            if (rendereMsgNaoEncontrado) {//msg de nenhum registro encontrado
                F.mensagem("", "Nenhum registro encontrado..", FacesMessage.SEVERITY_WARN);
            } else {
                F.mensagem("", "Preencher os campos de busca!", FacesMessage.SEVERITY_WARN);
            }

            request.addCallbackParam("FP", false);
            return false;
        } else if (p.getData_obito() != null) {
            F.mensagem("", "Paciente consta como morto!", FacesMessage.SEVERITY_WARN);
            request.addCallbackParam("FP", false);
        } else {
            request.addCallbackParam("FP", true);
        }

        if (getEntidade().getPaciente().getData_obito() != null) {
            F.mensagem("Erro!", "Paciente consta como morto!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getNome().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!!", FacesMessage.SEVERITY_WARN);
            request.addCallbackParam("FP", false);
            return false;
        } else {
            request.addCallbackParam("FP", true);
        }
        if (getEntidade().getPaciente().getNum_prontuario().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getCns().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getData_nascimento_s().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getSexo().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getCorPdf().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getCor().equals("5") && getEntidade().getPaciente().getEtnia().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getNome_mae().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
//
//        if (getEntidade().getPaciente().getNome_mae().isEmpty()) {
//            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
//            return false;
//        }
//        if (getEntidade().getPaciente().getTelefone_mae().isEmpty()) {
//           // F.mensagem("", "O campo(10) o n° telefone da mãe está vázio!", FacesMessage.SEVERITY_WARN);
//        }

        if (getEntidade().getPaciente().getEndereco().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        if (getEntidade().getPaciente().getMunicipio().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getCod_ibge_municipio().trim().isEmpty() || getEntidade().getPaciente().getCod_ibge_municipio().trim().equals("0")) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getUf().isEmpty()) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (getEntidade().getPaciente().getCep().trim().isEmpty() || getEntidade().getPaciente().getCep().trim().equals("0")) {
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        return retorno;
    }

    private boolean verificarPreenchimentoPag1() {
        //verifica estabelecimento de saude solicitante
        if (getEntidade().getEstabelecimento_de_saude_solicitante().getId_estabelecimento_saude() == -1) {
            F.mensagem("Erro", "Informe o estabelecimento de saúde solicitante!", FacesMessage.SEVERITY_WARN);
            return false;
        }
        // paciente
        if (getEntidade().getPaciente().getNum_prontuario().isEmpty()) {
            F.mensagem("Erro", "Informe os dados do paciente!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            if (!verificCamposPaciente()) {
                return false;
            }
        }

        // procedimento sus
        if (getEntidade().getP1().getCodigo().isEmpty()) {
            setRedblackcamp18("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp18("black");
        }
        // cid
        if (getEntidade().getProc_justificativa().getCid_principal().getCid().equals("")) {
            setRedblackcamp36("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp36("black");
        }
        //observação cid
        if (getEntidade().getProc_justificativa().getObservacoes().isEmpty()) {
            setRedblackcamp40("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp40("black");
        }
        return true;
    }

    private boolean verificarPreenchimento() {  //verifica se todos os campos obrigatórios foram preemchidos
        boolean r = true;
        if (!verificarPreenchimentoPag1()) {
            return false;
        }

        //se o perfil for 4 onco ou 3 adm e !flagSalvarApenasPag1
        if (!flagSalvarApenasPag1 && (getLogado().getPerfil().getId_perfil() == 4 || getLogado().getPerfil().getId_perfil() == 3)) {

            //verifica  se os campos da oncologia estão preenchidos
            if (verificarCamposOnco()) {

                if (rendereFsRadioterapiapag2) {
                    r = verificarCamposRadio();

                } else {
                    r = verificarCamposQuimio();

                }

            } else {
                r = false;
            }

        }
        return r;
    }

    private boolean verificarCamposOnco() {

//        if ((getEntidade().getData().after(new Date()))) {
//            F.mensagem("", "A data da solicitação não deve ser posterior a data de hoje!", FacesMessage.SEVERITY_WARN);
//            return false;
//        }
        if (getEntidade().getPag2().getLocal_tumor_primario().isEmpty()) {
            setRedblackcamp56("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp56("black");
        }

        if (getEntidade().getPag2().getLinfo_reg_invalido() < 0 || getEntidade().getPag2().getLinfo_reg_invalido() > 2) {
            setRedblackcamp58("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp58("black");
        }

//        if (getEntidade().getPag2().getLoc_meta().isEmpty()) {
//            setRedblackcamp59("red");
//            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
//            return false;
//        } else {
//            setRedblackcamp59("black");
//        }
        if (getEntidade().getPag2().getEsta_uicc().isEmpty()) {
            setRedblackcamp60("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp60("black");
        }
//        if (getEntidade().getPag2().getEsta_outros().isEmpty()) {
//            setRedblackcamp61("red");
//            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
//            return false;
//        } else {
//            setRedblackcamp61("black");
//        }
        if (getEntidade().getPag2().getGrau_isto().isEmpty()) {
            setRedblackcamp62("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp62("black");
        }
        if (getEntidade().getPag2().getDiag_cito_isto().isEmpty()) {
            setRedblackcamp63("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp63("black");
        }
        if (getEntidade().getPag2().getData_64() == null) {
            setRedblackcamp64("red");
            F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
            return false;
        } else {
            setRedblackcamp64("black");
        }

        return true;

    }

    private boolean verificarCamposRadio() {

        boolean x = true;
        //tratamento anteriores marcado como sim  
        if (getEntidade().getPag2().isTrate_ante_radio()) {//if1
            if (getEntidade().getPag2().getTrate_ante1_radio().isEmpty() //if2
                    && getEntidade().getPag2().getTrate_ante2_radio().isEmpty()
                    && getEntidade().getPag2().getTrate_ante3_radio().isEmpty()) {
                setRedblackcamp74("red");
                F.mensagem("", "Campos em vermelho são obrigatórios1!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {//if1
                setRedblackcamp74("black");
            }

            if (getEntidade().getPag2().getDt_trate_ante1_radio() == null //if2
                    && getEntidade().getPag2().getDt_trate_ante2_radio() == null
                    && getEntidade().getPag2().getDt_trate_ante3_radio() == null) {
                setRedblackcamp75("red");
                F.mensagem("", "Campos em vermelho são obrigatórios2!", FacesMessage.SEVERITY_WARN);
                return false;

            } else {//if2
                setRedblackcamp75("black");
            }
            //descricao nao vazia e data vazia

            if (!getEntidade().getPag2().getTrate_ante1_radio().isEmpty() && getEntidade().getPag2().getDt_trate_ante1_radio() == null) {
                setRedblackcamp75("red");
                x = false;
            } else if (!getEntidade().getPag2().getTrate_ante2_radio().isEmpty() && getEntidade().getPag2().getDt_trate_ante2_radio() == null) {
                setRedblackcamp75("red");
                x = false;
            } else if (!getEntidade().getPag2().getTrate_ante3_radio().isEmpty() && getEntidade().getPag2().getDt_trate_ante3_radio() == null) {
                setRedblackcamp75("red");
                x = false;
            } else if (getEntidade().getPag2().getDt_trate_ante1_radio() != null && getEntidade().getPag2().getTrate_ante1_radio().isEmpty()) {
                setRedblackcamp74("red");
                x = false;
            } else if (getEntidade().getPag2().getDt_trate_ante2_radio() != null && getEntidade().getPag2().getTrate_ante2_radio().isEmpty()) {
                setRedblackcamp74("red");
                x = false;
            } else if (getEntidade().getPag2().getDt_trate_ante3_radio() != null && getEntidade().getPag2().getTrate_ante3_radio().isEmpty()) {
                setRedblackcamp74("red");

                x = false;
            }
            if (!x) {
                F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            }
        } else {
            limparDadosTratAnteriorRadio();

        }
        if (isRendereFsRadioterapiapag2()) {

            x = verificarCamposRadioContTrat();

        } else {

            limparDadosContinueTratRadio();

        }

        return x;

    }

    private boolean verificarCamposRadioContTrat() {
        if (isRendereFsRadioterapiapag2()) {
//        if (getEntidade().getPag2().isContinue_trate_radio()) {
//            
//        }
            if (getEntidade().getPag2().getDt_ini_trata_radio() == null) {
                setRedblackcamp77("red");
                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {
                setRedblackcamp77("black");
            }

            // se finalidade estiver 
            if (getEntidade().getPag2().getFinalidade().getId() < 1) {

                setRedblackcamp78("red");
                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {
                setRedblackcamp78("black");
            }
            // se cid topograficop nao for preenchido
            if (getEntidade().getPag2().getCid_topografico1_radio().isEmpty() //if2
                    && getEntidade().getPag2().getCid_topografico2_radio().isEmpty()
                    && getEntidade().getPag2().getCid_topografico3_radio().isEmpty()) {
                setRedblackcamp79("red");
                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {//if2  pelo menos um cid topografico irrad foi selecionado
                //CID nao vazio e descriçao vazia
                setRedblackcamp79("black");

                if (!getEntidade().getPag2().getCid_topografico1_radio().isEmpty()) {
                    if (getEntidade().getPag2().getArea_irradi_descricao1_radio().isEmpty()) {
                        setRedblackcamp80("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp80("black");
                    }
                    if (getEntidade().getPag2().getNcampo_incer1_radio().isEmpty()) {
                        setRedblackcamp81("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp81("black");
                    }
                    if (getEntidade().getPag2().getDt_ini_area_irrad1_radio() == null) {
                        setRedblackcamp82("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp82("black");
                    }
                    if (getEntidade().getPag2().getDt_fim_area_irrad1_radio() == null) {
                        setRedblackcamp83("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp83("black");
                    }
                }
                if (!getEntidade().getPag2().getCid_topografico2_radio().isEmpty()) {
                    if (getEntidade().getPag2().getArea_irradi_descricao2_radio().isEmpty()) {
                        setRedblackcamp80("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp80("black");
                    }
                    if (getEntidade().getPag2().getNcampo_incer2_radio().isEmpty()) {
                        setRedblackcamp81("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp81("black");
                    }
                    if (getEntidade().getPag2().getDt_ini_area_irrad2_radio() == null) {
                        setRedblackcamp82("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp82("black");
                    }
                    if (getEntidade().getPag2().getDt_fim_area_irrad2_radio() == null) {
                        setRedblackcamp83("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp83("black");
                    }
                }
                if (!getEntidade().getPag2().getCid_topografico3_radio().isEmpty()) {
                    if (getEntidade().getPag2().getArea_irradi_descricao3_radio().isEmpty()) {
                        setRedblackcamp80("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp80("black");
                    }
                    if (getEntidade().getPag2().getNcampo_incer3_radio().isEmpty()) {
                        setRedblackcamp81("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp81("black");
                    }
                    if (getEntidade().getPag2().getDt_ini_area_irrad3_radio() == null) {
                        setRedblackcamp82("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp82("black");
                    }
                    if (getEntidade().getPag2().getDt_fim_area_irrad3_radio() == null) {
                        setRedblackcamp83("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    } else {
                        setRedblackcamp83("black");
                    }
                }

                if (getEntidade().getPag2().getCid_topografico1_radio().isEmpty()) {
                    if (!getEntidade().getPag2().getArea_irradi_descricao1_radio().isEmpty()
                            || !getEntidade().getPag2().getNcampo_incer1_radio().isEmpty()
                            || !(getEntidade().getPag2().getDt_ini_area_irrad1_radio() == null)
                            || !(getEntidade().getPag2().getDt_fim_area_irrad1_radio() == null)) {
                        setRedblackcamp79("red");
//                System.out.println("Campo 1 falso");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    }
                }

                if (getEntidade().getPag2().getCid_topografico3_radio().isEmpty()) {
                    if (!getEntidade().getPag2().getArea_irradi_descricao3_radio().isEmpty()
                            || !getEntidade().getPag2().getNcampo_incer3_radio().isEmpty()
                            || !(getEntidade().getPag2().getDt_ini_area_irrad3_radio() == null)
                            || !(getEntidade().getPag2().getDt_fim_area_irrad3_radio() == null)) {
                        setRedblackcamp79("red");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
//                System.out.println("Campo 3 falso");
                        return false;
                    }
                }

                if (getEntidade().getPag2().getCid_topografico2_radio().isEmpty()) {
                    if (!getEntidade().getPag2().getArea_irradi_descricao2_radio().isEmpty()
                            || !getEntidade().getPag2().getNcampo_incer2_radio().isEmpty()
                            || !(getEntidade().getPag2().getDt_ini_area_irrad2_radio() == null)
                            || !(getEntidade().getPag2().getDt_fim_area_irrad2_radio() == null)) {
                        setRedblackcamp79("red");
//                System.out.println("Campo 2 falso");
                        F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                        return false;
                    }
                }

            }
        } else {
            getEntidade().getPag2().setDt_ini_trata_radio(null);
            getEntidade().getPag2().setFinalidade(new Finalidade_radio_f2());
            getEntidade().getPag2().setCid_topografico1_radio("");
            getEntidade().getPag2().setCid_topografico2_radio("");
            getEntidade().getPag2().setCid_topografico3_radio("");
            getEntidade().getPag2().setArea_irradi_descricao1_radio("");
            getEntidade().getPag2().setArea_irradi_descricao2_radio("");
            getEntidade().getPag2().setArea_irradi_descricao3_radio("");
            getEntidade().getPag2().setNcampo_incer1_radio("");
            getEntidade().getPag2().setNcampo_incer2_radio("");
            getEntidade().getPag2().setNcampo_incer3_radio("");
            getEntidade().getPag2().setDt_ini_area_irrad1_radio(null);
            getEntidade().getPag2().setDt_ini_area_irrad2_radio(null);
            getEntidade().getPag2().setDt_ini_area_irrad3_radio(null);
            getEntidade().getPag2().setDt_fim_area_irrad1_radio(null);
            getEntidade().getPag2().setDt_fim_area_irrad2_radio(null);
            getEntidade().getPag2().setDt_fim_area_irrad3_radio(null);

        }

        return true;
    }

    private boolean verificarCamposQuimio() {
        boolean x = true;// verificar os campos  tratamento anteriores se for marcada a opcao sim
        boolean y = true; // verificar os campos continuidade de tratamento
        //tratamento anteriores marcado como sim
        if (getEntidade().getPag2().isTrate_anteriores_quimio()) {//if1
            if (getEntidade().getPag2().getTrate_ante1_quimio().isEmpty() //if2
                    && getEntidade().getPag2().getTrate_ante2_quimio().isEmpty()
                    && getEntidade().getPag2().getTrate_ante3_quimio().isEmpty()) {
                setRedblackcamp66("red");
                F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else if (getEntidade().getPag2().getDt_trate_ante1_quimio() == null //if2
                    && getEntidade().getPag2().getDt_trate_ante2_quimio() == null
                    && getEntidade().getPag2().getDt_trate_ante3_quimio() == null) {
                setRedblackcamp67("red");
                F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {//if2
                setRedblackcamp66("black");
                //descricao nao vazia e data vazia
                setRedblackcamp67("black");
                if (!getEntidade().getPag2().getTrate_ante1_quimio().isEmpty() && getEntidade().getPag2().getDt_trate_ante1_quimio() == null) {
                    setRedblackcamp67("red");
                    F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                    x = false;
                } else if (!getEntidade().getPag2().getTrate_ante2_quimio().isEmpty() && getEntidade().getPag2().getDt_trate_ante2_quimio() == null) {
                    setRedblackcamp67("red");
                    F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                    x = false;
                } else if (!getEntidade().getPag2().getTrate_ante3_quimio().isEmpty() && getEntidade().getPag2().getDt_trate_ante3_quimio() == null) {
                    setRedblackcamp67("red");
                    F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                    x = false;
                } else if ((getEntidade().getPag2().getDt_trate_ante1_quimio() != null) && getEntidade().getPag2().getTrate_ante1_quimio().isEmpty()) {
                    setRedblackcamp66("red");
                    F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                    x = false;
                } else if ((getEntidade().getPag2().getDt_trate_ante2_quimio() != null) && getEntidade().getPag2().getTrate_ante2_quimio().isEmpty()) {
                    setRedblackcamp66("red");
                    F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                    x = false;
                } else if ((getEntidade().getPag2().getDt_trate_ante3_quimio() != null) && getEntidade().getPag2().getTrate_ante3_quimio().isEmpty()) {
                    setRedblackcamp66("red");
                    F.mensagem("", "Campos em vermelho são obrigatórios!", FacesMessage.SEVERITY_WARN);
                    x = false;
                }

                if (!x) {
                    return false;
                }
            }
        } else {//if 1
            limparTratAnteriorQuimio();

        }

        if (!isRendereFsRadioterapiapag2()) {
            y = VerificarCamposContinueTrateQuimio();
        } else {
            limparDadosContinueTratQuimio();
        }
        if (x && y) {
            return true;
        } else {
            return false;
        }

    }

    private boolean VerificarCamposContinueTrateQuimio() {
        if (!isRendereFsRadioterapiapag2()) {
//        if (getEntidade().getPag2().isContinue_trate_quimio()) {
//            
//        }

            if (getEntidade().getPag2().getDt_ini_trata_quimio() == null) {
                setRedblackcamp69("red");
                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {
                setRedblackcamp69("black");
            }
            if (getEntidade().getPag2().getEsquema_sigla_quimio().isEmpty()) {
                setRedblackcamp70("red");
                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {
                setRedblackcamp70("black");
            }
            if (getEntidade().getPag2().getQtd_mese_planejado_quimio() <= 0) {
                setRedblackcamp71("red");
                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
                return false;
            } else {
                setRedblackcamp71("black");
            }
//            if (getEntidade().getPag2().getQtd_mese_autorizados_quimio() <= 0) {
//                F.mensagem("", "Campos em vermelho săo obrigatórios!", FacesMessage.SEVERITY_WARN);
//                return false;
//            }
        } else {
            getEntidade().getPag2().setDt_ini_trata_quimio(null);
            getEntidade().getPag2().setEsquema_sigla_quimio("");
            getEntidade().getPag2().setQtd_mese_planejado_quimio(0);
            getEntidade().getPag2().setQtd_mese_autorizados_quimio(0);
        }

        return true;
    }

    //close dlg
    public void closeDlgBuscaProntuario() throws ErroSistema {
        pacienteTemp = new Paciente();
        txtBuscaNumPront = "";
        //FabricaDeConexoes.fecharConecxao();
        FabricaDeConexoes.fecharConecxaoAghuBarros();
    }

    public void closeDlgBuscaNomePaci() throws ErroSistema {
        rendereMsgNaoEncontrado = false;
        txtBuscaNumPront = "";
        somVarPaciente = "";
        pacienteTemp = new Paciente();
        pacientesAghuBarros.clear();
        //FabricaDeConexoes.fecharConecxao();
    }

    public void closeDlgAddProcedimento() throws ErroSistema {
        //FabricaDeConexoes.fecharConecxao();
    }

    public void closeDlgSelecionarCidPrincipal() throws ErroSistema {
        cidTemp = new Cid();
        cids.clear();
        //FabricaDeConexoes.fecharConecxao();
    }

    public void closeDlgPreencherRapido() throws ErroSistema {
        pacienteTemp.setNum_prontuario("");
        pacienteTemp.setNome_mae("");
        limpDlgPreencherRapido();
        setIsPreenchimentoRapido(false);
        FabricaDeConexoes.fecharConecxaoAghuBarros();
        //FabricaDeConexoes.fecharConecxao();
    }

    public void closeDlgBuscaform() throws ErroSistema {
        //FabricaDeConexoes.fecharConecxao();
    }

    public void closeDlgAutorizacao() throws ErroSistema {
        //FabricaDeConexoes.fecharConecxao();
    }

//fim close dlg
    //auxiliares
    //tela formulario jsf
    public boolean verificarApacRepetida() throws ErroSistema {

        List<Formulario> l = getDao().buscar(
                "paciente.`num_prontuario` = '" + getEntidade().getPaciente().getNum_prontuario() + "' AND "
                + "(formulario.`data` between '" + F.dataStringBanco(F.somarDiasData(getEntidade().getData(), -1)) + "' AND '" + F.dataStringBanco(F.somarDiasData(getEntidade().getData(), 1)) + "') AND "
                + "status.`id_status` = '" + 3 + "' "
        );
        if (l != null) {
            Calendar dataSistema =  Calendar.getInstance();
            Calendar dataForm =  Calendar.getInstance();
            Calendar dataFormCriacao =  Calendar.getInstance();
            String horasatual =(dataSistema.get(Calendar.HOUR_OF_DAY)<10) ? "0"+dataSistema.get(Calendar.HOUR_OF_DAY):dataSistema.get(Calendar.HOUR_OF_DAY)+"";
            String mintual=(dataSistema.get(Calendar.MINUTE)<10) ? "0"+dataSistema.get(Calendar.MINUTE):dataSistema.get(Calendar.MINUTE)+"";
            String horaFomrEmitido="";
            String minFomrEmitido="";
            for (Formulario f : l) {
                f.buscaProcedimentosForm();
                if (f.getP1().getCodigo().equals(getEntidade().getP1().getCodigo())) {//if 1

                    dataForm.setTime(f.getData());
                    dataFormCriacao.setTime(f.getData_criacao());
                    int diasEntre = F.diasEntre(getEntidade().getData(), f.getData()); 
                    horaFomrEmitido=(dataFormCriacao.get(Calendar.HOUR_OF_DAY)<10) ? "0"+dataFormCriacao.get(Calendar.HOUR_OF_DAY):dataFormCriacao.get(Calendar.HOUR_OF_DAY)+"";
                    minFomrEmitido=(dataFormCriacao.get(Calendar.MINUTE)<10) ? "0"+dataFormCriacao.get(Calendar.MINUTE):dataFormCriacao.get(Calendar.MINUTE)+"";
                    if (diasEntre == 0) {//if 2 - mesmo dia
                        F.mensagem("", "Já existe formulario n°:"+f.getMascaraId()+" para o mesmo paciente e mesmo procedimento emitido em "+F.dataString(f.getData()), FacesMessage.SEVERITY_WARN);
                        return true;

                    } else {//fim if 2 dias diferentes
                        if(diasEntre == 1){// amanha
                            if(Integer.parseInt(horaFomrEmitido +""+minFomrEmitido) <= Integer.parseInt(horasatual +""+mintual)){
                                F.mensagem("", "Já existe formulario n°:"+f.getMascaraId()+" para o mesmo paciente e mesmo procedimento emitido em "+F.dataString(f.getData()), FacesMessage.SEVERITY_WARN);
                                return true;
                            }else{
                                return false;
                            }
                        }else{// =-1 ontem
                                                       
                            if(Integer.parseInt(horaFomrEmitido+""+horaFomrEmitido) > Integer.parseInt(horasatual+""+mintual)){
                                F.mensagem("", "Já existe formulario n°:"+f.getMascaraId()+" para o mesmo paciente e mesmo procedimento emitido em "+F.dataString(f.getData()), FacesMessage.SEVERITY_WARN);
                                return true;
                            }else{
                                return false;
                            }
                        }
                    }
                    
                } else {//fim if 1 - procedimentos diferentes
                    return false;
                }
            }//fim for

        }
        return false;
    }

    public boolean bloqueioImpressao() {
        Formulario f = getEntidade();

        //((id do usuario logado != id  do usuario do solicitante da apac) ou se o status do formulario != 3(emitido) ou !(perfil 2 solicitante ou 4 oncologico)  e nao perfil 3 adm)
        return (((getLogado().getId_usuario() != f.getSolicitante().getUsuario().getId_usuario()) || (f.getStatus().getId_status() != 3) || !(getLogado().getPerfil().getId_perfil() == 2 || getLogado().getPerfil().getId_perfil() == 4)) && (getLogado().getPerfil().getId_perfil() != 3));
    }
//tela formulario jsf

    public boolean bloqueioEdicao(Formulario f) {
        // ((dono da apac == usuario logado) e status = 2(salvo)  e (perfil 2 solicitante ou 4 oncologico) )        
        return !(((getLogado().getId_usuario() == f.getSolicitante().getUsuario().getId_usuario()) && (f.getStatus().getId_status() == 2) && (getLogado().getPerfil().getId_perfil() == 2 || getLogado().getPerfil().getId_perfil() == 4)) || (getLogado().getPerfil().getId_perfil() == 3));

    }

    //limpar campos 79 80 81 82 82
    public void limparCampo79(int flag) {
        switch (flag) {
            case 1: {
                getEntidade().getPag2().setCid_topografico1_radio("");
                getEntidade().getPag2().setArea_irradi_descricao1_radio("");
                getEntidade().getPag2().setNcampo_incer1_radio("");
                getEntidade().getPag2().setDt_ini_area_irrad1_radio(null);
                getEntidade().getPag2().setDt_fim_area_irrad1_radio(null);
            }
            case 2: {
                getEntidade().getPag2().setCid_topografico2_radio("");
                getEntidade().getPag2().setArea_irradi_descricao2_radio("");
                getEntidade().getPag2().setNcampo_incer2_radio("");
                getEntidade().getPag2().setDt_ini_area_irrad2_radio(null);
                getEntidade().getPag2().setDt_fim_area_irrad2_radio(null);
            }
            case 3: {
                getEntidade().getPag2().setCid_topografico3_radio("");
                getEntidade().getPag2().setArea_irradi_descricao3_radio("");
                getEntidade().getPag2().setNcampo_incer3_radio("");
                getEntidade().getPag2().setDt_ini_area_irrad3_radio(null);
                getEntidade().getPag2().setDt_fim_area_irrad3_radio(null);
            }

        }

    }

    // legenda do dialogo confirmar edicao
    public String dlgDlgConfirmeEditF1_legenda() {
        //nao houver  pag 2 e a pagina 1 estuver renderizada
        if (getEntidade().getPag2().getFormulario_id() < 1 && renderePag1) {
            return "Deseja salvar as alterações? ";
        } else {
            return "Deseja realmente editar apenas Folha 1?";
        }
    }

    public void todosBlackFolha1() {
        setRedblackcamp18("black");
        setRedblackcamp36("black");
        setRedblackcamp40("black");
    }

    public void todosBlackFolha2() {
        todosBlackOnco();
        todosBlackQuimio();
        todosBlackRadio();
    }

    public void todosBlackOnco() {
        setRedblackcamp56("black");
        setRedblackcamp58("black");
        setRedblackcamp59("black");
        setRedblackcamp60("black");
        setRedblackcamp61("black");
        setRedblackcamp62("black");
        setRedblackcamp63("black");
        setRedblackcamp64("black");

    }

    // ao trocar para a radioterapia seta a cor preta nos campos da quimioterapia 
    public void todosBlackQuimio() {
        setRedblackcamp66("black");
        setRedblackcamp67("black");
        setRedblackcamp69("black");
        setRedblackcamp70("black");
        setRedblackcamp71("black");
    }

    // ao trocar para a quimioterapia seta a cor preta nos campos da radioterapia 
    public void todosBlackRadio() {
        setRedblackcamp74("black");
        setRedblackcamp75("black");
        setRedblackcamp77("black");
        setRedblackcamp78("black");
        setRedblackcamp79("black");
        setRedblackcamp80("black");
        setRedblackcamp81("black");
        setRedblackcamp82("black");
        setRedblackcamp83("black");
    }

    public void limparDialogos() throws ErroSistema {
        limpDlgPreencherRapido();
        limparDlgAddProcedimento();

    }

    public void transicaoConsultEditBusca(Formulario f) throws ErroSistema {
        if (f != null) {
            f = new FormularioDAO().buscaId(f.getId_formulario() + "");
            f.buscaProcedimentosForm();
            f.setPag2(null);//null e uma flag que indica que o set vai buscar as informacoes no banco.
            setEntidade(f);
        }
    }

    public String corLabael(String s) {
        if ((s == null || s.trim().isEmpty() || s.trim().equals("0")) && !getEntidade().getPaciente().getNum_prontuario().isEmpty()) {
            return "red";
        } else {
            return "black";
        }

    }

    private void limparDadosQuimio() {
        limparTratAnteriorQuimio();
        limparDadosContinueTratQuimio();
        todosBlackQuimio();

    }

    private void limparTratAnteriorQuimio() {
        getEntidade().getPag2().setTrate_anteriores_quimio(false);

        getEntidade().getPag2().setTrate_ante1_quimio("");
        getEntidade().getPag2().setTrate_ante2_quimio("");
        getEntidade().getPag2().setTrate_ante3_quimio("");
        getEntidade().getPag2().setDt_trate_ante1_quimio(null);
        getEntidade().getPag2().setDt_trate_ante2_quimio(null);
        getEntidade().getPag2().setDt_trate_ante3_quimio(null);

    }

    private void limparDadosContinueTratQuimio() {
        getEntidade().getPag2().setContinue_trate_quimio(false);
        getEntidade().getPag2().setDt_ini_trata_quimio(null);
        getEntidade().getPag2().setEsquema_sigla_quimio("");
        getEntidade().getPag2().setQtd_mese_planejado_quimio(0);
        getEntidade().getPag2().setQtd_mese_autorizados_quimio(0);
    }

    private void limparDadosRadio() {

        limparDadosTratAnteriorRadio();
        limparDadosContinueTratRadio();
        todosBlackRadio();

    }

    private void limparDadosTratAnteriorRadio() {
        getEntidade().getPag2().setTrate_ante_radio(false);

        getEntidade().getPag2().setTrate_ante1_radio("");
        getEntidade().getPag2().setTrate_ante2_radio("");
        getEntidade().getPag2().setTrate_ante3_radio("");
        getEntidade().getPag2().setDt_trate_ante1_radio(null);
        getEntidade().getPag2().setDt_trate_ante2_radio(null);
        getEntidade().getPag2().setDt_trate_ante3_radio(null);

    }

    private void limparDadosContinueTratRadio() {
        getEntidade().getPag2().setContinue_trate_radio(false);
        getEntidade().getPag2().setDt_ini_trata_radio(null);
        getEntidade().getPag2().setFinalidade(new Finalidade_radio_f2());
        getEntidade().getPag2().setCid_topografico1_radio(null);
        getEntidade().getPag2().setCid_topografico2_radio(null);
        getEntidade().getPag2().setCid_topografico3_radio(null);
        getEntidade().getPag2().setArea_irradi_descricao1_radio(null);
        getEntidade().getPag2().setArea_irradi_descricao2_radio(null);
        getEntidade().getPag2().setArea_irradi_descricao3_radio(null);
        getEntidade().getPag2().setNcampo_incer1_radio("");
        getEntidade().getPag2().setNcampo_incer2_radio("");
        getEntidade().getPag2().setNcampo_incer3_radio("");
        getEntidade().getPag2().setDt_ini_area_irrad1_radio(null);
        getEntidade().getPag2().setDt_ini_area_irrad2_radio(null);
        getEntidade().getPag2().setDt_ini_area_irrad3_radio(null);
        getEntidade().getPag2().setDt_fim_area_irrad1_radio(null);
        getEntidade().getPag2().setDt_fim_area_irrad2_radio(null);
        getEntidade().getPag2().setDt_fim_area_irrad3_radio(null);
    }

    //fimauxuliares
    public void btPacienteLimpar() {
        getEntidade().setPaciente(new Paciente());
        pacientesAghuBarros = new ArrayList<>();
    }

    public void btProcedimentoLimpar() {
        getEntidade().setP1(new Procedimento_sus());
        getEntidade().setP2(new Procedimento_sus());
        getEntidade().setP3(new Procedimento_sus());
        getEntidade().setP4(new Procedimento_sus());
        getEntidade().setP5(new Procedimento_sus());
        getEntidade().setP6(new Procedimento_sus());
        procedimentosTemp = new Procedimento_sus();

        getEntidade().getProc_justificativa().setCid_principal(new Cid());
        getEntidade().getProc_justificativa().setCid_secundario(new Cid());
        getEntidade().getProc_justificativa().setCid_causas_assoc(new Cid());
    }

    public void btCidLimpar() {
        cidTemp = new Cid();
        getEntidade().getProc_justificativa().setCid_principal(new Cid());
        getEntidade().getProc_justificativa().setCid_secundario(new Cid());
        getEntidade().getProc_justificativa().setCid_causas_assoc(new Cid());
    }

    public void btPreenchiRapido() throws ErroSistema {
        itQuantidadeDlgPreencherRapido_qtd = 1;
        limpDlgPreencherRapido();
        closeDlgBuscaNomePaci();//limpar dlg buscar paci nome
    }

    public void btCidConfirmar() throws ErroSistema {
        // RequestContext request = RequestContext.getCurrentInstance();
        Boolean tempT = true;

        if (cidTemp.getCid().isEmpty() && getIsPreenchimentoRapido() == false) {
            tempT = false;
            F.mensagem("", "Preencher os campos de busca!", FacesMessage.SEVERITY_WARN);

        } else {

            if (acao == 1 || acao == 2) {
                if ((cidTemp.getCid().equals(getEntidade().getProc_justificativa().getCid_principal().getCid())
                        || cidTemp.getCid().equals(getEntidade().getProc_justificativa().getCid_secundario().getCid())
                        || cidTemp.getCid().equals(getEntidade().getProc_justificativa().getCid_causas_assoc().getCid())) && (!cidTemp.getCid().isEmpty())) {

                    tempT = false;
                    F.mensagem("CID 10 já inserido!", "Selecione um CID 10 diferente!", FacesMessage.SEVERITY_WARN);
                    if (//if 1
                            !getEntidade().getProc_justificativa().getCid_principal().getCid().isEmpty()
                            && !getEntidade().getProc_justificativa().getCid_secundario().getCid().isEmpty()
                            && !getEntidade().getProc_justificativa().getCid_causas_assoc().getCid().isEmpty()) {
                        F.mensagem("", "Limite de CIDs atingido!", FacesMessage.SEVERITY_INFO);
                    }//if 1

                } else {
                    tempT = true;
                    if (getEntidade().getProc_justificativa().getCid_principal().getCid().equals("")) {
                        getEntidade().getProc_justificativa().setCid_principal(cidTemp);
                        setRedblackcamp36("black");
                        setRedblackcamp79("black");

                    } else if (getEntidade().getProc_justificativa().getCid_secundario().getCid().equals("")) {
                        getEntidade().getProc_justificativa().setCid_secundario(cidTemp);
                    } else if (getEntidade().getProc_justificativa().getCid_causas_assoc().getCid().equals("")) {
                        getEntidade().getProc_justificativa().setCid_causas_assoc(cidTemp);
                    } else {
                        F.mensagem("", "Limite de CIDs atingido!", FacesMessage.SEVERITY_INFO);
                        tempT = true;
                    }
                }
            }

            cidTemp = new Cid();
            cids.clear();
            //FabricaDeConexoes.fecharConecxao();

            //  request.addCallbackParam("FA", tempT);
        }

    }

    public void selecionarTipoDoc(int tipo) {
        if (tipo == 0 || tipo == 1) {
            getEntidade().getSolicitante().setTipo_doc(tipo);
        }
    }

    public void btProcedimentoConfirmar() throws ErroSistema {
        RequestContext request = RequestContext.getCurrentInstance();
        // se for null e não for preenchimento rapido

        if (procedimentosTemp.getCodigo().isEmpty() && getIsPreenchimentoRapido() == false) {
            if (getVitBuscaProcedimento().isEmpty()) {
                F.mensagem("", "Preencha o campo de busca!", FacesMessage.SEVERITY_WARN);
            } else {
                F.mensagem("", "Procedimento não encontrado!", FacesMessage.SEVERITY_WARN);

            }
            request.addCallbackParam("FA", false);
            procedimentosTemp.setQtd(0);
        } //        if (procedimentosTemp.getCodigo().isEmpty() && getIsPreenchimentoRapido() == false) {
        //            request.addCallbackParam("FA", false);
        //            F.mensagem("", "Procedimento não encontrado!", FacesMessage.SEVERITY_WARN);
        //
        //            procedimentosTemp.setQtd(0);
        //        }
        else {
            request.addCallbackParam("FA", true);

            procedimentosTemp.setQtd(itQuantidadeDlgAddProcedimento_qtd);

        }

        if (acao == 1 || acao == 2) {//1novo 1 editar
            if ((procedimentosTemp.getCodigo().equals(getEntidade().getP1().getCodigo()) && (!procedimentosTemp.getCodigo().isEmpty())
                    || (procedimentosTemp.getCodigo().equals(getEntidade().getP2().getCodigo()) && (!procedimentosTemp.getCodigo().isEmpty())
                    || (procedimentosTemp.getCodigo().equals(getEntidade().getP3().getCodigo()) && (!procedimentosTemp.getCodigo().isEmpty())
                    || (procedimentosTemp.getCodigo().equals(getEntidade().getP4().getCodigo()) && (!procedimentosTemp.getCodigo().isEmpty())
                    || (procedimentosTemp.getCodigo().equals(getEntidade().getP5().getCodigo()) && (!procedimentosTemp.getCodigo().isEmpty())
                    || procedimentosTemp.getCodigo().equals(getEntidade().getP6().getCodigo()) && (!procedimentosTemp.getCodigo().isEmpty()))))))) {
                request.addCallbackParam("FA", false);

                if ((!(getEntidade().getP1().getCodigo().equals("")))
                        && (!(getEntidade().getP2().getCodigo().equals("")))
                        && (!(getEntidade().getP3().getCodigo().equals("")))
                        && (!(getEntidade().getP4().getCodigo().equals("")))
                        && (!(getEntidade().getP5().getCodigo().equals("")))
                        && (!(getEntidade().getP6().getCodigo().equals("")))) {

                    F.mensagem("", "Limite de procedimentos atingido!", FacesMessage.SEVERITY_WARN);
                    F.mensagem("Procedimento já inserido!", "Selecione um Procedimento diferente!", FacesMessage.SEVERITY_WARN);
                } else {

                    F.mensagem("Procedimento já inserido!", "Selecione um Procedimento diferente!", FacesMessage.SEVERITY_WARN);
                }

            } else {

                if (getEntidade().getP1().getCodigo().equals("")) {
                    getEntidade().setP1(procedimentosTemp);
                    getEntidade().getProc_justificativa().setCid_principal(new Cid());
                    getEntidade().getProc_justificativa().setCid_secundario(new Cid());
                    getEntidade().getProc_justificativa().setCid_causas_assoc(new Cid());
                    setRedblackcamp18("black");
                } else if (getEntidade().getP2().getCodigo().equals("")) {
                    getEntidade().setP2(procedimentosTemp);
                } else if (getEntidade().getP3().getCodigo().equals("")) {
                    getEntidade().setP3(procedimentosTemp);
                } else if (getEntidade().getP4().getCodigo().equals("")) {
                    getEntidade().setP4(procedimentosTemp);
                } else if (getEntidade().getP5().getCodigo().equals("")) {
                    getEntidade().setP5(procedimentosTemp);
                } else if (getEntidade().getP6().getCodigo().equals("")) {
                    getEntidade().setP6(procedimentosTemp);
                } else {
                    request.addCallbackParam("FA", true);

                    F.mensagem("", "Limite de procedimentos atingido!", FacesMessage.SEVERITY_WARN);
                }
            }
            procedimentosTemp = new Procedimento_sus();
            limparDlgAddProcedimento();
        }
        itQuantidadeDlgAddProcedimento_qtd = 1;
        itQuantidadeDlgPreencherRapido_qtd = 1;
        //FabricaDeConexoes.fecharConecxao();
    }

    //formulario.jsf e formulario_editar.jsf
    public void limparDlgAddProcedimento() {
        vsomProcedimento = "";
        procedimentos_susItem.clear();
    }

    public void sustituirProced() {
        procedimentosTemp.setQtd(itQuantidadeDlgAddProcedimento_qtd);

        getEntidade().setP1(procedimentosTemp);

        procedimentosTemp = new Procedimento_sus();
    }

    public void substituirCid() {
        getEntidade().getProc_justificativa().setCid_principal(cidTemp);
        cidTemp = new Cid();
    }

    public void btOkPreenchimentoRapido() throws ErroSistema {

//iguala as quantidades do preenchimento rapido e do add procedimento
        itQuantidadeDlgAddProcedimento_qtd = itQuantidadeDlgPreencherRapido_qtd;
        setIsPreenchimentoRapido(true);

        sustituirProced();
        substituirCid();

        if (!pacienteTemp.getNum_prontuario().isEmpty()) {
            verificaCamposPacienteVoid();
        } else {
            if (txtBuscaNumPront.isEmpty()) {
                F.mensagem("", "Preencher o campo de busca!", FacesMessage.SEVERITY_WARN);
            } else {
                F.mensagem("", "Informe dados do paciente!", FacesMessage.SEVERITY_WARN);
            }
        }
        closeDlgPreencherRapido();
    }

    public void btImprimir(Usuario logado) throws ErroSistema {
        if (!verificarApacRepetida()) {

            if (getEntidade().getPag2().getFormulario_id() < 0) {//se for menor que 0 entao pag2 nao ta preenchida salvar so pag1

                if (getEntidade().getStatus().getId_status() != 3) {
                    setEntidade(getDao().buscaId(getEntidade().getId_formulario() + ""));
                    getEntidade().buscaProcedimentosForm();
                    getEntidade().getStatus().setId_status(3);
                    getDao().atualizar(getEntidade());
                    F.mensagem("Formulário N° " + getEntidade().getMascaraId() + " finalizado com sucesso!", "", FacesMessage.SEVERITY_INFO);

                }

                // printFolha1();
            } else {
                btImprimirF2(logado);

            }
        } 
        //FabricaDeConexoes.fecharConecxao();
    }

    private void btImprimirF2(Usuario logado) throws ErroSistema {
        if (getEntidade().getStatus().getId_status() != 3) {
            getEntidade().getStatus().setId_status(3);
            getDao().atualizar(getEntidade());
            F.mensagem("Formulario N° " + getEntidade().getMascaraId() + " finalizado com sucesso!", "", FacesMessage.SEVERITY_INFO);
        }
        // printFolha1e2();
        //FabricaDeConexoes.fecharConecxao();
    }

    public void printFolha1() {

        //id da folha 1 maior que 0 e id da folha dois menor que 1
        if (getEntidade().getId_formulario() > 0 && getEntidade().getPag2().getFormulario_id() < 1) {
            //cria uma lista de objetos para gerar a  pdf
            List<Object> l = new ArrayList<>();
            l.add(getEntidade());
            Relatorios r = new Relatorios("/hujbb/informatica/apac/util/relatorio/apac.jpg");

            r.gerarPDF(l, "report1");
        } else if (getEntidade().getPag2().getFormulario_id() > 0) {
            printFolha1e2();
        }

    }

    public void printFolha1e2() {
        if (getEntidade().getId_formulario() > 0 && getEntidade().getPag2().getFormulario_id() > 0) {

            //cria uma lista de objetos para gerar a  pdf
            List<Object> l = new ArrayList<>();
            l.add(getEntidade());
            Relatorios r = new Relatorios("/hujbb/informatica/apac/util/relatorio/apac.jpg", "/hujbb/informatica/apac/util/relatorio/apac2.jpg");

            r.gerarPDF(l, "report2");
        }
    }

    public void btLimparP1() {
        getEntidade().setP1(new Procedimento_sus());
        getEntidade().getProc_justificativa().setCid_principal(new Cid());
        getEntidade().getProc_justificativa().setCid_secundario(new Cid());
        getEntidade().getProc_justificativa().setCid_causas_assoc(new Cid());
    }

    public void btLimparP2() {
        getEntidade().setP2(new Procedimento_sus());
    }

    public void btLimparP3() {
        getEntidade().setP3(new Procedimento_sus());
    }

    public void btLimparP4() {
        getEntidade().setP4(new Procedimento_sus());
    }

    public void btLimparP5() {
        getEntidade().setP5(new Procedimento_sus());
    }

    public void btLimparP6() {
        getEntidade().setP6(new Procedimento_sus());
    }

    public void btLimparCid2() {
        getEntidade().getProc_justificativa().setCid_secundario(new Cid());
    }

    public void btLimparCid3() {
        getEntidade().getProc_justificativa().setCid_causas_assoc(new Cid());
    }

    //botao adicionar cid  do formulario principal
    public void btAddCid() {
        cids = new ArrayList<>();
    }

    public void btAddProcedimento() {
        //adiciona na lista todos os procedimentos

        if (getEntidade().getP1().equals(new Procedimento_sus())
                || getEntidade().getP2().equals(new Procedimento_sus())
                || getEntidade().getP3().equals(new Procedimento_sus())
                || getEntidade().getP4().equals(new Procedimento_sus())
                || getEntidade().getP5().equals(new Procedimento_sus())
                || getEntidade().getP6().equals(new Procedimento_sus())) {//if 1

            List<Procedimento_sus> l = new ArrayList<>();
            if (getEntidade().getP1() != new Procedimento_sus()) {
                l.add(getEntidade().getP1());
            }
            if (getEntidade().getP2() != new Procedimento_sus()) {
                l.add(getEntidade().getP2());
            }
            if (getEntidade().getP3() != new Procedimento_sus()) {
                l.add(getEntidade().getP3());
            }
            if (getEntidade().getP4() != new Procedimento_sus()) {
                l.add(getEntidade().getP4());
            }
            if (getEntidade().getP5() != new Procedimento_sus()) {
                l.add(getEntidade().getP5());
            }
            if (getEntidade().getP6() != new Procedimento_sus()) {
                l.add(getEntidade().getP6());
            }
            Procedimento_susBean.listaStatica = l;
            F.abrirDlgPreenchimentoRapido("dlg/dlgaddprocedimento", 800, 400, true);
        } else {//else if 1
            F.mensagem("", "Limite de Procedimentos atingido!", FacesMessage.SEVERITY_WARN);
        }

        rendereMsgNaoEncontrado = false;
        procedimentosTemp.setQtd(0);
        procedimentosTemp = new Procedimento_sus();
        procedimentos_susItem.clear();
    }

    public void verificaCamposPacienteVoid() throws ErroSistema {

        getEntidade().setPaciente(pacienteTemp);
        pacienteTemp = new Paciente();
        closeDlgBuscaProntuario();
        verificCamposPaciente();
    }

    //fim de funcoes de botoes
    public void sorChange65() {
        if (!getEntidade().getPag2().isTrate_anteriores_quimio()) {
            limparTratAnteriorQuimio();
        }
    }

    public void btNovaBusca() {
        F.redirecionarPagina("consultaLaudo.jsf");
    }

    private void log(Usuario logado, int acao) throws ErroSistema {
        F.log(logado, getEntidade().nun_entidade_bd(), getEntidade().getId_formulario(), acao, "", "");
    }

    public void selecionarPaciente(String prontuario) throws ErroSistema {
        buscaProntuarioAghu(prontuario);

    }

    public Autorizacao getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(Autorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getEstabelecimentoSelecionado() {
        return estabelecimentoSelecionado;
    }

    public void setEstabelecimentoSelecionado(String estabelecimentoSelecionado) {

        this.estabelecimentoSelecionado = estabelecimentoSelecionado;
    }

    public ArrayList<SelectItem> getEstabelecimentos() {
        return estabelecimentos;
    }

    //busca todos os estabelecimentos
    public void setEstabelecimentos(ArrayList<SelectItem> estabelecimentos) throws ErroSistema {
        if (estabelecimentos == null) {
            estabelecimentos = new ArrayList<>();
            List<Estabelecimento_de_saude> es = new Estabelecimento_de_saudeDAO().buscar("WHERE 1");
            estabelecimentos.add(new SelectItem("-999", "SELECIONE O ESTABELECIMENTO DE SAÚDE"));
            for (int i = 0; i < es.size(); i++) {
                estabelecimentos.add(new SelectItem(es.get(i).getId_estabelecimento_saude() + "", es.get(i).getNome().toUpperCase()));
            }
        }//fim if
        this.estabelecimentos = estabelecimentos;

    }

    //seta o paciente do formulario com o resultado da busca no aghu
    public void buscaProntuarioAghu(String n_prontuario) throws ErroSistema {

        n_prontuario = n_prontuario.trim();

        n_prontuario = n_prontuario.replaceAll("[^0-9]", "9");

// F correponde a tabela agh.aip_pacientes no paciente dao
        txtBuscaNumPront = "";

        if (n_prontuario.length() > 3) {

            List<Paciente> lp = new PacienteDAO().buscarAghuBarros(" p.prontuario = '" + n_prontuario + "' ");
            Paciente p = new Paciente();
            if (lp != null && lp.size() > 0) {
                p = lp.get(0);
                dlgBuscaPaciVisible = false;
                if (p.getData_obito() != null) {
                    txtBuscaNumPront = "Paciente consta como morto!";
                }
            } else {
                dlgBuscaPaciVisible = true;
                txtBuscaNumPront = "Nenhum registro encontrado...";
            }

            pacienteTemp = p;

        }
        //FabricaDeConexoes.fecharConecxao();
    }

    //limpa os ddos do paciente do formulario
    public void limpPaciente() {
        getEntidade().setPaciente(new Paciente());
    }

    public String getPacienteSelecionado() {
        return pacienteSelecionado;
    }

    public void setPacienteSelecionado(String pacienteSelecionado) {
        this.pacienteSelecionado = pacienteSelecionado;
    }

    public ArrayList<SelectItem> getPacientesAghuBarros() {
        return pacientesAghuBarros;
    }

    public void setPacientesAghuBarros(ArrayList<SelectItem> pacientesAghuBarros) {
        this.pacientesAghuBarros = pacientesAghuBarros;
    }

    //busca os pacientes na base aghu que iniciam com parte do nome fornecido setabdo o primeiro valo no formulario
    public void setPacientesAghuBarros(ArrayList<SelectItem> pacientesAghuBarros, String nome) throws ErroSistema {
        nome = nome.trim();
        if (pacientesAghuBarros == null) {

            // getEntidade().setPaciente(new Paciente());//zera o paciente
            pacientesAghuBarros = new ArrayList<>();//zera a lista
            if (nome.length() > 2) {//if1
                List<Paciente> paci = new PacienteDAO().buscarAghuBarros(" p.nome ILIKE '" + nome + "%' ORDER BY p.nome LIMIT 10");
                if (paci != null && paci.size() > 0) {

                    buscaProntuarioAghu(paci.get(0).getNum_prontuario());

                    for (int i = 0; i < paci.size(); i++) {
                        pacientesAghuBarros.add(new SelectItem(paci.get(i).getNum_prontuario() + "", paci.get(i).getNome().toUpperCase()));
                    }
                }

                if (pacientesAghuBarros.isEmpty()) {
                    setTxtBuscaNumPront("Nenhum registro encontrado!");
                    limpMsgNaoEncontrado();
                } else {
                    setTxtBuscaNumPront("");
                }

            } else {//if1
                setTxtBuscaNumPront("");
            }

        }
        this.pacientesAghuBarros = pacientesAghuBarros;
    }

    public void limpMsgNaoEncontrado() {

        pacienteTemp.setNum_prontuario("");

        pacienteTemp.setNome("");
        pacienteTemp.setNome_mae("");
        pacienteTemp = new Paciente();

    }

    //data de hoje mais 120 dias
    public Date gethJ() {
        //hJ = F.somarDiasData(new Date(), 120);
        return hJ;
    }

    public Date gethJtil120() {
        hJtil120 = F.somarDiasData(new Date(), 120);
        return hJtil120;
    }

    public void sethJ(Date hJ) {
        this.hJ = hJ;
    }

    public String getTxtBuscaNumPront() {

        return txtBuscaNumPront;
    }

    public void setTxtBuscaNumPront(String txtBuscaNumPront) {
        this.txtBuscaNumPront = txtBuscaNumPront;
    }

    public boolean isDlgBuscaPaciVisible() {
        return dlgBuscaPaciVisible;
    }

    public void setDlgBuscaPaciVisible(boolean dlgBuscaPaciVisible) {
        this.dlgBuscaPaciVisible = dlgBuscaPaciVisible;
    }

    public String getQtdProcedimentoSelecionado() {
        return qtdProcedimentoSelecionado;
    }

    public void setQtdProcedimentoSelecionado(String qtdProcedimentoSelecionado) {
        this.qtdProcedimentoSelecionado = qtdProcedimentoSelecionado;
    }

    public ArrayList<SelectItem> getProcedimentos_susItem() {
        return procedimentos_susItem;
    }

    public void setProcedimentos_susItem(ArrayList<SelectItem> procedimentos_susItem) {
        this.procedimentos_susItem = procedimentos_susItem;
    }

    public List<Procedimento_sus> getProcedimentos_sus() {
        return procedimentos_sus;
    }

    public void setProcedimentos_sus(List<Procedimento_sus> procedimentos_sus) {
        this.procedimentos_sus = procedimentos_sus;
    }

    public void buscaProcedimento(String busca) throws ErroSistema {
        rendereMsgNaoEncontrado = false;
        busca = busca.trim();
        if (busca.length() > 2) {

            procedimentos_sus = new ArrayList<>();
            procedimentos_susItem = new ArrayList<>();

            procedimentos_sus = new Procedimento_susDAO().buscar("WHERE (procedimento_sus.`nome` LIKE '%" + busca + "%' OR procedimento_sus.`codigo` LIKE '%" + busca + "%') AND (procedimento_sus.`dt_competencia` = " + F.getCompetencia().getCompetencia() + ") ORDER BY  procedimento_sus.`nome`");
            //System.out.println(F.getCompetencia().competenciaString());
            if (procedimentos_sus.size() > 0) {
                procedimentosTemp = procedimentos_sus.get(0);//seta o primeiro valor da lista como

                for (int i = 0; i < procedimentos_sus.size(); i++) {
                    procedimentos_susItem.add(new SelectItem(i + "", (procedimentos_sus.get(i).getNome().length() < 100) ? procedimentos_sus.get(i).getCodigo() + " - " + procedimentos_sus.get(i).getNome().toUpperCase() : procedimentos_sus.get(i).getCodigo() + " - " + procedimentos_sus.get(i).getNome().substring(0, 99).toUpperCase()));
                }
            } else {
                procedimentosTemp = new Procedimento_sus();

            }
            rendereMsgNaoEncontrado = procedimentos_susItem.isEmpty();

        } else {
            if (!busca.isEmpty()) {
                renderMsgAddProcBlank = true;
            } else {
                renderMsgAddProcBlank = false;
            }
        }

    }

    //evento da selecao do procedimento no combo box
    public void selecionarProcedimento(String selecao) {
        try {
            int i = Integer.parseInt(selecao);
            procedimentosTemp = new Procedimento_sus();
            if (i != -1) {//if1
                procedimentosTemp = procedimentos_sus.get(i);
            }//fim if 1
        } catch (NumberFormatException e) {
            F.setMsgErro("Formulariobean:selecionarProcedimento():" + e);
        }

    }

    public Procedimento_sus getProcedimentosTemp() {

        return procedimentosTemp;
    }

    public void setProcedimentosTemp(Procedimento_sus procedimentosTemp) {
        this.procedimentosTemp = procedimentosTemp;
    }

    public ArrayList<SelectItem> getCids() {
        return cids;
    }

    public void setCids(ArrayList<SelectItem> cids) {
        this.cids = cids;
    }

    public String getSomProcedDlgPreencRapido() {
        return somProcedDlgPreencRapido;
    }

    public void setSomProcedDlgPreencRapido(String somProcedDlgPreencRapido) {
        this.somProcedDlgPreencRapido = somProcedDlgPreencRapido;
    }

    public void chengeCidPreenchiRapido(String cid) throws ErroSistema {
        if (!cid.isEmpty()) {
            List<Cid> c = new CidDAO().buscar("WHERE cid = '" + cid + "'");
            if (!c.isEmpty()) {
                cidTemp = c.get(0);
            }
        }
        //FabricaDeConexoes.fecharConecxao();
    }

    public void buscaCids(String cid) throws ErroSistema {
        cid = cid.trim();
        rendereMsgNaoEncontrado = false;
        if (cid.length() > 1) {
            String sql = "WHERE (cid.`nome` LIKE '%" + cid + "%') OR (cid.`cid` LIKE '%" + cid + "%') ORDER BY cid.`nome`";
            cids = new ArrayList<>();

            List<Cid> cd = new CidDAO().buscar(sql);
            if (cd != null && cd.size() > 0) {
                cidTemp = cd.get(0);//seta o primeiro valor da lista como

                for (int i = 0; i < cd.size(); i++) {
                    cids.add(new SelectItem(cd.get(i).getCid() + "", cd.get(i).getCid() + " - " + cd.get(i).getNome().toUpperCase()));
                }
            } else {
                cidTemp = new Cid();
            }

            rendereMsgNaoEncontrado = cids.isEmpty();
        }

    }

    public void buscaForm(String id) throws ErroSistema {
        setEntidade(getDao().buscaId(id));
        getEntidade().setPag2(null);//null é a flag pra buscar os dados no banco na funcao setPag2

        if (getEntidade().getId_formulario() == -1) {
            F.mensagem("", "Nenhum formulário encontrado!", FacesMessage.SEVERITY_INFO);
        } else {
            buscaProcedimentosForm();
        }

    }

    protected void buscaProcedimentosForm() throws ErroSistema {
        if (getEntidade() != null) {
            List<Formulario_has_procedimento_sus> procHasFormaList = new Formulario_has_procedimento_susDAO().buscar("WHERE `formulario_id_formulario` = " + getEntidade().getId_formulario());
            if (procHasFormaList != null) {
                List<Procedimento_sus> procedimentos = new ArrayList<>();
                for (Formulario_has_procedimento_sus procHasFormaList1 : procHasFormaList) {
                    Procedimento_sus ps = new Procedimento_susDAO().buscaIdComp(procHasFormaList1.getProcedimento_sus().getCodigo() + "", procHasFormaList1.getProcedimento_sus().getDt_competencia());
                    ps.setQtd(procHasFormaList1.getQuantidade());
                    procedimentos.add(ps);
                }
                getEntidade().addProcedimento(procedimentos);
            }
        }
        //FabricaDeConexoes.fecharConecxao();
    }

    public void btSalvarAlteracoes(Usuario usuario) throws ErroSistema {
        flagSalvarApenasPag1 = renderePag1;

        limparDialogos();
        boolean alterouPaciente = false;
        if (getEntidade().getId_formulario() > 0) {
            Formulario ftemp = getDao().buscaId(getEntidade().getId_formulario() + "");//formulario original antes das alteracoes

            if (ftemp == null) {//if 1
                ftemp = new Formulario();
            } //if1
            //se houve alteracao no formulario
            if (!getEntidade().equals(ftemp)) {//if4
                //se houve mudanca de paciente
                if (!getEntidade().getPaciente().equals(ftemp.getPaciente())) {//if 5
                    alterouPaciente = true;
                    Paciente p = new PacienteDAO().salvar(getEntidade().getPaciente());
                    if (p != null) {//if6
                        getEntidade().setPaciente(p);
                    }//if 6
                }//if 5
                if (verificarPreenchimento()) {//if7

                    //se o formulario F2 nao for vazio e o id do formulario F2 for menor que 1(nao foi salvo anteriormente)
                    if (!getEntidade().getPag2().equals(new Formulario_f2()) && getEntidade().getPag2().getFormulario_id() < 1) {
                        getEntidade().getPag2().setFormulario_id(getEntidade().getId_formulario()); // copia id da F1 para F2
                        new Formulario_f2DAO().salvar(getEntidade().getPag2());
                    }

                    if (getDao().atualizar(getEntidade()) != null) {//if 2
                        if (!alterouPaciente) {
                            new PacienteDAO().deletar(ftemp.getPaciente());
                        }
                        log(usuario, 2);
                        F.mensagem("Formulário Nº: " + getEntidade().getMascaraId(), "atualizado!", FacesMessage.SEVERITY_INFO);
                    } else {//if 2
                        F.mensagem("Erro ao editar formulário!", "", FacesMessage.SEVERITY_WARN);
                    }//if2
                }//IF7
            }//if4
        }
        //FabricaDeConexoes.fecharConecxao();
    }

    public void btDlgAutorizacaoSalvar(Usuario usuario) throws ErroSistema {
        if (verificarPreenchimentoAutorizacao()) {
            //adiciona uma autorizacao
            autorizacao = new AutorizacaoDAO().salvar(autorizacao);

            //atualiza o formulario com a nova autorizacao
            getEntidade().setAutorizacao(autorizacao);
            getEntidade().setStatus(new Status(5, "autorizado"));
            getDao().atualizar(getEntidade());
            F.mensagem("", "Autorização registrada com sucesso!", FacesMessage.SEVERITY_INFO);
            log(usuario, 6);

        }
        //FabricaDeConexoes.fecharConecxao();
    }

    public String dataEdit(Date d) {
        return F.dataString(d);
    }

    private boolean verificarPreenchimentoAutorizacao() {
        if (autorizacao.getNum_autorizacao().isEmpty()) {
            F.mensagem("", "Informe o Número da autorização (APAC)", FacesMessage.SEVERITY_WARN);
            return false;
        }
        if (autorizacao.getData_val_ini() == null || autorizacao.getData_val_fim() == null) {
            F.mensagem("", "Informe corretamente o período de validade da APAC", FacesMessage.SEVERITY_WARN);
            return false;
        }

        return true;
    }

    public Cid getCidTemp() {
        return cidTemp;
    }

    public void setCidTemp(Cid cidTemp) {
        this.cidTemp = cidTemp;
    }

    public boolean isDisableBtSalvar() {
        disableBtSalvar = (getEntidade().getStatus().getId_status() > 2);
        return disableBtSalvar;
    }

    public void setDisableBtSalvar(boolean disableBtSalvar) {
        this.disableBtSalvar = disableBtSalvar;
    }

    public boolean isRendereBtInformAutorizacao() {
        //status menor q 3(impresso) ou maior 4(enviado pro sus)
        if ((getEntidade().getId_formulario() == -1 || getEntidade().getStatus().getId_status() < 3 || getEntidade().getStatus().getId_status() > 4 || acao != 4)) {
            rendereBtInformAutorizacao = false;
        } else {
            rendereBtInformAutorizacao = true;
        }
        return rendereBtInformAutorizacao;
    }

    public void setDisableBtInformAutorizacao(boolean disableBtInformAutorizacao) {
        this.rendereBtInformAutorizacao = disableBtInformAutorizacao;
    }

    public boolean isDisableBtSalvarDlgAutorizacao() {
        disableBtSalvarDlgAutorizacao = false;
        if (autorizacao.getNum_autorizacao().isEmpty()) {
            disableBtSalvarDlgAutorizacao = true;
        }
        return disableBtSalvarDlgAutorizacao;
    }

    public void setDisableBtSalvarDlgAutorizacao(boolean disableBtSalvarDlgAutorizacao) {
        this.disableBtSalvarDlgAutorizacao = disableBtSalvarDlgAutorizacao;
    }

    public boolean isRenderebtEditarFsAutorizacao() {
        int status = getEntidade().getStatus().getId_status();
        renderebtEditarFsAutorizacao = false;
        if (acao == 2 && status > 2 && status != 99) {//acao = 2 (editar)
            renderebtEditarFsAutorizacao = true;

        } else {

        }

        return renderebtEditarFsAutorizacao;
    }

    public void setRenderebtEditarFsAutorizacao(boolean renderebtEditarFsAutorizacao) {
        this.renderebtEditarFsAutorizacao = renderebtEditarFsAutorizacao;
    }

    public ArrayList<SelectItem> getTipoDocItem() {
        tipoDocItem = new ArrayList<>();
        tipoDocItem.add(new SelectItem(0 + "", "CNS"));
        tipoDocItem.add(new SelectItem(1 + "", "CPF"));
        return tipoDocItem;
    }

    public void setTipoDocItem(ArrayList<SelectItem> tipoDocItem) {
        this.tipoDocItem = tipoDocItem;
    }

    public boolean isRenderebtAlterarData() {
        renderebtAlterarData = false;
        if (!isBusca() && getEntidade().getId_formulario() != -1 && getEntidade().getStatus().getId_status() < 3 && getEntidade().getStatus().getId_status() != -9) {
            renderebtAlterarData = true;
        }
        return renderebtAlterarData;
    }

    public void setRenderebtAlterarData(boolean renderebtAlterarData) {
        this.renderebtAlterarData = renderebtAlterarData;
    }

    public String getValueBtAddCid() {

        if (acao == 2) {
            valueBtAddCid = "EDITAR";
        } else {
            valueBtAddCid = "ADICIONAR CID";
        }

        return valueBtAddCid;
    }

    public void setValueBtAddCid(String valueBtAddCid) {
        this.valueBtAddCid = valueBtAddCid;
    }

    public boolean isRenderebtAddCid() {
        renderebtAddCid = false;
        if (isInseri() || (getEntidade().getStatus().getId_status() == 2 && acao == 2)) {
            renderebtAddCid = true;
        }
        return renderebtAddCid;
    }

    public void setRenderebtAddCid(boolean renderebtAddCid) {
        this.renderebtAddCid = renderebtAddCid;
    }

    public String getValueBtAddProcedimento() {

        if (acao == 2) {
            valueBtAddProcedimento = "EDITAR";
        } else {
            valueBtAddProcedimento = "ADICIONAR PROCEDIMENTO";
        }

        return valueBtAddProcedimento;
    }

    public void setValueBtAddProcedimento(String valueBtAddProcedimento) {
        this.valueBtAddProcedimento = valueBtAddProcedimento;
    }

    public String getValueBtAddPaciente() throws ErroSistema {
        closeDlgBuscaProntuario();
        if (acao == 2) {
            valueBtAddPaciente = "EDITAR";
        } else {
            valueBtAddPaciente = "BUSCAR";
        }
        return valueBtAddPaciente;
    }

    public void setValueBtAddPaciente(String valueBtAddPaciente) {
        this.valueBtAddPaciente = valueBtAddPaciente;
    }

    public boolean isRenderebtImprimir() {

        renderebtImprimir = false;
        //id form>0 e status do form >1 (1 = salvo) e id form <4 (4 =enviado pro sus) e  quando o estado da tela nao for busca
        if (getEntidade().getId_formulario() > 0 && getEntidade().getStatus().getId_status() == 2 && !isBusca()) {
            renderebtImprimir = true;
        }
        return renderebtImprimir;
    }

    public void setRenderebtImprimir(boolean renderebtImprimir) {

        this.renderebtImprimir = renderebtImprimir;
    }

    public boolean isRenderebtImprimir2() {
        //id form >0 e estado da tela for busca 
        renderebtImprimir2 = getEntidade().getId_formulario() > 0 && getEntidade().getStatus().getId_status() > 2;
        return renderebtImprimir2;
    }

    public void setRenderebtImprimir2(boolean renderebtImprimir2) {
        this.renderebtImprimir2 = renderebtImprimir2;
    }

    public boolean isRendereMsgNaoEncontrado() {
        return rendereMsgNaoEncontrado;
    }

    public void setRendereMsgNaoEncontrado(boolean rendereMsgNaoEncontrado) {
        this.rendereMsgNaoEncontrado = rendereMsgNaoEncontrado;
    }

    public int getItQuantidadeDlgPreencherRapido_qtd() {
        return itQuantidadeDlgPreencherRapido_qtd;
    }

    public void setItQuantidadeDlgPreencherRapido_qtd(int itQuantidadeDlgPreencherRapido_qtd) {
        this.itQuantidadeDlgPreencherRapido_qtd = itQuantidadeDlgPreencherRapido_qtd;
    }

    public int getItQuantidadeDlgAddProcedimento_qtd() {
        return itQuantidadeDlgAddProcedimento_qtd;
    }

    public void setItQuantidadeDlgAddProcedimento_qtd(int itQuantidadeDlgAddProcedimento_qtd) {
        this.itQuantidadeDlgAddProcedimento_qtd = itQuantidadeDlgAddProcedimento_qtd;
    }

    public boolean isRenderePag1() {
        return renderePag1;
    }

    public void setRenderePag1(boolean renderePag1) {
        this.renderePag1 = renderePag1;
    }

    public String getLegend_fsMaster() {
        if (renderePag1) {
            legend_fsMaster = "Laudo Procedimento Folha 1";
        } else {
            legend_fsMaster = "Laudo Procedimento Folha 2";
        }
        return legend_fsMaster;
    }

    public String getLegend_fsMasterEdit() {
        if (renderePag1) {
            if (isEdita()) {
                legend_fsMaster = "Editar Laudo Procedimento Folha 1";
            } else {
                legend_fsMaster = "Laudo Procedimento Folha 1";
            }
        } else {
            if (isEdita()) {
                legend_fsMaster = "Editar Laudo Procedimento Folha 2";

            } else {
                legend_fsMaster = "Laudo Procedimento Folha 2";
            }
        }
        return legend_fsMaster;
    }

    public void setLegend_fsMaster(String legend_fsMaster) {
        this.legend_fsMaster = legend_fsMaster;
    }

    public boolean isRendereBtPreencPag2() {
        //se estiver buscando

        if (isBusca()) {//if 1
            if (getEntidade() == null) {//if 3
                rendereBtPreencPag2 = false;
            } else if (getEntidade().getPag2() != null) {//if 4
                //se o id da pag 2 for menor que o quer dizer que nao tem folha 2 na busca
                if ((getEntidade().getPag2().getFormulario_id() < 1)) {//if 5
                    rendereBtPreencPag2 = false;
                } else {//if 5 (id >=1)
                    rendereBtPreencPag2 = true;
                }
            } else {//if 4 (pag2 nula)
                rendereBtPreencPag2 = false;
            }//if4
        } else {//if1 nao estiver buscando
            //perfil onco ou adm
            if (isInseri()) {//if6
                //perfil 4 onco ou 3 
                if ((getLogado().getPerfil().getId_perfil() == 4 || getLogado().getPerfil().getId_perfil() == 3)) {//if 2
                    rendereBtPreencPag2 = true;
                } else {//else if 2 
                    rendereBtPreencPag2 = false;
                }
            } else {//fi else if6  (editando)
                //perfil onco ou adm
                if ((getLogado().getPerfil().getId_perfil() == 4 || getLogado().getPerfil().getId_perfil() == 3)) {//if 2
                    rendereBtPreencPag2 = true;
                } else {//else if 2 
                    rendereBtPreencPag2 = false;
                }
            }

        }
        return rendereBtPreencPag2;
    }

    public void setRendereBtPreencPag2(boolean rendereBtPreencPag2) {
        this.rendereBtPreencPag2 = rendereBtPreencPag2;
    }

    public boolean isDisabledBtPreencPag2() {
        if (getEntidade().getId_formulario() < 0) {
            disabledBtPreencPag2 = true;
        } else {
            disabledBtPreencPag2 = false;
        }
        return disabledBtPreencPag2;
    }

    public void setDisabledBtPreencPag2(boolean disabledBtPreencPag2) {
        this.disabledBtPreencPag2 = disabledBtPreencPag2;
    }

    public String getValueBtPreencPag2() {
        if (renderePag1) {

            valueBtPreencPag2 = "Avançar";

        } else {

            valueBtPreencPag2 = "Voltar";

        }
        return valueBtPreencPag2;
    }

    public void setValueBtPreencPag2(String valueBtPreencPag2) {
        this.valueBtPreencPag2 = valueBtPreencPag2;
    }

    public boolean isRendereFsRadioterapiapag2() {
        rendereFsRadioterapiapag2 = getEntidade().getPag2().isQuimio_radio();
        if (rendereFsRadioterapiapag2) {//false ==  quimio true == radio
            limparDadosQuimio();
        } else {
            limparDadosRadio();
        }
        return rendereFsRadioterapiapag2;
    }

    public void setRendereFsRadioterapiapag2(boolean rendereFsRadioterapiapag2) {
        this.rendereFsRadioterapiapag2 = rendereFsRadioterapiapag2;
    }

    public int getSelecaoCidPag2() {

        return selecaoCidPag2;
    }

    public void setSelecaoCidPag2(int selecaoCidPag2) {

        cids.clear();
        cidTemp = new Cid();
        this.selecaoCidPag2 = selecaoCidPag2;
    }

    public boolean isRenderebtBtSalvar() {
        if (isInseri()) {//if 1
            renderebtBtSalvar = (getLogado().getPerfil().getId_perfil() == 2 || getEntidade().getPag2().getFormulario_id() > 0);// solicitante 
            if (!renderePag1) {//if 2 se pag 2 estiver renderizada 
                renderebtBtSalvar = (getLogado().getPerfil().getId_perfil() == 4 || getLogado().getPerfil().getId_perfil() == 3);//se perfil for solicitante onco ou adm
            }//if2
        } else {//if 1
            return false;
        }//if 1
        return renderebtBtSalvar;
    }

    public void setRenderebtBtSalvar(boolean renderebtBtSalvar) {
        this.renderebtBtSalvar = renderebtBtSalvar;
    }

    public boolean isRenderebtBtSalvar2() {
        //(pag 1 renderizada)  e (perfil onco ou adm)
        renderebtBtSalvar2 = (isInseri() && renderePag1 && getEntidade().getPag2().getFormulario_id() < 1) && (getLogado().getPerfil().getId_perfil() == 4 || getLogado().getPerfil().getId_perfil() == 3);// solicitante ou admonistrador  e pag 1 estiver renderizada
        return renderebtBtSalvar2;
    }

    public void setRenderebtBtSalvar2(boolean renderebtBtSalvar2) {
        this.renderebtBtSalvar2 = renderebtBtSalvar2;
    }

    public boolean isFlagSalvarApenasPag1() {
        if (getLogado().getPerfil().getId_perfil() == 2) {//soliciante
            flagSalvarApenasPag1 = true;
        }
        if (!renderePag1) {  // se pagina1 nao estiver renderizada

            flagSalvarApenasPag1 = false;
        }
        return flagSalvarApenasPag1;
    }

    public void setFlagSalvarApenasPag1(boolean flagSalvarApenasPag1) {
        this.flagSalvarApenasPag1 = flagSalvarApenasPag1;
    }

    public boolean isSomenteLeituraPag2() {
        ///status 3 laudo fechado  ou (estado tela inserir e status salvo) ou estado tela buscar
        somenteLeituraPag2 = ((getEntidade().getStatus().getId_status() > 2) || (isBusca()));

        return somenteLeituraPag2;
    }

    public void setSomenteLeituraPag2(boolean somenteLeituraPag2) {
        this.somenteLeituraPag2 = somenteLeituraPag2;
    }

    public Paciente getPacienteTemp() {
        return pacienteTemp;
    }

    public void setPacienteTemp(Paciente pacienteTemp) {
        this.pacienteTemp = pacienteTemp;
    }

    public boolean isRenderebtBtPreenchimentoRapido() {
        renderebtBtPreenchimentoRapido = !isSomenteLeituraPag2() && isInseri();
        return renderebtBtPreenchimentoRapido;
    }

    public void setRenderebtBtPreenchimentoRapido(boolean renderebtBtPreenchimentoRapido) {
        this.renderebtBtPreenchimentoRapido = renderebtBtPreenchimentoRapido;
    }

    public String getVsOMBuscaCidPag2() {
        return vsOMBuscaCidPag2;
    }

    public void setVsOMBuscaCidPag2(String vsOMBuscaCidPag2) {
        this.vsOMBuscaCidPag2 = vsOMBuscaCidPag2;
    }

    public Boolean getIsPreenchimentoRapido() {
        return IsPreenchimentoRapido;
    }

    public void setIsPreenchimentoRapido(Boolean IsPreenchimentoRapido) {
        this.IsPreenchimentoRapido = IsPreenchimentoRapido;
    }

    public boolean isRendereBtSalvarAlteracao() {
        //se o estado da tela for inserir  e a pag 1 nao estiver renderizada
        rendereBtSalvarAlteracao = isEdita() && !isRenderePag1();
        return rendereBtSalvarAlteracao;
    }

    public void setRendereBtSalvarAlteracao(boolean rendereBtSalvarAlteracao) {
        this.rendereBtSalvarAlteracao = rendereBtSalvarAlteracao;
    }

    public String getVsomProcedimento() {
        if (vsomProcedimento == null) {
            vsomProcedimento = "";
        }
        return vsomProcedimento;
    }

    public void setVsomProcedimento(String vsomProcedimento) {
        this.vsomProcedimento = vsomProcedimento;
    }

    public String getScbmItem0() {
        return scbmItem0;
    }

    public void setScbmItem0(String scbmItem0) {
        this.scbmItem0 = scbmItem0;
    }

    public String getScbmItemI() {
        return scbmItemI;
    }

    public void setScbmItemI(String scbmItemI) {
        this.scbmItemI = scbmItemI;
    }

    public String getScbmItemII() {
        return scbmItemII;
    }

    public void setScbmItemII(String scbmItemII) {
        this.scbmItemII = scbmItemII;
    }

    public String getScbmItemIII() {
        return scbmItemIII;
    }

    public void setScbmItemIII(String scbmItemIII) {
        this.scbmItemIII = scbmItemIII;
    }

    public String getScbmItemVI() {
        return scbmItemVI;
    }

    public void setScbmItemVI(String scbmItemVI) {
        this.scbmItemVI = scbmItemVI;
    }

    public String getRedblackcamp56() {
        return redblackcamp56;
    }

    public void setRedblackcamp56(String redblackcamp56) {
        this.redblackcamp56 = redblackcamp56;
    }

    public String getRedblackcamp58() {
        return redblackcamp58;
    }

    public void setRedblackcamp58(String redblackcamp58) {
        this.redblackcamp58 = redblackcamp58;
    }

    public String getRedblackcamp59() {
        return redblackcamp59;
    }

    public void setRedblackcamp59(String redblackcamp59) {
        this.redblackcamp59 = redblackcamp59;
    }

    public String getRedblackcamp60() {
        return redblackcamp60;
    }

    public void setRedblackcamp60(String redblackcamp60) {
        this.redblackcamp60 = redblackcamp60;
    }

    public String getRedblackcamp61() {
        return redblackcamp61;
    }

    public void setRedblackcamp61(String redblackcamp61) {
        this.redblackcamp61 = redblackcamp61;
    }

    public String getRedblackcamp62() {
        return redblackcamp62;
    }

    public void setRedblackcamp62(String redblackcamp62) {
        this.redblackcamp62 = redblackcamp62;
    }

    public String getRedblackcamp63() {
        return redblackcamp63;
    }

    public void setRedblackcamp63(String redblackcamp63) {
        this.redblackcamp63 = redblackcamp63;
    }

    public String getRedblackcamp64() {
        return redblackcamp64;
    }

    public void setRedblackcamp64(String redblackcamp64) {
        this.redblackcamp64 = redblackcamp64;
    }

    public String getRedblackcamp66() {
        return redblackcamp66;
    }

    public void setRedblackcamp66(String redblackcamp66) {
        this.redblackcamp66 = redblackcamp66;
    }

    public String getRedblackcamp67() {
        return redblackcamp67;
    }

    public void setRedblackcamp67(String redblackcamp67) {
        this.redblackcamp67 = redblackcamp67;
    }

    public String getRedblackcamp68() {
        return redblackcamp68;
    }

    public void setRedblackcamp68(String redblackcamp68) {
        this.redblackcamp68 = redblackcamp68;
    }

    public String getRedblackcamp69() {
        return redblackcamp69;
    }

    public void setRedblackcamp69(String redblackcamp69) {
        this.redblackcamp69 = redblackcamp69;
    }

    public String getRedblackcamp70() {
        return redblackcamp70;
    }

    public void setRedblackcamp70(String redblackcamp70) {
        this.redblackcamp70 = redblackcamp70;
    }

    public String getRedblackcamp71() {
        return redblackcamp71;
    }

    public void setRedblackcamp71(String redblackcamp71) {
        this.redblackcamp71 = redblackcamp71;
    }

    public String getRedblackcamp74() {
        return redblackcamp74;
    }

    public void setRedblackcamp74(String redblackcamp74) {
        this.redblackcamp74 = redblackcamp74;
    }

    public String getRedblackcamp75() {
        return redblackcamp75;
    }

    public void setRedblackcamp75(String redblackcamp75) {
        this.redblackcamp75 = redblackcamp75;
    }

    public String getRedblackcamp77() {
        return redblackcamp77;
    }

    public void setRedblackcamp77(String redblackcamp77) {
        this.redblackcamp77 = redblackcamp77;
    }

    public String getRedblackcamp78() {
        return redblackcamp78;
    }

    public void setRedblackcamp78(String redblackcamp78) {
        this.redblackcamp78 = redblackcamp78;
    }

    public String getRedblackcamp79() {
        return redblackcamp79;
    }

    public void setRedblackcamp79(String redblackcamp79) {
        this.redblackcamp79 = redblackcamp79;
    }

    public String getRedblackcamp80() {
        return redblackcamp80;
    }

    public void setRedblackcamp80(String redblackcamp80) {
        this.redblackcamp80 = redblackcamp80;
    }

    public String getRedblackcamp81() {
        return redblackcamp81;
    }

    public void setRedblackcamp81(String redblackcamp81) {
        this.redblackcamp81 = redblackcamp81;
    }

    public String getRedblackcamp82() {
        return redblackcamp82;
    }

    public void setRedblackcamp82(String redblackcamp82) {
        this.redblackcamp82 = redblackcamp82;
    }

    public String getRedblackcamp83() {
        return redblackcamp83;
    }

    public void setRedblackcamp83(String redblackcamp83) {
        this.redblackcamp83 = redblackcamp83;
    }

    public String getRedblackcamp18() {

        return redblackcamp18;
    }

    public void setRedblackcamp18(String redblackcamp18) {
        this.redblackcamp18 = redblackcamp18;
    }

    public String getRedblackcamp36() {
        return redblackcamp36;
    }

    public void setRedblackcamp36(String redblackcamp36) {
        this.redblackcamp36 = redblackcamp36;
    }

    public String getRedblackcamp40() {
        return redblackcamp40;
    }

    public void setRedblackcamp40(String redblackcamp40) {
        this.redblackcamp40 = redblackcamp40;
    }

    public String getSomVarPaciente() {
        return somVarPaciente;
    }

    public void setSomVarPaciente(String somVarPaciente) {
        this.somVarPaciente = somVarPaciente;
    }

    public boolean isRenderMsgAddProcBlank() {
        return renderMsgAddProcBlank;
    }

    public void setRenderMsgAddProcBlank(boolean renderMsgAddProcBlank) {
        this.renderMsgAddProcBlank = renderMsgAddProcBlank;
    }

    public String getVitBuscaProcedimento() {
        if (vitBuscaProcedimento == null) {
            vitBuscaProcedimento = "";
        }
        return vitBuscaProcedimento;
    }

    public void setVitBuscaProcedimento(String vitBuscaProcedimento) {
        this.vitBuscaProcedimento = vitBuscaProcedimento;
    }

    @Override
    public Usuario getLogado() {
        if (logado == null) {
            logado = new Usuario();
        }
        return logado;
    }

    @Override
    public void setLogado(Usuario logado) {
        this.logado = logado;
    }

    @Override
    public FormularioDAO getDao() {
        if (formularioDAO == null) {
            formularioDAO = new FormularioDAO();
        }
        return formularioDAO;
    }

    @Override
    public Formulario criarNovaEntidade() {
        return new Formulario();
    }

    //dlg preenchimento rapido
    public void abrirDlgPreenchimentoRapido() {
        F.abrirDlgPreenchimentoRapido("dlg/dlgpreenchirapido", 800, 335, true);
    }

    public void retornoDlgPreenchimenoRapido(SelectEvent event) {

        List<Object> l = (List<Object>) event.getObject();
        if (l != null) {// if 0
            Paciente p = (Paciente) l.get(0);
            if (p != null) {//if 1
                if (!p.equals(new Paciente())) {
                    getEntidade().setPaciente(p);
                }
            } //if 1
            Procedimento_sus proced = (Procedimento_sus) l.get(1);
            if (proced != null) {//if 2
                if (!proced.equals(new Procedimento_sus())) {
                    getEntidade().setP1(proced);
                }
            } //if 2
            Cid cid = (Cid) l.get(2);
            if (cid != null) {
                if (!cid.equals(new Cid())) {
                    getEntidade().getProc_justificativa().setCid_principal(cid);
                }
            }
        }//if 0
    }

    // fimdlg preenchimento rapido
    //dlg addProcedimento
    public void procedimentoSelecionado(SelectEvent event) throws ErroSistema {
        procedimentosTemp = (Procedimento_sus) event.getObject();
        btProcedimentoConfirmar();

    }

    //fim dlg addProcedimento
    //DLG BUSCAR PACIENTE
    public void abrirDlgBuscaPaciente() {
        F.abrirDlgPreenchimentoRapido("dlg/dlgBuscaPaciente", 800, 370, true);
    }

    public void retornoDlgabrirDlgBuscaPaciente(SelectEvent event) {
        Paciente p = (Paciente) event.getObject();
        if (p != null) {
            if (!p.equals(new Paciente())) {
                getEntidade().setPaciente(p);
            }
        }
    }

    //FIM DLG BUSCAR PACIENTE
    //dlg add cid
    public void verificarLimiteCid() {
//        if (getEntidade().getP1()) {

        if (getEntidade().getProc_justificativa().getCid_principal().equals(new Cid())
                || getEntidade().getProc_justificativa().getCid_secundario().equals(new Cid())
                || getEntidade().getProc_justificativa().getCid_causas_assoc().equals(new Cid())) {//if 1

            abrirDlgBuscaCid();

        } else {// else if 1
            F.mensagem("", "Limite de cids atingido!", FacesMessage.SEVERITY_WARN);
        }
    }
//else {
//            F.mensagem("", "Primeiramente insira o procedimento principal!", FacesMessage.SEVERITY_WARN);
//        }
//    }

    public void abrirDlgBuscaCid() {

        //se qualquer cid algum vago
        List<Cid> l = new ArrayList<>();
        if (!getEntidade().getProc_justificativa().getCid_principal().equals(new Cid())) {
            l.add(getEntidade().getProc_justificativa().getCid_principal());
        }
        if (!getEntidade().getProc_justificativa().getCid_secundario().equals(new Cid())) {
            l.add(getEntidade().getProc_justificativa().getCid_secundario());
        }
        if (!getEntidade().getProc_justificativa().getCid_causas_assoc().equals(new Cid())) {
            l.add(getEntidade().getProc_justificativa().getCid_causas_assoc());
        }
        DlgBuscaCidBean.procedimentoStatic = getEntidade().getP1();
        DlgBuscaCidBean.listaCidStatica = l;
        F.abrirDlgPreenchimentoRapido("dlg/dlgBuscaCid", 800, 360, true);

    }

    public void retornoDlgCid(SelectEvent event) throws ErroSistema {
        Cid c = (Cid) event.getObject();
        cidTemp = c;
        btCidConfirmar();
    }

    public void retornoDlgCidTopografico1(SelectEvent event) throws ErroSistema {
        Cid c = (Cid) event.getObject();
        getEntidade().getPag2().setCid_topografico1_radio(c.getCid());
        setRedblackcamp79("black");
    }

    public void retornoDlgCidTopografico2(SelectEvent event) throws ErroSistema {
        Cid c = (Cid) event.getObject();
        getEntidade().getPag2().setCid_topografico2_radio(c.getCid());
    }

    public void retornoDlgCidTopografico3(SelectEvent event) throws ErroSistema {
        Cid c = (Cid) event.getObject();
        getEntidade().getPag2().setCid_topografico3_radio(c.getCid());

    }
    //fim dlg add cid
}
