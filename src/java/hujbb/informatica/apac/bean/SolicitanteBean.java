package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.dao.Ativ_desativ_usuarioDAO;
import hujbb.informatica.apac.dao.SolicitanteDAO;
import hujbb.informatica.apac.dao.SetorDAO;
import hujbb.informatica.apac.dao.UsuarioDAO;
import hujbb.informatica.apac.entidades.Ativ_desativ_usuario;
import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Solicitante;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.entidades.Motivo_ativ_desativ_usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class SolicitanteBean extends CrudBean<Solicitante, SolicitanteDAO> {
    
    private String busca;
    private SolicitanteDAO dao;
    private List<SelectItem> perfis;
    private List<SelectItem> setorItem;
    private List<SelectItem> cboItem;
    private List<SelectItem> situacao;
    private List<SelectItem> situacaoBusca;
    private List<SelectItem> motivoAD_item;
    private String motivo;
    private Date dt_motivo;
    private int id_motivo;
    private Ativ_desativ_usuario ativ_desativ_usuario;

    //vairaveis da tela 
    private Solicitante solicitanteEdit;//e carregada apartir do datatable do busca usuario.xhtml
    private Solicitante solicitanteCad;//cadastrar novo usuario cadastrarusuario.xhtml
    private String vsituacaoUsuario; //Valor do componete situação usuario na tela buscarUsuario.xhtml
    private String msgdlgConfirmDesativar;
    private String vitaDlgCarga;

    //editar solicitante
    private String cpfaux;
    
    @PostConstruct
    public void init() {
        perfis = new ArrayList<>();
        perfis.add(new SelectItem(1, "ADMINISTRATIVO"));
        perfis.add(new SelectItem(0, "ADMINISTRADOR HOSPITALAR"));
        perfis.add(new SelectItem(2, "SOLICITANTE"));
        perfis.add(new SelectItem(3, "ADMINISTRADOR"));
        perfis.add(new SelectItem(4, "SOLICITANTE ONCOLOGICO"));
        
        situacao = new ArrayList<>();
        situacao.add(new SelectItem("1", "Ativo"));
        situacao.add(new SelectItem("0", "Inativo"));
        
        situacaoBusca = new ArrayList<>();
        situacaoBusca.add(new SelectItem("1", "Ativo"));
        situacaoBusca.add(new SelectItem("0", "Inativo"));
        situacaoBusca.add(new SelectItem("2", "Todos"));
        
        setorItem = new ArrayList<>();
        try {
            cboItem = Cbo.item("");
        } catch (ErroSistema ex) {
            F.mensagem("ERRO", "Erro ao buscar CBO", FacesMessage.SEVERITY_ERROR);
        }
        
        carregaSetores("WHERE `id_setor` <> -1 ORDER BY `sigla` ");
        buscaAtivos();
        solicitanteCad = new Solicitante();
        ativ_desativ_usuario = new Ativ_desativ_usuario();
        vsituacaoUsuario = "1";
        
    }
    
    public void verificaPerfil(int perfil) {
        
        if (perfil != 3) {
            F.redirecionarPagina("index.jsf");
        }
        
    }
    
    public void buscaAtivos() {
        buscar("WHERE `ativo` = 1  ORDER BY solicitante.`nome`");
    }
//funcoes de botoes

    public void btCadastrar(Usuario logado) throws ErroSistema {
        
        if (verificarCampos()) {
            
            getSolicitanteCad().setNome(getSolicitanteCad().getUsuario().getNome());// Copia o nome do usuário pro solicitante

            getSolicitanteCad().getUsuario().setAtivo(1);
            
            if (getDao().salvar(getSolicitanteCad()) != null) {
                
                log(logado, 1);
                
                F.mensagem("", getSolicitanteCad().getNome().toUpperCase() + " foi cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
                
                solicitanteCad = new Solicitante();
                
            } else {
                
                F.mensagem("", "Erro ao cadastrar Usuario!", FacesMessage.SEVERITY_ERROR);
            }
            
        }
    }
    
    public void btBuscar(String busca) throws ErroSistema {
        if (vsituacaoUsuario.equals("2")) {//buscar todos 
            buscar("WHERE solicitante.`nome` LIKE '" + busca + "%'  ORDER BY solicitante.`nome`");
            
        } else {
            buscar("WHERE solicitante.`nome` LIKE '" + busca + "%' AND  `ativo` = " + vsituacaoUsuario + " ORDER BY solicitante.`nome`");
            
        }
        //FabricaDeConexoes.fecharConecxao();
    }
    
    public void btAtualizar(Usuario logado) throws ErroSistema {
        if (verificarCamposUp(getEntidade())) {
            getEntidade().getUsuario().setNome(getEntidade().getNome());//sicroniza os nomes

            if (new UsuarioDAO().atualizar(getEntidade().getUsuario()) == null) {
                F.mensagem("", "Erro ao atualizar dados!", FacesMessage.SEVERITY_ERROR);
            } else if (editar(getEntidade()) == null) {
                F.mensagem("", "Erro ao atualizar dados!", FacesMessage.SEVERITY_ERROR);
            } else {
                log(logado, 2);
                F.mensagem("", "Dados atualizados!", FacesMessage.SEVERITY_INFO);
            }
        }
        buscaAtivos();
        //FabricaDeConexoes.fecharConecxao();
        
    }
    
    public void btVusualisar(Solicitante solicitante) {
        try {
            setEntidade(getDao().buscaId(solicitante.getId_solicitante().toString()));
            setCpfaux(getEntidade().getCpf());
        } catch (ErroSistema ex) {
            F.mensagem("Erro busca", "Erro ao buscar dados!  " + ex, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void btDelConfirSimdlgDesativar(Solicitante solicitante, Usuario logado) throws ErroSistema {
        if (ativ_desativ_usuario.getMotivo().getId() != 0) {
            
            String msg = "";
            //se o usuario selecionado é diferente do usuario logado
            if (!Objects.equals(solicitante.getUsuario().getId_usuario(), logado.getId_usuario())) {
                ativ_desativ_usuario.setDt_motivo(new Date());
                ativ_desativ_usuario.setUsuario(solicitante.getUsuario());
                if (solicitante.getUsuario().getAtivo() == 0) {//pergunta se o usuario é inativo
                    solicitante.getUsuario().setAtivo(1);//muda o status
                    msg = "ativado(a)";
                    
                } else {
                    solicitante.getUsuario().setAtivo(0);//muda o status para desativado
                    msg = "desativado(a)";
                }

                //salva a atualizacao no banco
                if (new UsuarioDAO().atualizar(solicitante.getUsuario()) != null) {//!= null -> sucesso ao autalizar
                    getEntidades().remove(solicitante);
                    F.mensagem("", "Usuário:" + solicitante.getNome().toUpperCase() + " foi " + msg + " com sucesso!", FacesMessage.SEVERITY_INFO);

                    //salva o motivos da ativação ou desativação no banco
                    if (new Ativ_desativ_usuarioDAO().salvar(ativ_desativ_usuario) == null) {
                        F.mensagem("", "Erro ao registrar motivo da ativação/desativação.", FacesMessage.SEVERITY_ERROR);
                    }
                    
                    ativ_desativ_usuario = new Ativ_desativ_usuario();
                    setSolicitanteEdit(new Solicitante()); //limpa
                } else {//erro ao atulaizar no banco
                    F.mensagem("Erro ao atualizar operação no banco de dados!", "", FacesMessage.SEVERITY_ERROR);
                }
                
            } else {
                F.mensagem("Não é possivel desativar ou ativar o próprio cadastro!", "", FacesMessage.SEVERITY_ERROR);
            }
            limpDlgConfirmDesativar();
        } else {//se nao tiver sido selecionado im motivo
            F.mensagem("Erro!", "Motivo não selecionado!", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void btSimDlgConfirmDesativar(Solicitante solicitante) {
        limpDlgConfirmDesativar();
        if (solicitante.getUsuario().getAtivo() == 0) {
            setMsgdlgConfirmDesativar("Motivo para ativação do usuário");
        } else {
            setMsgdlgConfirmDesativar("Motivo para desativação do usuário");
        }
        setSolicitanteEdit(solicitante);
        //lidia
        try {
            motivoAD_item = Motivo_ativ_desativ_usuario.itemMotivo("", solicitanteEdit.getUsuario().getAtivo());
        } catch (ErroSistema ex) {
            F.mensagem("ERRO", "Erro ao buscar Motivo", FacesMessage.SEVERITY_ERROR);
        }
    }

//fim funcoes botoes
    //limp dialogos
    public void limpDlgConfirmDesativar() {
        setMotivo("");
        ativ_desativ_usuario = new Ativ_desativ_usuario();
    }

    //fim limp dialogos
    public void verificaCpfEdit(String cpf) throws ErroSistema {
        if (!cpf.equals(getEntidade().getCpf())) {
            verificaCpf(cpf);
        }
    }
    
    public void verificaCpf(String cpf) throws ErroSistema {
        
        if (!F.isCPF(cpf.trim()) && !cpf.trim().isEmpty()) {
            F.mensagem("", "CPF inválido!.", FacesMessage.SEVERITY_WARN);
            
        }
        verificarCpfDuplicado();
    }
    
    private boolean verificarCampos() throws ErroSistema {
        
        if (FabricaDeConexoes.buscarLdapEbserh(getSolicitanteCad().getUsuario().getLogin(), "") != null || FabricaDeConexoes.buscarLdapEbserhHubfs(getSolicitanteCad().getUsuario().getLogin(), "") != null) {//verifica o login ad

            if (getSolicitanteCad().getUsuario().getNome().trim().isEmpty()) {
                F.mensagem("Erro ao cadastrar!", "Campo NOME é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (getSolicitanteCad().getCpf().trim().isEmpty()) {
                F.mensagem("Erro ao cadastrar!", "Campo CPF é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (!F.isCPF(getSolicitanteCad().getCpf().trim())) {
                F.mensagem("Erro ao cadastrar!", "CPF inválido!.", FacesMessage.SEVERITY_WARN);
                return false;
            } else if (verificarCpfDuplicado()) {
                return false;
            }
            if (getSolicitanteCad().getUsuario().getLogin().trim().isEmpty()) {
                F.mensagem("Erro ao cadastrar!", "Campo LOGIN é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            /* if (getSolicitanteCad().getUsuario().getSenha().trim().isEmpty()) {
                F.mensagem("Erro ao cadastrar!", "Informe uma senha.", FacesMessage.SEVERITY_WARN);
                return false;
            }*/
            if (getSolicitanteCad().getCns().trim().isEmpty()) {
                F.mensagem("Erro ao cadastrar!", "Campo CNS é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (getSolicitanteCad().getUsuario().getSetor().getId_setor() < 1) {
                F.mensagem("Erro ao cadastrar!", "Selecione o SETOR .", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (getSolicitanteCad().getUsuario().getPerfil().getId_perfil() < 0) {
                F.mensagem("Erro ao cadastrar!", "Selecione o PERFIL .", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (getSolicitanteCad().getUsuario().getCbo().getCod().equals("0")) {
                F.mensagem("Erro ao cadastrar!", "Selecione o CBO .", FacesMessage.SEVERITY_WARN);
                return false;
            }
            
        } else {
            F.mensagem("Erro ao cadastrar!", "Informe os dados do usuário", FacesMessage.SEVERITY_WARN);//LIDIA
            return false;
        }
        
        return !verificarSolicitanteCadastrado();
    }
    
    public boolean verificarCamposUp(Solicitante s) throws ErroSistema {
        
        if (FabricaDeConexoes.buscarLdapEbserh(s.getUsuario().getLogin(), "") != null || FabricaDeConexoes.buscarLdapEbserhHubfs(s.getUsuario().getLogin(), "") != null) {//verifica o login ad

            if (s.getUsuario().getNome().trim().isEmpty()) {
                F.mensagem("Erro ao atualizar!", "Campo NOME é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (s.getCpf().trim().isEmpty()) {
                F.mensagem("Erro ao atualizar!", "Campo CPF é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (!F.isCPF(s.getCpf().trim())) {
                F.mensagem("Erro ao atualizar!", "CPF inválido!.", FacesMessage.SEVERITY_WARN);
                return false;
            } else if (!getCpfaux().equals(s.getCpf())) {
                if (verificarCpfDuplicado()) {
                    return false;
                }
            }
            if (s.getUsuario().getLogin().trim().isEmpty()) {
                F.mensagem("Erro ao atualizar!", "Campo LOGIN é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            /* if (s.getUsuario().getSenha().trim().isEmpty()) {
                F.mensagem("Erro ao cadastrar!", "Informe uma senha.", FacesMessage.SEVERITY_WARN);
                return false;
            }*/
            if (s.getCns().trim().isEmpty()) {
                F.mensagem("Erro ao atualizar!", "Campo CNS é obrigatório.", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (s.getUsuario().getSetor().getId_setor() < 1) {
                F.mensagem("Erro ao atualizar!", "Selecione o SETOR .", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (s.getUsuario().getPerfil().getId_perfil() < 0) {
                F.mensagem("Erro ao atualizar!", "Selecione o PERFIL .", FacesMessage.SEVERITY_WARN);
                return false;
            }
            if (s.getUsuario().getCbo().getCod().equals("0")) {
                F.mensagem("Erro ao atualizar!", "Selecione o CBO .", FacesMessage.SEVERITY_WARN);
                return false;
            }
            
        } else {
            F.mensagem("Erro ao atualizar!", "Informe os dados do usuário", FacesMessage.SEVERITY_WARN);//LIDIA
            return false;
        }
        
        return true;
    }
    //funcoes

    public void verificarLoginAdEbserh() {
        
        Solicitante aux = FabricaDeConexoes.buscarLdapEbserh(solicitanteCad.getUsuario().getLogin(), "");//procurando no hujbb
        if (aux != null) {//se encontrou  //if1
            solicitanteCad = aux;
        } else {//se nao encontrou no hujbb procura no hubfs   //if 1
            aux = FabricaDeConexoes.buscarLdapEbserhHubfs(solicitanteCad.getUsuario().getLogin(), "");
            if (aux != null) {//se encontrou no hubfs    //if2
                solicitanteCad = aux;
            } else {//se nao encontrou hubfs e hujbb   //if2
                F.mensagem("Login não encontrado!", "", FacesMessage.SEVERITY_ERROR);
                solicitanteCad = new Solicitante();
            }//elseif2

        }//elseif1
    }

// hujbb
    public void verificarLoginAd() {
        
        Solicitante aux = FabricaDeConexoes.buscarLdap(solicitanteCad.getUsuario().getLogin(), "");
        
        if (aux != null) {
            solicitanteCad = aux;
        } else {
            F.mensagem("Login não encontrado!", "", FacesMessage.SEVERITY_ERROR);
        }
    }
//    fimfuncoes

    private boolean verificarCpfDuplicado() throws ErroSistema {
        SolicitanteDAO solicitanteDAO = new SolicitanteDAO();
        List<Solicitante> solicitantes = solicitanteDAO.buscar("WHERE  `cpf` = '" + getEntidade().getCpf() + "' "); //verifica se existe um usuario com esse login
        if (solicitantes != null && !solicitantes.isEmpty()) {//se existe o cpf
            //verifica se existe um solicitante com esse usuario
            F.mensagem("Cpf já cadastrado.", "", FacesMessage.SEVERITY_ERROR);
            return true;
            
        }
        return false;
    }
    
    private boolean verificarSolicitanteCadastrado() throws ErroSistema {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        //verifica se existe um usuario com esse login
        List<Usuario> lu = usuarioDAO.buscar("WHERE  `login` = '" + getSolicitanteCad().getUsuario().getLogin() + "' ");
        if (lu != null && lu.size() > 0) {//se existe o usuario
            //verifica se existe um solicitante com esse usuario
            List<Solicitante> ls = getDao().buscar("WHERE `usuario_id_usuario` = " + lu.get(0).getId_usuario());
            if (ls != null && ls.size() > 0) {//se existe o solicitante
                F.mensagem("Já há um solicitante cadastrado com esse login!", "", FacesMessage.SEVERITY_ERROR);
                return true;
            } else {//caso exista o usuario e nao exista um solicitante correspondente
                usuarioDAO.deletar(lu.get(0));//deletar o usuario que nao tem solicitante corrrespondente
                return false;
            }
        }
        
        return false;
    }
    
    public String titleButtonDataTableUsuarios(Solicitante s) {
        if (s.getUsuario().getAtivo() == 0) {
            return "Ativar";
        } else {
            return "Desativar";
        }
    }
    
    public String iconButtonDataTableUsuarios(Solicitante s) {
        if (s.getUsuario().getAtivo() == 0) {
            return " ui-icon-check";
        } else {
            return "ui-icon-cancel";
        }
    }
    
    public void closeDlgEditUsuario() {
        situacao.clear();
        situacao.add(new SelectItem("1", "Ativo"));
        situacao.add(new SelectItem("0", "Inativo"));
        situacao.add(new SelectItem("2", "Todos"));
        
    }
    
    private void log(Usuario logado, int acao) throws ErroSistema {
        F.log(logado, getEntidade().nun_entidade_bd(), getEntidade().getId_solicitante(), acao, "", "");
    }
    
    public Solicitante getSolicitanteEdit() {
        return solicitanteEdit;
    }
    
    public void setSolicitanteEdit(Solicitante solicitanteEdit) {
        this.solicitanteEdit = solicitanteEdit;
        
    }
    
    public String getBusca() {
        return busca;
    }
    
    public void setBusca(String busca) {
        this.busca = busca;
    }
    
    public List<SelectItem> getPerfis() {
        return perfis;
    }
    
    public void setPerfis(List<SelectItem> perfis) {
        this.perfis = perfis;
    }
    
    public List<SelectItem> getSetorItem() {
        return setorItem;
    }
    
    public void setSetorItem(List<SelectItem> setorItem) {
        this.setorItem = setorItem;
    }
    
    public Solicitante getSolicitanteCad() {
        return solicitanteCad;
    }
    
    public void setSolicitanteCad(Solicitante solicitanteCad) {
        this.solicitanteCad = solicitanteCad;
    }
    
    public List<SelectItem> getCboItem() {
        return cboItem;
    }
    
    public void setCboItem(List<SelectItem> cboItem) {
        this.cboItem = cboItem;
    }
    
    public void carregaSetores(String condicao) {
        List<Setor> setores;
        try {
            setores = new SetorDAO().buscar(condicao);
            setorItem.add(new SelectItem(-1, "SELECIONE O SETOR"));
            if (setores != null && setores.size() > 0) {
                for (Setor setor : setores) {
                    setorItem.add(new SelectItem(setor.getId_setor(), setor.getSigla().toUpperCase() + " - " + setor.getNome()));
                }
            } else {
                F.mensagem("Falha ao carregar dados dos setores do Banco de dados!", "", FacesMessage.SEVERITY_ERROR);
            }
        } catch (ErroSistema ex) {
            F.mensagem(ex.toString(), "", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public String getVsituacaoUsuario() {
        return vsituacaoUsuario;
    }
    
    public void setVsituacaoUsuario(String vsituacaoUsuario) {
        this.vsituacaoUsuario = vsituacaoUsuario;
    }
    
    public List<SelectItem> getSituacao() {
        return situacao;
    }
    
    public void setSituacao(List<SelectItem> situacao) {
        this.situacao = situacao;
    }
    
    public String getMsgdlgConfirmDesativar() {
        return msgdlgConfirmDesativar;
    }
    
    public void setMsgdlgConfirmDesativar(String msgdlgConfirmDesativar) {
        this.msgdlgConfirmDesativar = msgdlgConfirmDesativar;
    }
    
    public List<SelectItem> getSituacaoBusca() {
        return situacaoBusca;
    }
    
    public void setSituacaoBusca(List<SelectItem> situacaoBusca) {
        this.situacaoBusca = situacaoBusca;
    }
    
    public List<SelectItem> getMotivoAD_item() {
        return motivoAD_item;
    }
    
    public void setMotivoAD(List<SelectItem> motivoAD_item) {
        this.motivoAD_item = motivoAD_item;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public Date getDt_motivo() {
        return dt_motivo;
    }
    
    public void setDt_motivo(Date dt_motivo) {
        this.dt_motivo = dt_motivo;
    }
    
    public int getId_motivo() {
        return id_motivo;
    }
    
    public void setId_motivo(int id_motivo) {
        this.id_motivo = id_motivo;
    }
    
    public Ativ_desativ_usuario getAtiv_desativ_usuario() {
        return ativ_desativ_usuario;
    }
    
    public void setAtiv_desativ_usuario(Ativ_desativ_usuario ativ_desativ_usuario) {
        this.ativ_desativ_usuario = ativ_desativ_usuario;
    }
    
    public String getCpfaux() {
        return cpfaux;
    }
    
    public void setCpfaux(String cpfaux) {
        this.cpfaux = cpfaux;
    }
    
    public void cargaSolicitante(String carga) {
        String msg = "";
        UsuarioDAO uDAO = new UsuarioDAO();
        Solicitante solicitante = new Solicitante();
        String[] linhas = carga.split("#");
        for (String s : linhas) {
           
            String[] dados = s.split(";");
            
            solicitante.setNome(dados[0]);
            solicitante.getUsuario().setNome(dados[0]);
            solicitante.getUsuario().setLogin(dados[1]);
            solicitante.setCpf(dados[2].replace(".", "").replace("-", ""));
            solicitante.setCns(dados[3].replace("a", ""));
            solicitante.getUsuario().getCbo().setCod(dados[4]);
            solicitante.getUsuario().getSetor().setNome(dados[5]);
            try {
                solicitante.getUsuario().getPerfil().setId_perfil(Integer.parseInt(dados[7]));
            } catch (NumberFormatException e) {
                solicitante.getUsuario().getPerfil().setId_perfil(2);//adm financeiro
            }
            try {
                solicitante.getUsuario().setAtivo(Integer.parseInt(dados[9]));
                
            } catch (NumberFormatException e) {
                solicitante.getUsuario().setAtivo(0);//inativo
            }
            solicitante.getUsuario().setDt_cadastro(new Date());//inativo
            try {
                solicitante.setUsuario(uDAO.salvar(solicitante.getUsuario()));
                getDao().salvar(solicitante);
                msg += "\n"+solicitante.toString();
            } catch (ErroSistema e) {
                F.setMsgErro(e.toString() + "solicitantebean:cargasolicitante()");
            }
            
        }
        
      
    }

    public String getVitaDlgCarga() {
        return vitaDlgCarga;
    }

    public void setVitaDlgCarga(String vitaDlgCarga) {
        this.vitaDlgCarga = vitaDlgCarga;
    }
    
    
    
    @Override
    public SolicitanteDAO getDao() {
        if (dao == null) {
            dao = new SolicitanteDAO();
        }
        return dao;
    }
    
    @Override
    public Solicitante criarNovaEntidade() {
        return new Solicitante();
    }
    
}
