package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Procedimento_qtd_contratualizado;
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

public class Procedimento_qtd_contratualizadoDAO implements CrudDAO<Procedimento_qtd_contratualizado> {

    @Override
    public Procedimento_qtd_contratualizado salvar(Procedimento_qtd_contratualizado entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `procedimento_qtd_contratualizado`(`procedimento_sus_codigo`, `qtd_contratualizado`, `qtd_executado`)  VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getProcedimento_sus_codigo());
            ps.setInt(2, entidade.getQtd_contratualizado());
            ps.setInt(3, entidade.getQtd_executado());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Procedimento_qtd_contratualizadoDAO 29! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Procedimento_qtd_contratualizado atualizar(Procedimento_qtd_contratualizado entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `procedimento_qtd_contratualizado` SET `qtd_contratualizado`=?,`qtd_executado`=? WHERE `procedimento_sus_codigo`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getQtd_contratualizado());
            ps.setInt(2, entidade.getQtd_executado());
            ps.setString(3, entidade.getProcedimento_sus_codigo());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Procedimento_qtd_contratualizadoDAO 49!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar Procedimento_qtd_contratualizado", ex);

        }
    }

    @Override
    public Procedimento_qtd_contratualizado deletar(Procedimento_qtd_contratualizado entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Procedimento_qtd_contratualizado> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `procedimento_sus_codigo`, `qtd_contratualizado`, `qtd_executado` FROM `procedimento_qtd_contratualizado` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Procedimento_qtd_contratualizado> entidades = new ArrayList<>();

            while (rs.next()) {
                Procedimento_qtd_contratualizado entidade = new Procedimento_qtd_contratualizado(
                        rs.getString("procedimento_sus_codigo"),
                        rs.getInt("qtd_contratualizado"),
                        rs.getInt("qtd_executado")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Procedimento_qtd_contratualizado 54", e);
        }
    }

    @Override
    public Procedimento_qtd_contratualizado buscaId(String id) throws ErroSistema {
        Procedimento_qtd_contratualizado c = null;
        List<Procedimento_qtd_contratualizado> l = buscar("WHERE `procedimento_sus_codigo` = '" + id + "'");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
