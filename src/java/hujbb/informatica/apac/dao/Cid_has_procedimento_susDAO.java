package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Cid_has_procedimento_sus;
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

public class Cid_has_procedimento_susDAO implements CrudDAO<Cid_has_procedimento_sus> {

    @Override
    public Cid_has_procedimento_sus salvar(Cid_has_procedimento_sus entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `cid_has_procedimento_sus`(`cid_cid`, `procedimento_sus_codigo`, `cid_principal`, `dt_competencia`) VALUES (?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getCod_cid());
            //F.setMsgErro(entidade.getCod_cid().toString);
            ps.setString(2, entidade.getCod_procedimento());
            ps.setBoolean(3, entidade.isPrincipal());
            ps.setInt(4, entidade.getDt_competencia());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Cid_has_procedimento_susDAO 35! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Cid_has_procedimento_sus atualizar(Cid_has_procedimento_sus entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cid_has_procedimento_sus deletar(Cid_has_procedimento_sus entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cid_has_procedimento_sus> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `cid_cid`, `procedimento_sus_codigo`, `cid_principal`, `dt_competencia` FROM `cid_has_procedimento_sus`  " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Cid_has_procedimento_sus> entidades = new ArrayList<>();

            while (rs.next()) {
                Cid_has_procedimento_sus entidade = new Cid_has_procedimento_sus(
                        rs.getString("cid_cid"),
                        rs.getString("procedimento_sus_codigo"),
                        false,
                        rs.getInt("dt_competencia")
                );
                if (rs.getInt("cid_principal") == 1) {
                    entidade.setPrincipal(true);
                }
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            F.setMsgErro("Erro ao buscar dados do Cid_has_procedimento_sus:" + e);
           
            System.out.println("teste git");
            return null;
        }
    }

    public List<Cid> buscarCids(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     cid.`cid` AS cid_cid,\n"
                    + "     cid.`nome` AS cid_nome,\n"
                    + "     cid.`tp_sexo_id` AS cid_tp_sexo_id\n"
                    + "FROM\n"
                    + "     `cid` cid INNER JOIN `cid_has_procedimento_sus` cid_has_procedimento_sus ON cid.`cid` = cid_has_procedimento_sus.`cid_cid`  " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Cid> cids = new ArrayList<>();

            while (rs.next()) {
                Cid cid = new Cid(
                        rs.getString("cid_cid"),
                        rs.getString("cid_nome"),
                        new Tp_sexo()
                );
                cid.getTp_sexo().setId(rs.getString("cid_tp_sexo_id"));
                
                cids.add(cid);
            }

            return cids;

        } catch (SQLException e) {
            F.setMsgErro("cid_has_procedimentoDAO:busca cid:" + e);
            return null;
        }
    }

    @Override
    public Cid_has_procedimento_sus buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
