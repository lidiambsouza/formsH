package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.dao.Ativ_desativ_usuarioDAO;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;

public class Usuario implements Serializable {

    //variaveis do banco
    private Integer id_usuario;
    private String nome;
    private String login;
    private String senha;
    private Setor setor;
    private Perfil perfil;
    private Integer ativo;
    private Cbo cbo;
    private Date dt_cadastro;

    //variaveis extras
    private String perfilString;
    private String ativoString;
    //variaveis para a tela
    private boolean logado = false;

    public Usuario(Integer id_usuario, String nome, String login, String senha, Setor setor, Perfil perfil, int ativo, Cbo cbo, Date dt_cadastro) {
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.setor = setor;
        this.perfil = perfil;
        this.ativo = ativo;
        this.cbo = cbo;
        this.dt_cadastro = dt_cadastro;
    }

    public Usuario() {
        this.id_usuario = -1;
        this.nome = "";
        this.login = "";
        this.senha = "";
        this.setor = new Setor();
        this.perfil = new Perfil();
        this.ativo = 1;//ativo
        this.cbo = new Cbo();
        this.dt_cadastro = null;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public int getAtivo() {
        if (ativo != 0) {
            ativo = 1;
        }
        return ativo;
    }

    public void setAtivo(int ativo) {

        this.ativo = ativo;
    }

    public void setPerfilString(String perfilString) {
        this.perfilString = perfilString;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public Cbo getCbo() {
        return cbo;
    }

    public void setCbo(Cbo cbo) {
        this.cbo = cbo;
    }

    public String getAtivoString() {
        switch (ativo) {
            case 0:
                ativoString = "Inativo";
                break;
            case 1:
                ativoString = "Ativo";
                break;
            default:
                ativoString = "";
        }
        return ativoString;
    }

    public void setAtivoString(String ativoString) {
        this.ativoString = ativoString;
    }

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd() {
        return 14;
    }

    public Ativ_desativ_usuario ativ_desativ_usuario() throws ErroSistema {
        List<Ativ_desativ_usuario> l = new Ativ_desativ_usuarioDAO().buscar("usuario.`id_usuario` = '" + getId_usuario() + "' AND motivo_ativ_desativ_usuario.`flag` = '" + getAtivo() + "'  ORDER BY ativ_desativ_usuario.`id` DESC");
        if (l != null) {
            if (l.isEmpty()) {
                return new Ativ_desativ_usuario();
            } else {
                return l.get(0);

            }
        } else {//erro ao buscar
            F.mensagem("Erro!", "Erro ao buscar motivo no banco de dados.", FacesMessage.SEVERITY_ERROR);
        }
        return new Ativ_desativ_usuario();

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id_usuario);
        hash = 31 * hash + Objects.hashCode(this.nome);
        hash = 31 * hash + Objects.hashCode(this.login);
        hash = 31 * hash + Objects.hashCode(this.senha);
        hash = 31 * hash + Objects.hashCode(this.setor);
        hash = 31 * hash + Objects.hashCode(this.perfil);
        hash = 31 * hash + this.ativo;
        hash = 31 * hash + (this.logado ? 1 : 0);
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
        final Usuario other = (Usuario) obj;
        if (this.logado != other.logado) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.id_usuario, other.id_usuario)) {
            return false;
        }
        if (!Objects.equals(this.setor, other.setor)) {
            return false;
        }
        if (!Objects.equals(this.perfil, other.perfil)) {
            return false;
        }
        if (!Objects.equals(this.ativo, other.ativo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", setor=" + setor + ", perfil=" + perfil + ", ativo=" + ativo + ", cbo=" + cbo + ", dt_cadastro=" + dt_cadastro + ", perfilString=" + perfilString + ", ativoString=" + ativoString + ", logado=" + logado + '}';
    }

}
