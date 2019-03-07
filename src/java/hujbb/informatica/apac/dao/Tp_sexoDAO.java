
package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Tp_sexo;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Tp_sexoDAO implements CrudDAO<Tp_sexo>{

    @Override
    public Tp_sexo salvar(Tp_sexo entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `tp_sexo`(`id`, `sexo`) VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
            ps.setString(1, entidade.getId());
            ps.setString(2, entidade.getSexo());
                       
            ps.executeUpdate();

            
            //comentei pq nao é auto incremento
           /* ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }*/

        } catch (SQLException ex) {
            F.setMsgErro("Tp_sexoDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Tp_sexo atualizar(Tp_sexo entidade) throws ErroSistema {
   Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `tp_sexo` SET `sexo`=? WHERE `id`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getSexo());
            ps.setString(2, entidade.getId());
            
            ps.execute();

           
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Tp_sexoDAO 55!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar tp_sexo", ex);

        } }

    @Override
    public Tp_sexo deletar(Tp_sexo entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tp_sexo> buscar(String condicao) throws ErroSistema {
    condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `sexo` FROM `tp_sexo` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tp_sexo> entidades = new ArrayList<>();

            while (rs.next()) {
                Tp_sexo entidade = new Tp_sexo(
                        rs.getString("id"),
                        rs.getString("sexo")
                        
                );
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Tp_sexo 91", e);
        }
    }

    @Override
    public Tp_sexo buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
