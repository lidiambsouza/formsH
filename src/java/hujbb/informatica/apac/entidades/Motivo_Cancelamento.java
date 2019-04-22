package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Motivo_CancelamentoDAO;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

public class Motivo_Cancelamento implements Serializable{
    private Integer id;
    private String motivo;

    public Motivo_Cancelamento() {
        this.id = -1;
        this.motivo = "";
    }

    public Motivo_Cancelamento(Integer id, String motivo) {
        this.id = id;
        this.motivo = motivo;
    }

        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo.toUpperCase();
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        
        if (condicao == null || condicao.isEmpty()) {
            condicao = "WHERE motivo_cancelamento.`id` <> '-1'  ORDER BY `motivo`";
        }
        ArrayList<SelectItem> itens = new ArrayList<>();
        List<Motivo_Cancelamento> m = new Motivo_CancelamentoDAO().buscar(condicao);
        
        for (int i = 0; i < m.size(); i++) {
            itens.add(new SelectItem(m.get(i).getId()+ "", m.get(i).getMotivo()));
        }
       
        System.out.println(itens.size());
        return itens;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.motivo);
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
        final Motivo_Cancelamento other = (Motivo_Cancelamento) obj;
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Motivo_Cancelamento{" + "id=" + id + ", motivo=" + motivo + '}';
    }
    
   
}
