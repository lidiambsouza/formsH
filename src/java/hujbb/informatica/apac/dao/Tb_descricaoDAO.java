package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Tb_descricao;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tb_descricaoDAO implements CrudDAO<Tb_descricao> {

    @Override
    public Tb_descricao salvar(Tb_descricao entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `tb_descricao` (`procedimento_sus_codigo`, `descricao`, `dt_competencia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
            ps.setString(1, entidade.getCod_procedimento());
            ps.setString(2, entidade.getDescricao());
            ps.setInt(3, entidade.getDt_competencia());

            ps.executeUpdate();

            //comentei pq nao é auto incremento
            /* ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }*/
        } catch (SQLException ex) {
            F.setMsgErro("Tb_descricaoDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Tb_descricao atualizar(Tb_descricao entidade) throws ErroSistema {
         Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `tb_descricao` SET `descricao`=? WHERE `procedimento_sus_codigo`=? and `dt_competencia`=? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getDescricao());
            ps.setString(2, entidade.getCod_procedimento());
            ps.setInt(3, entidade.getDt_competencia());
            

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Tb_descricaoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Tb_descricao deletar(Tb_descricao entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tb_descricao> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `procedimento_sus_codigo`, `descricao`, `dt_competencia` FROM `tb_descricao` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tb_descricao> entidades = new ArrayList<>();

            while (rs.next()) {
                Tb_descricao entidade = new Tb_descricao(
                        rs.getString("procedimento_sus_codigo"),
                        rs.getString("descricao"),
                        rs.getInt("dt_competencia")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do tb_descricao", e);
        }
    }

    @Override
    public Tb_descricao buscaId(String id) throws ErroSistema {
       return null;
    }
    
    public Tb_descricao buscaId(String id,int dt_competencia) throws ErroSistema {
        Tb_descricao c = null;
        List<Tb_descricao> l = buscar("WHERE `procedimento_sus_codigo` = '" + id +"' AND dt_competencia = "+dt_competencia);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
