package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Autorizacao;
import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Estabelecimento_de_saude;
import hujbb.informatica.apac.entidades.Formulario;
import hujbb.informatica.apac.entidades.Paciente;
import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.entidades.Proc_justificativa;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Solicitante;
import hujbb.informatica.apac.entidades.Status;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaLaudoDAO extends FormularioDAO {

    public List<Formulario> buscarLaudos(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        if (!condicao.contains("ORDER BY")) {
            condicao += " ORDER BY formulario.`id_formulario` DESC ";
        }
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     formulario.`id_formulario` AS formulario_id_formulario,\n"
                    + "     formulario.`data` AS formulario_data,\n"
                    + "     paciente.`id_paciente` AS paciente_id_paciente,\n"
                    + "     paciente.`num_prontuario` AS paciente_num_prontuario,\n"
                    + "     paciente.`nome` AS paciente_nome,\n"
                    + "     paciente.`cns` AS paciente_cns,\n"
                    + "     solicitante.`id_solicitante` AS solicitante_id_solicitante,\n"
                    + "     solicitante.`nome` AS solicitante_nome,\n"
                    + "     solicitante.`usuario_id_usuario` AS solicitante_usuario_id_usuario,\n"
                    + "     status.`id_status` AS status_id_status,\n"
                    + "     status.`status` AS status_status\n"
                    + "FROM\n"
                    + "     `paciente` paciente INNER JOIN `formulario` formulario ON paciente.`id_paciente` = formulario.`paciente_id_paciente`\n"
                    + "     INNER JOIN `solicitante` solicitante ON formulario.`solicitante_id_solicitante` = solicitante.`id_solicitante`\n"
                    + "     INNER JOIN `status` status ON formulario.`status_id_status` = status.`id_status` " + condicao;

            // System.out.println(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Formulario> entidades = new ArrayList<>();

            while (rs.next()) {

                Formulario entidade = new Formulario(
                        rs.getInt("formulario_id_formulario"),
                        rs.getDate("formulario_data"),
                        "",//formulario_autenticacao
                        new Paciente(
                                rs.getInt("paciente_id_paciente"),
                                rs.getString("paciente_num_prontuario"),
                                rs.getString("paciente_nome"),
                                "",//paciente_sexo
                                rs.getString("paciente_cns"),
                                null,//paciente_data_nascimento
                                "",//paciente_cor
                                "",//paciente_etnia
                                "",//paciente_nome_mae
                                "",//paciente_telefone_mae
                                "",//paciente_nome_responsavel
                                "",//paciente_telefone_responsavel
                                0f,//paciente_peso
                                0f,//paciente_altura
                                "",//paciente_logradouro
                                "",//paciente_num_residencia
                                "",//paciente_bairro
                                "",//paciente_municipio
                                "",//paciente_cod_ibge_municipio
                                "",//paciente_uf
                                "",//paciente_cep
                                null//dt obito
                        ),
                        new Solicitante(
                                rs.getInt("solicitante_id_solicitante"),
                                rs.getString("solicitante_nome"),
                                0,//solicitante_tipo_doc
                                "",//solicitante_cns
                                "",//solicitante_cpf
                                new Usuario(
                                        rs.getInt("solicitante_usuario_id_usuario"),
                                        "",//usuario_nome
                                        "",//usuario_login
                                        "",//usuario_senha
                                        new Setor(),
                                        new Perfil(),
                                        0,//usuario_ativo
                                        new Cbo( ),
                                        null//usuario_dt_cadastro
                                )
                        ),
                        new Estabelecimento_de_saude(),
                        new Estabelecimento_de_saude(),
                        new Proc_justificativa(),
                        new Status(
                                rs.getInt("status_id_status"),
                                rs.getString("status_status")
                        ),
                        new Autorizacao()
                );
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            F.setMsgErro(e.toString() + " consultaLaudoDAO - buscarLaudos");
            throw new ErroSistema("consultaLaudoDAO - buscarLaudos", e);
        }
    }

}
