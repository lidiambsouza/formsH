package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.dao.Cid_has_procedimento_susDAO;
import hujbb.informatica.apac.dao.Forma_organizacaoDAO;
import hujbb.informatica.apac.dao.Grupo_procedimentoDAO;
import hujbb.informatica.apac.dao.Procedimento_susDAO;
import hujbb.informatica.apac.dao.Sub_grupo_procedimentoDAO;
import hujbb.informatica.apac.dao.Tb_descricaoDAO;
import hujbb.informatica.apac.dao.Tb_registroDAO;
import hujbb.informatica.apac.dao.Tb_registro_has_procedimento_susDAO;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

public class Procedimento_sus implements Serializable {

    private String codigo;
    private String nome;
    private Proced_tp_compexidade proced_tp_compexidade;
    private Tp_sexo tp_sexo;
    private int qtd_max;
    private int qtd_pontos;
    private int qtd;
    private int idade_min;
    private int idade_max;
    private float valor_hospitalar;
    private float valor_ambulatorial;
    private float valor_proficional;
    private Tb_financiamento proced_financiamento;
    private Tb_modalidade tb_modalidade;
    private int dt_competencia;
    private String qtd_string;
    private String mascaraCodigo;

    public Procedimento_sus() {
        this.codigo = "";
        this.nome = "";
        this.proced_tp_compexidade = new Proced_tp_compexidade();
        this.tp_sexo = new Tp_sexo();
        this.qtd_max = -1;
        this.qtd_pontos = -1;
        this.qtd = -1;
        this.idade_min = -1;
        this.idade_max = -1;
        this.valor_hospitalar = -1;
        this.valor_ambulatorial = -1;
        this.valor_proficional = -1;
        this.proced_financiamento = new Tb_financiamento();
        this.tb_modalidade = new Tb_modalidade();
        this.dt_competencia = -1;

    }

    public Procedimento_sus(String codigo, String nome, Proced_tp_compexidade proced_tp_compexidade, Tp_sexo tp_sexo, int qtd_max, int qtd_pontos, int qtd, int idade_min, int idade_max, float valor_hospitalar, float valor_ambulatorial, float valor_proficional, Tb_financiamento proced_financiamento, Tb_modalidade tb_modalidade, int dt_competencia) {
        this.codigo = codigo;
        this.nome = nome;
        this.proced_tp_compexidade = proced_tp_compexidade;
        this.tp_sexo = tp_sexo;
        this.qtd_max = qtd_max;
        this.qtd_pontos = qtd_pontos;
        this.qtd = qtd;
        this.idade_min = idade_min;
        this.idade_max = idade_max;
        this.valor_hospitalar = valor_hospitalar;
        this.valor_ambulatorial = valor_ambulatorial;
        this.valor_proficional = valor_proficional;
        this.proced_financiamento = proced_financiamento;
        this.tb_modalidade = tb_modalidade;
        this.dt_competencia = dt_competencia;

    }

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd() {
        return 7;
    }

    public String getMascaraCodigo() { 

        if (!getCodigo().isEmpty()) {
            String mscod = getCodigo();
            

            StringBuilder stringBuilder = new StringBuilder(mscod);
            stringBuilder.insert(mscod.length() - 1, '-');
            stringBuilder.insert(mscod.length() - 4, '.');
            stringBuilder.insert(mscod.length() - 6, '.');
            stringBuilder.insert(mscod.length() - 8, '.');
            mascaraCodigo = stringBuilder.toString();

        } else {
            mascaraCodigo = "";
        }
        return mascaraCodigo;
    }

