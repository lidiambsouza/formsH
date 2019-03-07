package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Auditoria;
import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Entidade;
import hujbb.informatica.apac.entidades.Log_procedimento;
import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Usuario;
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

public class AuditoriaDAO implements Serializable, CrudDAO<Auditoria> {

    @Override
    public Auditoria salvar(Auditoria entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {//, 

            String sql = "INSERT INTO `auditoria`( `data`,  `usuario_id_usuario`, `tipo_entidade_id`, `id_entidade_alvo`,`log_procedimento_id`,`campo_antigo`, `campo_novo`) VALUES (NOW(),'',?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // ps.setTimestamp(1, new Timestamp(entidade.getData().getTime()) );
            ps.setInt(1, entidade.getUsuario().getId_usuario());
            ps.setInt(2, entidade.getTipo_entidade_id().getId());
            ps.setInt(3, entidade.getId_entidade_alvo());
            ps.setInt(4, entidade.getLog_procedimento().getId());
            ps.setString(5, entidade.getCampo_antigo());
            ps.setString(6, entidade.getCampo_novo());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_auditoria(rs.getInt(1));
            }

        } catch (SQLException ex) {
            F.setMsgErro("auditoriaDAO 44! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Auditoria atualizar(Auditoria entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `auditoria` SET `data`=?,`procedimento_realizado`=?,`usuario_id_usuario`=?,"
                    + "`tipo_entidade_id`=?,`id_entidade_alvo`=?,`log_procedimento_id`=?,`campo_antigo`=?,`campo_novo`=? "
                    + "WHERE `id_auditoria`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, F.sqlDate(entidade.getData()));
            ps.setString(2, entidade.getAfetado());
            ps.setInt(3, entidade.getUsuario().getId_usuario());
            ps.setInt(4, entidade.getTipo_entidade_id().getId());
            ps.setInt(5, entidade.getId_entidade_alvo());
            ps.setInt(6, entidade.getLog_procedimento().getId());
            ps.setString(7, entidade.getCampo_antigo());
            ps.setString(8, entidade.getCampo_novo());
            ps.setInt(9, entidade.getId_auditoria());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! AuditoriaDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar o Log", ex);

        }
    }

    @Override
    public Auditoria deletar(Auditoria entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Auditoria> buscar(String condicao) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     auditoria.`id_auditoria` AS auditoria_id_auditoria,\n"
                    + "     auditoria.`data` AS auditoria_data,\n"
                    + "     auditoria.`procedimento_realizado` AS auditoria_procedimento_realizado,\n"
                    + "     auditoria.`id_entidade_alvo` AS auditoria_id_entidade_alvo,\n"
                    + "     auditoria.`campo_antigo` AS auditoria_campo_antigo,\n"
                    + "     auditoria.`campo_novo` AS auditoria_campo_novo,\n"
                    + "     entidade.`id` AS entidade_id,\n"
                    + "     entidade.`nome` AS entidade_nome,\n"
                    + "     log_procedimento.`id` AS log_procedimento_id,\n"
                    + "     log_procedimento.`descricao` AS log_procedimento_descricao,\n"
                    + "     usuario.`id_usuario` AS usuario_id_usuario,\n"
                    + "     usuario.`nome` AS usuario_nome,\n"
                    + "     usuario.`login` AS usuario_login,\n"
                    + "     usuario.`senha` AS usuario_senha,\n"
                    + "     usuario.`ativo` AS usuario_ativo,\n"
                    + "     usuario.`dt_cadastro` AS usuario_dt_cadastro,\n"
                    + "     cbo.`id` AS cbo_id,\n"
                    + "     cbo.`ocupacao` AS cbo_ocupacao,\n"
                    + "     setor.`id_setor` AS setor_id_setor,\n"
                    + "     setor.`nome` AS setor_nome,\n"
                    + "     setor.`sigla` AS setor_sigla,\n"
                    + "     perfil.`id_perfil` AS perfil_id_perfil,\n"
                    + "     perfil.`perfil` AS perfil_perfil\n"
                    + "FROM\n"
                    + "     `entidade` entidade INNER JOIN `auditoria` auditoria ON entidade.`id` = auditoria.`tipo_entidade_id`\n"
                    + "     INNER JOIN `log_procedimento` log_procedimento ON auditoria.`log_procedimento_id` = log_procedimento.`id`\n"
                    + "     INNER JOIN `usuario` usuario ON auditoria.`usuario_id_usuario` = usuario.`id_usuario`\n"
                    + "     INNER JOIN `cbo` cbo ON usuario.`cbo_id` = cbo.`id`\n"
                    + "     INNER JOIN `setor` setor ON usuario.`setor_id_setor` = setor.`id_setor`\n"
                    + "     INNER JOIN `perfil` perfil ON usuario.`perfil` = perfil.`id_perfil " + condicao + "ORDER BY `data` DESC LIMIT 100";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Auditoria> entidades = new ArrayList<>();

            while (rs.next()) {
                Auditoria entidade = new Auditoria(
                        rs.getInt("id_auditoria"),
                        rs.getDate("data"),
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
                                new Perfil(
                                        rs.getInt("perfil_id_perfil"),
                                        rs.getString("perfil_perfil")
                                ),
                                rs.getInt("usuario_ativo"),
                                new Cbo(
                                        rs.getString("cbo_id"),
                                        rs.getString("cbo_ocupacao")
                                ),
                                rs.getDate("usuario_dt_cadastro")
                        ),
                        new Entidade(
                                rs.getInt("id"),
                                rs.getString("nome")
                        ),
                        rs.getInt("id_entidade_alvo"),
                        new Log_procedimento(
                                rs.getInt("id"),
                                rs.getString("descricao")
                        ),
                        rs.getString("campo_antigo"),
                        rs.getString("campo_novo")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados da auditoria", e);
        }
    }

    @Override
    public Auditoria buscaId(String id) throws ErroSistema {
        Auditoria c = null;
        List<Auditoria> l = buscar("WHERE `id_auditoria` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
