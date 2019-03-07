package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO implements CrudDAO<Perfil> {

    @Override
    public Perfil salvar(Perfil entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `perfil`(`id_perfil`, `perfil`) VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getId_perfil());
            ps.setString(2, entidade.getPerfil());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("PerfilDAO F:salvar! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Perfil atualizar(Perfil entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `perfil` SET `perfil`=?  WHERE `id_perfil`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getPerfil());
            ps.setInt(2, entidade.getId_perfil());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! PerfilDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar Perfil", ex);

        }
    }

    @Override
    public Perfil deletar(Perfil entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM `perfil` WHERE `id_perfil`=?");
            ps.setInt(1, entidade.getId_perfil());
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o Perfil!", ex);
        }
        return entidade;
    }

    @Override
    public List<Perfil> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     perfil.`id_perfil` AS perfil_id_perfil,\n"
                    + "     perfil.`perfil` AS perfil_perfil\n"
                    + "FROM\n"
                    + "     `perfil` perfil " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Perfil> entidades = new ArrayList<>();

            while (rs.next()) {
                Perfil entidade = new Perfil(
                        rs.getInt("perfil_id_perfil"),
                        rs.getString("perfil_perfil")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Perfil", e);
        }
    }

    @Override
    public Perfil buscaId(String id) throws ErroSistema {
        Perfil c = null;
        List<Perfil> l = buscar("WHERE `id_perfil`= " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
