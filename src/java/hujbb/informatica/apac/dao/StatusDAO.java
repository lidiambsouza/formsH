package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Status;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO implements Serializable,CrudDAO<Status> {

    @Override
    public Status salvar(Status entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `status`( `status`) VALUES (?)";
            PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getStatus());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_status(rs.getInt(1));
            }
        } catch (SQLException ex) {
            F.setMsgErro("statusDAO 34! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Status atualizar(Status entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `status` SET `status`=? WHERE `id_status`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getStatus());
            ps.setInt(2, entidade.getId_status());
            

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! StatusDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Status deletar(Status entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Status> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id_status`, `status` FROM `status` " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Status> listStatus = new ArrayList<>();

            while (rs.next()) {
                Status status = new Status();

                status.setId_status(rs.getInt("id_status"));
                status.setStatus(rs.getString("status"));

                listStatus.add(status);
            }
            
            return listStatus;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar status", e);
        }
    }

    @Override
    public Status buscaId(String id) throws ErroSistema {
        Status c = null;
        List<Status> l = buscar("WHERE `id_status` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

   
}
