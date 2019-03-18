package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.Procedimento_susDAO;
import hujbb.informatica.apac.entidades.Competencia;
import hujbb.informatica.apac.entidades.Forma_organizacao;
import hujbb.informatica.apac.entidades.Grupo_procedimento;
import hujbb.informatica.apac.entidades.Procedimento_sus;
import hujbb.informatica.apac.entidades.Sub_grupo_procedimento;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class Procedimento_susBean extends CrudBean<Procedimento_sus, Procedimento_susDAO> {

    private Procedimento_susDAO dao;

    private ArrayList<SelectItem> procedimentos = new ArrayList<>();
    private int quantidade;

    //variaveis para tela-sigtap.jsf
    private ArrayList<SelectItem> grupo_procedimento_item = new ArrayList<>();
    private ArrayList<SelectItem> sub_grupo_procedimento_item = new ArrayList<>();
    private ArrayList<SelectItem> forma_organozacao_item = new ArrayList<>();
    private ArrayList<SelectItem> competencia_item = new ArrayList<>();

    private String buscaCodProced;
    private String buscaNomeProced;

    public static List<Procedimento_sus> listaStatica;
    private List<Procedimento_sus> listaAux;

    @PostConstruct
    public void init() {
        procedimentos = new ArrayList<>();
       
    }

    public void initTelaSigtap() throws ErroSistema {
        grupo_procedimento_item = Grupo_procedimento.item("");
        sub_grupo_procedimento_item = new ArrayList<>();
        forma_organozacao_item = new ArrayList<>();
        competencia_item = Competencia.item("");
        buscaCodProced = "";
        buscaNomeProced = "";

    }

    //busca todos os estabelecimentos
    public void setProcedimentos(ArrayList<SelectItem> procedimentos) throws ErroSistema {

        this.procedimentos = procedimentos;

    }

    //busca todos os estabelecimentos com parte do nome
    public void buscaProcedimentosNome(String nome) throws ErroSistema {
        procedimentos = new ArrayList<>();
        if (nome.length() > 2) {

            List<Procedimento_sus> es = new Procedimento_susDAO().buscar("WHERE nome LIKE '%" + nome + "%' ORDER BY nome");
            if (es != null) {
                setEntidade(es.get(0));//seta o primeiro valor da lista como
                for (int i = 0; i < es.size(); i++) {
                    procedimentos.add(new SelectItem(es.get(i).getCodigo() + "", es.get(i).getNome().toUpperCase()));
                }
            } else {
                procedimentos.add(new SelectItem("", ""));
                setEntidade(new Procedimento_sus());
                getEntidade().setCodigo("");
            }
        }
    }

    //busca todos os estabelecimentos com parte do nome
    public void buscaProcedimentoscodigo(String codigo) throws ErroSistema {

        procedimentos = new ArrayList<>();

        List<Procedimento_sus> es = new Procedimento_susDAO().buscar("WHERE codigo = " + codigo);
        if (es != null && es.size() > 0) {

            setEntidade(es.get(0));//seta o primeiro valor da lista como
            for (int i = 0; i < es.size(); i++) {
                procedimentos.add(new SelectItem(es.get(i).getCodigo() + "", es.get(i).getNome().toUpperCase()));
            }
        } else {
            procedimentos.add(new SelectItem("", ""));
            setEntidade(new Procedimento_sus());
            getEntidade().setCodigo("");
            F.mensagem("", "Código não encontrado!", FacesMessage.SEVERITY_ERROR);
        }
    }

    public ArrayList<SelectItem> getProcedimentos() {
        return procedimentos;
    }

    public void selecionarProcedimento(String codProcedimento) {
        getEntidade().setCodigo(codProcedimento);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
//metodos tela sigtap

    public ArrayList<SelectItem> getGrupo_procedimento_item() {
        return grupo_procedimento_item;
    }

    public void setGrupo_procedimento_item(ArrayList<SelectItem> grupo_procedimento_item) {
        this.grupo_procedimento_item = grupo_procedimento_item;
    }

    public ArrayList<SelectItem> getSub_grupo_procedimento_item() {
        return sub_grupo_procedimento_item;
    }

    public void setSub_grupo_procedimento_item(ArrayList<SelectItem> sub_grupo_procedimento_item) {
        this.sub_grupo_procedimento_item = sub_grupo_procedimento_item;
    }

    public ArrayList<SelectItem> getForma_organozacao_item() {
        return forma_organozacao_item;
    }

    public void setForma_organozacao_item(ArrayList<SelectItem> forma_organozacao_item) {
        this.forma_organozacao_item = forma_organozacao_item;
    }

    public ArrayList<SelectItem> getCompetencia_item() {
        return competencia_item;
    }

    public void setCompetencia_item(ArrayList<SelectItem> competencia_item) {
        this.competencia_item = competencia_item;
    }

    public String getBuscaCodProced() {
        return buscaCodProced;
    }

    public void setBuscaCodProced(String buscaCodProced) {
        this.buscaCodProced = buscaCodProced.trim();
    }

    public String getBuscaNomeProced() {
        return buscaNomeProced;
    }

    public void setBuscaNomeProced(String buscaNomeProced) {
        this.buscaNomeProced = buscaNomeProced.trim();
    }

    //eventos telas sigtap
    public void changeSomGrupoProcedimento(String codGrupo) throws ErroSistema {

        sub_grupo_procedimento_item = Sub_grupo_procedimento.item("`grupo_procedimento_id` = '" + codGrupo + "'");
        forma_organozacao_item = new ArrayList<>();

    }

    public void changeSomSubGrupoProcedimento(String codSubGrupo) throws ErroSistema {
        forma_organozacao_item = Forma_organizacao.item("`sub_grupo_procedimento_id` = '" + codSubGrupo + "'");
    }

    public void btBuscarProcedimento(String grupo, String subGp, String forma, String competencia) throws ErroSistema {
        String condicao = "";

        //verifica forma subgp e grupo
        if (!forma.isEmpty() && !forma.equals("-1")) {//if 1
            condicao += "(procedimento_sus.`codigo` LIKE '" + forma + buscaCodProced + "%') ";
        } else if (!subGp.isEmpty() && !subGp.equals("-1")) {//if 2 forma vazia e sub gp nao vazio
            condicao += "(procedimento_sus.`codigo` LIKE '" + subGp + "%') ";
        } else if (!grupo.isEmpty() && !grupo.equals("-1")) {//if 3 subgp e forma vazios grupo nao vazio 
            condicao += "(procedimento_sus.`codigo` LIKE '" + grupo + "%') ";
        } else if (!buscaCodProced.isEmpty()) {//if 4
            condicao += "(procedimento_sus.`codigo` LIKE '" + buscaCodProced + "%') ";
        }

        if (condicao.isEmpty()) {
            if (!buscaNomeProced.isEmpty()) {
                condicao += "(procedimento_sus.`nome` LIKE '" + buscaNomeProced + "%') ";
                condicao += "AND (procedimento_sus.`dt_competencia` ='" + competencia + "' )  ORDER BY procedimento_sus.`codigo`";
            } else {
                condicao += " (procedimento_sus.`dt_competencia` ='" + competencia + "' ) ORDER BY procedimento_sus.`codigo`";
            }
        } else {
            if (!buscaNomeProced.isEmpty()) {
                condicao += "AND (procedimento_sus.`nome` LIKE '" + buscaNomeProced + "%') ";
            }
            condicao += "AND (procedimento_sus.`dt_competencia` ='" + competencia + "' ) ORDER BY procedimento_sus.`codigo`";
        }

        buscar(condicao);
        ////FabricaDeConexoes.fecharConecxao();
    }

    public void btBuscarProcedimentoDlg(String busca) throws ErroSistema {
       

        setEntidade(null);

        getEntidades().clear();

        if (busca.length() > 1) {

            String condicao = "";

            if (!busca.isEmpty()) {

                condicao += "((procedimento_sus.`nome` LIKE '%" + busca + "%') OR ";
                condicao += "(procedimento_sus.`codigo` LIKE '" + busca + "%')) ";
                condicao += "AND (procedimento_sus.`dt_competencia` =" + F.getCompetencia().getCompetencia() + " )  GROUP BY procedimento_sus.`codigo` ORDER BY procedimento_sus.`nome`";
            } else {

                condicao += " (procedimento_sus.`dt_competencia` =" + F.getCompetencia().getCompetencia() + ") GROUP BY procedimento_sus.`codigo` ORDER BY procedimento_sus.`nome`";
            }

            buscar(condicao);
            if (getEntidades() != null) {

                if (getEntidades().size() > 0) {

                    setEntidade(getEntidades().get(0));
                }
            }
        }
    }

    public void rowSelectEventProced(SelectEvent e) throws ErroSistema {

        selecionarProcedimentoDlg((Procedimento_sus) e.getObject());
    }

    public void selecionarProcedimentoDlg(Procedimento_sus entidade) throws ErroSistema {
       
        if (entidade != null) {
            if (!entidade.equals(new Procedimento_sus())) {

                if (!verificaProcedimentoEscolhido(entidade)) {
                    F.fecharDlg(entidade);
                } else {
                    F.mensagem("Procedimeto já inserido!", "Selecione um procedimento diferente.", FacesMessage.SEVERITY_WARN);
                }
            }
        }
    }

    public void setarListaAux() {

        listaAux = listaStatica;
        listaStatica = null;
    }

    //tela dlg busca procedimento
    ///verificar se o procedimento solicitado ja foi solicitato anteriormente
    public boolean verificaProcedimentoEscolhido(Procedimento_sus p) {

        for (Procedimento_sus p2 : getListaAux()) {
            if (p.equals(p2)) {
                return true;
            }
        }
        return false;
    }

    public List<Procedimento_sus> getListaAux(){
        if(listaAux ==  null){
            listaAux =  new ArrayList<>();
        }
        return listaAux;
    }
    
    //fim eventos telas sigtap
    @Override
    public Procedimento_susDAO getDao() {
        if (dao == null) {
            dao = new Procedimento_susDAO();
        }
        return dao;
    }

    @Override
    public Procedimento_sus criarNovaEntidade() {
        return new Procedimento_sus();
    }

}
