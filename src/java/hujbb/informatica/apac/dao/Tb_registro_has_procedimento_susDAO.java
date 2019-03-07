package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Tb_registro_has_procedimento_sus;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tb_registro_has_procedimento_susDAO implements CrudDAO<Tb_registro_has_procedimento_sus> {

    @Override
    public Tb_registro_has_procedimento_sus salvar(Tb_registro_has_procedimento_sus entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `tb_registro_has_procedimento_sus`(`tb_registro_id`, `procedimento_sus_codigo`, `dt_competencia`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql/*, Statement.RETURN_GENERATED_KEYS*/);
            ps.setInt(1, entidade.getTb_registro_id());
            ps.setString(2, entidade.getProcedimento_sus_codigo());
            ps.setInt(3, entidade.getDt_competencia());

            ps.executeUpdate();

            //comentei pq nao é auto incremento
            /* ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }*/
        } catch (SQLException ex) {
            F.setMsgErro("Tb_registro_has_procedimento_susDAO 35! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Tb_registro_has_procedimento_sus atualizar(Tb_registro_has_procedimento_sus entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tb_registro_has_procedimento_sus deletar(Tb_registro_has_procedimento_sus entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tb_registro_has_procedimento_sus> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `tb_registro_id`, `procedimento_sus_codigo`, `dt_competencia` FROM `tb_registro_has_procedimento_sus` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tb_registro_has_procedimento_sus> entidades = new ArrayList<>();

            while (rs.next()) {
                Tb_registro_has_procedimento_sus entidade = new Tb_registro_has_procedimento_sus(
                        rs.getInt("tb_registro_id"),
                        rs.getString("procedimento_sus_codigo"),
                        rs.getInt("dt_competencia")
                );
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do Tb_registro_has_procedimento_sus", e);
        }
    }

    @Override
    public Tb_registro_has_procedimento_sus buscaId(String id) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
