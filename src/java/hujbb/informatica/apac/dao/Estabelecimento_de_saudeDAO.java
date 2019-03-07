package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Estabelecimento_de_saude;
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

public class Estabelecimento_de_saudeDAO implements Serializable,CrudDAO<Estabelecimento_de_saude>{

    @Override
    public Estabelecimento_de_saude salvar(Estabelecimento_de_saude entidade) throws ErroSistema {
       Connection conexao  =  FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `estabelecimento_de_saude`( `cnes`, `nome`) VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getCnes());
            ps.setString(2, entidade.getNome());          
            ps.executeUpdate();
            
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                entidade.setId_estabelecimento_saude(rs.getInt(1));
            }
            F.mensagem("Sucesso!", "Estabelecimento de saúde ("+entidade.getNome()+") foi salvo com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (SQLException ex) {
            F.setMsgErro("estabelecimento_de_saudeDAO 35! "+ ex.toString());
        }
        
        return entidade;
        
    }

    

    @Override
    public Estabelecimento_de_saude atualizar(Estabelecimento_de_saude entidade) throws ErroSistema {
        Connection conexao = FabricaDeConexoes.getConexao();
        try {
            String sql = "UPDATE `estabelecimento_de_saude` SET "
                    + "`cnes`=?,`nome`=? WHERE `id_estabelecimento_de_saude`=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, entidade.getCnes());
            ps.setString(2, entidade.getNome());
            ps.setInt(3, entidade.getId_estabelecimento_saude());

            ps.execute();

            
            return entidade;
        } catch (SQLException ex) {
            F.setMsgErro("Erro! EstabelecimentoDAO F:Atualizar!" + ex.toString());
            throw new ErroSistema("Erro ao atualizar Estabelecimento", ex);

        }
    }


    @Override
    public Estabelecimento_de_saude deletar(Estabelecimento_de_saude entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Estabelecimento_de_saude> buscar(String condicao) throws ErroSistema {
       try { 
            Connection conexao =  FabricaDeConexoes.getConexao();
            String sql = "SELECT `id_estabelecimento_de_saude`, `cnes`, `nome` FROM `estabelecimento_de_saude` "+condicao;
            PreparedStatement ps =  conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Estabelecimento_de_saude> estabelecimentos_de_saude = new ArrayList<>();
            
            while(rs.next()){
                Estabelecimento_de_saude estabelecimento_de_saude =  new Estabelecimento_de_saude();
              
                estabelecimento_de_saude.setId_estabelecimento_saude(rs.getInt("id_estabelecimento_de_saude"));
                estabelecimento_de_saude.setCnes(rs.getString("cnes"));
                estabelecimento_de_saude.setNome(rs.getString("nome"));            
                
                estabelecimentos_de_saude.add(estabelecimento_de_saude);
            }
            
            return estabelecimentos_de_saude;
            
        } catch (SQLException  e) {
            throw new ErroSistema("Erro ao buscar dados do estabelecimento de saúde", e);
        }
    }

    @Override
    public Estabelecimento_de_saude buscaId(String id) throws ErroSistema {
        Estabelecimento_de_saude c  =  null;
        List<Estabelecimento_de_saude> l = buscar("WHERE `id_estabelecimento_de_saude` = "+id);
        if(l.size()>0){
            c = l.get(0);
        }
        return c;
    }

}
