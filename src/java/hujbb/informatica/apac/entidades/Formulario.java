package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Formulario_f2DAO;
import hujbb.informatica.apac.dao.Formulario_has_procedimento_susDAO;
import hujbb.informatica.apac.dao.Procedimento_susDAO;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Formulario implements Serializable, Comparable<Formulario> {

    //variaveis bd
    private Integer id_formulario = -1;
    private Date data = new Date();
    private Date data_criacao = new Date();
    private String autenticacao = "";
    private Paciente paciente = new Paciente();
    private Solicitante solicitante = new Solicitante();
    private Estabelecimento_de_saude estabelecimento_de_saude_solicitante = new Estabelecimento_de_saude();
    private Estabelecimento_de_saude estabelecimento_de_saude_executante = new Estabelecimento_de_saude();
    private Proc_justificativa proc_justificativa = new Proc_justificativa();
    private Status status = new Status();
    private Autorizacao autorizacao = new Autorizacao();
    private Motivo_Cancelamento motivo_cancelamento = new Motivo_Cancelamento();

    private Formulario_f2 pag2;

    //variaveis de relacionamento NxM
    private Procedimento_sus p1 = new Procedimento_sus();
    private Procedimento_sus p2 = new Procedimento_sus();
    private Procedimento_sus p3 = new Procedimento_sus();
    private Procedimento_sus p4 = new Procedimento_sus();
    private Procedimento_sus p5 = new Procedimento_sus();
    private Procedimento_sus p6 = new Procedimento_sus();

    //variaveis extras
    private String dataString;
    private String mascaraId;

    //controle tela emitidos jsf
    private boolean chekBoxEnviar;//

    public Formulario(
            Integer id_formulario,
            Date data,
            Date data_criacao,
            String autenticacao, 
            Paciente paciente, 
            Solicitante solicitante, 
            Estabelecimento_de_saude estabelecimento_de_saude_solicitante, 
            Estabelecimento_de_saude estabelecimento_de_saude_executante, 
            Proc_justificativa proc_justificativa, 
            Status status, 
            Autorizacao autorizacao,
            Motivo_Cancelamento motivo_cancelamento
    ) {
        this.id_formulario = id_formulario;
        this.data = data;
        this.data_criacao = data_criacao;
        this.autenticacao = autenticacao;
        this.paciente = paciente;
        this.solicitante = solicitante;
        this.estabelecimento_de_saude_solicitante = estabelecimento_de_saude_solicitante;
        this.estabelecimento_de_saude_executante = estabelecimento_de_saude_executante;
        this.proc_justificativa = proc_justificativa;
        this.status = status;
        this.autorizacao = autorizacao;
        this.motivo_cancelamento =  new Motivo_Cancelamento();
        pag2 = new Formulario_f2();

    }

    public Formulario() {
        pag2 = new Formulario_f2();
        chekBoxEnviar = true;
    }
    //retorna o numedo da entidade na tabela entidade no banco de dados

    public int nun_entidade_bd() {
        return 7;
    }

    public void addProcedimento(List<Procedimento_sus> l) {
        for (int i = 0; i < l.size(); i++) {
            switch (i) {
                case 0:
                    p1 = l.get(0);
                    break;
                case 1:
                    p2 = l.get(1);
                    break;
                case 2:
                    p3 = l.get(2);
                    break;
                case 3:
                    p4 = l.get(3);
                    break;
                case 4:
                    p5 = l.get(4);
                    break;
                case 5:
                    p6 = l.get(5);
                    break;
                default:
                    break;
            }

        }
    }

    public String getMascaraId() {
        mascaraId = "";
        if (getId_formulario() > 0) {

            mascaraId = Integer.toString(getId_formulario());//converte intero para string
            if (mascaraId.length() > 4) {
                mascaraId = mascaraId.substring(4) + mascaraId.substring(0, 4);
                StringBuilder stringBuilder = new StringBuilder(mascaraId);
                stringBuilder.insert(mascaraId.length() - 4, '/');
                mascaraId = stringBuilder.toString();
            }
        } else {

           mascaraId = getId_formulario()+"";
        }
        return mascaraId;
    }

    public void setMascaraId(String mascaraId) {
        this.mascaraId = mascaraId;
    }

    public Integer getId_formulario() {
        return id_formulario;
    }

    public void setId_formulario(Integer id_formulario) {
        this.id_formulario = id_formulario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAutenticacao() {
        return autenticacao.toUpperCase();
    }

    public void setAutenticacao(String autenticacao) {
        this.autenticacao = autenticacao;
    }

    public Paciente getPaciente() {
        if (paciente == null) {
            paciente = new Paciente();
        }
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Solicitante getSolicitante() {
        if (solicitante == null) {
            solicitante = new Solicitante();
        }
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Estabelecimento_de_saude getEstabelecimento_de_saude_solicitante() {
        if (estabelecimento_de_saude_solicitante == null) {
            estabelecimento_de_saude_solicitante = new Estabelecimento_de_saude();
        }
        return estabelecimento_de_saude_solicitante;
    }

    public void setEstabelecimento_de_saude_solicitante(Estabelecimento_de_saude estabelecimento_de_saude_solicitante) {
        this.estabelecimento_de_saude_solicitante = estabelecimento_de_saude_solicitante;
    }

    public Estabelecimento_de_saude getEstabelecimento_de_saude_executante() {
        if (estabelecimento_de_saude_executante == null) {
            estabelecimento_de_saude_executante = new Estabelecimento_de_saude();
        }
        return estabelecimento_de_saude_executante;
    }

    public void setEstabelecimento_de_saude_executante(Estabelecimento_de_saude estabelecimento_de_saude_executante) {
        this.estabelecimento_de_saude_executante = estabelecimento_de_saude_executante;
    }

    public Proc_justificativa getProc_justificativa() {
        if (proc_justificativa == null) {
            proc_justificativa = new Proc_justificativa();
        }
        return proc_justificativa;
    }

    public void setProc_justificativa(Proc_justificativa proc_justificativa) {
        this.proc_justificativa = proc_justificativa;
    }

    public Status getStatus() {
        if (status == null) {
            status = new Status();
        }
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Autorizacao getAutorizacao() {
        if (autorizacao == null) {
            autorizacao = new Autorizacao();
        }
        return autorizacao;
    }

    public void setAutorizacao(Autorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public Motivo_Cancelamento getMotivo_cancelamento() {
        return motivo_cancelamento;
    }

    public void setMotivo_cancelamento(Motivo_Cancelamento motivo_cancelamento) {
        this.motivo_cancelamento = motivo_cancelamento;
    }
      
    public String getDataString() {
        if (id_formulario == -1) {
            dataString = "";
        } else {
            dataString = F.dataString(data);
        }
        return dataString;
    }
    public String getDataStringSoli() {
        if (id_formulario == -1) {
            dataString = "";
        } else {
            dataString = F.dataString(data_criacao);
        }
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public Procedimento_sus getP1() {
        return p1;
    }

    public void setP1(Procedimento_sus p1) {
        this.p1 = p1;
    }

    public Procedimento_sus getP2() {
        return p2;
    }

    public void setP2(Procedimento_sus p2) {
        this.p2 = p2;
    }

    public Procedimento_sus getP3() {
        return p3;
    }

    public void setP3(Procedimento_sus p3) {
        this.p3 = p3;
    }

    public Procedimento_sus getP4() {
        return p4;
    }

    public void setP4(Procedimento_sus p4) {
        this.p4 = p4;
    }

    public Procedimento_sus getP5() {
        return p5;
    }

    public Procedimento_sus getP6() {
        return p6;
    }

    public void setP5(Procedimento_sus p5) {
        this.p5 = p5;
    }

    public void setP6(Procedimento_sus p6) {
        this.p6 = p6;
    }

    //controle tela
    public boolean isChekBoxEnviar() {

        return chekBoxEnviar;
    }

    public void setChekBoxEnviar(boolean chekBoxEnviar) {
        this.chekBoxEnviar = chekBoxEnviar;
    }

    public Formulario_f2 getPag2() {
        if (pag2 == null) {
            pag2 = new Formulario_f2();
        }
        return pag2;
    }

    public void setPag2(Formulario_f2 pag2) throws ErroSistema {
        if (pag2 == null) {
            pag2 = new Formulario_f2DAO().buscaId(getId_formulario() + "");
        }
        this.pag2 = pag2;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public void buscaProcedimentosForm() throws ErroSistema {
        if (this != null) {
            List<Formulario_has_procedimento_sus> procHasFormaList = new Formulario_has_procedimento_susDAO().buscar(
                    " WHERE `formulario_id_formulario` = " + this.getId_formulario() + " ORDER BY `posicao`");
            if (procHasFormaList != null) {
                List<Procedimento_sus> procedimentos = new ArrayList<>();
                for (Formulario_has_procedimento_sus procHasFormaList1 : procHasFormaList) {
                    Procedimento_sus ps = new Procedimento_susDAO().buscaIdComp(procHasFormaList1.getProcedimento_sus().getCodigo() + "",procHasFormaList1.getProcedimento_sus().getDt_competencia());
                    ps.setQtd(procHasFormaList1.getQuantidade());
                    procedimentos.add(ps);
                }
                this.addProcedimento(procedimentos);
            }
        }

    }

    public String dataStringBanco() {
        return F.dataStringBanco(data);
    }
    
    public String data_criacaoString() {
        return F.dataString(data_criacao);
    }

//fim controle tela
    @Override
    public int compareTo(Formulario f) {
        return this.getP1().getCodigo().compareTo(f.getP1().getCodigo());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id_formulario);
        hash = 71 * hash + Objects.hashCode(this.data);
        hash = 71 * hash + Objects.hashCode(this.autenticacao);
        hash = 71 * hash + Objects.hashCode(this.paciente);
        hash = 71 * hash + Objects.hashCode(this.solicitante);
        hash = 71 * hash + Objects.hashCode(this.estabelecimento_de_saude_solicitante);
        hash = 71 * hash + Objects.hashCode(this.estabelecimento_de_saude_executante);
        hash = 71 * hash + Objects.hashCode(this.proc_justificativa);
        hash = 71 * hash + Objects.hashCode(this.status);
        hash = 71 * hash + Objects.hashCode(this.autorizacao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Formulario other = (Formulario) obj;
        if (!Objects.equals(this.autenticacao, other.autenticacao)) {
            return false;
        }
        if (!Objects.equals(this.id_formulario, other.id_formulario)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.solicitante, other.solicitante)) {
            return false;
        }
        if (!Objects.equals(this.estabelecimento_de_saude_solicitante, other.estabelecimento_de_saude_solicitante)) {
            return false;
        }
        if (!Objects.equals(this.estabelecimento_de_saude_executante, other.estabelecimento_de_saude_executante)) {
            return false;
        }
        if (!Objects.equals(this.proc_justificativa, other.proc_justificativa)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.autorizacao, other.autorizacao)) {
            return false;
        }
        if (!Objects.equals(this.pag2, other.pag2)) {
            return false;
        }
        if (!Objects.equals(this.p1, other.p1)) {
            return false;
        }
        if (!Objects.equals(this.p2, other.p2)) {
            return false;
        }
        if (!Objects.equals(this.p3, other.p3)) {
            return false;
        }
        if (!Objects.equals(this.p4, other.p4)) {
            return false;
        }
        if (!Objects.equals(this.p5, other.p5)) {
            return false;
        }
        if (!Objects.equals(this.p6, other.p6)) {
            return false;
        }
        return true;
    }

}
