package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Tb_registro;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tb_registroDAO implements CrudDAO<Tb_registro> {

    @Override
    public Tb_registro salvar(Tb_registro entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `tb_registro`(`id`, `nome`, `dt_competecia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getId());
            ps.setString(2, entidade.getNome());
            ps.setInt(3, entidade.getDt_competencia());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Tb_registroDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Tb_registro atualizar(Tb_registro entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `tb_registro` SET `nome`=? WHERE `id`=? AND `dt_competecia`=? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setInt(2, entidade.getId());
            ps.setInt(3, entidade.getDt_competencia());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Tp_registroDAO 51!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar Tp_registro", ex);

        }
    }

    @Override
    public Tb_registro deletar(Tb_registro entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tb_registro> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `nome`, `dt_competecia` FROM `tb_registro` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tb_registro> entidades = new ArrayList<>();

            while (rs.next()) {
                Tb_registro entidade = new Tb_registro(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("dt_competecia")
                );
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Tb_registro", e);
        }
    }

    @Override
    public Tb_registro buscaId(String id) throws ErroSistema {
        Tb_registro c = null;
        List<Tb_registro> l = buscar("WHERE `id` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
