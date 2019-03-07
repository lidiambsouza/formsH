package hujbb.informatica.apac.util.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


public class Relatorios {
    private HttpServletResponse response;
    private FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream stream;
    private String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
    private Map<String,Object> parametros =  new HashMap<String, Object>();
    

    public Relatorios(String caminhoImagem) {
        ImageIcon gto = new ImageIcon(getClass().getResource(caminhoImagem));
 
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse)context.getExternalContext().getResponse();
        
        parametros.put("logo",gto.getImage());
       
        
     }
    
    public Relatorios(String caminhoImagem,String caminhoImagem2) {
        ImageIcon gto = new ImageIcon(getClass().getResource(caminhoImagem));
        ImageIcon gto2 = new ImageIcon(getClass().getResource(caminhoImagem2));
 
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse)context.getExternalContext().getResponse();
        
        parametros.put("logo",gto.getImage());
        parametros.put("logo2",gto2.getImage());
       
        
     }
    
    public void gerarPDF(List<Object> list,String jasper){
        stream = this.getClass().getResourceAsStream("/hujbb/informatica/apac/util/relatorio/"+jasper+".jasper");
        baos = new ByteArrayOutputStream();
        try {
            
            JasperReport jr = (JasperReport) JRLoader.loadObject(stream);
            JasperPrint print = JasperFillManager.fillReport(jr, parametros,new JRBeanCollectionDataSource(list));
            JasperExportManager.exportReportToPdfStream(print, baos);
            //tretamento do arquivo pdf a ser gerado
            response.reset();
            response.setContentType("application/pdf");//tipo do arquivoF.setMsgErro("1");
            response.setContentLength(baos.size());//tamanho do arquivoF.setMsgErro("1");
            response.setHeader("Content-disposition", "inline; filename=formulario.pdf");//inline = abrir pelo propio navegador  attachment= forca o download
            response.getOutputStream().write(baos.toByteArray());//escreve os dados
            response.getOutputStream().flush();//descarrega o conteudo strean
            response.getOutputStream().close();//fecha o strean
            
            context.responseComplete();//informa pro jsf q a resposta http esta pronta
            
        } catch (JRException ex) {
            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
