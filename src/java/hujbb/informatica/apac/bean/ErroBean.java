package hujbb.informatica.apac.bean;



import hujbb.informatica.apac.util.F;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ErroBean implements Serializable{

    private String msgErro;

    public void limparLog(){
        F.msgErro = "";
    }
    
    public String getMsgErro() {
        msgErro = F.msgErro;
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

}
