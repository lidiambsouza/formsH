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
            ps.setString(7, entidade.getEtnia().substring(0, Math.min(149, entidade.getEtnia().length())));
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
            ps.setString(19, entidade.getUf().substring(0, Math.min(2, entidade.getUf().length())));
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
            if (conexao != null) {

                String sql = " select distinct\n"
                        
                        + "p.codigo,\n" //1
                        + "p.prontuario,\n" //2
                        + "p.nro_cartao_saude,\n"//3
                        + "p.nome,\n" //4
                        + "p.nome_mae,\n"//5
                        + "p.dt_nascimento,\n"//6
                        + "p.cor,\n"//7---8
                        + "p.sexo_biologico,\n" //8----9                        
                        + "(p.ddd_fone_residencial||' '||p.fone_residencial) as telefone_residencial,\n" //9---10e11
                        + "(p.ddd_fone_recado ||' '||p.fone_recado) as telefone_Recado,\n"//10---
                        + "r.nome as responsavel,\n"//11----
                        + "(r.ddd_fone ||'-'||r.fone)as telefone_responsavel,\n"//12----
                        + "(tpl.descricao ||' '||ls.nome) as logradouro,\n"//13-------14e15
                        + "ep.nro_logradouro,\n"//14------16                        
                        + "b.descricao as bairro,\n"//15---17
                        + "c.nome as municipio,\n"//16-----18
                        + "c.cod_ibge,\n"//17---19
                        + "c.uf_sigla,\n"//18----20
                        + "ep.bcl_clo_cep as cep,\n"//19---21
                        + "e.descricao as etnia,\n" //20---22
                        + "p.dt_obito,\n"//21---23                        
                        + "ep.cdd_codigo,\n"//22-----                        
                        + "ep.logradouro,\n"//23--13b----14e15
                        + "ep.bairro,\n"//24---15b                      
                        + "c.cep,\n" //25---19b ----21 
                        + "ep.cep,\n"//26-----19c----21
                        + "ep.cidade,\n"//27----16b----18
                        + "ep.uf_sigla,\n"//28----18b----20                        
                        + "ep.seqp\n"//29----                        
                        + "from agh.aip_pacientes p\n"
                        + "left join agh.aip_etnias e on p.etn_id = e.id\n"
                        + "left join agh.agh_responsaveis r on p.codigo = r.pac_codigo\n"
                        + "left join agh.aip_enderecos_pacientes ep on p.codigo = ep.pac_codigo\n"
                        + "left join agh.aip_bairros_cep_logradouro bcl on ep.bcl_clo_lgr_codigo = bcl.clo_lgr_codigo and ep.bcl_bai_codigo = bcl.bai_codigo and ep.bcl_clo_cep = bcl.clo_cep\n"
                        + "left join agh.aip_bairros b on bcl.bai_codigo = b.codigo\n"
                        + "left join agh.aip_cep_logradouros l on bcl.clo_lgr_codigo = l.lgr_codigo\n"
                        + "left join agh.aip_logradouros ls on l.lgr_codigo = ls.codigo\n"
                        + "left join agh.aip_cidades c on ls.cdd_codigo = c.codigo or ep.cdd_codigo = c.codigo\n"
                        + "left join agh.aip_tipo_logradouros tpl on ls.tlg_codigo = tpl.codigo\n"
                        + "\n"                        
                        + condicao;

                  //System.out.println(sql);
                PreparedStatement ps = conexao.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Paciente paciente = new Paciente();

                    paciente.setNum_prontuario(rs.getInt(2) + "");
                    paciente.setNome(rs.getString(4));
                    paciente.setSexo(rs.getString(8));
                    paciente.setCns(rs.getString(3));
                    paciente.setData_nascimento(rs.getDate(6));
                    paciente.setCor(rs.getString(7));
                    paciente.setEtnia(rs.getString(20));
                    paciente.setNome_mae(rs.getString(5));
                   
                    paciente.setTelefone_mae(rs.getString(9));                   
                    
                    paciente.setNome_responsavel(rs.getString(11));
                    
                    paciente.setTelefone_responsavel(rs.getString(12));
                    paciente.setPeso((float) 0);
                    paciente.setAltura((float) 0);
                    
                   
                    
                    if(rs.getString(13)==null){
                        paciente.setLogradouro(rs.getString(23));//ep
                    }else{
                        paciente.setLogradouro(rs.getString(13));//ls
                    }
                    
                    paciente.setNum_residencia(rs.getInt(14) + "");
                    if(rs.getString(15)==null){
                        paciente.setBairro(rs.getString(24));//ep
                    }else{
                        paciente.setBairro(rs.getString(15));//b
                    }
                    
                    if(rs.getString(16)==null){
                        paciente.setMunicipio(rs.getString(27));//ep
                    }else{
                        paciente.setMunicipio(rs.getString(16));//c
                    }
                    
                    paciente.setCod_ibge_municipio(rs.getInt(17) + "");
                    
                    if(rs.getString(18)==null){
                        paciente.setUf(rs.getString(28));//ep
                    }else{
                        paciente.setUf(rs.getString(18));//c
                    }
                    
                    if((rs.getInt(19)+"").isEmpty()||rs.getInt(19)==0){
                        if((rs.getInt(25)+ "").isEmpty()||rs.getInt(25)==0){
                            paciente.setCep(rs.getInt(26) + "");//ep
                        }else{
                            paciente.setCep(rs.getInt(25) + "");//c
                        }
                    }else{                       
                        paciente.setCep(rs.getInt(19) + "");//ep.b
                    }
                    
                    
                    paciente.setData_obito(rs.getDate(21));

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
