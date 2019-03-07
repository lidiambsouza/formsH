package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Forma_organizacaoDAO;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

public class Forma_organizacao {

    private String id;
    private Sub_grupo_procedimento sub_grupo_procedimento;
    private String nome;
    private int dt_competencia;

    public Forma_organizacao() {
        this.id = "";
        this.sub_grupo_procedimento = new Sub_grupo_procedimento();
        this.nome = "";
        this.dt_competencia = -1;

    }

    public Forma_organizacao(String id, Sub_grupo_procedimento sub_grupo_procedimento, String nome, int dt_competencia) {
        this.id = id;
        this.sub_grupo_procedimento = sub_grupo_procedimento;
        this.nome = nome;
        this.dt_competencia = dt_competencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sub_grupo_procedimento getSub_grupo_procedimento() {
        return sub_grupo_procedimento;
    }

    public void setSub_grupo_procedimento(Sub_grupo_procedimento sub_grupo_procedimento) {
        this.sub_grupo_procedimento = sub_grupo_procedimento;
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
        List<Forma_organizacao> m = new Forma_organizacaoDAO().buscar(condicao);
        item.add(new SelectItem(-1, "SELECIONE"));
        for (int i = 0; i < m.size(); i++) {
            item.add(new SelectItem(m.get(i).getId() + "",m.get(i).getId().substring(4) +" - "+ m.get(i).getNome()));
        }
        
        return item;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.sub_grupo_procedimento);
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
        final Forma_organizacao other = (Forma_organizacao) obj;
        if (this.dt_competencia != other.dt_competencia) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.sub_grupo_procedimento, other.sub_grupo_procedimento)) {
            return false;
        }
        return true;
    }
 
}
