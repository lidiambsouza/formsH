
package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Proced_tp_compexidade;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Proced_tp_compexidadeDAO implements CrudDAO<Proced_tp_compexidade>{

    @Override
    public Proced_tp_compexidade salvar(Proced_tp_compexidade entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `proced_tp_compexidade`(`id`, `complexidade`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
            ps.setString(1, entidade.getId());
            ps.setString(2, entidade.getComplexidade());
            
           
            ps.executeUpdate();

            
            //comentei pq nao é auto incremento
           /* ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }*/

        } catch (SQLException ex) {
            F.setMsgErro("Proced_tp_compexidadeDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Proced_tp_compexidade atualizar(Proced_tp_compexidade entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `proced_tp_compexidade` SET `complexidade`=? WHERE `id`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getComplexidade());
            ps.setString(2, entidade.getId());
           
            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Proced_tp_complexidadeDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Proced_tp_compexidade deletar(Proced_tp_compexidade entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Proced_tp_compexidade> buscar(String condicao) throws ErroSistema {
      condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `complexidade` FROM `proced_tp_compexidade` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Proced_tp_compexidade> entidades = new ArrayList<>();

            while (rs.next()) {
                Proced_tp_compexidade entidade = new Proced_tp_compexidade(
                        rs.getString("id"),
                        rs.getString("proced_tp_compexidade")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Proced_tp_compexidade 74", e);
        }
    }

    @Override
    public Proced_tp_compexidade buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
