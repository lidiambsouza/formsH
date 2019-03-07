package hujbb.informatica.apac.util;

import hujbb.informatica.apac.dao.CboDAO;
import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.dao.Cid_has_procedimento_susDAO;
import hujbb.informatica.apac.dao.CompetenciaDAO;
import hujbb.informatica.apac.dao.Forma_organizacaoDAO;
import hujbb.informatica.apac.dao.Grupo_procedimentoDAO;
import hujbb.informatica.apac.dao.Procedimento_susDAO;
import hujbb.informatica.apac.dao.Procedimento_sus_has_cboDAO;
import hujbb.informatica.apac.dao.Sub_grupo_procedimentoDAO;
import hujbb.informatica.apac.dao.Tb_descricaoDAO;
import hujbb.informatica.apac.dao.Tb_financiamentoDAO;
import hujbb.informatica.apac.dao.Tb_modalidadeDAO;
import hujbb.informatica.apac.dao.Tb_registroDAO;
import hujbb.informatica.apac.dao.Tb_registro_has_procedimento_susDAO;
import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Cid_has_procedimento_sus;
import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Competencia;

import hujbb.informatica.apac.entidades.Forma_organizacao;
import hujbb.informatica.apac.entidades.Grupo_procedimento;
import hujbb.informatica.apac.entidades.Procedimento_sus;
import hujbb.informatica.apac.entidades.Procedimento_sus_has_cbo;
import hujbb.informatica.apac.entidades.Sub_grupo_procedimento;
import hujbb.informatica.apac.entidades.Tb_descricao;
import hujbb.informatica.apac.entidades.Tb_financiamento;
import hujbb.informatica.apac.entidades.Tb_modalidade;
import hujbb.informatica.apac.entidades.Tb_registro;
import hujbb.informatica.apac.entidades.Tb_registro_has_procedimento_sus;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class CargaSIGTAP {

    private String caminhaZip;

    private List<String> coluna;
    private List<Integer> tamanho;
    private List<Integer> inicio;
    private List<Integer> fim;

    private List<String> linhasTxt;

    private String sql = "";

    public CargaSIGTAP(String caminhoZip) throws ErroSistema {
        this.caminhaZip = caminhoZip;
        linhasTxt = new ArrayList<>();
        coluna = new ArrayList<>();
        tamanho = new ArrayList<>();
        inicio = new ArrayList<>();
        fim = new ArrayList<>();
        zerarLayout();

        cargaGrupo_procedimento();
        F.intAux = 10;

        cargaSub_grupo_procedimento();
        F.intAux = 15;

        cargaForma_organizacao();
        F.intAux = 25;

        // cargaCbo();
        F.intAux = 30;

        cargaCid();
        F.intAux = 33;

        cargaModalidade();
        F.intAux = 38;

        cargaProced_financiamento();
        F.intAux = 46;

        cargaProcedimentos();
        F.intAux = 51;

        cargaRl_procedimento_modalidade();
        F.intAux = 63;

        cargaProcedimento_sus_has_cbo();
        F.intAux = 69;

        cargaCid_has_procedimento_sus();
        F.intAux = 80;

        cargaTb_registro();
        F.intAux = 86;

        cargaTb_registro_has_procedimento_sus();
        F.intAux = 99;

        cargaTb_descricao();
        F.intAux = 100;

        FabricaDeConexoes.fecharConecxao();
    }

    private void cargaGrupo_procedimento() throws ErroSistema {

        pegarosdados("tb_grupo");
        Grupo_procedimento conteudo = new Grupo_procedimento();
        Grupo_procedimentoDAO dao = new Grupo_procedimentoDAO();
        sql = "INSERT INTO `grupo_procedimento`(`id`, `nome`, `dt_competencia`) VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_GRUPO
                        conteudo.setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//NO_GRUPO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                            //salva a competencia
                            new CompetenciaDAO().salvar(new Competencia(conteudo.getDt_competencia()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 112");
                        }

                        sql += " ('" + conteudo.getId() + "','" + conteudo.getNome() + "','" + conteudo.getDt_competencia() + "'),\n";

                        conteudo = new Grupo_procedimento();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaSub_grupo_procedimento() throws ErroSistema {
        pegarosdados("tb_sub_grupo");
        Sub_grupo_procedimento conteudo = new Sub_grupo_procedimento();
        Sub_grupo_procedimentoDAO dao = new Sub_grupo_procedimentoDAO();
        sql = "INSERT INTO `sub_grupo_procedimento`(`id`, `grupo_procedimento_id`, `nome`, `dt_competencia`) VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_GRUPO
                        conteudo.getGrupo_Procedimento().setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//CO_SUB_GRUPO(cod gp + cod sub gp)
                        conteudo.setId(
                                conteudo.getGrupo_Procedimento().getId() //cod gp
                                + linha.substring(inicio.get(i) - 1, fim.get(i)).trim() //cod sub gp
                        );
                        break;
                    }
                    case 2: {//NO_SUB_GRUPO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 3: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_Competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 102");
                        }
                        sql += " ('" + conteudo.getId() + "','" + conteudo.getGrupo_Procedimento().getId() + "','" + conteudo.getNome() + "','" + conteudo.getDt_Competencia() + "'),\n";

                        conteudo = new Sub_grupo_procedimento();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }//

    private void cargaForma_organizacao() throws ErroSistema {
        pegarosdados("tb_forma_organizacao");
        Forma_organizacao conteudo = new Forma_organizacao();
        Forma_organizacaoDAO dao = new Forma_organizacaoDAO();
        sql = "INSERT INTO `forma_organizacao`(`id`, `sub_grupo_procedimento_id`, `nome`, `dt_competencia`)VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_GRUPO
                        conteudo.getSub_grupo_procedimento().getGrupo_Procedimento().setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//CO_SUB_GRUPO (cod gp + cod sub gp)
                        conteudo.getSub_grupo_procedimento().setId(
                                conteudo.getSub_grupo_procedimento().getGrupo_Procedimento().getId() // cod gp
                                + linha.substring(inicio.get(i) - 1, fim.get(i)).trim() // cod sub gp
                        );
                        break;
                    }
                    case 2: {//CO_FORMA_ORGANIZACAO (cod gp + cd sub gp + cod forma)
                        conteudo.setId(
                                conteudo.getSub_grupo_procedimento().getId() // cod gp + cod sub gp
                                + linha.substring(inicio.get(i) - 1, fim.get(i)).trim() // cod forma
                        );
                        break;
                    }
                    case 3: {//NO_FORMA_ORGANIZACAO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 4: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 148");
                        }
                        sql += " ('" + conteudo.getId() + "','" + conteudo.getSub_grupo_procedimento().getId() + "','" + conteudo.getNome() + "','" + conteudo.getDt_competencia() + "'),\n";
                        conteudo = new Forma_organizacao();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);

    }

    private void cargaCbo() throws ErroSistema {
        pegarosdados("tb_ocupacao");
        Cbo conteudo = new Cbo();
        CboDAO dao = new CboDAO();
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_OCUPACAO
                        conteudo.setCod(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//NO_OCUPACAO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());

                        dao.salvar(conteudo);

                        conteudo = new Cbo();

                        break;
                    }

                }

            }//fim for 2
        }

    }

    private void cargaCid() throws ErroSistema {
        pegarosdados("tb_cid");
        Cid conteudo = new Cid();
        CidDAO dao = new CidDAO();
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_CID
                        conteudo.setCid(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//NO_CID
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 3: {//TP_SEXO
                        conteudo.getTp_sexo().setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());

                        dao.salvar(conteudo);

                        conteudo = new Cid();
                        break;
                    }

                    default: {
                        break;
                    }
                }

            }//fim for 2
        }

    }

    private void cargaProced_financiamento() throws ErroSistema {
        pegarosdados("tb_financiamento");
        Tb_financiamento conteudo = new Tb_financiamento();
        Tb_financiamentoDAO dao = new Tb_financiamentoDAO();
        sql = "INSERT INTO `proced_financiamento`(`id`, `nome`, `dt_competencia`) VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_FINANCIAMENTO
                        conteudo.setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//NO_FINANCIAMENTO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 260");
                        }
                        sql += " ('" + conteudo.getId() + "','" + conteudo.getNome() + "','" + conteudo.getDt_competencia() + "'),\n";

                        conteudo = new Tb_financiamento();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaModalidade() throws ErroSistema {
        pegarosdados("tb_modalidade");
        Tb_modalidade conteudo = new Tb_modalidade();
        Tb_modalidadeDAO dao = new Tb_modalidadeDAO();
        sql = "INSERT INTO `tb_modalidade`(`id`, `modalidade`, `dt_competencia`) VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_MODALIDADE
                        conteudo.setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//NO_MODALIDADE
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 300");
                        }
                        sql += " ('" + conteudo.getId() + "','" + conteudo.getNome() + "','" + conteudo.getDt_competencia() + "'),\n";
                        conteudo = new Tb_modalidade();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaProcedimentos() throws ErroSistema {
        pegarosdados("tb_procedimento");
        Procedimento_sus conteudo = new Procedimento_sus();
        Procedimento_susDAO dao = new Procedimento_susDAO();
        sql = "INSERT INTO `procedimento_sus`(`codigo`, `nome`, `proced_tp_compexidade_id`, `tp_sexo_id`, `qtd_max`, `qtd_pontos`, `qtd`, `idade_min`, `idade_max`, `valor_hospitalar`, `valor_ambulatorial`, `valor_profissonal`, `proced_financiamento_id`, `tb_modalidade_id`, `dt_competencia`)VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_PROCEDIMENTO
                        conteudo.setCodigo(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//NO_PROCEDIMENTO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 2: {//TP_COMPLEXIDADE
                        conteudo.getProced_tp_compexidade().setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 3: {//TP_SEXO
                        conteudo.getTp_sexo().setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 4: {//QT_MAXIMA_EXECUCAO
                        conteudo.setQtd_max(F.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 6: {//QT_PONTOS
                        conteudo.setQtd_pontos(F.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 7: {//VL_IDADE_MINIMA
                        conteudo.setIdade_min(F.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 8: {//VL_IDADE_MAXIMA
                        conteudo.setIdade_max(F.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 9: {//VL_SH
                        conteudo.setValor_hospitalar(F.parseFloat(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 10: {//VL_SA
                        conteudo.setValor_ambulatorial(F.parseFloat(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 11: {//VL_SP
                        conteudo.setValor_proficional(F.parseFloat(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 12: {//CO_FINANCIAMENTO
                        conteudo.getProced_financiamento().setId(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }

                    case 15: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 384");
                        }
                        sql += " ('"
                                + conteudo.getCodigo() + "','"
                                + conteudo.getNome() + "','"
                                + conteudo.getProced_tp_compexidade().getId() + "','"
                                + conteudo.getTp_sexo().getId() + "','"
                                + conteudo.getQtd_max() + "','"
                                + conteudo.getQtd_pontos() + "','"
                                + conteudo.getQtd() + "','"
                                + conteudo.getIdade_min() + "','"
                                + conteudo.getIdade_max() + "','"
                                + conteudo.getValor_hospitalar() + "','"
                                + conteudo.getValor_ambulatorial() + "','"
                                + conteudo.getValor_proficional() + "','"
                                + conteudo.getProced_financiamento().getId() + "','"
                                + conteudo.getTb_modalidade().getId() + "','"
                                + conteudo.getDt_competencia()
                                + "'),\n";

                        conteudo = new Procedimento_sus();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaRl_procedimento_modalidade() throws ErroSistema {
        pegarosdados("rl_procedimento_modalidade");
        String codModalidade = "";
        String codProcedimento = "";
        int dt_competencia = -1;
        Procedimento_susDAO dao = new Procedimento_susDAO();
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_PROCEDIMENTO
                        codProcedimento = linha.substring(inicio.get(i) - 1, fim.get(i)).trim();
                        break;
                    }
                    case 1: {//CO_MODALIDADE
                        codModalidade = linha.substring(inicio.get(i) - 1, fim.get(i)).trim();
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            dt_competencia = Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 432");
                        }
                        dao.atualizarCarga(codProcedimento, dt_competencia, codModalidade);
                        codProcedimento = "";
                        codModalidade = "";
                        dt_competencia = -1;
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }

    }

    private void cargaProcedimento_sus_has_cbo() throws ErroSistema, ErroSistema {
        pegarosdados("rl_procedimento_ocupacao");
        Procedimento_sus_has_cbo conteudo = new Procedimento_sus_has_cbo();
        Procedimento_sus_has_cboDAO dao = new Procedimento_sus_has_cboDAO();
        sql = "INSERT INTO `procedimento_sus_has_cbo`(`procedimento_sus_codigo`, `cbo_id`, `dt_competencia`) VALUES\n";
        int cont = 0;
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_PROCEDIMENTO
                        conteudo.setProcedimento_sus_codigo(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//CO_OCUPACAO
                        conteudo.setCbo_id(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 419");
                        }
                        if (cont > 15000) {
                            sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
                            F.executSql(sql);
                            cont = 0;
                            sql = "INSERT INTO `procedimento_sus_has_cbo`(`procedimento_sus_codigo`, `cbo_id`, `dt_competencia`) VALUES\n";
                        }

                        sql += " ('"
                                + conteudo.getProcedimento_sus_codigo() + "','"
                                + conteudo.getCbo_id() + "','"
                                + conteudo.getDt_competencia()
                                + "'),\n";
                        cont++;
                        conteudo = new Procedimento_sus_has_cbo();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2

        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaCid_has_procedimento_sus() throws ErroSistema {

        pegarosdados("rl_procedimento_cid");
        Cid_has_procedimento_sus conteudo = new Cid_has_procedimento_sus();
        Cid_has_procedimento_susDAO dao = new Cid_has_procedimento_susDAO();
        sql = "INSERT INTO `cid_has_procedimento_sus`(`cid_cid`, `procedimento_sus_codigo`, `cid_principal`, `dt_competencia`) VALUES\n";
        int cont = 0;
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_PROCEDIMENTO
                        conteudo.setCod_procedimento(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//CO_CID
                        conteudo.setCod_cid(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 2: {//ST_PRINCIPAL
                        if (linha.substring(inicio.get(i) - 1, fim.get(i)).trim().equals("S")) {
                            conteudo.setPrincipal(true);
                        } else {
                            conteudo.setPrincipal(false);
                        }
                        break;
                    }

                    case 3: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 465");
                        }

                        if (cont > 15000) {
                            sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
                            F.executSql(sql);
                            cont = 0;
                            sql = "INSERT INTO `cid_has_procedimento_sus`(`cid_cid`, `procedimento_sus_codigo`, `cid_principal`, `dt_competencia`) VALUES\n";
                        }

                        sql += " ('"
                                + conteudo.getCod_cid() + "','"
                                + conteudo.getCod_procedimento() + "',"
                                + conteudo.isPrincipal() + ",'"
                                + conteudo.getDt_competencia()
                                + "'),\n";
                        conteudo = new Cid_has_procedimento_sus();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2

        }//for1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaTb_registro() throws ErroSistema {
        pegarosdados("tb_registro");
        Tb_registro conteudo = new Tb_registro();
        Tb_registroDAO dao = new Tb_registroDAO();
        sql = "INSERT INTO `tb_registro`(`id`, `nome`, `dt_competecia`) VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_REGISTRO
                        conteudo.setId(F.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }
                    case 1: {//NO_REGISTRO
                        conteudo.setNome(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 419");
                        }
                        sql += " ('"
                                + conteudo.getId() + "','"
                                + conteudo.getNome() + "','"
                                + conteudo.getDt_competencia()
                                + "'),\n";

                        conteudo = new Tb_registro();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaTb_registro_has_procedimento_sus() throws ErroSistema {
        pegarosdados("rl_procedimento_registro");
        Tb_registro_has_procedimento_sus conteudo = new Tb_registro_has_procedimento_sus();
        Tb_registro_has_procedimento_susDAO dao = new Tb_registro_has_procedimento_susDAO();
        sql = "INSERT INTO `tb_registro_has_procedimento_sus`(`tb_registro_id`, `procedimento_sus_codigo`, `dt_competencia`) VALUES\n";
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_PROCEDIMENTO
                        conteudo.setProcedimento_sus_codigo(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//CO_REGISTRO
                        conteudo.setTb_registro_id(F.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 541");
                        }
                        sql += " ('"
                                + conteudo.getTb_registro_id() + "','"
                                + conteudo.getProcedimento_sus_codigo() + "','"
                                + conteudo.getDt_competencia()
                                + "'),\n";
                        conteudo = new Tb_registro_has_procedimento_sus();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void cargaTb_descricao() throws ErroSistema {
        pegarosdados("tb_descricao");
        Tb_descricao conteudo = new Tb_descricao();
        Tb_descricaoDAO dao = new Tb_descricaoDAO();
        sql = "INSERT INTO `tb_descricao`(`procedimento_sus_codigo`, `descricao`, `dt_competencia`) VALUES\n";
        int cont = 0;
        for (String linha : linhasTxt) {

            for (int i = 0; i < coluna.size(); i++) {//2

                switch (i) {
                    case 0: {//CO_PROCEDIMENTO
                        conteudo.setCod_procedimento(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }
                    case 1: {//DS_PROCEDIMENTO
                        conteudo.setDescricao(linha.substring(inicio.get(i) - 1, fim.get(i)).trim());
                        break;
                    }

                    case 2: {//DT_COMPETENCIA
                        try {
                            conteudo.setDt_competencia(Integer.parseInt(linha.substring(inicio.get(i) - 1, fim.get(i)).trim()));
                        } catch (NumberFormatException e) {
                            F.setMsgErro(e.toString() + ":cargaSigtap 580");
                        }
                        if (cont > 5000) {
                            sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
                            F.executSql(sql);
                            cont = 0;
                            sql = "INSERT INTO `tb_descricao`(`procedimento_sus_codigo`, `descricao`, `dt_competencia`) VALUES\n";
                        }

                        sql += " ('"
                                + conteudo.getCod_procedimento() + "','"
                                + conteudo.getDescricao().replace("'", "") + "','"
                                + conteudo.getDt_competencia()
                                + "'),\n";
                        conteudo = new Tb_descricao();
                        break;
                    }
                    default: {
                        break;
                    }
                }

            }//fim for 2
        }//for 1
        sql = sql.substring(0, sql.length() - 2) + ";\n\n\n";
        F.executSql(sql);
    }

    private void pegarosdados(String nomeArqTxt) {
        setLayout(nomeArqTxt);
        lertxt(nomeArqTxt + ".txt");

    }

    private void setLayout(String layuotTxt) {
        zerarLayout();
        lertxt(layuotTxt + "_layout.txt");

        String linhaAtual = "";

        //ler cada linha do vetor(LinhasTxt)
        for (int i = 1; i < linhasTxt.size(); i++) {//1
            linhaAtual = linhasTxt.get(i);
            //ler cada caracter da linha atual
            String dados[] = linhaAtual.split(",");
            coluna.add(dados[0]);
            tamanho.add(Integer.parseInt(dados[1]));
            inicio.add(Integer.parseInt(dados[2]));
            fim.add(Integer.parseInt(dados[3]));
            //   F.setMsgErro("inicio" + inicio);
            //  F.setMsgErro("fim" + fim);
        }//fim for 1

    }

    private void zerarLayout() {
        coluna.clear();
        tamanho.clear();
        inicio.clear();
        fim.clear();
    }

    private void lertxt(String nomeTxt) {
        String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("")
                + "arqtemp/";
        String linha;
        linhasTxt.clear();
        nomeTxt = nomeTxt.replace(".txt", "") + ".txt";
        //F.setMsgErro(x+nomeTxt);
        try {
//            BufferedReader br = new BufferedReader(new FileReader(new File(caminho + nomeTxt)));
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + nomeTxt), "ISO-8859-1"));
            
            linha = br.readLine();
            while (linha != null) {

                //   F.setMsgErro(linha);
                linhasTxt.add(F.tratarStringBanco(linha));
                linha = br.readLine();
               
            }

            br.close();
        } catch (IOException ioe) {
            F.setMsgErro(ioe.toString());

        } catch (NullPointerException ioe) {
            F.setMsgErro(ioe.toString());
            F.mensagem(nomeTxt, "Não encontrado no arquivo zip!", FacesMessage.SEVERITY_ERROR);
        }

    }

}
