package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Autorizacao;
import hujbb.informatica.apac.entidades.Competencia;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompetenciaDAO implements CrudDAO<Competencia> {

    @Override
    public Competencia salvar(Competencia entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `competencia`(`competencia`) VALUES  (?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getCompetencia());
            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("competenciaDAO 26! " + ex.toString());
        }
        return entidade;
    }

    @Override
    public Competencia atualizar(Competencia entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Competencia deletar(Competencia entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Competencia> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `competencia` FROM `competencia` " + condicao +" ORDER BY `competencia` DESC";
           // F.setMsgErro(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Competencia> entidades = new ArrayList<>();
            Competencia entidade;
            while (rs.next()) {
                entidade =  new Competencia(rs.getInt("competencia"));
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados da competencia", e);
        }
    }

    @Override
    public Competencia buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
