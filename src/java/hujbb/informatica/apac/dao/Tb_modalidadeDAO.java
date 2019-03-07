package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Tb_modalidade;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tb_modalidadeDAO implements CrudDAO<Tb_modalidade> {

    @Override
    public Tb_modalidade salvar(Tb_modalidade entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `tb_modalidade`(`id`, `modalidade`, `dt_competencia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            //F.setMsgErro(entidade.getId());
            ps.setString(1, entidade.getId());
            ps.setString(2, entidade.getNome());
            ps.setInt(3, entidade.getDt_competencia());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Tb_modalidadeDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Tb_modalidade atualizar(Tb_modalidade entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `tb_modalidade` SET `modalidade`=? WHERE `id`=? and `dt_competencia`=? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getId());
            ps.setInt(3, entidade.getDt_competencia());
            
            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Tb_modalidadeDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Tb_modalidade deletar(Tb_modalidade entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tb_modalidade> buscar(String condicao) throws ErroSistema {
       condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `modalidade`, `dt_competencia` FROM `tb_modalidade` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tb_modalidade> entidades = new ArrayList<>();

            while (rs.next()) {
                Tb_modalidade entidade = new Tb_modalidade(
                        rs.getString("id"),
                        rs.getString("modalidade"),
                        rs.getInt("dt_competencia")
                );
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Tb_modalidade 69", e);
        }
    }

    @Override
    public Tb_modalidade buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
