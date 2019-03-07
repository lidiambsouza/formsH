package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.StatusDAO;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

public class Status implements Serializable {

    private Integer id_status;
    private String status;

    public Status(Integer id_status, String status) {
        this.id_status = id_status;
        this.status = status;
    }

    public Status() {
        id_status = -1;
        status = "";
    }

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd() {
        return 13;
    }

    public Integer getId_status() {
        
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //itens
    public static ArrayList<SelectItem> item(String condicao) throws ErroSistema {
        if (condicao.isEmpty()) {
            condicao = "`id_status` > -1  ORDER BY `id_status`";
        }
        ArrayList<SelectItem> item = new ArrayList<>();
        List<Status> m = new StatusDAO().buscar(condicao);
        item.add(new SelectItem("0", "Todos"));
        for (int i = 0; i < m.size(); i++) {
            item.add(new SelectItem(m.get(i).getId_status() + "", m.get(i).getStatus()));
        }
        
        return item;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id_status);
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
        final Status other = (Status) obj;
        if (!Objects.equals(this.id_status, other.id_status)) {
            return false;
        }
        return true;
    }

}
