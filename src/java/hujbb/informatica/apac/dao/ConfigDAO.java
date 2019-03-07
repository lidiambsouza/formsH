package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.Config;
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

public class ConfigDAO implements Serializable,CrudDAO<Config>{

    @Override
    public Config salvar(Config entidade) throws ErroSistema {
       Connection conexao  =  FabricaDeConexoes.getConexao();
        try {
            String sql = "INSERT INTO `config`( `nome`, `descricao`, `paranmetro`) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getDescricao());
            ps.setString(3, entidade.getParanmetro());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                entidade.setId_config(rs.getInt(1));
            }
          
        } catch (SQLException ex) {
            F.setMsgErro("configDAO 35! "+ ex.toString());
        }
        
        return entidade;
    }


    @Override
    public Config atualizar(Config entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Config deletar(Config entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Config> buscar(String condicao) throws ErroSistema {
       try { 
            Connection conexao =  FabricaDeConexoes.getConexao();
            String sql = "SELECT `id_config`, `nome`, `descricao`, `paranmetro` FROM `config` "+condicao;
            PreparedStatement ps =  conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Config> configs = new ArrayList<>();
            
            while(rs.next()){
                Config config =  new Config();
              
                config.setId_config(rs.getInt("id_config"));
                config.setNome(rs.getString("nome"));
                config.setDescricao(rs.getString("descricao"));
                config.setParanmetro(rs.getString("paranmetro"));             
                
                configs.add(config);
            }
            
            return configs;
            
        } catch (SQLException  e) {
            throw new ErroSistema("Erro ao buscar dados de configuração", e);
        }
    }
 @Override
    public Config buscaId(String id) throws ErroSistema {
        Config c  =  null;
        List<Config> l = buscar("WHERE `id_config` = "+id);
        if(l.size()>0){
            c = l.get(0);
        }
        return c;
    }

    
}
