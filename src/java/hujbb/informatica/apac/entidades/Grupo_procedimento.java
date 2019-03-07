package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Grupo_procedimentoDAO;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

public class Grupo_procedimento {

    private String id;
    private String nome;
    private int dt_competencia;

    public Grupo_procedimento() {
        this.id = "";
        this.nome = "";
        this.dt_competencia = -1;
    }

    public Grupo_procedimento(String id, String nome, int dt_competencia) {
        this.id = id;
        this.nome = nome;
        this.dt_competencia = dt_competencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDt_competencia() {
        return dt_competencia;
    }

    public void setDt_competencia(int dt_competencia) {
        this.dt_competencia = dt_competencia;
    }

    //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        if (condicao.isEmpty()) {
            condicao = "`id` > -1  ORDER BY `id`";
        }
        ArrayList<SelectItem> item = new ArrayList<>();
        List<Grupo_procedimento> m = new Grupo_procedimentoDAO().buscar(condicao);
        item.add(new SelectItem(-1, "SELECIONE"));
        for (int i = 0; i < m.size(); i++) {
            item.add(new SelectItem(m.get(i).getId() + "",m.get(i).getId() +" - "+ m.get(i).getNome()));
        }
        
        return item;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.nome);
        hash = 67 * hash + this.dt_competencia;
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
        final Grupo_procedimento other = (Grupo_procedimento) obj;
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    

    
}
