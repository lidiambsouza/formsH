
package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Sub_grupo_procedimentoDAO;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;


public class Sub_grupo_procedimento {
 private String id; 
 private Grupo_procedimento  grupo_Procedimento;
 private String nome;
 private int dt_Competencia; 

    public Sub_grupo_procedimento() {
    this.id = "";
        this.grupo_Procedimento = new Grupo_procedimento();
        this.nome = "";
        this.dt_Competencia = -1;
    }

    public Sub_grupo_procedimento(String id, Grupo_procedimento grupo_Procedimento, String nome, int dt_Competencia) {
        this.id = id;
        this.grupo_Procedimento = grupo_Procedimento;
        this.nome = nome;
        this.dt_Competencia = dt_Competencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Grupo_procedimento getGrupo_Procedimento() {
        return grupo_Procedimento;
    }

    public void setGrupo_Procedimento(Grupo_procedimento grupo_Procedimento) {
        this.grupo_Procedimento = grupo_Procedimento;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDt_Competencia() {
        return dt_Competencia;
    }

    public void setDt_Competencia(int dt_Competencia) {
        this.dt_Competencia = dt_Competencia;
    }
    
    //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        if (condicao.isEmpty()) {
            condicao = "`id` > -1  ORDER BY `id`";
        }
        ArrayList<SelectItem> item = new ArrayList<>();
        List<Sub_grupo_procedimento> m = new Sub_grupo_procedimentoDAO().buscar(condicao);
        item.add(new SelectItem(-1, "SELECIONE"));
        for (int i = 0; i < m.size(); i++) {
            item.add(new SelectItem(m.get(i).getId() + "",m.get(i).getId().substring(2) +" - "+ m.get(i).getNome()));
        }
        
        return item;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.grupo_Procedimento);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + this.dt_Competencia;
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
        final Sub_grupo_procedimento other = (Sub_grupo_procedimento) obj;
        if (this.dt_Competencia != other.dt_Competencia) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.grupo_Procedimento, other.grupo_Procedimento)) {
            return false;
        }
        return true;
    }
    
    
 
}