    public void setMascaraCodigo(String mascaraCodigo) {
        this.mascaraCodigo = mascaraCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Proced_tp_compexidade getProced_tp_compexidade() {
        return proced_tp_compexidade;
    }

    public void setProced_tp_compexidade(Proced_tp_compexidade proced_tp_compexidade) {
        this.proced_tp_compexidade = proced_tp_compexidade;
    }

    public Tp_sexo getTp_sexo() {
        return tp_sexo;
    }

    public void setTp_sexo(Tp_sexo tp_sexo) {
        this.tp_sexo = tp_sexo;
    }

    public int getQtd_max() {
        return qtd_max;
    }

    public void setQtd_max(int qtd_max) {
        this.qtd_max = qtd_max;
    }

    public int getQtd_pontos() {
        return qtd_pontos;
    }

    public void setQtd_pontos(int qtd_pontos) {
        this.qtd_pontos = qtd_pontos;
    }

    public int getIdade_min() {
        return idade_min;
    }

    public void setIdade_min(int idade_min) {
        this.idade_min = idade_min;
    }

    public int getIdade_max() {
        return idade_max;
    }

    public void setIdade_max(int idade_max) {
        this.idade_max = idade_max;
    }

    public float getValor_hospitalar() {
        return valor_hospitalar;
    }

    public void setValor_hospitalar(float valor_hospitalar) {
        if (valor_hospitalar == 0) {
            this.valor_hospitalar = valor_hospitalar;
        } else {
            this.valor_hospitalar = valor_hospitalar / 100;
        }

    }

    public Tb_modalidade getTb_modalidade() {
        return tb_modalidade;
    }

    public void setTb_modalidade(Tb_modalidade tb_modalidade) {
        this.tb_modalidade = tb_modalidade;
    }

    public float getValor_ambulatorial() {
        return valor_ambulatorial;
    }

    public void setValor_ambulatorial(float valor_ambulatorial) {
        if (valor_ambulatorial == 0) {
            this.valor_ambulatorial = valor_ambulatorial;
        } else {
            this.valor_ambulatorial = valor_ambulatorial / 100;

        }
    }

    public float getValor_proficional() {
        return valor_proficional;
    }

    public void setValor_proficional(float valor_proficional) {
        if (valor_proficional == 0) {
            this.valor_proficional = valor_proficional;
        } else {
            this.valor_proficional = valor_proficional / 100;
        }
    }

    public Tb_financiamento getProced_financiamento() {
        return proced_financiamento;
    }

    public void setProced_financiamento(Tb_financiamento proced_financiamento) {
        this.proced_financiamento = proced_financiamento;
    }

    public int getDt_competencia() {
        return dt_competencia;
    }

    public void setDt_competencia(int dt_competencia) {
        this.dt_competencia = dt_competencia;
    }

    public int getQtd() {
        if (qtd == -1) {
            qtd = 0;
        }
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getQtd_string() {
        if (getQtd() < 1) {
            qtd_string = "";
        } else {
            qtd_string = getQtd() + "";
        }

        return qtd_string;
    }

    public void setQtd_string(String qtd_string) {
        this.qtd_string = qtd_string;
    }

    //funcoes tela sigtap
    //retorna o grupo de procedimento
    public Grupo_procedimento grupoProced() throws ErroSistema {
        if (!codigo.isEmpty()) {
            Grupo_procedimento gp = new Grupo_procedimentoDAO().buscaId(getCodigo().substring(0, 2));
            
            return gp;
        } else {
            return new Grupo_procedimento();
        }
    }

    //retorna o  subgrupo de procedimento
    public Sub_grupo_procedimento subGrupoProced() throws ErroSistema {
        if (!codigo.isEmpty()) {
            Sub_grupo_procedimento s = new Sub_grupo_procedimentoDAO().buscaId(getCodigo().substring(0, 4));
            s.setId(s.getId().substring(2));
            

            return s;
        } else {
            
            return new Sub_grupo_procedimento();
        }
    }

    //retorna o  forma de organização de procedimento
    public Forma_organizacao formaOrganizaProced() throws ErroSistema {

        if (!codigo.isEmpty()) {
            Forma_organizacao f = new Forma_organizacaoDAO().buscaId(getCodigo().substring(0, 6));
            f.setId(f.getId().substring(4));
            
            return f;
        } else {
            
            return new Forma_organizacao();
        }
    }

    //retorna o intrumento de registro deste  procedimento
    public Tb_registro registro() throws ErroSistema {
        List<Tb_registro_has_procedimento_sus> listRegistroProced = new Tb_registro_has_procedimento_susDAO().buscar("`procedimento_sus_codigo` = '" + codigo + "'");
        if (!listRegistroProced.isEmpty()) {
            Tb_registro_has_procedimento_sus tb_registro_has_procedimento_sus = listRegistroProced.get(0);
            return new Tb_registroDAO().buscaId(tb_registro_has_procedimento_sus.getTb_registro_id() + "");
        }
        
        return new Tb_registro();
    }

    //retorna a descricao do procedimento
    public String descricao() throws ErroSistema {
        Tb_descricao descricao = new Tb_descricaoDAO().buscaId(codigo, dt_competencia);
        if (descricao == null) {
           
            return "";
        }
        
        return descricao.getDescricao();
    }

    //retorna todos os cids relacionados com esse procedimento
    public List<Cid> cids() throws ErroSistema { System.out.println("teste git");
        List<Cid> cids = new Cid_has_procedimento_susDAO().buscarCids("cid_has_procedimento_sus.`procedimento_sus_codigo` = '" + codigo + "' AND cid_has_procedimento_sus.`dt_competencia` = " +getDt_competencia()+" ORDER BY cid.`nome`  ");
        return cids;
    }

    //retorna todos os cids relacionados com esse procedimento
    public List<Cbo> cbos() throws ErroSistema {
        /*  Cbo cbo = new Cbo();
        CboDAO cboDAO = new CboDAO();
        List<Cbo> cids = new ArrayList<>();
        List<Procedimento_sus_has_cbo> cbos = new Procedimento_sus_has_cboDAO().buscar("procedimento_sus_codigo = '" + codigo + "'");
        if (cbos != null) {
            for (Procedimento_sus_has_cbo cb : cbos) {
                cbo = cboDAO.buscaId(cb.getCbo_id());
                if(cbo!=null){
                    cids.add(cid);
                }
            }
        }
        */
        return null;
    }
//metodos extras

    public String qtdString(int qtd) {
        if (qtd == 9999) {
            return "Não se aplica";
        } else {
            return qtd + "";
        }
    }

    public String qtdIdadeString(int qtd) {
        if (qtd == 9999) {
            return "Não se aplica";
        } else if (qtd == 0 || qtd < 12) {
            return qtd + " meses";
        } else {
            qtd = qtd / 12;
            if (qtd == 1) {
                return qtd + "ano";
            } else {
                return qtd + " anos";

            }
        }
    }
    
    //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        
        if (condicao.isEmpty()) {
            condicao = "WHERE procedimento_sus.`codigo` <> '-9'  ORDER BY procedimento_sus.`nome`";
        }
        ArrayList<SelectItem> itens = new ArrayList<>();
        List<Procedimento_sus> m = new Procedimento_susDAO().buscar(condicao);
        for (int i = 0; i < m.size(); i++) {
            String nome = (m.get(i).getNome().length()<70) ? m.get(i).getNome(): m.get(i).getNome().substring(0, 69);
            itens.add(new SelectItem(m.get(i).getCodigo() + "",m.get(i).getCodigo()+" - "+ nome ));
        }
               
        return itens;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.codigo);
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.proced_tp_compexidade);
        hash = 53 * hash + Objects.hashCode(this.tp_sexo);
        hash = 53 * hash + this.qtd_max;
        hash = 53 * hash + this.qtd_pontos;
        hash = 53 * hash + this.idade_min;
        hash = 53 * hash + this.idade_max;
        hash = 53 * hash + Float.floatToIntBits(this.valor_hospitalar);
        hash = 53 * hash + Float.floatToIntBits(this.valor_ambulatorial);
        hash = 53 * hash + Float.floatToIntBits(this.valor_proficional);
        hash = 53 * hash + Objects.hashCode(this.proced_financiamento);
        hash = 53 * hash + Objects.hashCode(this.tb_modalidade);
        hash = 53 * hash + this.dt_competencia;
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
        final Procedimento_sus other = (Procedimento_sus) obj;
        if (this.qtd_max != other.qtd_max) {
            return false;
        }
        if (this.qtd_pontos != other.qtd_pontos) {
            return false;
        }
        if (this.idade_min != other.idade_min) {
            return false;
        }
        if (this.idade_max != other.idade_max) {
            return false;
        }
        if (Float.floatToIntBits(this.valor_hospitalar) != Float.floatToIntBits(other.valor_hospitalar)) {
            return false;
        }
        if (Float.floatToIntBits(this.valor_ambulatorial) != Float.floatToIntBits(other.valor_ambulatorial)) {
            return false;
        }
        if (Float.floatToIntBits(this.valor_proficional) != Float.floatToIntBits(other.valor_proficional)) {
            return false;
        }
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.proced_tp_compexidade, other.proced_tp_compexidade)) {
            return false;
        }
        if (!Objects.equals(this.tp_sexo, other.tp_sexo)) {
            return false;
        }
        if (!Objects.equals(this.proced_financiamento, other.proced_financiamento)) {
            return false;
        }
        if (!Objects.equals(this.tb_modalidade, other.tb_modalidade)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Procedimento_sus{" + "codigo=" + codigo + ", nome=" + nome + ", proced_tp_compexidade=" + proced_tp_compexidade + ", tp_sexo=" + tp_sexo + ", qtd_max=" + qtd_max + ", qtd_pontos=" + qtd_pontos + ", qtd=" + qtd + ", idade_min=" + idade_min + ", idade_max=" + idade_max + ", valor_hospitalar=" + valor_hospitalar + ", valor_ambulatorial=" + valor_ambulatorial + ", valor_proficional=" + valor_proficional + ", proced_financiamento=" + proced_financiamento + ", tb_modalidade=" + tb_modalidade + ", dt_competencia=" + dt_competencia + '}';
    }

}
