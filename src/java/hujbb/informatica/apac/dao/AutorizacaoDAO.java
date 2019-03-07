package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hujbb.informatica.apac.entidades.Autorizacao;
import java.io.Serializable;
import java.sql.Statement;

public class AutorizacaoDAO implements Serializable,CrudDAO<Autorizacao>{

    @Override
    public Autorizacao salvar(Autorizacao entidade) throws ErroSistema {
       Connection conexao  =  FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `autorizacao`(`autorizador`, `cod_org_emissor`, `tipo_doc`, `num_doc`, `data`, `num_autorizacao`, `data_val_ini`, `data_val_fim`) VALUES  (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getAutorizador());
            ps.setString(2, entidade.getCod_org_emissor());
            ps.setInt(3, entidade.getTipo_doc());
            ps.setString(4, entidade.getNum_doc());
            ps.setDate(5, F.sqlDate(entidade.getData()));
            ps.setString(6, entidade.getNum_autorizacao());
            ps.setDate(7, F.sqlDate(entidade.getData_val_ini()));
            ps.setDate(8, F.sqlDate(entidade.getData_val_fim()));
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                entidade.setId_autorizacao(rs.getInt(1));
            }
            
            
        } catch (SQLException ex) {
            F.setMsgErro("autorizacaoDAO 42! "+ ex.toString());
        }
        
        return entidade;
        
    }

    @Override
    public Autorizacao atualizar(Autorizacao entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `autorizacao` SET "
                    + "`autorizador`=?,`cod_org_emissor`=?,`tipo_doc`=?,"
                    + "`num_doc`=?,`data`=?,`num_autorizacao`=?,"
                    + "`data_val_ini`=?,`data_val_fim`=? WHERE `id_autorizacao`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getAutorizador());
            ps.setString(2, entidade.getCod_org_emissor());
            ps.setString(3, entidade.getTipo_doc_string());
            ps.setString(4, entidade.getNum_doc());
            ps.setDate(5, F.sqlDate(entidade.getData()));
            ps.setString(6, entidade.getNum_autorizacao());
            ps.setDate(7, F.sqlDate(entidade.getData_val_ini()));
            ps.setDate(8, F.sqlDate(entidade.getData_val_fim()));
            ps.setInt(9, entidade.getId_autorizacao());
            
            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! AutorizaçãoDAO F:atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar autorização", ex);

        }
    }


    @Override
    public Autorizacao deletar(Autorizacao entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Autorizacao> buscar(String condicao) throws ErroSistema {
       try { 
            Connection conexao =  FabricaDeConexoes.getConexao();
            String sql = "SELECT `id_autorizacao`, `autorizador`, `cod_org_emissor`, `tipo_doc`, `num_doc`, `data`, 'num_autorizacao', 'data_val_ini', 'data_val_fim' FROM `autorizacao` "+condicao;
            F.setMsgErro(sql);
            PreparedStatement ps =  conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Autorizacao> autorizacoes = new ArrayList<>();
            
            while(rs.next()){
                Autorizacao autorizacao =  new Autorizacao();
              
                autorizacao.setId_autorizacao(rs.getInt("id_autorizacao"));
                autorizacao.setAutorizador(rs.getString("autorizador"));
                autorizacao.setCod_org_emissor(rs.getString("cod_org_emissor"));
                autorizacao.setTipo_doc(rs.getInt("tipo_doc"));
                autorizacao.setNum_doc(rs.getString("num_doc"));
                autorizacao.setData(rs.getDate("data"));
                autorizacao.setNum_autorizacao(rs.getString("num_autorizacao"));
                autorizacao.setData_val_ini(rs.getDate("data_val_ini"));
                autorizacao.setData_val_fim(rs.getDate("data_val_fim"));
                              
                autorizacoes.add(autorizacao);
            }
           
            return autorizacoes;
            
        } catch (SQLException  e) {
            throw new ErroSistema("Erro ao buscar dados da autorizaçaõ", e);
        }
    }

    @Override
    public Autorizacao buscaId(String id) throws ErroSistema {
        Autorizacao c  =  null;
        List<Autorizacao> l = buscar("WHERE `id_autorizacao` = "+id);
        if(l.size()>0){
            c = l.get(0);
        }
        return c;
    }

}
