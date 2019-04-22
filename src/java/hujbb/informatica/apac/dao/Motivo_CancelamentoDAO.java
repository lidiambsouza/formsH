package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Motivo_Cancelamento;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Motivo_CancelamentoDAO implements Serializable, CrudDAO<Motivo_Cancelamento> {

    @Override
    public Motivo_Cancelamento salvar(Motivo_Cancelamento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO `motivo_cancelamento`(`id`,`motivo`) VALUES (?,?,)");

            ps.setInt(1, entidade.getId());
            ps.setString(2, entidade.getMotivo());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("motivo_cancelamentoDAO F:salvar! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Motivo_Cancelamento atualizar(Motivo_Cancelamento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `motivo_cancelamento` SET `motivo`=? WHERE `id`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getMotivo());
            ps.setInt(2, entidade.getId());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! motivo_cancelamentoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar motivo_cancelamento", ex);

        }
    }

    @Override
    public Motivo_Cancelamento deletar(Motivo_Cancelamento entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM `motivo_cancelamento` WHERE `id`=?");
            ps.setInt(1, entidade.getId());
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o motivo_cancelamento!", ex);
        }
        return entidade;
    }

    @Override
    public List<Motivo_Cancelamento> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     motivo_cancelamento.`id` AS motivo_cancelamento_id,\n"
                    + "     motivo_cancelamento.`motivo` AS motivo_cancelamento_motivo\n"
                    + "FROM\n"
                    + "     motivo_cancelamento " + condicao;
//            System.out.println(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Motivo_Cancelamento> entidades = new ArrayList<>();

            while (rs.next()) {
                Motivo_Cancelamento entidade = new Motivo_Cancelamento(
                        rs.getInt("motivo_cancelamento_id"),
                        rs.getString("motivo_cancelamento_motivo")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do motivo_cancelamentoDAO", e);
        }
    }

    @Override
    public Motivo_Cancelamento buscaId(String id) throws ErroSistema {
        Motivo_Cancelamento c = null;
        List<Motivo_Cancelamento> l = buscar("WHERE motivo_cancelamento.`id`= " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
