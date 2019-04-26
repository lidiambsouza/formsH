package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Autorizacao;
import hujbb.informatica.apac.entidades.Cbo;
import hujbb.informatica.apac.entidades.Cid;
import hujbb.informatica.apac.entidades.Estabelecimento_de_saude;
import hujbb.informatica.apac.entidades.Formulario;
import hujbb.informatica.apac.entidades.Formulario_f2;
import hujbb.informatica.apac.entidades.Formulario_has_procedimento_sus;
import hujbb.informatica.apac.entidades.Motivo_Cancelamento;
import hujbb.informatica.apac.entidades.Paciente;
import hujbb.informatica.apac.entidades.Perfil;
import hujbb.informatica.apac.entidades.Proc_justificativa;
import hujbb.informatica.apac.entidades.Setor;
import hujbb.informatica.apac.entidades.Solicitante;
import hujbb.informatica.apac.entidades.Status;
import hujbb.informatica.apac.entidades.Tb_aux_id_form;
import hujbb.informatica.apac.entidades.Tp_sexo;
import hujbb.informatica.apac.entidades.Usuario;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;

public class FormularioDAO implements Serializable, CrudDAO<Formulario> {

    @Override
    public Formulario salvar(Formulario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();

        entidade.setPaciente(new PacienteDAO().salvar(entidade.getPaciente()));
        entidade.setProc_justificativa(new Proc_justificativaDAO().salvar(entidade.getProc_justificativa()));

        //saber quantos formularios tem em um determinado ano
        Tb_aux_id_formDAO tb_aux_id_formDAO = new Tb_aux_id_formDAO();
        tb_aux_id_formDAO.salvar(new Tb_aux_id_form(0, F.anoDaData(entidade.getData())));
        int i = tb_aux_id_formDAO.buscarCont("`ano` = " + F.anoDaData(entidade.getData()));
        entidade.setId_formulario(Integer.parseInt(F.anoDaData(entidade.getData()) + "" + (i)));

        try {
            String sql = "INSERT INTO `formulario`( `id_formulario`, `data`, `data_criacao`, `autenticacao`,  `paciente_id_paciente`, `solicitante_id_solicitante`,`estabelecimento_de_saude_id_solicitante`,  `estabelecimento_de_saude_id_executante`, `proc_justificativa_id`, `status_id_status`, `autorizacao_id_autorizacao`, `motivo_cancelamento_id`) VALUES (?,?,?,?,?,?,?,-1,?,?,-1,?)";

            PreparedStatement ps = conexao.prepareStatement(sql);

            //tratar para incluir data e hora
            ps.setInt(1, entidade.getId_formulario());
            ps.setTimestamp(2, new Timestamp(entidade.getData().getTime()));
            ps.setTimestamp(3, new Timestamp(new Date().getTime()));
            ps.setString(4, entidade.getAutenticacao());
            ps.setInt(5, entidade.getPaciente().getId_paciente());
            ps.setInt(6, entidade.getSolicitante().getId_solicitante());
            ps.setInt(7, entidade.getEstabelecimento_de_saude_solicitante().getId_estabelecimento_saude());
            ps.setInt(8, entidade.getProc_justificativa().getId_proc_justificativa());
            ps.setInt(9, entidade.getStatus().getId_status());
            ps.setInt(10, entidade.getMotivo_cancelamento().getId());

            ps.executeUpdate();

        } catch (SQLException ex) {
            new PacienteDAO().deletar(entidade.getPaciente());
            new Proc_justificativaDAO().deletar(entidade.getProc_justificativa());
            F.setMsgErro("formularioDAO 57! " + ex.toString());
            F.mensagem("", "Falha ao salvar o formulário!", FacesMessage.SEVERITY_ERROR);
        }

        return entidade;
    }

