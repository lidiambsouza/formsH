package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Paciente;
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

public class PacienteDAO implements Serializable, CrudDAO<Paciente> {

    @Override
    public Paciente salvar(Paciente entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `paciente`( `num_prontuario`, `nome`, `sexo`, `cns`, `data_nascimento`, `cor`, `etnia`, `nome_mae`, `telefone_mae`, `nome_responsavel`, `telefone_responsavel`, `peso`, `altura`, `logradouro`, `num_residencia`, `bairro`, `municipio`, `cod_ibge_municipio`, `uf`, `cep`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getNum_prontuario());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getSexo());
            ps.setString(4, entidade.getCns());
            ps.setDate(5, F.sqlDate(entidade.getData_nascimento()));
            ps.setString(6, entidade.getCor());
            ps.setString(7, entidade.getEtnia().substring(0,Math.min(149, entidade.getEtnia().length())));
            ps.setString(8, entidade.getNome_mae());
            ps.setString(9, entidade.getTelefone_mae());
            ps.setString(10, entidade.getNome_responsavel());
            ps.setString(11, entidade.getTelefone_responsavel());
            ps.setFloat(12, entidade.getPeso());
            ps.setFloat(13, entidade.getAltura());
            ps.setString(14, entidade.getLogradouro());
            ps.setString(15, entidade.getNum_residencia().replace("Nº", "").replace("N", ""));
            ps.setString(16, entidade.getBairro());
            ps.setString(17, entidade.getMunicipio());
            ps.setString(18, entidade.getCod_ibge_municipio());
            ps.setString(19, entidade.getUf().substring(0,Math.min(2, entidade.getUf().length())));
            ps.setString(20, entidade.getCep());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId_paciente(rs.getInt(1));
            }

        } catch (SQLException ex) {
            
            F.setMsgErro("pacienteDAO 55! " + ex.toString());

        }

        return entidade;
    }

    @Override
    public Paciente atualizar(Paciente entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paciente deletar(Paciente entidade) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM `paciente` WHERE `id_paciente` = ?");
            ps.setInt(1, entidade.getId_paciente());
            ps.execute();

        } catch (SQLException ex) {

            F.setMsgErro("PacienteDAO72: " + ex);
            entidade = null;

        }

        return entidade;

    }

    @Override
    public List<Paciente> buscar(String condicao) throws ErroSistema {
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT `id_paciente`, `num_prontuario`, `nome`, `sexo`, `cns`, `data_nascimento`, 'cor', 'etnia', 'nome_mae', 'telefone_mae', 'nome_responsavel', 'telefone_responsavel', 'peso', 'altura', 'logradouro', 'num_residencia', 'bairro', 'municipio', 'cod_ibge_municipio', 'uf', 'cep' FROM `paciente` " + condicao;

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Paciente> pacientes = new ArrayList<>();

            while (rs.next()) {
                Paciente paciente = new Paciente();

                paciente.setId_paciente(rs.getInt("id_paciente"));
                paciente.setNum_prontuario(rs.getString("num_prontuario"));
                paciente.setNome(rs.getString("nome"));
                paciente.setSexo(rs.getString("sexo"));
                paciente.setCns(rs.getString("cns"));
                paciente.setData_nascimento(rs.getDate("data_nascimento"));
                paciente.setCor(rs.getString("cor"));
                paciente.setEtnia(rs.getString("etnia"));
                paciente.setNome_mae(rs.getString("nome_mae"));
                paciente.setTelefone_mae(rs.getString("telefone_mae"));
                paciente.setNome_responsavel(rs.getString("nome_responsavel"));
                paciente.setTelefone_responsavel(rs.getString("telefone_responsavel"));
                paciente.setPeso(0f);
                paciente.setAltura(0f);
                paciente.setLogradouro(rs.getString("logradouro"));
                paciente.setNum_residencia(rs.getString("num_residencia"));
                paciente.setBairro(rs.getString("bairro"));
                paciente.setMunicipio(rs.getString("municipio"));
                paciente.setCod_ibge_municipio(rs.getString("cod_ibge_municipio"));
                paciente.setUf(rs.getString("uf"));
                paciente.setCep(rs.getString("cep"));
                

                pacientes.add(paciente);
            }

            return pacientes;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do paciente", e);
        }
    }

    public List<Paciente> buscarAghuBarros(String condicao) throws ErroSistema {
        condicao = F.tratarCondicaoSQL(condicao);
        try {
            List<Paciente> pacientes = new ArrayList<>();
            Connection conexao = FabricaDeConexoes.getConexaoAghuBarros();
            if(conexao != null){
                
            
            String sql = " SELECT agh.aip_pacientes.codigo,\n"//1
                    + "  agh.aip_pacientes.prontuario, \n"//2
                    + "  agh.aip_pacientes.nro_cartao_saude, \n" //3
                    + "  agh.aip_pacientes.nome,\n"//4
                    + "   agh.aip_pacientes.nome_mae, \n"//5
                    + "   agh.aip_pacientes.dt_nascimento,\n"//6
                    + "   agh.aip_pacientes.dt_identificacao,\n"//7
                    + "   agh.aip_pacientes.cor,"//8
                    + " agh.aip_pacientes.sexo,\n"//9
                    + "    agh.aip_pacientes.ddd_fone_residencial,\n"//10
                    + "     agh.aip_pacientes.fone_residencial,\n"//11
                    + "      agh.aip_pacientes.cpf,\n"//12
                    + "      agh.aip_pacientes.rg,\n"//13
                    + "      agh.aip_tipo_logradouros.descricao AS tipo_logradouro,\n"//14
                    + "       agh.aip_logradouros.nome AS logradouro,\n"//15
                    + "       agh.aip_enderecos_pacientes.nro_logradouro,\n"//16
                    + "       agh.aip_bairros.descricao AS bairro,\n"//17
                    + "       agh.aip_cidades.nome AS cidade,       \n"//18
                    + "       agh.aip_cidades.cod_ibge,\n"//19
                    + "       agh.aip_cidades.uf_sigla,\n"//20
                    + "       agh.aip_enderecos_pacientes.bcl_clo_cep AS cep,\n"//21
                    + "       agh.aip_etnias.descricao AS etinia,\n"//22
                    + "       agh.aip_pacientes.dt_obito\n"//23
                    + "FROM (((((agh.aip_pacientes LEFT JOIN agh.aip_enderecos_pacientes ON agh.aip_pacientes.codigo = agh.aip_enderecos_pacientes.pac_codigo) \n"
                    + "LEFT JOIN agh.aip_bairros ON agh.aip_enderecos_pacientes.bcl_bai_codigo = agh.aip_bairros.codigo) \n"
                    + "LEFT JOIN agh.aip_logradouros ON agh.aip_enderecos_pacientes.bcl_clo_lgr_codigo = agh.aip_logradouros.codigo) \n"
                    + "LEFT JOIN agh.aip_cidades ON agh.aip_logradouros.cdd_codigo = agh.aip_cidades.codigo) \n"
                    + "LEFT JOIN agh.aip_tipo_logradouros ON agh.aip_logradouros.tlg_codigo = agh.aip_tipo_logradouros.codigo) \n"
                    + "LEFT JOIN agh.aip_etnias ON agh.aip_pacientes.etn_id = agh.aip_etnias.id  " + condicao;

              //  System.out.println(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            

            while (rs.next()) {
                Paciente paciente = new Paciente();

                paciente.setNum_prontuario(rs.getInt(2) + "");
                paciente.setNome(rs.getString(4));
                paciente.setSexo(rs.getString(9));
                paciente.setCns(rs.getString(3));
                paciente.setData_nascimento(rs.getDate(6));
                paciente.setCor(rs.getString(8));
                paciente.setEtnia(rs.getString(22));
                paciente.setNome_mae(rs.getString(5));
                paciente.setTelefone_mae(rs.getLong(10) + " " + rs.getLong(11) + "");
                paciente.setNome_responsavel("");
                paciente.setTelefone_responsavel("");
                paciente.setPeso((float) 0);
                paciente.setAltura((float) 0);
                paciente.setLogradouro(rs.getString(14) + " " + rs.getString(15));
                paciente.setNum_residencia(rs.getInt(16) + "");
                paciente.setBairro(rs.getString(17));
                paciente.setMunicipio(rs.getString(18));
                paciente.setCod_ibge_municipio(rs.getInt(19) + "");
                paciente.setUf(rs.getString(20));
                paciente.setCep(rs.getInt(21) + "");
                paciente.setData_obito(rs.getDate(23));

                pacientes.add(paciente);
            }
            }
            return pacientes;

        } catch (SQLException e) {
            F.setMsgErro(e.toString() + " pacienteDAO buscarpaciaghu");
            throw new ErroSistema("Erro ao buscar dados do paciente", e);
        }
    }

    @Override
    public Paciente buscaId(String id) throws ErroSistema {
        Paciente c = null;
        List<Paciente> l = buscar("WHERE `id_paciente` = '" + id + "'");
        if (l.size() > 0) {
            c = l.get(0);
        }
        return c;
    }

}
