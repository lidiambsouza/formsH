/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.entidades.Ativ_desativ_usuario;
import hujbb.informatica.apac.entidades.Motivo_ativ_desativ_usuario;
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

/**
 *
 * @author lidia.souza
 */
public class Ativ_desativ_usuarioDAO implements Serializable, CrudDAO<Ativ_desativ_usuario> {

    @Override
    public Ativ_desativ_usuario salvar(Ativ_desativ_usuario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `ativ_desativ_usuario`( `dt`, `motivo_desativ_usuario_id`, `usuario_id_usuario`,`obs`) VALUES (?,?,?,?)";//pedro
            PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, F.sqlDate(entidade.getDt_motivo()));
            ps.setInt(2, entidade.getMotivo().getId());
            ps.setInt(3, entidade.getUsuario().getId_usuario());
            ps.setString(4, entidade.getObs());//pedro

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            F.setMsgErro("ativ_desativ_usuarioDAO F:salvar! " + ex.toString());
            return null;
        }

        return entidade;
    }

    @Override
    public Ativ_desativ_usuario atualizar(Ativ_desativ_usuario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `ativ_desativ_usuario` SET `dt`=?,`motivo_desativ_usuario_id`=?,`usuario_id_usuario`=?,`obs`=? WHERE `id`=?";//pedro
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, F.sqlDate(entidade.getDt_motivo()));
            ps.setInt(2, entidade.getMotivo().getId());
            ps.setInt(3, entidade.getUsuario().getId_usuario());
            ps.setInt(4, entidade.getId());
            ps.setString(5, entidade.getObs());//pedro

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! ativ_desativ_usuarioDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar solicitante", ex);

        }
    }

    @Override
    public Ativ_desativ_usuario deletar(Ativ_desativ_usuario entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from ativ_desativ_usuario where WHERE `id`=?");
            ps.setInt(1, entidade.getId());
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o ativ_desativ_usuario!", ex);
        }
        return entidade;
    }

    @Override
    public List<Ativ_desativ_usuario> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     ativ_desativ_usuario.`id` AS ativ_desativ_usuario_id,\n"
                    + "     ativ_desativ_usuario.`dt` AS ativ_desativ_usuario_dt,\n"
                    + "     ativ_desativ_usuario.`obs` AS ativ_desativ_usuario_obs,\n"
                    + "     motivo_ativ_desativ_usuario.`id` AS motivo_ativ_desativ_usuario_id,\n"
                    + "     motivo_ativ_desativ_usuario.`motivo` AS motivo_ativ_desativ_usuario_motivo,\n"
                    + "     motivo_ativ_desativ_usuario.`flag` AS motivo_ativ_desativ_usuario_flag,\n"
                    + "     usuario.`id_usuario` AS usuario_id_usuario,\n"
                    + "     usuario.`nome` AS usuario_nome,\n"
                    + "     usuario.`login` AS usuario_login,\n"
                    + "     usuario.`senha` AS usuario_senha,\n"
                    + "     usuario.`perfil` AS usuario_perfil,\n"
                    + "     usuario.`ativo` AS usuario_ativo,\n"
                    + "     usuario.`dt_cadastro` AS usuario_dt_cadastro,\n"
                    + "     setor.`id_setor` AS setor_id_setor,\n"
                    + "     setor.`nome` AS setor_nome,\n"
                    + "     setor.`sigla` AS setor_sigla,\n"
                    + "     cbo.`id` AS cbo_id,\n"
                    + "     cbo.`ocupacao` AS cbo_ocupacao\n"
                    + "FROM\n"
                    + "     `motivo_ativ_desativ_usuario` motivo_ativ_desativ_usuario INNER JOIN `ativ_desativ_usuario` ativ_desativ_usuario ON motivo_ativ_desativ_usuario.`id` = ativ_desativ_usuario.`motivo_desativ_usuario_id`\n"
                    + "     INNER JOIN `usuario` usuario ON ativ_desativ_usuario.`usuario_id_usuario` = usuario.`id_usuario`\n"
                    + "     INNER JOIN `setor` setor ON usuario.`setor_id_setor` = setor.`id_setor`\n"
                    + "     INNER JOIN `cbo` cbo ON usuario.`cbo_id` = cbo.`id` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Ativ_desativ_usuario> entidades = new ArrayList<>();

            while (rs.next()) {
                Ativ_desativ_usuario entidade = new Ativ_desativ_usuario(
                        rs.getInt("ativ_desativ_usuario_id"),
                        rs.getDate("ativ_desativ_usuario_dt"),
                        rs.getString("ativ_desativ_usuario_obs"),
                        new Motivo_ativ_desativ_usuario(
                                rs.getInt("motivo_ativ_desativ_usuario_id"),
                                rs.getString("motivo_ativ_desativ_usuario_motivo"),
                                rs.getInt("motivo_ativ_desativ_usuario_flag")
                        ),
                        new Usuario(
                                rs.getInt("usuario_id_usuario"),
                                rs.getString("usuario_nome"),
                                rs.getString("usuario_login"),
                                rs.getString("usuario_senha"),
                                new Setor(
                                        rs.getInt("setor_id_setor"),
                                        rs.getString("setor_nome"),
                                        rs.getString("setor_sigla")
                                ),
                                new Perfil(rs.getInt("usuario_perfil"), ""),
                                rs.getInt("usuario_ativo"),
                                new Cbo(
                                        rs.getString("cbo_id"),
                                        rs.getString("cbo_ocupacao")
                                ),
                                rs.getDate("usuario_dt_cadastro")
                        )
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar ativ_desativ_usuario", e);
        }
    }

    @Override
    public Ativ_desativ_usuario buscaId(String id) throws ErroSistema {
        Ativ_desativ_usuario c = null;
        List<Ativ_desativ_usuario> l = buscar("WHERE `id`= " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
