package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Tp_sexo;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidDAO implements CrudDAO<Cid> ,Serializable {

    @Override
    public Cid salvar(Cid entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `cid`(`cid`, `nome`, `tp_sexo_id`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getCid());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getTp_sexo().getId().toLowerCase());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("CidDAO 27! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Cid atualizar(Cid entidade) throws ErroSistema {
         Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `cid` SET `nome`=?,`tp_sexo_id`=? WHERE `cid`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getTp_sexo().getId());
            ps.setString(3, entidade.getCid());
           

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! CidDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar Cid", ex);

        }
    }

    @Override
    public Cid deletar(Cid entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cid> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = " \n"
                    + "        SELECT\n"
                    + "     cid.`cid` AS cid_cid,\n"
                    + "     cid.`nome` AS cid_nome,\n"
                    + "     tp_sexo.`id` AS tp_sexo_id,\n"
                    + "     tp_sexo.`sexo` AS tp_sexo_sexo\n"
                    + "FROM\n"
                    + "     `tp_sexo` tp_sexo INNER JOIN `cid` cid ON tp_sexo.`id` = cid.`tp_sexo_id` " + condicao;
         //   System.out.println(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Cid> entidades = new ArrayList<>();

            while (rs.next()) {
                Cid entidade = new Cid(
                        rs.getString("cid_cid"),
                        rs.getString("cid_nome"),
                        new Tp_sexo(
                                rs.getString("tp_sexo_id"),
                                rs.getString("tp_sexo_sexo")
                        )
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Cid", e);
        }

    }

    @Override
    public Cid buscaId(String id) throws ErroSistema {
        Cid c = null;
        List<Cid> l = buscar("WHERE cid.`cid` = '" + id+"'");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }
}
