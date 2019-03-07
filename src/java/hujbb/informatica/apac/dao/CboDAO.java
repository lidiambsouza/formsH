package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CboDAO implements CrudDAO<Cbo> {

    @Override
    public Cbo salvar(Cbo entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `cbo`(`id`, `ocupacao`)  VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getCod());
            ps.setString(2, entidade.getNome());

            ps.executeUpdate();

            

        } catch (SQLException ex) {
            F.setMsgErro("CboDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Cbo atualizar(Cbo entidade) throws ErroSistema {
         Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `cbo` SET `ocupacao`=? WHERE `id`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getCod());
           
            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! CbDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar CBO", ex);

        }
    }

    @Override
    public Cbo deletar(Cbo entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cbo> buscar(String condicao) throws ErroSistema {
     condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `ocupacao` FROM `cbo`  " + condicao;
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Cbo> entidades = new ArrayList<>();

            while (rs.next()) {
                Cbo entidade = new Cbo();

                entidade.setCod(rs.getString("id"));
                entidade.setNome(rs.getString("ocupacao"));

                entidades.add(entidade);
            }
            
            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar CBO", e);
        }
    }

    @Override
    public Cbo buscaId(String id) throws ErroSistema {
         Cbo c = null;
        List<Cbo> l = buscar("WHERE id = '" + id+"'");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
