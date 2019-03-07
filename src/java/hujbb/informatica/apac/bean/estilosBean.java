package hujbb.informatica.apac.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class estilosBean implements Serializable{

    public String botao() {

        return "color: blue;\n"
                + "background: white;";
    }

    public String labelCabechalho() {
        return "color: #2e6e9e;\n"
                + "font-weight: bold; \n"
                + "font-size:15px ;";
    }
    public String labelNaoEncontrado() {
        return "color: red;\n" +
                "font-weight: bold; ";
    }

}
