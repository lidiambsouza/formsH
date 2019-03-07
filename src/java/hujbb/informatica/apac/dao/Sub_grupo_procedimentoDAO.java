package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Grupo_procedimento;
import hujbb.informatica.apac.entidades.Sub_grupo_procedimento;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sub_grupo_procedimentoDAO implements CrudDAO<Sub_grupo_procedimento> {

    @Override
    public Sub_grupo_procedimento salvar(Sub_grupo_procedimento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `sub_grupo_procedimento`(`id`, `grupo_procedimento_id`, `nome`, `dt_competencia`) VALUES (?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getId());
            ps.setString(2, entidade.getGrupo_Procedimento().getId());
            ps.setString(3, entidade.getNome());
            ps.setInt(4, entidade.getDt_Competencia());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Sub_grupo_procedimentoDAO 36! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Sub_grupo_procedimento atualizar(Sub_grupo_procedimento entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `sub_grupo_procedimento` SET "
                    + "`grupo_procedimento_id`=?,`nome`=?"
                    + " WHERE `id`=? and `dt_competencia`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getGrupo_Procedimento().getId());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getId());
            ps.setInt(4, entidade.getDt_Competencia());
           

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Sub_grupo_procedimentoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Sub_grupo_procedimento deletar(Sub_grupo_procedimento entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sub_grupo_procedimento> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `grupo_procedimento_id`, `nome`, `dt_competencia` FROM `sub_grupo_procedimento` " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Sub_grupo_procedimento> entidades = new ArrayList<>();

            while (rs.next()) {
                Sub_grupo_procedimento entidade = new Sub_grupo_procedimento();

                entidade.setId(rs.getString("id"));
                entidade.setNome(rs.getString("nome"));
                entidade.setGrupo_Procedimento(new Grupo_procedimento(rs.getString("grupo_procedimento_id"), "", 0));
                entidade.setDt_Competencia(rs.getInt("dt_competencia"));

                entidades.add(entidade);
            }
            
            return entidades;

        } catch (SQLException e) {
            F.setMsgErro("subgrupoProcedimetno DAO 77" + e.toString());
            throw new ErroSistema("Erro ao buscar sub grupo procedimento ", e);
        }
    }

    @Override
    public Sub_grupo_procedimento buscaId(String id) throws ErroSistema {
        Sub_grupo_procedimento c = null;
        List<Sub_grupo_procedimento> l = buscar("WHERE `id` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
