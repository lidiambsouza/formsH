package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Cbo;
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

public class UsuarioDAO implements Serializable, CrudDAO<Usuario> {

    @Override
    public Usuario salvar(Usuario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `usuario`( `nome`, `login`, `senha`, `perfil`, `ativo`, `setor_id_setor`, `cbo_id`, `dt_cadastro`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getLogin());
            ps.setString(3, entidade.getSenha());
            ps.setInt(4, entidade.getPerfil().getId_perfil());
            ps.setInt(5, entidade.getAtivo());
            ps.setInt(6, entidade.getSetor().getId_setor());
            ps.setString(7, entidade.getCbo().getCod());
            ps.setDate(8, F.sqlDate(entidade.getDt_cadastro()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_usuario(rs.getInt(1));
            }

        } catch (SQLException ex) {
            F.setMsgErro("usuarioDAO F:salvar! " + ex.toString());
        }

        return entidade;
    }

    @Override
    public Usuario atualizar(Usuario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `usuario` SET `nome`=?,`login`=?,`senha`=?,"
                    + "`perfil`=?,`ativo`=?,`setor_id_setor`=?,`cbo_id`=?,`dt_cadastro`=? WHERE `id_usuario`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getLogin());
            ps.setString(3, entidade.getSenha());
            ps.setInt(4, entidade.getPerfil().getId_perfil());
            ps.setInt(5, entidade.getAtivo());
            ps.setInt(6, entidade.getSetor().getId_setor());
            ps.setString(7, entidade.getCbo().getCod());
            ps.setDate(8, F.sqlDate(entidade.getDt_cadastro()));

            ps.setInt(9, entidade.getId_usuario());

            ps.execute();

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! usuarioDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar solicitante", ex);

        }
    }

    @Override
    public Usuario deletar(Usuario entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from usuario where id_usuario= ?");
            ps.setInt(1, entidade.getId_usuario());
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o usuário!", ex);
        }
        return entidade;
    }

    @Override
    public List<Usuario> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
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
                    + "     `perfil` perfil INNER JOIN `usuario` usuario ON perfil.`id_perfil` = usuario.`perfil`\n"
                    + "     INNER JOIN `setor` setor ON usuario.`setor_id_setor` = setor.`id_setor`\n"
                    + "     INNER JOIN `cbo` cbo ON usuario.`cbo_id` = cbo.`id` " + condicao;

           
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Usuario> entidades = new ArrayList<>();

            while (rs.next()) {
                
                Usuario entidade = new Usuario(
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
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            F.setMsgErro(e.toString()+"usuario dao - buscar()");
            throw new ErroSistema("Erro ao buscar dados do usuário", e);
        }
    }

    @Override
    public Usuario buscaId(String id) throws ErroSistema {
        Usuario c = null;
        List<Usuario> l = buscar("WHERE `id_usuario` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
