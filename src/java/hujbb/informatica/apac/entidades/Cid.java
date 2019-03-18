package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.CidDAO;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.faces.model.SelectItem;

public class Cid implements Serializable {

    private String cid ;
    private String nome ;
    private Tp_sexo tp_sexo ;

    public Cid() {
         this.cid = "";
        this.nome = "";
        this.tp_sexo = new Tp_sexo();
    }

    public Cid(String cid, String nome, Tp_sexo tp_sexo) {
        this.cid = cid;
        this.nome = nome;
        this.tp_sexo = tp_sexo;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tp_sexo getTp_sexo() {
        return tp_sexo;
    }

    public void setTp_sexo(Tp_sexo tp_sexo) {
        this.tp_sexo = tp_sexo;
    }
    
    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 3;
    }
    
     //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        
        if (condicao.isEmpty()) {
            condicao = "WHERE cid.`cid` <> '-9'  ORDER BY cid.`nome`";
        }
        ArrayList<SelectItem> itens = new ArrayList<>();
        List<Cid> m = new CidDAO().buscar(condicao);
        for (int i = 0; i < m.size(); i++) {
            String nome = (m.get(i).getNome().length()<70) ? m.get(i).getNome():m.get(i).getNome().substring(0, 69);
            itens.add(new SelectItem(m.get(i).getCid() + "",m.get(i).getCid()+" - "+ nome));
        }
               
        return itens;
    }
       
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.cid);
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.tp_sexo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cid other = (Cid) obj;
        if (!Objects.equals(this.cid, other.cid)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "Cid{" + "cid=" + cid + ", nome=" + nome + ", tp_sexo=" + tp_sexo.toString() + '}';
    }

   

    

    
}
