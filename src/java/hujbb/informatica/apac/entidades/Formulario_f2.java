package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.util.F;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Formulario_f2 implements Serializable {

    private int formulario_id;
    private String local_tumor_primario;
    private String cid_topografia_onco;
    private int linfo_reg_invalido;//0 sim 1 nao 2 nao av

    private String loc_meta;
    private String esta_uicc;
    private String esta_outros;
    private String grau_isto;
    private String diag_cito_isto;
    private Date data_64;           //
    private boolean quimio_radio; //flag false quimo true radio
    private boolean trate_anteriores_quimio; //
    private String trate_ante1_quimio;
    private String trate_ante2_quimio;
    private String trate_ante3_quimio;
    private Date dt_trate_ante1_quimio;  //
    private Date dt_trate_ante2_quimio;  //
    private Date dt_trate_ante3_quimio;  //
    private boolean continue_trate_quimio;  //
    private Date dt_ini_trata_quimio;      //
    private String esquema_sigla_quimio;
    private int qtd_mese_planejado_quimio;
    private int qtd_mese_autorizados_quimio;
    ///tranforma para string para impressão
    private String qtd_mese_planejado_quimioSTR;
    private String qtd_mese_autorizados_quimioSTR;

    private boolean trate_ante_radio;       //
    private String trate_ante1_radio;
    private String trate_ante2_radio;
    private String trate_ante3_radio;
    private Date dt_trate_ante1_radio;      //
    private Date dt_trate_ante2_radio;      //
    private Date dt_trate_ante3_radio;      //
    private boolean continue_trate_radio;   //
    private Date dt_ini_trata_radio;        //
    private Finalidade_radio_f2 finalidade;
    private String cid_topografico1_radio;
    private String cid_topografico2_radio;
    private String cid_topografico3_radio;
    private String area_irradi_descricao1_radio;
    private String area_irradi_descricao2_radio;
    private String area_irradi_descricao3_radio;
    private String ncampo_incer1_radio;
    private String ncampo_incer2_radio;
    private String ncampo_incer3_radio;
    private Date dt_ini_area_irrad1_radio;     //
    private Date dt_ini_area_irrad2_radio;      //
    private Date dt_ini_area_irrad3_radio;      //
    private Date dt_fim_area_irrad1_radio;      //
    private Date dt_fim_area_irrad2_radio;      //
    private Date dt_fim_area_irrad3_radio;      //

    private String trate_anteriores_quimioStringString; //para o outputlabael 65

    //VARIAVEIS EXTRAS PARA PDF
    private String trate_anteriores_quimioStringSim;
    private String trate_anteriores_quimioStringNao;
    private String data_64String;
    private String dt_trate_ante1_quimioString;
    private String dt_trate_ante2_quimioString;
    private String dt_trate_ante3_quimioString;
    private String continue_trate_quimioStringSim;
    private String continue_trate_quimioStringNao;
    private String dt_ini_trata_quimioString;
    private String trate_ante_radioStringSim;
    private String trate_ante_radioStringNao;
    private String dt_trate_ante1_radioString;
    private String dt_trate_ante2_radioString;
    private String dt_trate_ante3_radioString;
    private String continue_trate_radioStringSim;
    private String continue_trate_radioStringNao;
    private String dt_ini_trata_radioString;
    private String dt_ini_area_irrad1_radioString;
    private String dt_ini_area_irrad2_radioString;
    private String dt_ini_area_irrad3_radioString;
    private String dt_fim_area_irrad1_radioString;
    private String dt_fim_area_irrad2_radioString;
    private String dt_fim_area_irrad3_radioString;
    private String linfo_reg_invalidoStringSim;
    private String linfo_reg_invalidoStringNao;
    private String linfo_reg_invalidoNaoAvString;

    //variaveis para a tela
    /////////////////////////////////////////////
    public Formulario_f2() {
        this.formulario_id = -1;
        this.local_tumor_primario = "";
        this.cid_topografia_onco = "";
        this.linfo_reg_invalido = -1;
        this.linfo_reg_invalidoStringSim = "";
        this.linfo_reg_invalidoStringNao = "";

        this.linfo_reg_invalidoNaoAvString = "";
        this.loc_meta = "";
        this.esta_uicc = "";
        this.esta_outros = "";
        this.grau_isto = "";
        this.diag_cito_isto = "";
        this.data_64 = null;
        this.quimio_radio = false;//quimio
        this.trate_anteriores_quimio = false;
        this.trate_ante1_quimio = "";
        this.trate_ante2_quimio = "";
        this.trate_ante3_quimio = "";
        this.dt_trate_ante1_quimio = null;
        this.dt_trate_ante2_quimio = null;
        this.dt_trate_ante3_quimio = null;
        this.continue_trate_quimio = false;
        this.dt_ini_trata_quimio = null;
        this.esquema_sigla_quimio = "";
        this.qtd_mese_planejado_quimio = 0;
        this.qtd_mese_autorizados_quimio = 0;
        this.trate_ante_radio = false;
        this.trate_ante1_radio = "";
        this.trate_ante2_radio = "";
        this.trate_ante3_radio = "";
        this.dt_trate_ante1_radio = null;
        this.dt_trate_ante2_radio = null;
        this.dt_trate_ante3_radio = null;
        this.continue_trate_radio = false;
        this.dt_ini_trata_radio = null;
        this.finalidade = new Finalidade_radio_f2();
        this.cid_topografico1_radio = "";
        this.cid_topografico2_radio = "";
        this.cid_topografico3_radio = "";
        this.area_irradi_descricao1_radio = "";
        this.area_irradi_descricao2_radio = "";
        this.area_irradi_descricao3_radio = "";
        this.ncampo_incer1_radio = "";
        this.ncampo_incer2_radio = "";
        this.ncampo_incer3_radio = "";
        this.dt_ini_area_irrad1_radio = null;
        this.dt_ini_area_irrad2_radio = null;
        this.dt_ini_area_irrad3_radio = null;
        this.dt_fim_area_irrad1_radio = null;
        this.dt_fim_area_irrad2_radio = null;
        this.dt_fim_area_irrad3_radio = null;

    }

    public Formulario_f2(int formulario_id, String local_tumor_primario, String cid_topografia_onco, int linfo_reg_invalido, String loc_meta, String esta_uicc, String esta_outros, String grau_isto, String diag_cito_isto, Date data_64, boolean quimio_radio, boolean trate_anteriores_quimio, String trate_ante1_quimio, String trate_ante2_quimio, String trate_ante3_quimio, Date dt_trate_ante1_quimio, Date dt_trate_ante2_quimio, Date dt_trate_ante3_quimio, boolean continue_trate_quimio, Date dt_ini_trata_quimio, String esquema_sigla_quimio, int qtd_mese_planejado_quimio, int qtd_mese_autorizados_quimio, boolean trate_ante_radio, String trate_ante1_radio, String trate_ante2_radio, String trate_ante3_radio, Date dt_trate_ante1_radio, Date dt_trate_ante2_radio, Date dt_trate_ante3_radio, boolean continue_trate_radio, Date dt_ini_trata_radio, Finalidade_radio_f2 finalidade, String cid_topografico1_radio, String cid_topografico2_radio, String cid_topografico3_radio, String area_irradi_descricao1_radio, String area_irradi_descricao2_radio, String area_irradi_descricao3_radio, String ncampo_incer1_radio, String ncampo_incer2_radio, String ncampo_incer3_radio, Date dt_ini_area_irrad1_radio, Date dt_ini_area_irrad2_radio, Date dt_ini_area_irrad3_radio, Date dt_fim_area_irrad1_radio, Date dt_fim_area_irrad2_radio, Date dt_fim_area_irrad3_radio) {
        this.formulario_id = formulario_id;
        this.local_tumor_primario = local_tumor_primario;
        this.cid_topografia_onco = cid_topografia_onco;
        setLinfo_reg_invalido(linfo_reg_invalido);
        this.linfo_reg_invalidoStringSim = "";
        this.linfo_reg_invalidoStringNao = "";

        this.linfo_reg_invalidoNaoAvString = "";
        this.loc_meta = loc_meta;
        this.esta_uicc = esta_uicc;
        this.esta_outros = esta_outros;
        this.grau_isto = grau_isto;
        this.diag_cito_isto = diag_cito_isto;
        this.data_64 = (F.dataString(data_64).equals("01/01/1900")) ? null : data_64;
        this.quimio_radio = quimio_radio;
        this.trate_anteriores_quimio = trate_anteriores_quimio;
        this.trate_ante1_quimio = trate_ante1_quimio;
        this.trate_ante2_quimio = trate_ante2_quimio;
        this.trate_ante3_quimio = trate_ante3_quimio;
        this.dt_trate_ante1_quimio = (F.dataString(dt_trate_ante1_quimio).equals("01/01/1900")) ? null : dt_trate_ante1_quimio;
        this.dt_trate_ante2_quimio = (F.dataString(dt_trate_ante2_quimio).equals("01/01/1900")) ? null : dt_trate_ante2_quimio;
        this.dt_trate_ante3_quimio = (F.dataString(dt_trate_ante3_quimio).equals("01/01/1900")) ? null : dt_trate_ante3_quimio;
        this.continue_trate_quimio = continue_trate_quimio;
        this.dt_ini_trata_quimio = (F.dataString(dt_ini_trata_quimio).equals("01/01/1900")) ? null : dt_ini_trata_quimio;
        this.esquema_sigla_quimio = esquema_sigla_quimio;
        this.qtd_mese_planejado_quimio = qtd_mese_planejado_quimio;
        this.qtd_mese_autorizados_quimio = qtd_mese_autorizados_quimio;
        this.trate_ante_radio = trate_ante_radio;
        this.trate_ante1_radio = trate_ante1_radio;
        this.trate_ante2_radio = trate_ante2_radio;
        this.trate_ante3_radio = trate_ante3_radio;

        this.dt_trate_ante1_radio = dt_trate_ante1_radio;
        if (F.dataString(dt_trate_ante1_radio).equals("01/01/1900")) {
            this.dt_trate_ante1_radio = null;
        }

        this.dt_trate_ante2_radio = dt_trate_ante2_radio;
        if (F.dataString(dt_trate_ante2_radio).equals("01/01/1900")) {
            this.dt_trate_ante2_radio = null;
        }

        this.dt_trate_ante3_radio = dt_trate_ante3_radio;
        if (F.dataString(dt_trate_ante3_radio).equals("01/01/1900")) {
            this.dt_trate_ante3_radio = null;
        }
        this.continue_trate_radio = continue_trate_radio;

        this.dt_ini_trata_radio = dt_ini_trata_radio;
        if (F.dataString(dt_ini_trata_radio).equals("01/01/1900")) {
            this.dt_ini_trata_radio = null;
        }
        this.finalidade = finalidade;
        this.cid_topografico1_radio = cid_topografico1_radio;
        this.cid_topografico2_radio = cid_topografico2_radio;
        this.cid_topografico3_radio = cid_topografico3_radio;
        this.area_irradi_descricao1_radio = area_irradi_descricao1_radio;
        this.area_irradi_descricao2_radio = area_irradi_descricao2_radio;
        this.area_irradi_descricao3_radio = area_irradi_descricao3_radio;
        this.ncampo_incer1_radio = ncampo_incer1_radio;
        this.ncampo_incer2_radio = ncampo_incer2_radio;
        this.ncampo_incer3_radio = ncampo_incer3_radio;

        this.dt_ini_area_irrad1_radio = dt_ini_area_irrad1_radio;
        if (F.dataString(dt_ini_area_irrad1_radio).equals("01/01/1900")) {
            this.dt_ini_area_irrad1_radio = null;
        }

        this.dt_ini_area_irrad2_radio = dt_ini_area_irrad2_radio;
        if (F.dataString(dt_ini_area_irrad2_radio).equals("01/01/1900")) {
            this.dt_ini_area_irrad2_radio = null;
        }

        this.dt_ini_area_irrad3_radio = dt_ini_area_irrad3_radio;
        if (F.dataString(dt_ini_area_irrad3_radio).equals("01/01/1900")) {
            this.dt_ini_area_irrad3_radio = null;
        }
        this.dt_fim_area_irrad1_radio = dt_fim_area_irrad1_radio;
        if (F.dataString(dt_fim_area_irrad1_radio).equals("01/01/1900")) {
            this.dt_fim_area_irrad1_radio = null;
        }

        this.dt_fim_area_irrad2_radio = dt_fim_area_irrad2_radio;
        if (F.dataString(dt_fim_area_irrad2_radio).equals("01/01/1900")) {
            this.dt_fim_area_irrad2_radio = null;
        }

        this.dt_fim_area_irrad3_radio = dt_fim_area_irrad3_radio;
        if (F.dataString(dt_fim_area_irrad3_radio).equals("01/01/1900")) {
            this.dt_fim_area_irrad3_radio = null;
        }
    }

    /////////////////get set para pdf
    public String getTrate_anteriores_quimioStringSim() {
        if (trate_anteriores_quimio) {
            trate_anteriores_quimioStringSim = "X";
        } else {
            trate_anteriores_quimioStringSim = "";
        }
        return trate_anteriores_quimioStringSim;
    }

    public void setTrate_anteriores_quimioStringSim(String trate_anteriores_quimioStringSim) {
        this.trate_anteriores_quimioStringSim = (trate_anteriores_quimioStringSim == null) ? "" : trate_anteriores_quimioStringSim.trim();
    }

    public String getTrate_anteriores_quimioStringNao() {
        if (!trate_anteriores_quimio && !quimio_radio) {
            trate_anteriores_quimioStringNao = "X";
        } else {
            trate_anteriores_quimioStringNao = "";
        }
        return trate_anteriores_quimioStringNao;
    }

    public void setTrate_anteriores_quimioStringNao(String trate_anteriores_quimioStringNao) {
        this.trate_anteriores_quimioStringNao = (trate_anteriores_quimioStringNao == null) ? "" : trate_anteriores_quimioStringNao;
    }

    public String getData_64String() {
        data_64String = F.dataString(data_64);
        return data_64String;
    }

    public void setData_64String(String data_64String) {
        this.data_64String = (data_64String == null) ? "" : data_64String.trim();
    }

    public String getDt_trate_ante1_quimioString() {
        dt_trate_ante1_quimioString = F.dataString(dt_trate_ante1_quimio);
        return dt_trate_ante1_quimioString;
    }

    public void setDt_trate_ante1_quimioString(String dt_trate_ante1_quimioString) {
        this.dt_trate_ante1_quimioString = (dt_trate_ante1_quimioString == null) ? "" : dt_trate_ante1_quimioString;
    }

    public String getDt_trate_ante2_quimioString() {
        dt_trate_ante2_quimioString = F.dataString(dt_trate_ante2_quimio);
        return dt_trate_ante2_quimioString;
    }

    public void setDt_trate_ante2_quimioString(String dt_trate_ante2_quimioString) {
        this.dt_trate_ante2_quimioString = (dt_trate_ante2_quimioString == null) ? "" : dt_trate_ante2_quimioString.trim();
    }

    public String getDt_trate_ante3_quimioString() {
        dt_trate_ante3_quimioString = F.dataString(dt_trate_ante3_quimio);
        return dt_trate_ante3_quimioString;
    }

    public void setDt_trate_ante3_quimioString(String dt_trate_ante3_quimioString) {
        this.dt_trate_ante3_quimioString = (dt_trate_ante3_quimioString == null) ? "" : dt_trate_ante3_quimioString.trim();
    }

    public String getContinue_trate_quimioStringSim() {
        if (continue_trate_quimio) {
            continue_trate_quimioStringSim = "X";
        } else {
            continue_trate_quimioStringSim = "";
        }
        return continue_trate_quimioStringSim;
    }

    public void setContinue_trate_quimioStringSim(String continue_trate_quimioStringSim) {
        this.continue_trate_quimioStringSim = (continue_trate_quimioStringSim == null) ? "" : continue_trate_quimioStringSim;
    }

    public String getContinue_trate_quimioStringNao() {
        if (!continue_trate_quimio && !quimio_radio) {
            continue_trate_quimioStringNao = "X";
        } else {
            continue_trate_quimioStringNao = "";
        }
        return continue_trate_quimioStringNao;
    }

    public void setContinue_trate_quimioStringNao(String continue_trate_quimioStringNao) {
        this.continue_trate_quimioStringNao = (continue_trate_quimioStringNao == null) ? "" : continue_trate_quimioStringNao;
    }

    public String getDt_ini_trata_quimioString() {
        dt_ini_trata_quimioString = F.dataString(dt_ini_trata_quimio);
        return dt_ini_trata_quimioString;
    }

    public void setDt_ini_trata_quimioString(String dt_ini_trata_quimioString) {
        this.dt_ini_trata_quimioString = (dt_ini_trata_quimioString == null) ? "" : dt_ini_trata_quimioString.trim();
    }

    public String getTrate_ante_radioStringSim() {
        if (trate_ante_radio) {
            trate_ante_radioStringSim = "X";
        } else {
            trate_ante_radioStringSim = "";
        }
        return trate_ante_radioStringSim;

    }

    public void setTrate_ante_radioString(String trate_ante_radioStringSim) {
        this.trate_ante_radioStringSim = (trate_ante_radioStringSim == null) ? "" : trate_ante_radioStringSim;
    }

    public String getTrate_ante_radioStringNao() {
        if (!trate_ante_radio && quimio_radio) {
            trate_ante_radioStringNao = "X";
        } else {
            trate_ante_radioStringNao = "";
        }
        return trate_ante_radioStringNao;

    }

    public void setTrate_ante_radioStringNao(String trate_ante_radioStringNao) {
        this.trate_ante_radioStringNao = (trate_ante_radioStringNao == null) ? "" : trate_ante_radioStringNao.trim();
    }

    public String getDt_trate_ante1_radioString() {
        dt_trate_ante1_radioString = F.dataString(dt_trate_ante1_radio);
        return dt_trate_ante1_radioString;
    }

    public void setDt_trate_ante1_radioString(String dt_trate_ante1_radioString) {
        this.dt_trate_ante1_radioString = (dt_trate_ante1_radioString == null) ? "" : dt_trate_ante1_radioString.trim();
    }

    public String getDt_trate_ante2_radioString() {
        dt_trate_ante2_radioString = F.dataString(dt_trate_ante2_radio);
        return dt_trate_ante2_radioString;
    }

    public void setDt_trate_ante2_radioString(String dt_trate_ante2_radioString) {
        this.dt_trate_ante2_radioString = (dt_trate_ante2_radioString == null) ? "" : dt_trate_ante2_radioString.trim();
    }

    public String getDt_trate_ante3_radioString() {
        dt_trate_ante3_radioString = F.dataString(dt_trate_ante3_radio);
        return dt_trate_ante3_radioString;
    }

    public void setDt_trate_ante3_radioString(String dt_trate_ante3_radioString) {
        this.dt_trate_ante3_radioString = (dt_trate_ante3_radioString == null) ? "" : dt_trate_ante3_radioString.trim();
    }

    public String getContinue_trate_radioStringSim() {

        if (continue_trate_radio) {
            continue_trate_radioStringSim = "X";
        } else {
            continue_trate_radioStringSim = "";
        }
        return continue_trate_radioStringSim;

    }

    public void setContinue_trate_radioStringSim(String continue_trate_radioStringSim) {
        this.continue_trate_radioStringSim = (continue_trate_radioStringSim == null) ? "" : continue_trate_radioStringSim;
    }

    public String getContinue_trate_radioStringNao() {

        if (!continue_trate_radio && quimio_radio) {
            continue_trate_radioStringNao = "X";
        } else {
            continue_trate_radioStringNao = "";
        }
        return continue_trate_radioStringNao;

    }

    public void setContinue_trate_radioStringNao(String continue_trate_radioStringNao) {
        this.continue_trate_radioStringNao = (continue_trate_radioStringNao == null) ? "" : continue_trate_radioStringNao;
    }

    public String getDt_ini_trata_radioString() {
        dt_ini_trata_radioString = F.dataString(dt_ini_trata_radio);
        return dt_ini_trata_radioString;
    }

    public void setDt_ini_trata_radioString(String dt_ini_trata_radioString) {
        this.dt_ini_trata_radioString = (dt_ini_trata_radioString == null) ? "" : dt_ini_trata_radioString.trim();
    }

    public String getDt_ini_area_irrad1_radioString() {
        dt_ini_area_irrad1_radioString = F.dataString(dt_ini_area_irrad1_radio);
        return dt_ini_area_irrad1_radioString;
    }

    public void setDt_ini_area_irrad1_radioString(String dt_ini_area_irrad1_radioString) {
        this.dt_ini_area_irrad1_radioString = (dt_ini_area_irrad1_radioString == null) ? "" : dt_ini_area_irrad1_radioString.trim();
    }

    public String getDt_ini_area_irrad3_radioString() {
        dt_ini_area_irrad3_radioString = F.dataString(dt_ini_area_irrad3_radio);
        return dt_ini_area_irrad3_radioString;
    }

    public void setDt_ini_area_irrad3_radioString(String dt_ini_area_irrad3_radioString) {
        this.dt_ini_area_irrad3_radioString = (dt_ini_area_irrad3_radioString == null) ? "" : dt_ini_area_irrad3_radioString.trim();
    }

    public String getDt_fim_area_irrad1_radioString() {
        dt_fim_area_irrad1_radioString = F.dataString(dt_fim_area_irrad1_radio);
        return dt_fim_area_irrad1_radioString;
    }

    public void setDt_fim_area_irrad1_radioString(String dt_fim_area_irrad1_radioString) {
        this.dt_fim_area_irrad1_radioString = (dt_fim_area_irrad1_radioString == null) ? "" : dt_fim_area_irrad1_radioString.trim();
    }

    public String getDt_fim_area_irrad2_radioString() {
        dt_fim_area_irrad2_radioString = F.dataString(dt_fim_area_irrad2_radio);
        return dt_fim_area_irrad2_radioString;
    }

    public void setDt_fim_area_irrad2_radioString(String dt_fim_area_irrad2_radioString) {
        this.dt_fim_area_irrad2_radioString = (dt_fim_area_irrad2_radioString == null) ? "" : dt_fim_area_irrad2_radioString.trim();
    }

    public String getDt_fim_area_irrad3_radioString() {
        dt_fim_area_irrad3_radioString = F.dataString(dt_fim_area_irrad3_radio);
        return dt_fim_area_irrad3_radioString;
    }

    public void setDt_fim_area_irrad3_radioString(String dt_fim_area_irrad3_radioString) {
        this.dt_fim_area_irrad3_radioString = (dt_fim_area_irrad3_radioString == null) ? "" : dt_fim_area_irrad3_radioString.trim();
    }

    ////////////////////////////////////////////////////////////////////////////////
    public int getFormulario_id() {
        return formulario_id;
    }

    public void setFormulario_id(int formulario_id) {
        this.formulario_id = formulario_id;
    }

    public String getLocal_tumor_primario() {
        return local_tumor_primario;
    }

    public void setLocal_tumor_primario(String local_tumor_primario) {
        this.local_tumor_primario = (local_tumor_primario == null) ? "" : local_tumor_primario.trim().toUpperCase();
    }

    public String getCid_topografia_onco() {
        return cid_topografia_onco;
    }

    public void setCid_topografia_onco(String cid_topografia_onco) {
        this.cid_topografia_onco = (cid_topografia_onco == null) ? "" : cid_topografia_onco.trim();
    }

    public int getLinfo_reg_invalido() {
        return linfo_reg_invalido;
    }

    public String getLinfo_reg_invalidoString() {
        switch (linfo_reg_invalido) {
            case 0: {//sim
                return "Sim";
            }
            case 1: {//nao
                return "Não";
            }
            case 2: {//nao av
                return "Não avaliaveis";
            }
            default: {
                return "";
            }
        }
    }

    public String otlFinalidadeString() {
        switch (getFinalidade().getId()) {
            case 1: {//adjuvante
                return "ADJUVANTE";
            }
            case 2: {//antialgica
                return "ANTIÁLGICA";
            }
            case 3: {//antiemorragica
                return "ANTIHEMORRÁGICA";
            }
            case 4: {//paliativa
                return "PALIATIVA";
            }
            case 5: {//previa
                return "PRÉVIA";
            }
            case 6: {//radical
                return "RADICAL";
            }
            default: {
                return "";
            }
        }
    }

    public void setLinfo_reg_invalido(int linfo_reg_invalido) {

        switch (linfo_reg_invalido) {
            case 0: {//sim
                linfo_reg_invalidoStringSim = "X";
                linfo_reg_invalidoStringNao = "";
                linfo_reg_invalidoNaoAvString = "";
                break;
            }
            case 1: {//nao
                linfo_reg_invalidoStringSim = "";
                linfo_reg_invalidoStringNao = "X";
                linfo_reg_invalidoNaoAvString = "";
                break;
            }
            case 2: {//nao av
                linfo_reg_invalidoStringSim = "";
                linfo_reg_invalidoStringNao = "";
                linfo_reg_invalidoNaoAvString = "X";
                break;
            }
            default: {
                linfo_reg_invalidoStringSim = "";
                linfo_reg_invalidoStringNao = "";
                linfo_reg_invalidoNaoAvString = "";
                break;
            }

        }

        this.linfo_reg_invalido = linfo_reg_invalido;
    }

    public String getLoc_meta() {
        return loc_meta;
    }

    public void setLoc_meta(String loc_meta) {
        this.loc_meta = (loc_meta == null) ? "" : loc_meta.trim().toUpperCase();
    }

    public String getEsta_uicc() {

        if (!(esta_uicc.equals("0") || esta_uicc.equals("I") || esta_uicc.equals("II") || esta_uicc.equals("III") || esta_uicc.equals("IV"))) {
            esta_uicc = "";
        }
        return esta_uicc;
    }

    public void setEsta_uicc(String esta_uicc) {

        this.esta_uicc = (esta_uicc == null) ? "" : esta_uicc.trim().toUpperCase();
    }

    public String getEsta_outros() {
        return esta_outros;
    }

    public void setEsta_outros(String esta_outros) {
        this.esta_outros = (esta_outros == null) ? "" : esta_outros.trim().toUpperCase();
    }

    public String getGrau_isto() {
        if (!(grau_isto.equals("GX") || grau_isto.equals("G1") || grau_isto.equals("G2") || grau_isto.equals("G3") || grau_isto.equals("G4"))) {
            grau_isto = "";
        }
        return grau_isto;
    }

    public void setGrau_isto(String grau_isto) {
        this.grau_isto = (grau_isto == null) ? "" : grau_isto.trim().toUpperCase();
    }

    public String getDiag_cito_isto() {
        return diag_cito_isto;
    }

    public void setDiag_cito_isto(String diag_cito_isto) {
        this.diag_cito_isto = (diag_cito_isto == null) ? "" : diag_cito_isto.trim().toUpperCase();
    }

    public Date getData_64() {
        return data_64;
    }

    public void setData_64(Date data_64) {
        this.data_64 = data_64;
    }

    public String oplTratAntQuimioString() {
        if (trate_anteriores_quimio) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    public boolean isTrate_anteriores_quimio() {
        return trate_anteriores_quimio;
    }

    public void setTrate_anteriores_quimio(boolean trate_anteriores_quimio) {
        this.trate_anteriores_quimio = trate_anteriores_quimio;
    }

    public String getTrate_ante1_quimio() {
        if (trate_ante1_quimio.isEmpty() && dt_trate_ante1_quimio == null) {
            trate_ante1_quimio = getTrate_ante2_quimio();
            setTrate_ante2_quimio("");
        }
        return trate_ante1_quimio;
    }

    public void setTrate_ante1_quimio(String trate_ante1_quimio) {
        this.trate_ante1_quimio = (trate_ante1_quimio == null) ? "" : trate_ante1_quimio.trim().toUpperCase();
    }

    public String getTrate_ante2_quimio() {
        if (trate_ante2_quimio.isEmpty() && dt_trate_ante2_quimio == null) {
            trate_ante2_quimio = getTrate_ante3_quimio();
            setTrate_ante3_quimio("");
        }
        return trate_ante2_quimio;
    }

    public void setTrate_ante2_quimio(String trate_ante2_quimio) {
        this.trate_ante2_quimio = (trate_ante2_quimio == null) ? "" : trate_ante2_quimio.trim().toUpperCase();
    }

    public String getTrate_ante3_quimio() {
        return trate_ante3_quimio;
    }

    public void setTrate_ante3_quimio(String trate_ante3_quimio) {
        this.trate_ante3_quimio = (trate_ante3_quimio == null) ? "" : trate_ante3_quimio.trim().toUpperCase();
    }

    public Date getDt_trate_ante1_quimio() {
        if (dt_trate_ante1_quimio == null && trate_ante1_quimio.isEmpty()) {
            dt_trate_ante1_quimio = getDt_trate_ante2_quimio();
            setDt_trate_ante2_quimio(null);
        }
        return dt_trate_ante1_quimio;
    }

    public void setDt_trate_ante1_quimio(Date dt_trate_ante1_quimio) {
        this.dt_trate_ante1_quimio = dt_trate_ante1_quimio;
    }

    public Date getDt_trate_ante2_quimio() {
        if (dt_trate_ante2_quimio == null && trate_ante2_quimio.isEmpty()) {
            dt_trate_ante2_quimio = getDt_trate_ante3_quimio();
            setDt_trate_ante3_quimio(null);
        }
        return dt_trate_ante2_quimio;
    }

    public void setDt_trate_ante2_quimio(Date dt_trate_ante2_quimio) {
        this.dt_trate_ante2_quimio = dt_trate_ante2_quimio;
    }

    public Date getDt_trate_ante3_quimio() {
        return dt_trate_ante3_quimio;
    }

    public void setDt_trate_ante3_quimio(Date dt_trate_ante3_quimio) {
        this.dt_trate_ante3_quimio = dt_trate_ante3_quimio;
    }

     public String oplContinue_trate_quimio() {
        if (continue_trate_quimio) {
            return "Sim";
        } else {
            return "Não";
        }
    }
    
    public boolean isContinue_trate_quimio() {
        return continue_trate_quimio;
    }

    public void setContinue_trate_quimio(boolean continue_trate_quimio) {

        this.continue_trate_quimio = continue_trate_quimio;
    }

    public Date getDt_ini_trata_quimio() {
        return dt_ini_trata_quimio;
    }

    public void setDt_ini_trata_quimio(Date dt_ini_trata_quimio) {
        this.dt_ini_trata_quimio = dt_ini_trata_quimio;
    }

    public String getEsquema_sigla_quimio() {
        return esquema_sigla_quimio;
    }

    public void setEsquema_sigla_quimio(String esquema_sigla_quimio) {
        this.esquema_sigla_quimio = (esquema_sigla_quimio == null) ? "" : esquema_sigla_quimio.trim().toUpperCase();
    }

    public int getQtd_mese_planejado_quimio() {
        return qtd_mese_planejado_quimio;
    }

    public void setQtd_mese_planejado_quimio(int qtd_mese_planejado_quimio) {

        this.qtd_mese_planejado_quimio = qtd_mese_planejado_quimio;
    }

    public int getQtd_mese_autorizados_quimio() {
        return qtd_mese_autorizados_quimio;
    }

    public void setQtd_mese_autorizados_quimio(int qtd_mese_autorizados_quimio) {
        this.qtd_mese_autorizados_quimio = qtd_mese_autorizados_quimio;
    }

     public String oplTrate_ante_radio() {
        if (trate_ante_radio) {
            return "Sim";
        } else {
            return "Não";
        }
    }
    
    public boolean isTrate_ante_radio() {
        return trate_ante_radio;
    }

    public void setTrate_ante_radio(boolean trate_ante_radio) {
        this.trate_ante_radio = trate_ante_radio;
    }

    public String getTrate_ante1_radio() {
        if (trate_ante1_radio.isEmpty() && dt_trate_ante1_radio == null) {
            trate_ante1_radio = getTrate_ante2_radio();
            setTrate_ante2_radio("");
        }
        return trate_ante1_radio;
    }

    public void setTrate_ante1_radio(String trate_ante1_radio) {

        this.trate_ante1_radio = (trate_ante1_radio == null) ? "" : trate_ante1_radio.trim().toUpperCase();
    }

    public String getTrate_ante2_radio() {
        if (trate_ante2_radio.isEmpty() && dt_trate_ante1_radio == null) {
            trate_ante2_radio = getTrate_ante3_radio();
            setTrate_ante3_radio("");
        }
        return trate_ante2_radio;
    }

    public void setTrate_ante2_radio(String trate_ante2_radio) {
        this.trate_ante2_radio = (trate_ante2_radio == null) ? "" : trate_ante2_radio.trim().toUpperCase();
    }

    public String getTrate_ante3_radio() {
        return trate_ante3_radio;
    }

    public void setTrate_ante3_radio(String trate_ante3_radio) {
        this.trate_ante3_radio = (trate_ante3_radio == null) ? "" : trate_ante3_radio.trim().toUpperCase();
    }

    public Date getDt_trate_ante1_radio() {
        if (dt_trate_ante1_radio == null && trate_ante1_radio.isEmpty()) {
            dt_trate_ante1_radio = getDt_trate_ante2_radio();
            setDt_trate_ante2_radio(null);
        }
        return dt_trate_ante1_radio;
    }

    public void setDt_trate_ante1_radio(Date dt_trate_ante1_radio) {
        this.dt_trate_ante1_radio = dt_trate_ante1_radio;
    }

    public Date getDt_trate_ante2_radio() {
        if (dt_trate_ante2_radio == null && trate_ante2_radio.isEmpty()) {
            dt_trate_ante2_radio = getDt_trate_ante3_radio();
            setDt_trate_ante3_radio(null);
        }
        return dt_trate_ante2_radio;
    }

    public void setDt_trate_ante2_radio(Date dt_trate_ante2_radio) {
        this.dt_trate_ante2_radio = dt_trate_ante2_radio;
    }

    public Date getDt_trate_ante3_radio() {
        return dt_trate_ante3_radio;
    }

    public void setDt_trate_ante3_radio(Date dt_trate_ante3_radio) {
        this.dt_trate_ante3_radio = dt_trate_ante3_radio;
    }
    
     public String oplContinue_trate_radio() {
        if (continue_trate_radio) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    public boolean isContinue_trate_radio() {
        return continue_trate_radio;
    }

    public void setContinue_trate_radio(boolean continue_trate_radio) {
        this.continue_trate_radio = continue_trate_radio;
    }

    public Date getDt_ini_trata_radio() {
        return dt_ini_trata_radio;
    }

    public void setDt_ini_trata_radio(Date dt_ini_trata_radio) {
        this.dt_ini_trata_radio = dt_ini_trata_radio;
    }

    public Finalidade_radio_f2 getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(Finalidade_radio_f2 finalidade) {
        this.finalidade = finalidade;
    }

    public String getCid_topografico1_radio() {
        return (cid_topografico1_radio == null) ? "" : cid_topografico1_radio;
    }

    public void setCid_topografico1_radio(String cid_topografico1_radio) {

        this.cid_topografico1_radio = (cid_topografico1_radio == null) ? "" : cid_topografico1_radio.trim();
    }

    public String getCid_topografico2_radio() {
        return (cid_topografico2_radio == null) ? "" : cid_topografico2_radio;
    }

    public void setCid_topografico2_radio(String cid_topografico2_radio) {
        this.cid_topografico2_radio = (cid_topografico2_radio == null) ? "" : cid_topografico2_radio.trim();
    }

    public String getCid_topografico3_radio() {
        return (cid_topografico3_radio == null) ? "" : cid_topografico3_radio;
    }

    public void setCid_topografico3_radio(String cid_topografico3_radio) {
        this.cid_topografico3_radio = (cid_topografico3_radio == null) ? "" : cid_topografico3_radio.trim();
    }

    public String getArea_irradi_descricao1_radio() {
        return area_irradi_descricao1_radio;
    }

    public void setArea_irradi_descricao1_radio(String area_irradi_descricao1_radio) {

        this.area_irradi_descricao1_radio = (area_irradi_descricao1_radio == null) ? "" : area_irradi_descricao1_radio.trim().toUpperCase();
    }

    public String getArea_irradi_descricao2_radio() {
        return area_irradi_descricao2_radio;
    }

    public void setArea_irradi_descricao2_radio(String area_irradi_descricao2_radio) {
        this.area_irradi_descricao2_radio = (area_irradi_descricao2_radio == null) ? "" : area_irradi_descricao2_radio.trim().toUpperCase();
    }

    public String getArea_irradi_descricao3_radio() {
        return area_irradi_descricao3_radio;
    }

    public void setArea_irradi_descricao3_radio(String area_irradi_descricao3_radio) {
        this.area_irradi_descricao3_radio = (area_irradi_descricao3_radio == null) ? "" : area_irradi_descricao3_radio.trim().toUpperCase();
    }

    public String getNcampo_incer1_radio() {
        return ncampo_incer1_radio;
    }

    public void setNcampo_incer1_radio(String ncampo_incer1_radio) {
        this.ncampo_incer1_radio = (ncampo_incer1_radio == null) ? "" : ncampo_incer1_radio.trim().toUpperCase();
    }

    public String getNcampo_incer2_radio() {
        return ncampo_incer2_radio;
    }

    public void setNcampo_incer2_radio(String ncampo_incer2_radio) {
        this.ncampo_incer2_radio = (ncampo_incer2_radio == null) ? "" : ncampo_incer2_radio.trim().toUpperCase();
    }

    public String getNcampo_incer3_radio() {
        return ncampo_incer3_radio;
    }

    public void setNcampo_incer3_radio(String ncampo_incer3_radio) {
        this.ncampo_incer3_radio = (ncampo_incer3_radio == null) ? "" : ncampo_incer3_radio.trim().toUpperCase();
    }

    public Date getDt_ini_area_irrad1_radio() {
        return dt_ini_area_irrad1_radio;
    }

    public void setDt_ini_area_irrad1_radio(Date dt_ini_area_irrad1_radio) {
        this.dt_ini_area_irrad1_radio = dt_ini_area_irrad1_radio;
    }

    public Date getDt_ini_area_irrad2_radio() {
        return dt_ini_area_irrad2_radio;
    }

    public void setDt_ini_area_irrad2_radio(Date dt_ini_area_irrad2_radio) {
        this.dt_ini_area_irrad2_radio = dt_ini_area_irrad2_radio;
    }

    public Date getDt_ini_area_irrad3_radio() {
        return dt_ini_area_irrad3_radio;
    }

    public void setDt_ini_area_irrad3_radio(Date dt_ini_area_irrad3_radio) {
        this.dt_ini_area_irrad3_radio = dt_ini_area_irrad3_radio;
    }

    public Date getDt_fim_area_irrad1_radio() {
        return dt_fim_area_irrad1_radio;
    }

    public void setDt_fim_area_irrad1_radio(Date dt_fim_area_irrad1_radio) {
        this.dt_fim_area_irrad1_radio = dt_fim_area_irrad1_radio;
    }

    public Date getDt_fim_area_irrad2_radio() {
        return dt_fim_area_irrad2_radio;
    }

    public void setDt_fim_area_irrad2_radio(Date dt_fim_area_irrad2_radio) {
        this.dt_fim_area_irrad2_radio = dt_fim_area_irrad2_radio;
    }

    public Date getDt_fim_area_irrad3_radio() {
        return dt_fim_area_irrad3_radio;
    }

    public void setDt_fim_area_irrad3_radio(Date dt_fim_area_irrad3_radio) {
        this.dt_fim_area_irrad3_radio = dt_fim_area_irrad3_radio;
    }

    public String getLinfo_reg_invalidoStringSim() {
        if (linfo_reg_invalido == 0) {
            linfo_reg_invalidoStringSim = "X";
        }
        return linfo_reg_invalidoStringSim;
    }

    public void setLinfo_reg_invalidoStringSim(String linfo_reg_invalidoStringSim) {
        this.linfo_reg_invalidoStringSim = (linfo_reg_invalidoStringSim == null) ? "" : linfo_reg_invalidoStringSim.trim();
    }

    public String getLinfo_reg_invalidoStringNao() {
        if (linfo_reg_invalido == 1) {
            linfo_reg_invalidoStringNao = "X";
        }
        return linfo_reg_invalidoStringNao;
    }

    public void setLinfo_reg_invalidoStringNao(String linfo_reg_invalidoStringNao) {
        this.linfo_reg_invalidoStringNao = (linfo_reg_invalidoStringNao == null) ? "" : linfo_reg_invalidoStringNao.trim();
    }

    public String getLinfo_reg_invalidoNaoAvString() {
        if (linfo_reg_invalido == 2) {
            linfo_reg_invalidoNaoAvString = "X";
        }
        return linfo_reg_invalidoNaoAvString;
    }

    public void setLinfo_reg_invalidoNaoAvString(String linfo_reg_invalidoNaoAvString) {
        this.linfo_reg_invalidoNaoAvString = (linfo_reg_invalidoNaoAvString == null) ? "" : linfo_reg_invalidoNaoAvString.trim();
    }

    public String getDt_ini_area_irrad2_radioString() {
        dt_ini_area_irrad2_radioString = F.dataString(dt_ini_area_irrad2_radio);
        return dt_ini_area_irrad2_radioString;
    }

    public void setDt_ini_area_irrad2_radioString(String dt_ini_area_irrad2_radioString) {
        this.dt_ini_area_irrad2_radioString = (dt_ini_area_irrad2_radioString == null) ? "" : dt_ini_area_irrad2_radioString.trim();
    }

    public String getQtd_mese_planejado_quimioSTR() {
        if (getQtd_mese_planejado_quimio() < 1) {
            qtd_mese_planejado_quimioSTR = "";
        } else {
            qtd_mese_planejado_quimioSTR = getQtd_mese_planejado_quimio() + "";
        }
        return qtd_mese_planejado_quimioSTR;
    }

    public void setQtd_mese_planejado_quimioSTR(String qtd_mese_planejado_quimioSTR) {
        this.qtd_mese_planejado_quimioSTR = (qtd_mese_planejado_quimioSTR == null) ? "" : qtd_mese_planejado_quimioSTR.trim();
    }

    public String getQtd_mese_autorizados_quimioSTR() {
        if (getQtd_mese_autorizados_quimio() < 1) {

            qtd_mese_autorizados_quimioSTR = "";

        } else {
            qtd_mese_autorizados_quimioSTR = getQtd_mese_planejado_quimio() + "";

        }

        return qtd_mese_autorizados_quimioSTR;
    }

    public void setQtd_mese_autorizados_quimioSTR(String qtd_mese_autorizados_quimioSTR) {
        this.qtd_mese_autorizados_quimioSTR = (qtd_mese_autorizados_quimioSTR == null) ? "" : qtd_mese_autorizados_quimioSTR.trim();
    }
    
     public String oplQuimio_radio() {
        if (quimio_radio) {
            return "Radioterapia";
        } else {
            return "Quimioterapia";
        }
    }

    public boolean isQuimio_radio() {
        return quimio_radio;
    }

    public void setQuimio_radio(boolean quimio_radio) {

        this.quimio_radio = quimio_radio;
    }

    public String getTrate_anteriores_quimioStringString() {
        return trate_anteriores_quimioStringString;
    }

    public void setTrate_anteriores_quimioStringString(String trate_anteriores_quimioStringString) {
        this.trate_anteriores_quimioStringString = trate_anteriores_quimioStringString;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.formulario_id;
        hash = 97 * hash + Objects.hashCode(this.local_tumor_primario);
        hash = 97 * hash + Objects.hashCode(this.cid_topografia_onco);
        hash = 97 * hash + (this.linfo_reg_invalido);
        hash = 97 * hash + Objects.hashCode(this.loc_meta);
        hash = 97 * hash + Objects.hashCode(this.esta_uicc);
        hash = 97 * hash + Objects.hashCode(this.esta_outros);
        hash = 97 * hash + Objects.hashCode(this.grau_isto);
        hash = 97 * hash + Objects.hashCode(this.diag_cito_isto);
        hash = 97 * hash + Objects.hashCode(this.data_64);
        hash = 97 * hash + (this.trate_anteriores_quimio ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.trate_ante1_quimio);
        hash = 97 * hash + Objects.hashCode(this.trate_ante2_quimio);
        hash = 97 * hash + Objects.hashCode(this.trate_ante3_quimio);
        hash = 97 * hash + Objects.hashCode(this.dt_trate_ante1_quimio);
        hash = 97 * hash + Objects.hashCode(this.dt_trate_ante2_quimio);
        hash = 97 * hash + Objects.hashCode(this.dt_trate_ante3_quimio);
        hash = 97 * hash + (this.continue_trate_quimio ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.dt_ini_trata_quimio);
        hash = 97 * hash + Objects.hashCode(this.esquema_sigla_quimio);
        hash = 97 * hash + this.qtd_mese_planejado_quimio;
        hash = 97 * hash + this.qtd_mese_autorizados_quimio;
        hash = 97 * hash + (this.trate_ante_radio ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.trate_ante1_radio);
        hash = 97 * hash + Objects.hashCode(this.trate_ante2_radio);
        hash = 97 * hash + Objects.hashCode(this.trate_ante3_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_trate_ante1_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_trate_ante2_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_trate_ante3_radio);
        hash = 97 * hash + (this.continue_trate_radio ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.dt_ini_trata_radio);
        hash = 97 * hash + Objects.hashCode(this.finalidade);
        hash = 97 * hash + Objects.hashCode(this.cid_topografico1_radio);
        hash = 97 * hash + Objects.hashCode(this.cid_topografico2_radio);
        hash = 97 * hash + Objects.hashCode(this.cid_topografico3_radio);
        hash = 97 * hash + Objects.hashCode(this.area_irradi_descricao1_radio);
        hash = 97 * hash + Objects.hashCode(this.area_irradi_descricao2_radio);
        hash = 97 * hash + Objects.hashCode(this.area_irradi_descricao3_radio);
        hash = 97 * hash + Objects.hashCode(this.ncampo_incer1_radio);
        hash = 97 * hash + Objects.hashCode(this.ncampo_incer2_radio);
        hash = 97 * hash + Objects.hashCode(this.ncampo_incer3_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_ini_area_irrad1_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_ini_area_irrad2_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_ini_area_irrad3_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_fim_area_irrad1_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_fim_area_irrad2_radio);
        hash = 97 * hash + Objects.hashCode(this.dt_fim_area_irrad3_radio);
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
        final Formulario_f2 other = (Formulario_f2) obj;
        if (this.formulario_id != other.formulario_id) {
            return false;
        }
        if (this.linfo_reg_invalido != other.linfo_reg_invalido) {
            return false;
        }
        if (this.trate_anteriores_quimio != other.trate_anteriores_quimio) {
            return false;
        }
        if (this.continue_trate_quimio != other.continue_trate_quimio) {
            return false;
        }
        if (this.qtd_mese_planejado_quimio != other.qtd_mese_planejado_quimio) {
            return false;
        }
        if (this.qtd_mese_autorizados_quimio != other.qtd_mese_autorizados_quimio) {
            return false;
        }
        if (this.trate_ante_radio != other.trate_ante_radio) {
            return false;
        }
        if (this.continue_trate_radio != other.continue_trate_radio) {
            return false;
        }
        if (!Objects.equals(this.local_tumor_primario, other.local_tumor_primario)) {
            return false;
        }
        if (!Objects.equals(this.cid_topografia_onco, other.cid_topografia_onco)) {
            return false;
        }
        if (!Objects.equals(this.loc_meta, other.loc_meta)) {
            return false;
        }
        if (!Objects.equals(this.esta_uicc, other.esta_uicc)) {
            return false;
        }
        if (!Objects.equals(this.esta_outros, other.esta_outros)) {
            return false;
        }
        if (!Objects.equals(this.grau_isto, other.grau_isto)) {
            return false;
        }
        if (!Objects.equals(this.diag_cito_isto, other.diag_cito_isto)) {
            return false;
        }
        if (!Objects.equals(this.trate_ante1_quimio, other.trate_ante1_quimio)) {
            return false;
        }
        if (!Objects.equals(this.trate_ante2_quimio, other.trate_ante2_quimio)) {
            return false;
        }
        if (!Objects.equals(this.trate_ante3_quimio, other.trate_ante3_quimio)) {
            return false;
        }
        if (!Objects.equals(this.esquema_sigla_quimio, other.esquema_sigla_quimio)) {
            return false;
        }
        if (!Objects.equals(this.trate_ante1_radio, other.trate_ante1_radio)) {
            return false;
        }
        if (!Objects.equals(this.trate_ante2_radio, other.trate_ante2_radio)) {
            return false;
        }
        if (!Objects.equals(this.trate_ante3_radio, other.trate_ante3_radio)) {
            return false;
        }
        if (!Objects.equals(this.cid_topografico1_radio, other.cid_topografico1_radio)) {
            return false;
        }
        if (!Objects.equals(this.cid_topografico2_radio, other.cid_topografico2_radio)) {
            return false;
        }
        if (!Objects.equals(this.cid_topografico3_radio, other.cid_topografico3_radio)) {
            return false;
        }
        if (!Objects.equals(this.area_irradi_descricao1_radio, other.area_irradi_descricao1_radio)) {
            return false;
        }
        if (!Objects.equals(this.area_irradi_descricao2_radio, other.area_irradi_descricao2_radio)) {
            return false;
        }
        if (!Objects.equals(this.area_irradi_descricao3_radio, other.area_irradi_descricao3_radio)) {
            return false;
        }
        if (!Objects.equals(this.ncampo_incer1_radio, other.ncampo_incer1_radio)) {
            return false;
        }
        if (!Objects.equals(this.ncampo_incer2_radio, other.ncampo_incer2_radio)) {
            return false;
        }
        if (!Objects.equals(this.ncampo_incer3_radio, other.ncampo_incer3_radio)) {
            return false;
        }
        if (!Objects.equals(this.data_64, other.data_64)) {
            return false;
        }
        if (!Objects.equals(this.dt_trate_ante1_quimio, other.dt_trate_ante1_quimio)) {
            return false;
        }
        if (!Objects.equals(this.dt_trate_ante2_quimio, other.dt_trate_ante2_quimio)) {
            return false;
        }
        if (!Objects.equals(this.dt_trate_ante3_quimio, other.dt_trate_ante3_quimio)) {
            return false;
        }
        if (!Objects.equals(this.dt_ini_trata_quimio, other.dt_ini_trata_quimio)) {
            return false;
        }
        if (!Objects.equals(this.dt_trate_ante1_radio, other.dt_trate_ante1_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_trate_ante2_radio, other.dt_trate_ante2_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_trate_ante3_radio, other.dt_trate_ante3_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_ini_trata_radio, other.dt_ini_trata_radio)) {
            return false;
        }
        if (!Objects.equals(this.finalidade, other.finalidade)) {
            return false;
        }
        if (!Objects.equals(this.dt_ini_area_irrad1_radio, other.dt_ini_area_irrad1_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_ini_area_irrad2_radio, other.dt_ini_area_irrad2_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_ini_area_irrad3_radio, other.dt_ini_area_irrad3_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_fim_area_irrad1_radio, other.dt_fim_area_irrad1_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_fim_area_irrad2_radio, other.dt_fim_area_irrad2_radio)) {
            return false;
        }
        if (!Objects.equals(this.dt_fim_area_irrad3_radio, other.dt_fim_area_irrad3_radio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formulario_f2{" + "formulario_id=" + formulario_id + ", local_tumor_primario=" + local_tumor_primario + ", cid_topografia_onco=" + cid_topografia_onco + ", linfo_reg_invalido=" + linfo_reg_invalido + ", loc_meta=" + loc_meta + ", esta_uicc=" + esta_uicc + ", esta_outros=" + esta_outros + ", grau_isto=" + grau_isto + ", diag_cito_isto=" + diag_cito_isto + ", data_64=" + data_64 + ", trate_anteriores_quimio=" + trate_anteriores_quimio + ", trate_ante1_quimio=" + trate_ante1_quimio + ", trate_ante2_quimio=" + trate_ante2_quimio + ", trate_ante3_quimio=" + trate_ante3_quimio + ", dt_trate_ante1_quimio=" + dt_trate_ante1_quimio + ", dt_trate_ante2_quimio=" + dt_trate_ante2_quimio + ", dt_trate_ante3_quimio=" + dt_trate_ante3_quimio + ", continue_trate_quimio=" + continue_trate_quimio + ", dt_ini_trata_quimio=" + dt_ini_trata_quimio + ", esquema_sigla_quimio=" + esquema_sigla_quimio + ", qtd_mese_planejado_quimio=" + qtd_mese_planejado_quimio + ", qtd_mese_autorizados_quimio=" + qtd_mese_autorizados_quimio + ", trate_ante_radio=" + trate_ante_radio + ", trate_ante1_radio=" + trate_ante1_radio + ", trate_ante2_radio=" + trate_ante2_radio + ", trate_ante3_radio=" + trate_ante3_radio + ", dt_trate_ante1_radio=" + dt_trate_ante1_radio + ", dt_trate_ante2_radio=" + dt_trate_ante2_radio + ", dt_trate_ante3_radio=" + dt_trate_ante3_radio + ", continue_trate_radio=" + continue_trate_radio + ", dt_ini_trata_radio=" + dt_ini_trata_radio + ", finalidade=" + finalidade + ", cid_topografico1_radio=" + cid_topografico1_radio + ", cid_topografico2_radio=" + cid_topografico2_radio + ", cid_topografico3_radio=" + cid_topografico3_radio + ", area_irradi_descricao1_radio=" + area_irradi_descricao1_radio + ", area_irradi_descricao2_radio=" + area_irradi_descricao2_radio + ", area_irradi_descricao3_radio=" + area_irradi_descricao3_radio + ", ncampo_incer1_radio=" + ncampo_incer1_radio + ", ncampo_incer2_radio=" + ncampo_incer2_radio + ", ncampo_incer3_radio=" + ncampo_incer3_radio + ", dt_ini_area_irrad1_radio=" + dt_ini_area_irrad1_radio + ", dt_ini_area_irrad2_radio=" + dt_ini_area_irrad2_radio + ", dt_ini_area_irrad3_radio=" + dt_ini_area_irrad3_radio + ", dt_fim_area_irrad1_radio=" + dt_fim_area_irrad1_radio + ", dt_fim_area_irrad2_radio=" + dt_fim_area_irrad2_radio + ", dt_fim_area_irrad3_radio=" + dt_fim_area_irrad3_radio + '}';
    }

}
