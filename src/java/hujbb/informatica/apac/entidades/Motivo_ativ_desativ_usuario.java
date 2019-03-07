package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Motivo_ativ_desativ_usuarioDAO;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.model.SelectItem;

/**
 *
 * @author lidia.souza
 */
public class Motivo_ativ_desativ_usuario implements Serializable  {

    private Integer id;
    private String motivo;
    private int flag;

    public Motivo_ativ_desativ_usuario(Integer id, String motivo, int flag) {
        this.id = id;
        this.motivo = motivo;
        this.flag = flag;
    }

    public Motivo_ativ_desativ_usuario() {
        this.id = -1;
        this.motivo = "";
        this.flag = -1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    //itens lidia
    public static ArrayList<SelectItem> itemMotivo(String condicao , int usuario) throws ErroSistema {

        if (condicao.isEmpty()) {
            if (usuario == 1) {
                condicao = "WHERE `id` <> '' AND `flag` = 0 ORDER BY `motivo`";
            }else{
                condicao = "WHERE `id` <> '' AND `flag` = 1 ORDER BY `motivo`";
            }
        }
        ArrayList<SelectItem> itens = new ArrayList<>();
        List<Motivo_ativ_desativ_usuario> m = new Motivo_ativ_desativ_usuarioDAO().buscar(condicao);
        itens.add(new SelectItem("0", "Selecione o motivo..."));
        for (int i = 0; i < m.size(); i++) {
            itens.add(new SelectItem(m.get(i).getId() + "", m.get(i).getMotivo()));
        }
        

        return itens;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.motivo);
        hash = 53 * hash + this.flag;
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
        final Motivo_ativ_desativ_usuario other = (Motivo_ativ_desativ_usuario) obj;
        if (this.flag != other.flag) {
            return false;
        }
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
