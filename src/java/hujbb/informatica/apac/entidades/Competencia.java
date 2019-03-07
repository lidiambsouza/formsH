package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.CompetenciaDAO;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

public class Competencia implements Serializable {

    private int competencia;
    
    //variavel extra
private String competenciaString;
    public Competencia() {
         
    }

    public Competencia(int competencia) {
        this.competencia = competencia;
    }

    public String competenciaString() {
        String aux =  competencia+"";
        String aux2 = "" ;
        for(int i=aux.length();i<6;i++){
            aux2+="0";
        }
        aux = aux2+aux;
        aux = aux.substring(4, 6)+"/"+aux.substring(0, 4);
        return aux;
    }

    public int getCompetencia() {
        return competencia;
    }

    public void setCompetencia(int competencia) {
       
       
        this.competencia = competencia;
    }

    public String getCompetenciaString() {
        competenciaString =  competenciaString();
        return competenciaString;
    }

    public void setCompetenciaString(String competenciaString) {
        this.competenciaString = competenciaString;
    }
    
    
    
    //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        
        ArrayList<SelectItem> item = new ArrayList<>();
        List<Competencia> m = new CompetenciaDAO().buscar(condicao);
        
        for (int i = 0; i < m.size(); i++) {
            item.add(new SelectItem(m.get(i).getCompetencia() + "", m.get(i).competenciaString()));
        }
         return item;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.competencia;
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
        final Competencia other = (Competencia) obj;
        if (this.competencia != other.competencia) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Competencia{" + "competencia=" + competencia + '}';
    }

}
