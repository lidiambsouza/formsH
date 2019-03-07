
package hujbb.informatica.apac.util.execao;

import hujbb.informatica.apac.util.F;


public class ErroSistema extends Exception{

    public ErroSistema(String message) {
       // super(message);
        F.setMsgErro(message);
    }
    
    public ErroSistema(String message,Throwable cause){
       // super(message, cause);
        F.setMsgErro(message);
    }
    
    
}
