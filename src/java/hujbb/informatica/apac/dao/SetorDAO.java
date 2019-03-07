package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO implements CrudDAO<Setor> {

    @Override
    public Setor salvar(Setor entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Setor atualizar(Setor entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `setor` SET `nome`=?,`sigla`=? WHERE `id_setor`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getSigla());
            ps.setInt(3, entidade.getId_setor());
            

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! SetorDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    
    }

    @Override
    public Setor deletar(Setor entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Setor> buscar(String condicao) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id_setor`, `nome`, `sigla` FROM `setor` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Setor> setores = new ArrayList<>();

            while (rs.next()) {
                Setor setor = new Setor();
                setor.setId_setor(rs.getInt("id_setor"));
                setor.setNome(rs.getString("nome"));
                setor.setSigla(rs.getString("sigla"));

                setores.add(setor);
            }
            
            return setores;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do solicitante", e);
        }
    }

    @Override
    public Setor buscaId(String id) throws ErroSistema {
        Setor c = null;
        List<Setor> l = buscar("WHERE `id_setor` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }


}
