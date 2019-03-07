package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Tb_aux_id_form;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tb_aux_id_formDAO implements CrudDAO<Tb_aux_id_form> {

    @Override
    public Tb_aux_id_form salvar(Tb_aux_id_form entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `tb_aux_id_form`(`id`, `ano`) VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getId());
            ps.setInt(2, entidade.getAno());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Tb_aux_id_formDAO 28! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Tb_aux_id_form atualizar(Tb_aux_id_form entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `tb_aux_id_form` SET `id`=?,`ano`=? WHERE `tb_aux_id_form` = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getId());
            ps.setInt(2, entidade.getAno());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Tb_aux_id_formDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar Tb_aux_id_form", ex);

        }
    }

    @Override
    public Tb_aux_id_form deletar(Tb_aux_id_form entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM `tb_aux_id_form` WHERE 0");
            ps.setInt(1, entidade.getId());
            ps.setInt(2, entidade.getAno());
            ps.execute();

        } catch (SQLException ex) {
            entidade = null;
            throw new ErroSistema("Erro ao deletar o Tb_aux_id_form", ex);

        }
        return entidade;
    }

    @Override
    public List<Tb_aux_id_form> buscar(String condicao) throws ErroSistema {
        condicao =  F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id`, `ano` FROM `tb_aux_id_form`" + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tb_aux_id_form> tb_aux_id_forms = new ArrayList<>();

            while (rs.next()) {
                Tb_aux_id_form tb_aux_id_form = new Tb_aux_id_form();
                tb_aux_id_form.setId(rs.getInt("id"));
                tb_aux_id_form.setAno(rs.getInt("ano"));

                tb_aux_id_forms.add(tb_aux_id_form);
            }

            return tb_aux_id_forms;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do solicitante", e);
        }
    }

    public int buscarCont(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT COUNT(*) FROM `tb_aux_id_form`" + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                i = rs.getInt(1);
            }
            return i;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do solicitante", e);
        }
    }

    @Override
    public Tb_aux_id_form buscaId(String id) throws ErroSistema {
        Tb_aux_id_form c = null;
        List<Tb_aux_id_form> l = buscar("WHERE `id` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
