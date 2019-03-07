package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Proc_justificativa;
import hujbb.informatica.apac.entidades.Tp_sexo;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Proc_justificativaDAO implements Serializable, CrudDAO<Proc_justificativa> {

    @Override
    public Proc_justificativa salvar(Proc_justificativa entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `proc_justificativa`( `observacoes`, `cid_cid_principal`, `cid_cid_secundario`,`cid_cid_causas_assoc`) VALUES (?,?,?,?)";

            PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getObservacoes());
            ps.setString(2, entidade.getCid_principal().getCid());
            ps.setString(3, entidade.getCid_secundario().getCid());
            ps.setString(4, entidade.getCid_causas_assoc().getCid());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_proc_justificativa(rs.getInt(1));
            }

        } catch (SQLException ex) {
            F.setMsgErro("proc_justificativaDAO 47! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Proc_justificativa atualizar(Proc_justificativa entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `proc_justificativa` SET "
                    + "`observacoes`=?,"
                    + "`cid_cid_principal`=?,"
                    + "`cid_cid_secundario`=?,"
                    + "`cid_cid_causas_assoc`=?"
                    + " WHERE `id_proc_justificativa`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getObservacoes());
            ps.setString(2, entidade.getCid_principal().getCid());
            ps.setString(3, entidade.getCid_secundario().getCid());
            ps.setString(4, entidade.getCid_causas_assoc().getCid());
            ps.setInt(5, entidade.getId_proc_justificativa());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Proc_justificativaDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Proc_justificativa deletar(Proc_justificativa entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM `procedimento_sus` WHERE  `codigo` = ?");
            ps.setInt(1, entidade.getId_proc_justificativa());
            ps.execute();
        } catch (SQLException ex) {
            F.setMsgErro("Proc_justificativaDAO66: " + ex);
            entidade = null;

        }

        return entidade;
    }

    @Override
    public List<Proc_justificativa> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     proc_justificativa.`id_proc_justificativa` AS proc_justificativa_id_proc_justificativa,\n"
                    + "     proc_justificativa.`observacoes` AS proc_justificativa_observacoes,\n"
                    + "     cid.`cid` AS cid_cid,\n"
                    + "     cid.`nome` AS cid_nome,\n"
                    + "     tp_sexo.`id` AS tp_sexo_id,\n"
                    + "     tp_sexo.`sexo` AS tp_sexo_sexo,\n"
                    + "     cid_A.`cid` AS cid_A_cid,\n"
                    + "     cid_A.`nome` AS cid_A_nome,\n"
                    + "     tp_sexo_A.`id` AS tp_sexo_A_id,\n"
                    + "     tp_sexo_A.`sexo` AS tp_sexo_A_sexo,\n"
                    + "     cid_B.`cid` AS cid_B_cid,\n"
                    + "     cid_B.`nome` AS cid_B_nome,\n"
                    + "     tp_sexo_B.`id` AS tp_sexo_B_id,\n"
                    + "     tp_sexo_B.`sexo` AS tp_sexo_B_sexo\n"
                    + "FROM\n"
                    + "     `cid` cid INNER JOIN `proc_justificativa` proc_justificativa ON cid.`cid` = proc_justificativa.`cid_cid_principal`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo ON cid.`tp_sexo_id` = tp_sexo.`id`\n"
                    + "     INNER JOIN `cid` cid_A ON proc_justificativa.`cid_cid_secundario` = cid_A.`cid`\n"
                    + "     INNER JOIN `cid` cid_B ON proc_justificativa.`cid_cid_causas_assoc` = cid_B.`cid`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo_B ON cid_B.`tp_sexo_id` = tp_sexo_B.`id`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo_A ON cid_A.`tp_sexo_id` = tp_sexo_A.`id` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Proc_justificativa> entidades = new ArrayList<>();

            while (rs.next()) {
                Proc_justificativa entidade = new Proc_justificativa(
                        rs.getInt("proc_justificativa_id_proc_justificativa"),
                        rs.getString("proc_justificativa_observacoes"),
                        new Cid(
                                rs.getString("cid_cid"),
                                rs.getString("cid_nome"),
                                new Tp_sexo(
                                        rs.getString("tp_sexo_id"),
                                        rs.getString("tp_sexo_sexo")
                                )
                        ),
                        new Cid(
                                rs.getString("cid_A_cid"),
                                rs.getString("cid_A_nome"),
                                new Tp_sexo(
                                        rs.getString("tp_sexo_A_id"),
                                        rs.getString("tp_sexo_A_sexo")
                                )
                        ),
                        new Cid(
                                rs.getString("cid_B_cid"),
                                rs.getString("cid_B_nome"),
                                new Tp_sexo(
                                        rs.getString("tp_sexo_B_id"),
                                        rs.getString("tp_sexo_B_sexo")
                                )
                        )
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Proc_justificativaDAO 146", e);
        }

    }

    @Override
    public Proc_justificativa buscaId(String id) throws ErroSistema {
        Proc_justificativa c = null;
        List<Proc_justificativa> l = buscar("WHERE `id_proc_justificativa` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
