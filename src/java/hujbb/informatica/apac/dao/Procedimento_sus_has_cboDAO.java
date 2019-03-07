package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Procedimento_sus_has_cbo;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Procedimento_sus_has_cboDAO implements CrudDAO<Procedimento_sus_has_cbo> {

    @Override
    public Procedimento_sus_has_cbo salvar(Procedimento_sus_has_cbo entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `procedimento_sus_has_cbo`(`procedimento_sus_codigo`, `cbo_id`, `dt_competencia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getProcedimento_sus_codigo());
            //F.setMsgErro(entidade.getProcedimento_sus_codigo());
            ps.setString(2, entidade.getCbo_id());
            ps.setInt(3, entidade.getDt_competencia());


            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Procedimento_sus_has_cboDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Procedimento_sus_has_cbo atualizar(Procedimento_sus_has_cbo entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Procedimento_sus_has_cbo deletar(Procedimento_sus_has_cbo entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Procedimento_sus_has_cbo> buscar(String condicao) throws ErroSistema {
       condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `procedimento_sus_codigo`, `cbo_id`, `dt_competencia` FROM `procedimento_sus_has_cbo` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Procedimento_sus_has_cbo> entidades = new ArrayList<>();

            while (rs.next()) {
                Procedimento_sus_has_cbo entidade = new Procedimento_sus_has_cbo(
                        rs.getString("procedimento_sus_codigo"),
                        rs.getString("cbo_id"),
                        rs.getInt("dt_competencia")
                       
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Procedimento_sus_has_cbo 72", e);
        }
    }

    @Override
    public Procedimento_sus_has_cbo buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