    @Override
    public Formulario atualizar(Formulario entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `formulario` SET "
                    + "`data`=?,"
                    + "`autenticacao`=?,"
                    + "`paciente_id_paciente`=?,"
                    + "`solicitante_id_solicitante`=?,"
                    + "`estabelecimento_de_saude_id_solicitante`=?,"
                    + "`estabelecimento_de_saude_id_executante`=?,"
                    + "`proc_justificativa_id`=?,"
                    + "`status_id_status`=?,"
                    + "`autorizacao_id_autorizacao`=?, "
                    + "`motivo_cancelamento_id`=? "
                    + "WHERE `id_formulario`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setDate(1, F.sqlDate(entidade.getData()));
            ps.setString(2, entidade.getAutenticacao());
            ps.setInt(3, entidade.getPaciente().getId_paciente());
            ps.setInt(4, entidade.getSolicitante().getId_solicitante());
            ps.setInt(5, entidade.getEstabelecimento_de_saude_solicitante().getId_estabelecimento_saude());
            ps.setInt(6, entidade.getEstabelecimento_de_saude_executante().getId_estabelecimento_saude());
            ps.setInt(7, entidade.getProc_justificativa().getId_proc_justificativa());
            ps.setInt(8, entidade.getStatus().getId_status());
            ps.setInt(9, entidade.getAutorizacao().getId_autorizacao());
            ps.setInt(10, entidade.getMotivo_cancelamento().getId());
            ps.setInt(11, entidade.getId_formulario());

            ps.execute();
            //atualiza a folha 2
            if (!entidade.getPag2().equals(new Formulario_f2())) {

                Formulario_f2 pag2 = new Formulario_f2DAO().atualizar(entidade.getPag2());
                if (pag2 == null) {
                    return null;
                }
                entidade.setPag2(pag2);
            }

            //atualiza os cids
            new Proc_justificativaDAO().atualizar(entidade.getProc_justificativa());

            //atualiza os prodedimentos
            atualizarProcedimentosForm(entidade);

            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! formularioDAO atualizar!" + ex.toString());
            F.mensagem("Erro! formularioDAO atualizar!" + ex.toString(), "", FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }

    private boolean atualizarProcedimentosForm(Formulario entidade) throws ErroSistema {
        Formulario_has_procedimento_susDAO fhpd = new Formulario_has_procedimento_susDAO();

        //deleta todos os forms do do formulario
        if (fhpd.deletarTodosDoFomr(entidade.getId_formulario())) {//se for verdadeiro entao conseguiu excluir todos os procedimentos  if0
            fhpd.salvar(new Formulario_has_procedimento_sus(entidade, entidade.getP1(), entidade.getP1().getQtd(), 1));
            if (!entidade.getP2().getCodigo().isEmpty()) {//if2 

                fhpd.salvar(new Formulario_has_procedimento_sus(entidade, entidade.getP2(), entidade.getP2().getQtd(), 2));
            }//if2
            if (!entidade.getP3().getCodigo().isEmpty()) {//if3
                fhpd.salvar(new Formulario_has_procedimento_sus(entidade, entidade.getP3(), entidade.getP3().getQtd(), 3));
            }//if3
            if (!entidade.getP4().getCodigo().isEmpty()) {//if4
                fhpd.salvar(new Formulario_has_procedimento_sus(entidade, entidade.getP4(), entidade.getP4().getQtd(), 4));
            }//if4
            if (!entidade.getP5().getCodigo().isEmpty()) {//if5
                fhpd.salvar(new Formulario_has_procedimento_sus(entidade, entidade.getP5(), entidade.getP5().getQtd(), 5));
            }//if5
            if (!entidade.getP6().getCodigo().isEmpty()) {//if6
                fhpd.salvar(new Formulario_has_procedimento_sus(entidade, entidade.getP6(), entidade.getP6().getQtd(), 6));
            }//if6

        } else {//if0
            return false;
        }
        return true;
    }

    @Override
    public Formulario deletar(Formulario entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Formulario> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);

        if (!condicao.contains("ORDER BY")) {
            condicao += " ORDER BY formulario.`id_formulario` DESC ";
        }
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     formulario.`id_formulario` AS formulario_id_formulario,\n"
                    + "     formulario.`data` AS formulario_data,\n"
                    + "     formulario.`data_criacao` AS formulario_data_criacao,\n"
                    + "     formulario.`autenticacao` AS formulario_autenticacao,\n"
                    + "     paciente.`id_paciente` AS paciente_id_paciente,\n"
                    + "     paciente.`num_prontuario` AS paciente_num_prontuario,\n"
                    + "     paciente.`nome` AS paciente_nome,\n"
                    + "     paciente.`sexo` AS paciente_sexo,\n"
                    + "     paciente.`cns` AS paciente_cns,\n"
                    + "     paciente.`data_nascimento` AS paciente_data_nascimento,\n"
                    + "     paciente.`cor` AS paciente_cor,\n"
                    + "     paciente.`etnia` AS paciente_etnia,\n"
                    + "     paciente.`nome_mae` AS paciente_nome_mae,\n"
                    + "     paciente.`telefone_mae` AS paciente_telefone_mae,\n"
                    + "     paciente.`nome_responsavel` AS paciente_nome_responsavel,\n"
                    + "     paciente.`telefone_responsavel` AS paciente_telefone_responsavel,\n"
                    + "     paciente.`peso` AS paciente_peso,\n"
                    + "     paciente.`altura` AS paciente_altura,\n"
                    + "     paciente.`logradouro` AS paciente_logradouro,\n"
                    + "     paciente.`num_residencia` AS paciente_num_residencia,\n"
                    + "     paciente.`bairro` AS paciente_bairro,\n"
                    + "     paciente.`municipio` AS paciente_municipio,\n"
                    + "     paciente.`cod_ibge_municipio` AS paciente_cod_ibge_municipio,\n"
                    + "     paciente.`uf` AS paciente_uf,\n"
                    + "     paciente.`cep` AS paciente_cep,\n"
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
                    + "     setor.`id_setor` AS setor_id_setor,\n"
                    + "     setor.`nome` AS setor_nome,\n"
                    + "     setor.`sigla` AS setor_sigla,\n"
                    + "     cbo.`id` AS cbo_id,\n"
                    + "     cbo.`ocupacao` AS cbo_ocupacao,\n"
                    + "     proc_justificativa.`id_proc_justificativa` AS proc_justificativa_id_proc_justificativa,\n"
                    + "     proc_justificativa.`observacoes` AS proc_justificativa_observacoes,\n"
                    + "     proc_justificativa.`cid_cid_principal` AS proc_justificativa_cid_cid_principal,\n"
                    + "     proc_justificativa.`cid_cid_secundario` AS proc_justificativa_cid_cid_secundario,\n"
                    + "     proc_justificativa.`cid_cid_causas_assoc` AS proc_justificativa_cid_cid_causas_assoc,\n"
                    + "     cid.`cid` AS cid_cid,\n"
                    + "     cid.`nome` AS cid_nome,\n"
                    + "     cid_A.`cid` AS cid_A_cid,\n"
                    + "     cid_A.`nome` AS cid_A_nome,\n"
                    + "     cid_B.`cid` AS cid_B_cid,\n"
                    + "     cid_B.`nome` AS cid_B_nome,\n"
                    + "     estabelecimento_de_saude.`id_estabelecimento_de_saude` AS estabelecimento_de_saude_id_estabelecimento_de_saude,\n"
                    + "     estabelecimento_de_saude.`cnes` AS estabelecimento_de_saude_cnes,\n"
                    + "     estabelecimento_de_saude.`nome` AS estabelecimento_de_saude_nome,\n"
                    + "     estabelecimento_de_saude_A.`id_estabelecimento_de_saude` AS estabelecimento_de_saude_A_id_estabelecimento_de_saude,\n"
                    + "     estabelecimento_de_saude_A.`cnes` AS estabelecimento_de_saude_A_cnes,\n"
                    + "     estabelecimento_de_saude_A.`nome` AS estabelecimento_de_saude_A_nome,\n"
                    + "     status.`id_status` AS status_id_status,\n"
                    + "     status.`status` AS status_status,\n"
                    + "     autorizacao.`id_autorizacao` AS autorizacao_id_autorizacao,\n"
                    + "     autorizacao.`autorizador` AS autorizacao_autorizador,\n"
                    + "     autorizacao.`cod_org_emissor` AS autorizacao_cod_org_emissor,\n"
                    + "     autorizacao.`tipo_doc` AS autorizacao_tipo_doc,\n"
                    + "     autorizacao.`num_doc` AS autorizacao_num_doc,\n"
                    + "     autorizacao.`data` AS autorizacao_data,\n"
                    + "     autorizacao.`num_autorizacao` AS autorizacao_num_autorizacao,\n"
                    + "     autorizacao.`data_val_ini` AS autorizacao_data_val_ini,\n"
                    + "     autorizacao.`data_val_fim` AS autorizacao_data_val_fim,\n"
                    + "     motivo_cancelamento.id AS motivo_cancelamento_id,\n"
                    + "     motivo_cancelamento.motivo AS motivo_cancelamento_motivo,\n"
                    + "     tp_sexo.`id` AS tp_sexo_id,\n"
                    + "     tp_sexo.`sexo` AS tp_sexo_sexo,\n"
                    + "     tp_sexo_A.`id` AS tp_sexo_A_id,\n"
                    + "     tp_sexo_A.`sexo` AS tp_sexo_A_sexo,\n"
                    + "     tp_sexo_B.`id` AS tp_sexo_B_id,\n"
                    + "     tp_sexo_B.`sexo` AS tp_sexo_B_sexo,\n"
                    + "     perfil.`id_perfil` AS perfil_id_perfil,\n"
                    + "     perfil.`perfil` AS perfil_perfil\n"
                    + "FROM\n"
                    + "     `paciente` paciente INNER JOIN `formulario` formulario ON paciente.`id_paciente` = formulario.`paciente_id_paciente`\n"
                    + "     INNER JOIN `solicitante` solicitante ON formulario.`solicitante_id_solicitante` = solicitante.`id_solicitante`\n"
                    + "     INNER JOIN `proc_justificativa` proc_justificativa ON formulario.`proc_justificativa_id` = proc_justificativa.`id_proc_justificativa`\n"
                    + "     INNER JOIN `estabelecimento_de_saude` estabelecimento_de_saude ON formulario.`estabelecimento_de_saude_id_solicitante` = estabelecimento_de_saude.`id_estabelecimento_de_saude`\n"
                    + "     INNER JOIN `estabelecimento_de_saude` estabelecimento_de_saude_A ON formulario.`estabelecimento_de_saude_id_executante` = estabelecimento_de_saude_A.`id_estabelecimento_de_saude`\n"
                    + "     INNER JOIN `status` status ON formulario.`status_id_status` = status.`id_status`\n"
                    + "     INNER JOIN `autorizacao` autorizacao ON formulario.`autorizacao_id_autorizacao` = autorizacao.`id_autorizacao`\n"
                    + "     LEFT  JOIN `motivo_cancelamento` motivo_cancelamento ON formulario.motivo_cancelamento_id = motivo_cancelamento.id \n"
                    + "     INNER JOIN `cid` cid ON proc_justificativa.`cid_cid_principal` = cid.`cid`\n"
                    + "     INNER JOIN `cid` cid_A ON proc_justificativa.`cid_cid_secundario` = cid_A.`cid`\n"
                    + "     INNER JOIN `cid` cid_B ON proc_justificativa.`cid_cid_causas_assoc` = cid_B.`cid`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo_B ON cid_B.`tp_sexo_id` = tp_sexo_B.`id`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo_A ON cid_A.`tp_sexo_id` = tp_sexo_A.`id`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo ON cid.`tp_sexo_id` = tp_sexo.`id`\n"
                    + "     INNER JOIN `usuario` usuario ON solicitante.`usuario_id_usuario` = usuario.`id_usuario`\n"
                    + "     INNER JOIN `setor` setor ON usuario.`setor_id_setor` = setor.`id_setor`\n"
                    + "     INNER JOIN `cbo` cbo ON usuario.`cbo_id` = cbo.`id`\n"
                    + "     INNER JOIN `perfil` perfil ON usuario.`perfil` = perfil.`id_perfil`  " + condicao;

           
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Formulario> entidades = new ArrayList<>();

            while (rs.next()) {

                Formulario entidade = new Formulario(
                        rs.getInt("formulario_id_formulario"),
                        rs.getDate("formulario_data"),
                        rs.getDate("formulario_data_criacao"),
                        rs.getString("formulario_autenticacao"),
                        new Paciente(
                                rs.getInt("paciente_id_paciente"),
                                rs.getString("paciente_num_prontuario"),
                                rs.getString("paciente_nome"),
                                rs.getString("paciente_sexo"),
                                rs.getString("paciente_cns"),
                                rs.getDate("paciente_data_nascimento"),
                                rs.getString("paciente_cor"),
                                rs.getString("paciente_etnia"),
                                rs.getString("paciente_nome_mae"),
                                rs.getString("paciente_telefone_mae"),
                                rs.getString("paciente_nome_responsavel"),
                                rs.getString("paciente_telefone_responsavel"),
                                rs.getFloat("paciente_peso"),
                                rs.getFloat("paciente_altura"),
                                rs.getString("paciente_logradouro"),
                                rs.getString("paciente_num_residencia"),
                                rs.getString("paciente_bairro"),
                                rs.getString("paciente_municipio"),
                                rs.getString("paciente_cod_ibge_municipio"),
                                rs.getString("paciente_uf"),
                                rs.getString("paciente_cep"),
                                null//dt obito
                        ),
                        new Solicitante(
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
                        ),
                        new Estabelecimento_de_saude(
                                rs.getInt("estabelecimento_de_saude_id_estabelecimento_de_saude"),
                                rs.getString("estabelecimento_de_saude_cnes"),
                                rs.getString("estabelecimento_de_saude_nome")
                        ),
                        new Estabelecimento_de_saude(
                                rs.getInt("estabelecimento_de_saude_A_id_estabelecimento_de_saude"),
                                rs.getString("estabelecimento_de_saude_A_cnes"),
                                rs.getString("estabelecimento_de_saude_A_nome")
                        ),
                        new Proc_justificativa(
                                rs.getInt("proc_justificativa_id_proc_justificativa"),
                                rs.getString("proc_justificativa_observacoes"),
                                new Cid(
                                        rs.getString("cid_cid"),
                                        rs.getString("cid_nome"),
                                        new Tp_sexo(
                                                rs.getString("tp_sexo_id"),
                                                rs.getString("tp_sexo_sexo")
                                        )
                                ),
                                new Cid(
                                        rs.getString("cid_A_cid"),
                                        rs.getString("cid_A_nome"),
                                        new Tp_sexo(
                                                rs.getString("tp_sexo_A_id"),
                                                rs.getString("tp_sexo_A_sexo")
                                        )
                                ),
                                new Cid(
                                        rs.getString("cid_B_cid"),
                                        rs.getString("cid_B_nome"),
                                        new Tp_sexo(
                                                rs.getString("tp_sexo_B_id"),
                                                rs.getString("tp_sexo_B_sexo")
                                        )
                                )
                        ),
                        new Status(
                                rs.getInt("status_id_status"),
                                rs.getString("status_status")
                        ),
                        new Autorizacao(
                                rs.getInt("autorizacao_id_autorizacao"),
                                rs.getString("autorizacao_autorizador"),
                                rs.getString("autorizacao_cod_org_emissor"),
                                rs.getInt("autorizacao_tipo_doc"),
                                rs.getString("autorizacao_num_doc"),
                                rs.getDate("autorizacao_data"),
                                rs.getString("autorizacao_num_autorizacao"),
                                rs.getDate("autorizacao_data_val_ini"),
                                rs.getDate("autorizacao_data_val_fim")
                        ),
                        new Motivo_Cancelamento(
                                rs.getInt("motivo_cancelamento_id"),
                                rs.getString("motivo_cancelamento_motivo")
                        )
                );
                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            F.setMsgErro(e.toString() + " formularioDao - buscar");
            throw new ErroSistema("Erro ao buscar dados da auditoria", e);
        }
    }

    @Override
    public Formulario buscaId(String id) throws ErroSistema {
        Formulario c = null;
        List<Formulario> l = buscar("WHERE formulario.`id_formulario` = " + id);
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
