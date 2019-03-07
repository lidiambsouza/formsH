package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Finalidade_radio_f2;
import hujbb.informatica.apac.entidades.Formulario_f2;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Formulario_f2DAO implements CrudDAO<Formulario_f2> {

    @Override
    public Formulario_f2 salvar(Formulario_f2 entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();

        try {
            String sql = "INSERT INTO `formulario_f2`("
                    + "`formulario_id`,"
                    + " `local_tumor_primario`,"
                    + " `cid_topografia_onco`, "
                    + "`linfo_reg_invalidos`, "
                    + "`linfo_reg_invalidonaoav`, "
                    + "`loc_meta`, "
                    + "`esta_uicc`, "
                    + "`esta_outros`, "
                    + "`grau_histo`, "
                    + "`diag_cito_histo`, "
                    + "`data_64`, "
                    + "`quimio_radio`, "
                    + "`trate_anteriores_quimio`, "
                    + "`trate_ante1_quimio`, "
                    + "`trate_ante2_quimio`, "
                    + "`trate_ante3_quimio`, "
                    + "`dt_trate_ante1_quimio`, "
                    + "`dt_trate_ante2_quimio`, "
                    + "`dt_trate_ante3_quimio`, "
                    + "`continue_trate_quimio`, "
                    + "`dt_ini_trata_quimio`, "
                    + "`esquema_sigla_quimio`, "
                    + "`qtd_meses_planejado_quimio`, "
                    + "`qtd_meses_autrorizados_quimio`, "
                    + "`trate_anteriores_radio`, "
                    + "`trate_ante1_radio`, "
                    + "`trate_ante2_radio`, "
                    + "`trate_ante3_radio`, "
                    + "`dt_trate_ante1_radio`, "
                    + "`dt_trate_ante2_radio`, "
                    + "`dt_trate_ante3_radio`, "
                    + "`continue_trate_radio`, "
                    + "`dt_ini_trata_radio`, "
                    + "`finalidade_radio_f2_id`, "
                    + "`cid_topografico1_radio`, "
                    + "`cid_topografico2_radio`, "
                    + "`cid_topografico3_radio`, "
                    + "`area_irradi_descricao1_radio`, "
                    + "`area_irradi_descricao2_radio`, "
                    + "`area_irradi_descricao3_radio`, "
                    + "`ncampo_inscer1_radio`, "
                    + "`ncampo_inscer2_radio`, "
                    + "`ncampo_inscer3_radio`, "
                    + "`dt_ini_area_irradi1_radio`, "
                    + "`dt_ini_area_irradi2_radio`, "
                    + "`dt_ini_area_irradi3_radio`, "
                    + "`dt_fim_area_irradi1_radio`, "
                    + "`dt_fim_area_irradi2_radio`, "
                    + "`dt_fim_area_irradi3_radio`"
                    + ")"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, entidade.getFormulario_id());
            ps.setString(2, entidade.getLocal_tumor_primario());
            ps.setString(3, entidade.getCid_topografia_onco());
            ps.setBoolean(4, (entidade.getLinfo_reg_invalido()==0));
            ps.setBoolean(5, (entidade.getLinfo_reg_invalido()==2));
            ps.setString(6, entidade.getLoc_meta());
            ps.setString(7, entidade.getEsta_uicc());
            ps.setString(8, entidade.getEsta_outros());
            ps.setString(9, entidade.getGrau_isto());
            ps.setString(10, entidade.getDiag_cito_isto());
            ps.setDate(11, F.sqlDate(entidade.getData_64()));
            ps.setBoolean(12, entidade.isQuimio_radio());
            ps.setBoolean(13, entidade.isTrate_anteriores_quimio());
            ps.setString(14, entidade.getTrate_ante1_quimio());
            ps.setString(15, entidade.getTrate_ante2_quimio());
            ps.setString(16, entidade.getTrate_ante3_quimio());
            ps.setDate(17, F.sqlDate(entidade.getDt_trate_ante1_quimio()));
            ps.setDate(18, F.sqlDate(entidade.getDt_trate_ante2_quimio()));
            ps.setDate(19, F.sqlDate(entidade.getDt_trate_ante3_quimio()));
            ps.setBoolean(20, entidade.isContinue_trate_quimio());
            ps.setDate(21, F.sqlDate(entidade.getDt_ini_trata_quimio()));
            ps.setString(22, entidade.getEsquema_sigla_quimio());
            ps.setInt(23, entidade.getQtd_mese_planejado_quimio());
            ps.setInt(24, entidade.getQtd_mese_autorizados_quimio());
            ps.setBoolean(25, entidade.isTrate_ante_radio());
            ps.setString(26, entidade.getTrate_ante1_radio());
            ps.setString(27, entidade.getTrate_ante2_radio());
            ps.setString(28, entidade.getTrate_ante3_radio());
            ps.setDate(29, F.sqlDate(entidade.getDt_trate_ante1_radio()));
            ps.setDate(30, F.sqlDate(entidade.getDt_trate_ante2_radio()));
            ps.setDate(31, F.sqlDate(entidade.getDt_trate_ante3_radio()));
            ps.setBoolean(32, entidade.isContinue_trate_radio());
            ps.setDate(33, F.sqlDate(entidade.getDt_ini_trata_radio()));
            ps.setInt(34, entidade.getFinalidade().getId());
            ps.setString(35, entidade.getCid_topografico1_radio());
            ps.setString(36, entidade.getCid_topografico2_radio());
            ps.setString(37, entidade.getCid_topografico3_radio());
            ps.setString(38, entidade.getArea_irradi_descricao1_radio());
            ps.setString(39, entidade.getArea_irradi_descricao2_radio());
            ps.setString(40, entidade.getArea_irradi_descricao3_radio());
            ps.setString(41, entidade.getNcampo_incer1_radio());
            ps.setString(42, entidade.getNcampo_incer2_radio());
            ps.setString(43, entidade.getNcampo_incer3_radio());
            ps.setDate(44, F.sqlDate(entidade.getDt_ini_area_irrad1_radio()));
            ps.setDate(45, F.sqlDate(entidade.getDt_ini_area_irrad2_radio()));
            ps.setDate(46, F.sqlDate(entidade.getDt_ini_area_irrad3_radio()));
            ps.setDate(47, F.sqlDate(entidade.getDt_fim_area_irrad1_radio()));
            ps.setDate(48, F.sqlDate(entidade.getDt_fim_area_irrad2_radio()));
            ps.setDate(49, F.sqlDate(entidade.getDt_fim_area_irrad3_radio()));

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("formulario_f2DAO 122! " + ex.toString());
            return null;
        }

        return entidade;
    }

    @Override
    public Formulario_f2 atualizar(Formulario_f2 entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `formulario_f2` SET"
                    + "`local_tumor_primario`=?,"//1
                    + "`cid_topografia_onco`=?,"//2
                    + "`linfo_reg_invalidos`=?,"//3
                    + "`linfo_reg_invalidonaoav`=?,"//4
                    + "`loc_meta`=?,"//5
                    + "`esta_uicc`=?,"//6
                    + "`esta_outros`=?,"//7
                    + "`grau_histo`=?,"//8
                    + "`diag_cito_histo`=?,"//9
                    + "`data_64`=?,"//10
                    + "`quimio_radio`=?,"//11
                    + "`trate_anteriores_quimio`=?,"//11
                    + "`trate_ante1_quimio`=?,"//12
                    + "`trate_ante2_quimio`=?,"//13
                    + "`trate_ante3_quimio`=?,"//14
                    + "`dt_trate_ante1_quimio`=?,"//15
                    + "`dt_trate_ante2_quimio`=?,"//16
                    + "`dt_trate_ante3_quimio`=?,"//17
                    + "`continue_trate_quimio`=?,"//18
                    + "`dt_ini_trata_quimio`=?,"//19
                    + "`esquema_sigla_quimio`=?,"//20
                    + "`qtd_meses_planejado_quimio`=?,"//21
                    + "`qtd_meses_autrorizados_quimio`=?,"//22
                    + "`trate_anteriores_radio`=?,"//23
                    + "`trate_ante1_radio`=?,"//24
                    + "`trate_ante2_radio`=?,"//25
                    + "`trate_ante3_radio`=?,"//26
                    + "`dt_trate_ante1_radio`=?,"//27
                    + "`dt_trate_ante2_radio`=?,"//28
                    + "`dt_trate_ante3_radio`=?,"//29
                    + "`continue_trate_radio`=?,"//30
                    + "`dt_ini_trata_radio`=?,"//31
                    + "`finalidade_radio_f2_id`=?,"//32
                    + "`cid_topografico1_radio`=?,"//33
                    + "`cid_topografico2_radio`=?,"//34
                    + "`cid_topografico3_radio`=?,"//35
                    + "`area_irradi_descricao1_radio`=?,"//36
                    + "`area_irradi_descricao2_radio`=?,"//37
                    + "`area_irradi_descricao3_radio`=?,"//38
                    + "`ncampo_inscer1_radio`=?,"//39
                    + "`ncampo_inscer2_radio`=?,"//40
                    + "`ncampo_inscer3_radio`=?,"//41
                    + "`dt_ini_area_irradi1_radio`=?,"//42
                    + "`dt_ini_area_irradi2_radio`=?,"//43
                    + "`dt_ini_area_irradi3_radio`=?,"//44
                    + "`dt_fim_area_irradi1_radio`=?,"//45
                    + "`dt_fim_area_irradi2_radio`=?,"//46
                    + "`dt_fim_area_irradi3_radio`=?"//47
                    + " WHERE   `formulario_id`=?";//48
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, entidade.getLocal_tumor_primario());
            ps.setString(2, entidade.getCid_topografia_onco());
            ps.setBoolean(3, (entidade.getLinfo_reg_invalido()==0));
            ps.setBoolean(4, (entidade.getLinfo_reg_invalido()==2));
            ps.setString(5, entidade.getLoc_meta());
            ps.setString(6, entidade.getEsta_uicc());
            ps.setString(7, entidade.getEsta_outros());
            ps.setString(8, entidade.getGrau_isto());
            ps.setString(9, entidade.getDiag_cito_isto());
            ps.setDate(10, F.sqlDate(entidade.getData_64()));
            ps.setBoolean(11, entidade.isQuimio_radio());
            ps.setBoolean(12, entidade.isTrate_anteriores_quimio());
            ps.setString(13, entidade.getTrate_ante1_quimio());
            ps.setString(14, entidade.getTrate_ante2_quimio());
            ps.setString(15, entidade.getTrate_ante3_quimio());
            ps.setDate(16, F.sqlDate(entidade.getDt_trate_ante1_quimio()));
            ps.setDate(17, F.sqlDate(entidade.getDt_trate_ante2_quimio()));
            ps.setDate(18, F.sqlDate(entidade.getDt_trate_ante3_quimio()));
            ps.setBoolean(19, entidade.isContinue_trate_quimio());
            ps.setDate(20, F.sqlDate(entidade.getDt_ini_trata_quimio()));
            ps.setString(21, entidade.getEsquema_sigla_quimio());
            ps.setInt(22, entidade.getQtd_mese_planejado_quimio());
            ps.setInt(23, entidade.getQtd_mese_autorizados_quimio());
            ps.setBoolean(24, entidade.isTrate_ante_radio());
            ps.setString(25, entidade.getTrate_ante1_radio());
            ps.setString(26, entidade.getTrate_ante2_radio());
            ps.setString(27, entidade.getTrate_ante3_radio());
            ps.setDate(28, F.sqlDate(entidade.getDt_trate_ante1_radio()));
            ps.setDate(29, F.sqlDate(entidade.getDt_trate_ante2_radio()));
            ps.setDate(30, F.sqlDate(entidade.getDt_trate_ante3_radio()));
            ps.setBoolean(31, entidade.isContinue_trate_radio());
            ps.setDate(32, F.sqlDate(entidade.getDt_ini_trata_radio()));

            ps.setInt(33, entidade.getFinalidade().getId());
            ps.setString(34, entidade.getCid_topografico1_radio());
            ps.setString(35, entidade.getCid_topografico2_radio());
            ps.setString(36, entidade.getCid_topografico3_radio());
            ps.setString(37, entidade.getArea_irradi_descricao1_radio());
            ps.setString(38, entidade.getArea_irradi_descricao2_radio());
            ps.setString(39, entidade.getArea_irradi_descricao3_radio());
            ps.setString(40, entidade.getNcampo_incer1_radio());
            ps.setString(41, entidade.getNcampo_incer2_radio());
            ps.setString(42, entidade.getNcampo_incer3_radio());
            ps.setDate(43, F.sqlDate(entidade.getDt_ini_area_irrad1_radio()));
            ps.setDate(44, F.sqlDate(entidade.getDt_ini_area_irrad2_radio()));
            ps.setDate(45, F.sqlDate(entidade.getDt_ini_area_irrad3_radio()));
            ps.setDate(46, F.sqlDate(entidade.getDt_fim_area_irrad1_radio()));
            ps.setDate(47, F.sqlDate(entidade.getDt_fim_area_irrad2_radio()));
            ps.setDate(48, F.sqlDate(entidade.getDt_fim_area_irrad3_radio()));
            ps.setInt(49, entidade.getFormulario_id());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! formulario_f2DAO 238!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar formulario_f2", ex);
        }
    }

    @Override
    public Formulario_f2 deletar(Formulario_f2 entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Formulario_f2> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     formulario_f2.`formulario_id` AS formulario_f2_formulario_id,\n"
                    + "     formulario_f2.`local_tumor_primario` AS formulario_f2_local_tumor_primario,\n"
                    + "     formulario_f2.`cid_topografia_onco` AS formulario_f2_cid_topografia_onco,\n"
                    + "     formulario_f2.`linfo_reg_invalidos` AS formulario_f2_linfo_reg_invalidos,\n"
                    + "     formulario_f2.`linfo_reg_invalidonaoav` AS formulario_f2_linfo_reg_invalidonaoav,\n"
                    + "     formulario_f2.`loc_meta` AS formulario_f2_loc_meta,\n"
                    + "     formulario_f2.`esta_uicc` AS formulario_f2_esta_uicc,\n"
                    + "     formulario_f2.`esta_outros` AS formulario_f2_esta_outros,\n"
                    + "     formulario_f2.`grau_histo` AS formulario_f2_grau_histo,\n"
                    + "     formulario_f2.`diag_cito_histo` AS formulario_f2_diag_cito_histo,\n"
                    + "     formulario_f2.`data_64` AS formulario_f2_data_64,\n"
                    + "     formulario_f2.`quimio_radio` AS formulario_f2_quimio_radio,\n"
                    + "     formulario_f2.`trate_anteriores_quimio` AS formulario_f2_trate_anteriores_quimio,\n"
                    + "     formulario_f2.`trate_ante1_quimio` AS formulario_f2_trate_ante1_quimio,\n"
                    + "     formulario_f2.`trate_ante2_quimio` AS formulario_f2_trate_ante2_quimio,\n"
                    + "     formulario_f2.`trate_ante3_quimio` AS formulario_f2_trate_ante3_quimio,\n"
                    + "     formulario_f2.`dt_trate_ante1_quimio` AS formulario_f2_dt_trate_ante1_quimio,\n"
                    + "     formulario_f2.`dt_trate_ante2_quimio` AS formulario_f2_dt_trate_ante2_quimio,\n"
                    + "     formulario_f2.`dt_trate_ante3_quimio` AS formulario_f2_dt_trate_ante3_quimio,\n"
                    + "     formulario_f2.`continue_trate_quimio` AS formulario_f2_continue_trate_quimio,\n"
                    + "     formulario_f2.`dt_ini_trata_quimio` AS formulario_f2_dt_ini_trata_quimio,\n"
                    + "     formulario_f2.`esquema_sigla_quimio` AS formulario_f2_esquema_sigla_quimio,\n"
                    + "     formulario_f2.`qtd_meses_planejado_quimio` AS formulario_f2_qtd_meses_planejado_quimio,\n"
                    + "     formulario_f2.`qtd_meses_autrorizados_quimio` AS formulario_f2_qtd_meses_autorizados_quimio,\n"
                    + "     formulario_f2.`trate_anteriores_radio` AS formulario_f2_trate_anteriores_radio,\n"
                    + "     formulario_f2.`trate_ante1_radio` AS formulario_f2_trate_ante1_radio,\n"
                    + "     formulario_f2.`trate_ante2_radio` AS formulario_f2_trate_ante2_radio,\n"
                    + "     formulario_f2.`trate_ante3_radio` AS formulario_f2_trate_ante3_radio,\n"
                    + "     formulario_f2.`dt_trate_ante1_radio` AS formulario_f2_dt_trate_ante1_radio,\n"
                    + "     formulario_f2.`dt_trate_ante2_radio` AS formulario_f2_dt_trate_ante2_radio,\n"
                    + "     formulario_f2.`dt_trate_ante3_radio` AS formulario_f2_dt_trate_ante3_radio,\n"
                    + "     formulario_f2.`continue_trate_radio` AS formulario_f2_continue_trate_radio,\n"
                    + "     formulario_f2.`dt_ini_trata_radio` AS formulario_f2_dt_ini_trata_radio,\n"
                    + "     formulario_f2.`cid_topografico1_radio` AS formulario_f2_cid_topografico1_radio,\n"
                    + "     formulario_f2.`cid_topografico2_radio` AS formulario_f2_cid_topografico2_radio,\n"
                    + "     formulario_f2.`cid_topografico3_radio` AS formulario_f2_cid_topografico3_radio,\n"
                    + "     formulario_f2.`area_irradi_descricao1_radio` AS formulario_f2_area_irradi_descricao1_radio,\n"
                    + "     formulario_f2.`area_irradi_descricao2_radio` AS formulario_f2_area_irradi_descricao2_radio,\n"
                    + "     formulario_f2.`area_irradi_descricao3_radio` AS formulario_f2_area_irradi_descricao3_radio,\n"
                    + "     formulario_f2.`ncampo_inscer1_radio` AS formulario_f2_ncampo_inscer1_radio,\n"
                    + "     formulario_f2.`ncampo_inscer2_radio` AS formulario_f2_ncampo_inscer2_radio,\n"
                    + "     formulario_f2.`ncampo_inscer3_radio` AS formulario_f2_ncampo_inscer3_radio,\n"
                    + "     formulario_f2.`dt_ini_area_irradi1_radio` AS formulario_f2_dt_ini_area_irradi1_radio,\n"
                    + "     formulario_f2.`dt_ini_area_irradi2_radio` AS formulario_f2_dt_ini_area_irradi2_radio,\n"
                    + "     formulario_f2.`dt_ini_area_irradi3_radio` AS formulario_f2_dt_ini_area_irradi3_radio,\n"
                    + "     formulario_f2.`dt_fim_area_irradi1_radio` AS formulario_f2_dt_fim_area_irradi1_radio,\n"
                    + "     formulario_f2.`dt_fim_area_irradi2_radio` AS formulario_f2_dt_fim_area_irradi2_radio,\n"
                    + "     formulario_f2.`dt_fim_area_irradi3_radio` AS formulario_f2_dt_fim_area_irradi3_radio,\n"
                    + "     finalidade_radio_f2.`id` AS finalidade_radio_f2_id,\n"
                    + "     finalidade_radio_f2.`finalidade` AS finalidade_radio_f2_finalidade\n"
                    + "FROM\n"
                    + "     `finalidade_radio_f2` finalidade_radio_f2 INNER JOIN `formulario_f2` formulario_f2 ON finalidade_radio_f2.`id` = formulario_f2.`finalidade_radio_f2_id` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Formulario_f2> entidades = new ArrayList<>();

            while (rs.next()) {
                
                Formulario_f2 entidade = new Formulario_f2(
                        rs.getInt("formulario_f2_formulario_id"),
                        rs.getString("formulario_f2_local_tumor_primario"),
                        rs.getString("formulario_f2_cid_topografia_onco"),
                       (rs.getBoolean("formulario_f2_linfo_reg_invalidos")? 0:1),
                        rs.getString("formulario_f2_loc_meta"),
                        rs.getString("formulario_f2_esta_uicc"),
                        rs.getString("formulario_f2_esta_outros"),
                        rs.getString("formulario_f2_grau_histo"),
                        rs.getString("formulario_f2_diag_cito_histo"),
                        rs.getDate("formulario_f2_data_64"),
                        rs.getBoolean("formulario_f2_quimio_radio"),
                        rs.getBoolean("formulario_f2_trate_anteriores_quimio"),
                        rs.getString("formulario_f2_trate_ante1_quimio"),
                        rs.getString("formulario_f2_trate_ante2_quimio"),
                        rs.getString("formulario_f2_trate_ante3_quimio"),
                        rs.getDate("formulario_f2_dt_trate_ante1_quimio"),
                        rs.getDate("formulario_f2_dt_trate_ante2_quimio"),
                        rs.getDate("formulario_f2_dt_trate_ante3_quimio"),
                        rs.getBoolean("formulario_f2_continue_trate_quimio"),
                        rs.getDate("formulario_f2_dt_ini_trata_quimio"),
                        rs.getString("formulario_f2_esquema_sigla_quimio"),
                        rs.getInt("formulario_f2_qtd_meses_planejado_quimio"),
                        rs.getInt("formulario_f2_qtd_meses_autorizados_quimio"),
                        rs.getBoolean("formulario_f2_trate_anteriores_radio"),
                        rs.getString("formulario_f2_trate_ante1_radio"),
                        rs.getString("formulario_f2_trate_ante2_radio"),
                        rs.getString("formulario_f2_trate_ante3_radio"),
                        rs.getDate("formulario_f2_dt_trate_ante1_radio"),
                        rs.getDate("formulario_f2_dt_trate_ante2_radio"),
                        rs.getDate("formulario_f2_dt_trate_ante3_radio"),
                        rs.getBoolean("formulario_f2_continue_trate_radio"),
                        rs.getDate("formulario_f2_dt_ini_trata_radio"),
                        new Finalidade_radio_f2(
                                rs.getInt("finalidade_radio_f2_id"),
                                rs.getString("finalidade_radio_f2_finalidade")
                        ),
                        rs.getString("formulario_f2_cid_topografico1_radio"),
                        rs.getString("formulario_f2_cid_topografico2_radio"),
                        rs.getString("formulario_f2_cid_topografico3_radio"),
                        rs.getString("formulario_f2_area_irradi_descricao1_radio"),
                        rs.getString("formulario_f2_area_irradi_descricao2_radio"),
                        rs.getString("formulario_f2_area_irradi_descricao3_radio"),
                        rs.getString("formulario_f2_ncampo_inscer1_radio"),
                        rs.getString("formulario_f2_ncampo_inscer2_radio"),
                        rs.getString("formulario_f2_ncampo_inscer3_radio"),
                        rs.getDate("formulario_f2_dt_ini_area_irradi1_radio"),
                        rs.getDate("formulario_f2_dt_ini_area_irradi2_radio"),
                        rs.getDate("formulario_f2_dt_ini_area_irradi3_radio"),
                        rs.getDate("formulario_f2_dt_fim_area_irradi1_radio"),
                        rs.getDate("formulario_f2_dt_fim_area_irradi2_radio"),
                        rs.getDate("formulario_f2_dt_fim_area_irradi3_radio")
                );
                
                //decide se é nao av
                if(rs.getBoolean("formulario_f2_linfo_reg_invalidonaoav")){
                    entidade.setLinfo_reg_invalido(2);
                }
                

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            F.setMsgErro("Formulario_f2DAO: Buscar: " + e.toString());
            throw new ErroSistema("Erro ao buscar dados do Formulario_f2", e);
        }
    }

    @Override
    public Formulario_f2 buscaId(String id) throws ErroSistema {
        Formulario_f2 c = null;
        List< Formulario_f2> l = buscar("WHERE formulario_f2.`formulario_id` = '" + id + "'");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
