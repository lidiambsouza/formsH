package hujbb.informatica.apac.bean.dlg;

import hujbb.informatica.apac.dao.SolicitanteDAO;
import hujbb.informatica.apac.dao.UsuarioDAO;
import hujbb.informatica.apac.entidades.Solicitante;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DlgCargaSolicitanteBean implements Serializable {

    private SolicitanteDAO dao;

    private String vitaDlgCarga;

    private List<Solicitante> solicitantes;

    public void init() {
        solicitantes = new ArrayList<>();
    }

    public void cargaSolicitante(String carga) throws ErroSistema {
        solicitantes = new ArrayList<>();
        String msg = "";
        UsuarioDAO uDAO = new UsuarioDAO();

        Solicitante solicitanteAux;
        carga =  carga.replace(",", ";");
        String[] linhas = carga.split("#");
        for (String s : linhas) { //for 1
            
            String[] dados = s.split(";");
            Solicitante solicitante = new Solicitante();
            solicitante.setNome(dados[0]);
            solicitante.getUsuario().setNome(dados[0]);
            solicitante.getUsuario().setLogin(dados[1]);
            solicitante.setCpf(dados[2].replace(".", "").replace("-", ""));
            solicitante.setCns(dados[3].replace("a", ""));
            solicitante.getUsuario().getCbo().setCod(dados[4]);
            solicitante.getUsuario().getSetor().setNome(dados[5]);
            try {
                solicitante.getUsuario().getPerfil().setId_perfil(Integer.parseInt(dados[7]));
            } catch (NumberFormatException e) {
                solicitante.getUsuario().getPerfil().setId_perfil(2);//adm financeiro
            }
            try {
                solicitante.getUsuario().setAtivo(Integer.parseInt(dados[9]));

            } catch (NumberFormatException e) {
                solicitante.getUsuario().setAtivo(0);//inativo
            }
            solicitante.getUsuario().setDt_cadastro(new Date());//inativo
            try {
                
                    

                    solicitanteAux = getDao().salvar(solicitante);
                    if (solicitanteAux == null) {
                        solicitanteAux = solicitante;
                        solicitanteAux.getUsuario().setSenha("Não Inserido");
                    } else {
                        solicitanteAux.getUsuario().setSenha("Inserido");
                    }

                

                solicitantes.add(solicitanteAux);
            } catch (ErroSistema e) {
                F.setMsgErro(e.toString() + "solicitantebean:cargasolicitante()");
            }

        } // fim for1
FabricaDeConexoes.fecharConecxao();
    }

    public void limpDlgCargaSolicitante() {
        if (solicitantes.size() > 0) {
            solicitantes.clear();
            vitaDlgCarga = "";
        }
    }

    public String getVitaDlgCarga() {
        return vitaDlgCarga;
    }

    public void setVitaDlgCarga(String vitaDlgCarga) {
        this.vitaDlgCarga = vitaDlgCarga;
    }

    public List<Solicitante> getSolicitantes() {
        if (solicitantes == null) {
            solicitantes = new ArrayList<>();
        }
        return solicitantes;
    }

    public void setSolicitantes(List<Solicitante> solicitantes) {
        this.solicitantes = solicitantes;
    }

    public SolicitanteDAO getDao() {
        if (dao == null) {
            dao = new SolicitanteDAO();
        }
        return dao;
    }

    public Solicitante criarNovaEntidade() {
        return new Solicitante();
    }

}
