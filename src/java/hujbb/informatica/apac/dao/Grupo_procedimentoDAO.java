package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Grupo_procedimento;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Grupo_procedimentoDAO implements CrudDAO<Grupo_procedimento> {

    @Override
    public Grupo_procedimento salvar(Grupo_procedimento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `grupo_procedimento`(`id`, `nome`, `dt_competencia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
            ps.setString(1, entidade.getId());
            ps.setString(2, entidade.getNome());
            ps.setInt(3, entidade.getDt_competencia());

            ps.executeUpdate();

            //comentei pq nao é auto incremento
            /* ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }*/
        } catch (SQLException ex) {
            F.setMsgErro("Grupo_procedimentoDAO 35! " + ex.toString());
        }
        
        return entidade;
    }
    

    @Override
    public Grupo_procedimento atualizar(Grupo_procedimento entidade) throws ErroSistema {
         Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `grupo_procedimento` SET `nome`=? WHERE `id`=? and `dt_competencia`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getId());
            ps.setInt(3, entidade.getDt_competencia());            

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Grupo_procedimentoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Grupo_procedimento deletar(Grupo_procedimento entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Grupo_procedimento> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `nome`, `dt_competencia` FROM `grupo_procedimento` " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Grupo_procedimento> entidades = new ArrayList<>();

            while (rs.next()) {
                Grupo_procedimento entidade = new Grupo_procedimento();

                entidade.setId(rs.getString("id"));
                entidade.setNome(rs.getString("nome"));
                entidade.setDt_competencia(rs.getInt("dt_competencia"));

                entidades.add(entidade);
            }
            
            return entidades;

        } catch (SQLException e) {
            F.setMsgErro("grupoProcedimetno DAO 77" + e.toString());
            throw new ErroSistema("Erro ao buscar grupo procedimento ", e);
        }
    }

    @Override
    public Grupo_procedimento buscaId(String id) throws ErroSistema {
        Grupo_procedimento c = null;
        List<Grupo_procedimento> l = buscar("WHERE `id` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
