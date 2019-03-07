package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Solicitante implements Serializable {

    private Integer id_solicitante;
    private String nome;
    private int tipo_doc;
    private String cns;
    private String cpf;
    private Usuario usuario;

    //variaveis extras
    private String tipo_doc_string;
    private String nun_doc;

    //p preencher pdf
    private String xcns;
    private String xcpf;

    private String mascaraCnsS;
    private String mascaraCpfS;

    public Solicitante(Integer id_solicitante, String nome, int tipo_doc, String cns, String cpf, Usuario usuario) {
        this.id_solicitante = id_solicitante;
        this.nome = nome;
        this.tipo_doc = tipo_doc;
        this.cns = cns;
        this.cpf = cpf;
        if (usuario == null) {
            usuario = new Usuario();
        }
        this.usuario = usuario;

    }

    public Solicitante() {
        this.id_solicitante = -1;
        this.nome = "";
        this.tipo_doc = 0;
        this.cns = "";
        this.cpf = "";
        this.usuario = new Usuario();

    }

    public Integer getId_solicitante() {
        return id_solicitante;
    }

    public void setId_solicitante(Integer id_solicitante) {
        this.id_solicitante = id_solicitante;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo_doc() {
        if (tipo_doc != 0) {
            tipo_doc = 1;
        }
        return tipo_doc;
    }

    public void setTipo_doc(int tipo_doc) {
        if (tipo_doc != 0) {
            tipo_doc = 1;
        }
        this.tipo_doc = tipo_doc;
    }

    public String getCns() {
        return cns.toUpperCase();
    }

    public void setCns(String cns) {
        this.cns = cns.replace(".", "");
    }

    public String getCpf() {
        return cpf.toUpperCase();
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo_doc_string() {
        if (id_solicitante == -1) {
            tipo_doc_string = "";
        } else {
            if (tipo_doc == 0) {
                tipo_doc_string = "CNS";
            } else {
                tipo_doc_string = "CPF";
            }
        }

        return tipo_doc_string;
    }

    public void setTipo_doc_string(String tipo_doc_string) {
        this.tipo_doc_string = tipo_doc_string;
    }

    public String mascaraCpf(String CpfS) {
        if (CpfS == null) {
            return mascaraCpfS = "0000000000";
        }

        if (!CpfS.isEmpty() && CpfS.length()>11) {
            String msCpf = CpfS;
            StringBuilder stringBuilder = new StringBuilder(msCpf);
            stringBuilder.insert(msCpf.length() - 4, '.');
            stringBuilder.insert(msCpf.length() - 7, '.');
            stringBuilder.insert(msCpf.length() - 11, '-');
            mascaraCpfS = stringBuilder.toString();

        } else {
            mascaraCpfS = CpfS;
        }

        return mascaraCpfS;
    }

    public String mascaraCnsSolicitante(String cnsS) {
        if (cnsS == null) {
            return mascaraCnsS = "0000000000000000";
        }
        if (!cnsS.isEmpty()&&cnsS.length()>12) {
            
            String mscns = cnsS;
            StringBuilder stringBuilder = new StringBuilder(mscns);
            stringBuilder.insert(mscns.length() - 4, '.');
            stringBuilder.insert(mscns.length() - 8, '.');
            stringBuilder.insert(mscns.length() - 12, '.');
            mascaraCnsS = stringBuilder.toString();

        } else {
            mascaraCnsS = cnsS;
        }

        return mascaraCnsS;
    }

    public String getNun_doc() {
        if (tipo_doc == 0) {
            nun_doc = mascaraCnsSolicitante(cns);
        } else {
            nun_doc = mascaraCpf(cpf);
        }
        if (nun_doc == null) {
            nun_doc = "";
        }
        return nun_doc;

    }

    public void setNun_doc(String nun_doc) {
        this.nun_doc = nun_doc;
    }

    public String getXcns() {
        xcns = "";
        if (tipo_doc == 0) {
            xcns = "x";
        }
        return xcns.toUpperCase();
    }

    public void setXcns(String xcns) {

        this.xcns = xcns;
    }

    public String getXcpf() {
        xcpf = "";
        if (tipo_doc != 0) {
            xcpf = "x";
        }
        return xcpf.toUpperCase();
    }

    public void setXcpf(String xcpf) {
        this.xcpf = xcpf;
    }

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd() {
        return 12;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id_solicitante);
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
        final Solicitante other = (Solicitante) obj;
        if (!Objects.equals(this.id_solicitante, other.id_solicitante)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Solicitante{" + "id_solicitante=" + id_solicitante + ", nome=" + nome + ", tipo_doc=" + tipo_doc + ", cns=" + cns + ", cpf=" + cpf + ", usuario=" + usuario.toString() + ", tipo_doc_string=" + tipo_doc_string + ", nun_doc=" + nun_doc + ", xcns=" + xcns + ", xcpf=" + xcpf + '}';
    }

}
