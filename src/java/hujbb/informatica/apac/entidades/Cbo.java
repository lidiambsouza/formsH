
package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.CboDAO;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;


public class Cbo implements Serializable {
    
    private String cod;
    private String nome;

    public Cbo() {
       
        this.cod = "";
        this.nome = "";
    }

    public Cbo(String cod, String nome) {
        
        this.cod = cod;
        this.nome = nome;
    }
    
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        
        if (condicao.isEmpty()) {
            condicao = "WHERE `id` <> ''  ORDER BY `ocupacao`";
        }
        ArrayList<SelectItem> itens = new ArrayList<>();
        List<Cbo> m = new CboDAO().buscar(condicao);
        itens.add(new SelectItem("0", "Selecione CBO..."));
        for (int i = 0; i < m.size(); i++) {
            if(!m.get(i).getNome().isEmpty()){
                 itens.add(new SelectItem(m.get(i).getCod() + "", m.get(i).getCod()+" - "+m.get(i).getNome()));
            }
        }
       
        
        return itens;
    }
    


    @Override
    public int hashCode() {
        int hash = 7;
        
        hash = 17 * hash + Objects.hashCode(this.cod);
        hash = 17 * hash + Objects.hashCode(this.nome);
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
        final Cbo other = (Cbo) obj;
        
        if (!Objects.equals(this.cod, other.cod)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    
    
}
