package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Formulario_has_procedimento_sus;
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

public class Formulario_has_procedimento_susDAO implements Serializable, CrudDAO<Formulario_has_procedimento_sus> {

    @Override
    public Formulario_has_procedimento_sus salvar(Formulario_has_procedimento_sus entidade) throws ErroSistema {

        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO `formulario_has_procedimento_sus`( `formulario_id_formulario`, `procedimento_sus_codigo`,`procedimento_sus_dt_competencia`, `quantidade`,`posicao`) VALUES (?,?,?,?,?)");
            ps.setInt(1, entidade.getFormulario().getId_formulario());
            ps.setString(2, entidade.getProcedimento_sus().getCodigo());
            ps.setInt(3, entidade.getProcedimento_sus().getDt_competencia());
            ps.setInt(4, entidade.getQuantidade());
            ps.setInt(5, entidade.getPosicao());

            ps.execute();

        } catch (SQLException ex) {
            F.setMsgErro("formulario_has_procedimentosDAO 29! " + ex.toString());
            entidade = null;

        }

        return entidade;

    }

    @Override
    public Formulario_has_procedimento_sus atualizar(Formulario_has_procedimento_sus entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `formulario_has_procedimento_sus` SET `quantidade`=?"
                    + " WHERE `formulario_id_formulario`=? and "
                    + "`procedimento_sus_codigo`=? and `procedimento_sus_dt_competencia`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, entidade.getQuantidade());
            ps.setInt(2, entidade.getFormulario().getId_formulario());
            ps.setString(3, entidade.getProcedimento_sus().getCodigo());
            ps.setInt(4, entidade.getProcedimento_sus().getDt_competencia());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Formulario_has_procedimento_susDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Formulario_has_procedimento_sus deletar(Formulario_has_procedimento_sus entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean deletarTodosDoFomr(int idForm) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "DELETE FROM `formulario_has_procedimento_sus` WHERE `formulario_id_formulario` = '" + idForm + "'";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
        } catch (SQLException ex) {
            F.setMsgErro("Erro ao deletar o ativ_desativ_usuario! " + ex.toString());
            return false;
        }
        return true;
    }

    @Override
    public List<Formulario_has_procedimento_sus> buscar(String condicao) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT "
                    + "`formulario_id_formulario`,"
                    + " `procedimento_sus_codigo`,"
                    + " `procedimento_sus_dt_competencia`,"
                    + " `quantidade`,"
                    + " `posicao`"
                    + " FROM `formulario_has_procedimento_sus` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Formulario_has_procedimento_sus> list = new ArrayList<>();

            while (rs.next()) {
                Formulario_has_procedimento_sus formulario_has_procedimento_sus = new Formulario_has_procedimento_sus();

                formulario_has_procedimento_sus.getFormulario().setId_formulario(rs.getInt("formulario_id_formulario"));
                formulario_has_procedimento_sus.getProcedimento_sus().setCodigo(rs.getString("procedimento_sus_codigo"));
                formulario_has_procedimento_sus.getProcedimento_sus().setDt_competencia(rs.getInt("procedimento_sus_dt_competencia"));
                formulario_has_procedimento_sus.setQuantidade(rs.getInt("quantidade"));
                formulario_has_procedimento_sus.setPosicao(rs.getInt("posicao"));

                list.add(formulario_has_procedimento_sus);
            }

            return list;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados!", e);
        }
    }

    @Override
    public Formulario_has_procedimento_sus buscaId(String id) throws ErroSistema {
        Formulario_has_procedimento_sus c = null;
        List<Formulario_has_procedimento_sus> l = buscar("WHERE `formulario_id_formulario` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
