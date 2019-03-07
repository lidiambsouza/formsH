package hujbb.informatica.apac.bean;

import hujbb.informatica.apac.util.CargaSIGTAP;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@RequestScoped
public class ImportarBean {

    private int contBarraProTotal;

    private UploadedFile up;

    private String caminho = "";

    //controle de tela
    private boolean disable_btProcessarCarga;
    private boolean rendere_progBar;

    @PostConstruct
    public void init() {
        disable_btProcessarCarga = true;
    }

    public void doUpload(FileUploadEvent e) throws ErroSistema {
        this.up = e.getFile();
//opcao true = salvar false = atualizar
        String fileNameUploaded = up.getFileName(); //nome
        long fileSizeUploaded = up.getSize(); //tamanho

        if (verificarArqUp(fileNameUploaded)) {
            //pega o caminho relativo
            caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("")
                    + "arqtemp/" + fileNameUploaded;

            //cria uma copia do arquivo no servidor no na pasta padrao arqTemp
            File f = new File(caminho);

            OutputStream os = null;
            InputStream is = null;

            try {
                is = up.getInputstream();
                byte[] b = new byte[is.available()];
                os = new FileOutputStream(f);

                while (is.read(b) > 0) {
                    os.write(b);
                }

                os.flush();
                os.close();
                is.close();

            } catch (IOException ex) {
                F.mensagem("60", ex.toString(), FacesMessage.SEVERITY_ERROR);
                Logger.getLogger(ImportarBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            disable_btProcessarCarga = false;
            FabricaDeConexoes.fecharConecxao();

        } //if 1
    }

    public void btProcessarCarga() throws ErroSistema {
        //chama a funcao que processa a carga
        F.intAux = 0;
        if (isTodosTxt()) {
            new CargaSIGTAP(caminho);
        }
        
        FabricaDeConexoes.fecharConecxao();
    }

    public boolean isRendere_progBar() {
        return rendere_progBar;
    }

    public void setRendere_progBar(boolean rendere_progBar) {
        this.rendere_progBar = rendere_progBar;
    }

    public UploadedFile getUp() {
        return up;
    }

    public void setUp(UploadedFile up) {
        this.up = up;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getContBarraProTotal() {
        
        contBarraProTotal = F.intAux;
        return contBarraProTotal;
    }

    public void setContBarraProTotal(int contBarraProTotal) {
        this.contBarraProTotal = contBarraProTotal;
    }

    public boolean isDisable_btProcessarCarga() {

        return disable_btProcessarCarga;
    }

    public void setDisable_btProcessarCarga(boolean disable_btProcessarCarga) {
        this.disable_btProcessarCarga = disable_btProcessarCarga;
    }

    public boolean verificarArqUp(String nomeArq) {
        if (nomeArq.equals("tb_grupo.txt")
                || nomeArq.equals("tb_grupo_layout.txt")
                || nomeArq.equals("tb_sub_grupo.txt")
                || nomeArq.equals("tb_sub_grupo_layout.txt")
                || nomeArq.equals("tb_forma_organizacao.txt")
                || nomeArq.equals("tb_forma_organizacao_layout.txt")
                || nomeArq.equals("tb_ocupacao.txt")
                || nomeArq.equals("tb_ocupacao_layout.txt")
                || nomeArq.equals("tb_cid.txt")
                || nomeArq.equals("tb_cid_layout.txt")
                || nomeArq.equals("tb_financiamento.txt")
                || nomeArq.equals("tb_financiamento_layout.txt")
                || nomeArq.equals("tb_modalidade.txt")
                || nomeArq.equals("tb_modalidade_layout.txt")
                || nomeArq.equals("tb_procedimento.txt")
                || nomeArq.equals("tb_procedimento_layout.txt")
                || nomeArq.equals("rl_procedimento_modalidade.txt")
                || nomeArq.equals("rl_procedimento_modalidade_layout.txt")
                || nomeArq.equals("rl_procedimento_ocupacao.txt")
                || nomeArq.equals("rl_procedimento_ocupacao_layout.txt")
                || nomeArq.equals("rl_procedimento_cid.txt")
                || nomeArq.equals("rl_procedimento_cid_layout.txt")
                || nomeArq.equals("tb_registro.txt")
                || nomeArq.equals("tb_registro_layout.txt")
                || nomeArq.equals("rl_procedimento_registro.txt")
                || nomeArq.equals("rl_procedimento_registro_layout.txt")
                || nomeArq.equals("tb_descricao.txt")
                || nomeArq.equals("tb_descricao_layout.txt")) {

            return true;
        }

        return false;
    }

    //verifica se todos os txt existem antes de dar a carga
    public boolean isTodosTxt() {
        String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("")
                + "arqtemp/";

        if (!new File(caminho + "tb_grupo.txt").exists()) {F.setMsgErro(caminho);
            F.mensagem("Arquivo \"tb_grupo.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }

        if (!new File(caminho + "tb_grupo_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_grupo_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_sub_grupo.txt").exists()) {
            F.mensagem("Arquivo \"tb_sub_grupo.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_sub_grupo_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_sub_grupo_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_forma_organizacao.txt").exists()) {
            F.mensagem("Arquivo \"tb_forma_organizacao.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_forma_organizacao_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_forma_organizacao_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_ocupacao.txt").exists()) {
            F.mensagem("Arquivo \"tb_ocupacao.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_ocupacao_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_ocupacao_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_cid.txt").exists()) {
            F.mensagem("Arquivo \"tb_cid.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_cid_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_cid_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_financiamento.txt").exists()) {
            F.mensagem("Arquivo \"tb_financiamento.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_financiamento_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_financiamento_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_modalidade.txt").exists()) {
            F.mensagem("Arquivo \"tb_modalidade.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_modalidade_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_modalidade_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_procedimento.txt").exists()) {
            F.mensagem("Arquivo \"tb_procedimento.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_procedimento_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_procedimento_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_modalidade.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_modalidade.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_modalidade_layout.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_modalidade_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_ocupacao.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_ocupacao.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_ocupacao_layout.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_ocupacao_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_cid.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_cid.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_cid_layout.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_cid_layout.txt\" ", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_registro.txt").exists()) {
            F.mensagem("Arquivo \"tb_registro.txt\"", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_registro_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_registro_layout.txt\"", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_registro.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_registro.txt\"", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "rl_procedimento_registro_layout.txt").exists()) {
            F.mensagem("Arquivo \"rl_procedimento_registro_layout.txt\"", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_descricao.txt").exists()) {
            F.mensagem("Arquivo \"tb_descricao.txt\"", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if (!new File(caminho + "tb_descricao_layout.txt").exists()) {
            F.mensagem("Arquivo \"tb_descricao_layout.txt\"", "não encontrado!", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        return true;
    }
}
