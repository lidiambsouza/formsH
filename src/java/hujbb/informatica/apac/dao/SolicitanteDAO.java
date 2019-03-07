package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Solicitante;
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
import javax.faces.application.FacesMessage;

public class SolicitanteDAO implements Serializable, CrudDAO<Solicitante> {

    @Override
    public Solicitante salvar(Solicitante entidade) throws ErroSistema {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.buscar("usuario.`login` = '" + entidade.getUsuario().getLogin() + "'").size() < 1) {

            entidade.setUsuario(new UsuarioDAO().salvar(entidade.getUsuario()));//seta o usuario com o codigo gerado pelo banco

            Connection conexao = FabricaDeConexoes.getConexao();
            try {
                String sql = "INSERT INTO `solicitante`( `nome`, `tipo_doc`, `cns`,`cpf`,`usuario_id_usuario`) VALUES (?,?,?,?,?)";
                PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entidade.getNome());
                ps.setInt(2, 0);
                ps.setString(3, entidade.getCns());
                ps.setString(4, entidade.getCpf());
                ps.setInt(5, entidade.getUsuario().getId_usuario());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entidade.setId_solicitante(rs.getInt(1));
                }

            } catch (SQLException ex) {
                //  F.mensagem("Descricao do erro!", "solicitanteDAO F:salvar! " + ex.toString(), FacesMessage.SEVERITY_INFO);
                entidade = null;
            }
        }else{
            entidade =  null;
        }
    
        return entidade;
    }

    @Override
    public Solicitante atualizar(Solicitante entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `solicitante` SET `nome`=?,`tipo_doc`=?,`cns`=?,`cpf`=?,`usuario_id_usuario`=? WHERE `id_solicitante`=?";

            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setInt(2, 0);
            ps.setString(3, entidade.getCns());
            ps.setString(4, entidade.getCpf());
            ps.setInt(5, entidade.getUsuario().getId_usuario());
            ps.setInt(6, entidade.getId_solicitante());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao atualizar solicitante", ex);
        }

    }

    @Override
    public Solicitante deletar(Solicitante entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from solicitante where id_solicitante= ?");
            ps.setInt(1, entidade.getId_solicitante());
            ps.execute();
            new UsuarioDAO().deletar(entidade.getUsuario());
        } catch (SQLException ex) {
            entidade = null;
            throw new ErroSistema("Erro ao deletar o solicitante", ex);

        }
        return entidade;
    }

    @Override
    public List<Solicitante> buscar(String condicao) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     solicitante.`id_solicitante` AS solicitante_id_solicitante,\n"
                    + "     solicitante.`nome` AS solicitante_nome,\n"
                    + "     solicitante.`tipo_doc` AS solicitante_tipo_doc,\n"
                    + "     solicitante.`cns` AS solicitante_cns,\n"
                    + "     solicitante.`cpf` AS solicitante_cpf,\n"
                    + "     usuario.`id_usuario` AS usuario_id_usuario,\n"
                    + "     usuario.`nome` AS usuario_nome,\n"
                    + "     usuario.`login` AS usuario_login,\n"
                    + "     usuario.`senha` AS usuario_senha,\n"
                    + "     usuario.`ativo` AS usuario_ativo,\n"
                    + "     usuario.`dt_cadastro` AS usuario_dt_cadastro,\n"
                    + "     perfil.`id_perfil` AS perfil_id_perfil,\n"
                    + "     perfil.`perfil` AS perfil_perfil,\n"
                    + "     setor.`id_setor` AS setor_id_setor,\n"
                    + "     setor.`nome` AS setor_nome,\n"
                    + "     setor.`sigla` AS setor_sigla,\n"
                    + "     cbo.`id` AS cbo_id,\n"
                    + "     cbo.`ocupacao` AS cbo_ocupacao\n"
                    + "FROM\n"
                    + "     `usuario` usuario INNER JOIN `solicitante` solicitante ON usuario.`id_usuario` = solicitante.`usuario_id_usuario`\n"
                    + "     INNER JOIN `perfil` perfil ON usuario.`perfil` = perfil.`id_perfil`\n"
                    + "     INNER JOIN `setor` setor ON usuario.`setor_id_setor` = setor.`id_setor`\n"
                    + "     INNER JOIN `cbo` cbo ON usuario.`cbo_id` = cbo.`id` " + condicao;
            //  F.setMsgErro(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Solicitante> entidades = new ArrayList<>();

            while (rs.next()) {
                Solicitante entidade = new Solicitante(
                        rs.getInt("solicitante_id_solicitante"),
                        rs.getString("solicitante_nome"),
                        rs.getInt("solicitante_tipo_doc"),
                        rs.getString("solicitante_cns"),
                        rs.getString("solicitante_cpf"),
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
                        )
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            F.setMsgErro(e.toString() + " solicitanteDAO:buscar");
            throw new ErroSistema("Erro ao buscar dados do solicitante", e);
        }
    }

    @Override
    public Solicitante buscaId(String id) throws ErroSistema {
        Solicitante c = null;
        List<Solicitante> l = buscar("WHERE `id_solicitante` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
