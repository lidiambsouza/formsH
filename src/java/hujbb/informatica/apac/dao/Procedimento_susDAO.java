package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Proced_tp_compexidade;
import hujbb.informatica.apac.entidades.Procedimento_sus;
import hujbb.informatica.apac.entidades.Tb_financiamento;
import hujbb.informatica.apac.entidades.Tb_modalidade;
import hujbb.informatica.apac.entidades.Tp_sexo;
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

public class Procedimento_susDAO implements CrudDAO<Procedimento_sus>, Serializable {

    @Override
    public Procedimento_sus salvar(Procedimento_sus entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `procedimento_sus`(`codigo`, `nome`, `proced_tp_compexidade_id`, `tp_sexo_id`, `qtd_max`, `qtd_pontos`, `qtd`, `idade_min`, `idade_max`, `valor_hospitalar`, `valor_ambulatorial`, `valor_profissonal`, `proced_financiamento_id`, `tb_modalidade_id`, `dt_competencia`)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getCodigo());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getProced_tp_compexidade().getId());
            ps.setString(4, entidade.getTp_sexo().getId());
            ps.setInt(5, entidade.getQtd_max());
            ps.setInt(6, entidade.getQtd_pontos());
            ps.setInt(7, entidade.getQtd());
            ps.setInt(8, entidade.getIdade_min());
            ps.setInt(9, entidade.getIdade_max());
            ps.setFloat(10, entidade.getValor_hospitalar());
            ps.setFloat(11, entidade.getValor_ambulatorial());
            ps.setFloat(12, entidade.getValor_proficional());
            ps.setString(13, entidade.getProced_financiamento().getId());
            ps.setString(14, entidade.getTb_modalidade().getId());
            ps.setInt(15, entidade.getDt_competencia());

            ps.executeUpdate();

        } catch (SQLException ex) {
            F.setMsgErro("Procedimento_susDAO 47! " + ex.toString());
        }
        
        return entidade;
    }

    @Override
    public Procedimento_sus atualizar(Procedimento_sus entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `procedimento_sus` SET `nome`=?,`proced_tp_compexidade_id`=?,"
                    + "`tp_sexo_id`=?,`qtd_max`=?,`qtd_pontos`=?,`qtd`=?,`idade_min`=?,"
                    + "`idade_max`=?,`valor_hospitalar`=?,`valor_ambulatorial`=?,`valor_profissonal`=?,"
                    + "`proced_financiamento_id`=?,`tb_modalidade_id`=? WHERE `codigo`=? and `dt_competencia`=? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getProced_tp_compexidade().getId());
            ps.setString(3, entidade.getTp_sexo().getId());
            ps.setInt(4, entidade.getQtd_max());
            ps.setInt(5, entidade.getQtd_pontos());
            ps.setInt(6, entidade.getQtd());
            ps.setInt(7, entidade.getIdade_min());
            ps.setInt(8, entidade.getIdade_max());
            ps.setDouble(9, entidade.getValor_hospitalar());
            ps.setDouble(10, entidade.getValor_ambulatorial());
            ps.setDouble(11, entidade.getValor_proficional());
            ps.setString(12, entidade.getProced_financiamento().getId());
            ps.setString(13, entidade.getTb_modalidade().getId());
            ps.setString(14, entidade.getCodigo());
            ps.setInt(15, entidade.getDt_competencia());


            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! Procedimento_susDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar", ex);

        }
    }

    @Override
    public Procedimento_sus deletar(Procedimento_sus entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Procedimento_sus> buscar(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     procedimento_sus.`codigo` AS procedimento_sus_codigo,\n"
                    + "     procedimento_sus.`nome` AS procedimento_sus_nome,\n"
                    + "     procedimento_sus.`qtd_max` AS procedimento_sus_qtd_max,\n"
                    + "     procedimento_sus.`qtd_pontos` AS procedimento_sus_qtd_pontos,\n"
                    + "     procedimento_sus.`qtd_pontos` AS procedimento_sus_qtd,\n"
                    + "     procedimento_sus.`idade_min` AS procedimento_sus_idade_min,\n"
                    + "     procedimento_sus.`idade_max` AS procedimento_sus_idade_max,\n"
                    + "     procedimento_sus.`valor_hospitalar` AS procedimento_sus_valor_hospitalar,\n"
                    + "     procedimento_sus.`valor_ambulatorial` AS procedimento_sus_valor_ambulatorial,\n"
                    + "     procedimento_sus.`valor_profissonal` AS procedimento_sus_valor_profissonal,\n"
                    + "     procedimento_sus.`dt_competencia` AS procedimento_sus_dt_competencia,\n"
                    + "     proced_tp_compexidade.`id` AS proced_tp_compexidade_id,\n"
                    + "     proced_tp_compexidade.`complexidade` AS proced_tp_compexidade_complexidade,\n"
                    + "     tp_sexo.`id` AS tp_sexo_id,\n"
                    + "     tp_sexo.`sexo` AS tp_sexo_sexo,\n"
                    + "     proced_financiamento.`id` AS proced_financiamento_id,\n"
                    + "     proced_financiamento.`nome` AS proced_financiamento_nome,\n"
                    + "     proced_financiamento.`dt_competencia` AS proced_financiamento_dt_competencia,\n"
                    + "     tb_modalidade.`id` AS tb_modalidade_id,\n"
                    + "     tb_modalidade.`modalidade` AS tb_modalidade_modalidade,\n"
                    + "     tb_modalidade.`dt_competencia` AS tb_modalidade_dt_competencia\n"
                    + "FROM\n"
                    + "     `proced_tp_compexidade` proced_tp_compexidade INNER JOIN `procedimento_sus` procedimento_sus ON proced_tp_compexidade.`id` = procedimento_sus.`proced_tp_compexidade_id`\n"
                    + "     INNER JOIN `tp_sexo` tp_sexo ON procedimento_sus.`tp_sexo_id` = tp_sexo.`id`\n"
                    + "     INNER JOIN `proced_financiamento` proced_financiamento ON procedimento_sus.`proced_financiamento_id` = proced_financiamento.`id`\n"
                    + "     INNER JOIN `tb_modalidade` tb_modalidade ON procedimento_sus.`tb_modalidade_id` = tb_modalidade.`id` " + condicao;
            PreparedStatement ps = conexao.prepareStatement(sql);
            //System.out.println(sql);
            ResultSet rs = ps.executeQuery();
            List<Procedimento_sus> entidades = new ArrayList<>();
            while (rs.next()) {
                Procedimento_sus entidade = new Procedimento_sus(
                        rs.getString("procedimento_sus_codigo"),
                        rs.getString("procedimento_sus_nome"),
                        new Proced_tp_compexidade(
                                rs.getString("proced_tp_compexidade_id"),
                                rs.getString("proced_tp_compexidade_complexidade")
                        ),
                        new Tp_sexo(
                                rs.getString("tp_sexo_id"),
                                rs.getString("tp_sexo_sexo")
                        ),
                        rs.getInt("procedimento_sus_qtd_max"),
                        rs.getInt("procedimento_sus_qtd_pontos"),
                        rs.getInt("procedimento_sus_qtd"),
                        rs.getInt("procedimento_sus_idade_min"),
                        rs.getInt("procedimento_sus_idade_max"),
                        rs.getFloat("procedimento_sus_valor_hospitalar"),
                        rs.getFloat("procedimento_sus_valor_ambulatorial"),
                        rs.getFloat("procedimento_sus_valor_profissonal"),
                        new Tb_financiamento(
                                rs.getString("proced_financiamento_id"),
                                rs.getString("proced_financiamento_nome"),
                                rs.getInt("proced_financiamento_dt_competencia")
                        ),
                        new Tb_modalidade(
                                rs.getString("tb_modalidade_id"),
                                rs.getString("tb_modalidade_modalidade"),
                                rs.getInt("tb_modalidade_dt_competencia")
                        ),
                        rs.getInt("procedimento_sus_dt_competencia")
                );

                entidades.add(entidade);
            }

            return entidades;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do procedimento sus", e);
        }

    }

    @Override
    public Procedimento_sus buscaId(String id) throws ErroSistema {
        Procedimento_sus c = null;
        List<Procedimento_sus> l = buscar("WHERE procedimento_sus.`codigo` = '" + id+"'");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }
    
    public Procedimento_sus buscaIdComp(String id,int competencia) throws ErroSistema {
        Procedimento_sus c = null;
        List<Procedimento_sus> l = buscar("WHERE procedimento_sus.`codigo` = '" + id+"' AND procedimento_sus.`dt_competencia` = '"+competencia+"' ");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

//atualiza a modalidade do procedimento (usado pela carga)
    public void atualizarCarga(String codProcedimento, int dt_competencia, String codModalidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = " UPDATE `procedimento_sus` SET `tb_modalidade_id`=? WHERE `codigo`=? AND `dt_competencia`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codModalidade);
            ps.setString(2, codProcedimento);
            ps.setInt(3, dt_competencia);

            ps.execute();

        } catch (SQLException ex) {
            F.setMsgErro("Erro! Procedimento_susDAODAO 88!" + ex.toString());
        }
    }
}
