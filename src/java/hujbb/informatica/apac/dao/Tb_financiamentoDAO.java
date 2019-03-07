
package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Grupo_procedimento;
import hujbb.informatica.apac.entidades.Sub_grupo_procedimento;
import hujbb.informatica.apac.entidades.Tb_financiamento;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Tb_financiamentoDAO implements CrudDAO<Tb_financiamento>{

    @Override
    public Tb_financiamento salvar(Tb_financiamento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `proced_financiamento`(`id`, `nome`, `dt_competencia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
            ps.setString(1, entidade.getId());
            ps.setString(2, entidade.getNome());
            //F.setMsgErro(entidade.getNome());
            ps.setInt(3, entidade.getDt_competencia());
           
            ps.executeUpdate();

            
            //comentei pq nao é auto incremento
           /* ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }*/

        } catch (SQLException ex) {
            F.setMsgErro("Tb_financiamentoDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Tb_financiamento atualizar(Tb_financiamento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `proced_financiamento` SET `nome`=? WHERE `id`=? and `dt_competencia`=? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getId());
            ps.setInt(3, entidade.getDt_competencia());
            
            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Tb_financiamentoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Tb_financiamento deletar(Tb_financiamento entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tb_financiamento> buscar(String condicao) throws ErroSistema {
     condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `nome`, `dt_competencia` FROM `proced_financiamento` " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tb_financiamento> entidades = new ArrayList<>();

            while (rs.next()) {
                Tb_financiamento entidade = new Tb_financiamento(
                        rs.getString("id"),
                        rs.getString("nome"),
                        rs.getInt("dt_competencia")
                );

                entidades.add(entidade);
            }
            
            return entidades;

        } catch (SQLException e) {
            F.setMsgErro("Tb_financiamentoDAO 77" + e.toString());
            throw new ErroSistema("Erro ao buscar sub Tb_financiamento ", e);
        }
    }

    @Override
    public Tb_financiamento buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
