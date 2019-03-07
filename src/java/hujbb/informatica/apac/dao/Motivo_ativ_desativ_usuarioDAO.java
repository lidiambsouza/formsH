package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Motivo_ativ_desativ_usuario;
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

/**
 *
 * @author lidia.souza
 */
public class Motivo_ativ_desativ_usuarioDAO implements Serializable, CrudDAO<Motivo_ativ_desativ_usuario> {

    @Override
    public Motivo_ativ_desativ_usuario salvar(Motivo_ativ_desativ_usuario entidade) throws ErroSistema {//pedro
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement( "INSERT INTO `motivo_ativ_desativ_usuario`(`id`,`motivo`, `flag`) VALUES (?,?,?)");
            
            ps.setInt(1, entidade.getId());
            ps.setString(2, entidade.getMotivo());
            ps.setInt(3, entidade.getFlag());
            

            ps.executeUpdate();

            

        } catch (SQLException ex) {
            F.setMsgErro("motivo_ativ_desativ_usuarioDAO F:salvar! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Motivo_ativ_desativ_usuario atualizar(Motivo_ativ_desativ_usuario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `motivo_ativ_desativ_usuario` SET `motivo`=?,`flag`=? WHERE `id`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getMotivo());
            ps.setInt(2, entidade.getFlag());

            ps.setInt(3, entidade.getId());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! motivo_ativ_desativ_usuarioDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar motivo_ativ_desativ_usuario", ex);

        }
    }

    @Override
    public Motivo_ativ_desativ_usuario deletar(Motivo_ativ_desativ_usuario entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM `motivo_ativ_desativ_usuario` WHERE `id`=?");
            ps.setInt(1, entidade.getId());
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o motivo_ativ_desativ_usuario!", ex);
        }
        return entidade;
    }

    @Override
    public List<Motivo_ativ_desativ_usuario> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     motivo_ativ_desativ_usuario.`id` AS motivo_ativ_desativ_usuario_id,\n"
                    + "     motivo_ativ_desativ_usuario.`motivo` AS motivo_ativ_desativ_usuario_motivo,\n"
                    + "     motivo_ativ_desativ_usuario.`flag` AS motivo_ativ_desativ_usuario_flag\n"
                    + "FROM\n"
                    + "     `motivo_ativ_desativ_usuario` motivo_ativ_desativ_usuario " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Motivo_ativ_desativ_usuario> entidades = new ArrayList<>();

            while (rs.next()) {
                Motivo_ativ_desativ_usuario entidade = new Motivo_ativ_desativ_usuario(
                        rs.getInt("motivo_ativ_desativ_usuario_id"),
                        rs.getString("motivo_ativ_desativ_usuario_motivo"),
                        rs.getInt("motivo_ativ_desativ_usuario_flag")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do motivo_ativ_desativ_usuario", e);
        }
    }

    @Override
    public Motivo_ativ_desativ_usuario buscaId(String id) throws ErroSistema {
        Motivo_ativ_desativ_usuario c = null;
        List<Motivo_ativ_desativ_usuario> l = buscar("WHERE `id`= " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
