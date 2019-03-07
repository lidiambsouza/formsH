package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Forma_organizacao;
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

public class Forma_organizacaoDAO implements CrudDAO<Forma_organizacao> {

    @Override
    public Forma_organizacao salvar(Forma_organizacao entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `forma_organizacao`(`id`, `sub_grupo_procedimento_id`, `nome`, `dt_competencia`)VALUES (?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getId());
            //F.setMsgErro(entidade.getId());
            ps.setString(2, entidade.getSub_grupo_procedimento().getId());
            ps.setString(3, entidade.getNome());
            ps.setInt(4, entidade.getDt_competencia());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Forma_organizacaoDAO 36! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Forma_organizacao atualizar(Forma_organizacao entidade) throws ErroSistema {
         Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `forma_organizacao` SET `sub_grupo_procedimento_id`=?,"
                    + "`nome`=? WHERE  `id`=? and `dt_competencia`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getSub_grupo_procedimento().getId());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getId());
            ps.setInt(4, entidade.getDt_competencia());
           
            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Forma_organizacaoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Forma_organizacao deletar(Forma_organizacao entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Forma_organizacao> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `sub_grupo_procedimento_id`, `nome`, `dt_competencia` FROM `forma_organizacao` " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Forma_organizacao> entidades = new ArrayList<>();

            while (rs.next()) {
                Forma_organizacao entidade = new Forma_organizacao();

                entidade.setId(rs.getString("id"));
                entidade.setNome(rs.getString("nome"));
                entidade.setSub_grupo_procedimento(new Sub_grupo_procedimento(rs.getString("sub_grupo_procedimento_id"), new Grupo_procedimento(), "", 0));
                entidade.setDt_competencia(rs.getInt("dt_competencia"));

                entidades.add(entidade);
            }
            
            return entidades;

        } catch (SQLException e) {
            F.setMsgErro("Forma_organizacaoDAO 77" + e.toString());
            throw new ErroSistema("Erro ao buscar Forma_organizacao", e);
        }
    }

    @Override
    public Forma_organizacao buscaId(String id) throws ErroSistema {
        Forma_organizacao c = null;
        List<Forma_organizacao> l = buscar("WHERE `id` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
