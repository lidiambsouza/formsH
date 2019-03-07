package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Formulario_has_procedimento_sus implements Serializable{

    
    private Formulario formulario = new Formulario();
    private Procedimento_sus procedimento_sus =  new Procedimento_sus();
    private Integer quantidade =-1;
    private int posicao ;

    public Formulario_has_procedimento_sus( Formulario formulario, Procedimento_sus procedimento_sus, Integer quantidade,int posicao) {
       
        this.formulario = formulario;
        this.procedimento_sus = procedimento_sus;
        this.quantidade = quantidade;
        this.posicao = posicao;
    }

    public Formulario_has_procedimento_sus() {
    }

   

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd(){
        return 8;
    }

    public Formulario getFormulario() {
        if(formulario == null){
            formulario = new Formulario();
        }
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public Procedimento_sus getProcedimento_sus() {
        if(procedimento_sus == null){
            procedimento_sus = new Procedimento_sus();
        }
        return procedimento_sus;
    }

    public void setProcedimento_sus(Procedimento_sus procedimento_sus) {
        this.procedimento_sus = procedimento_sus;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.formulario);
        hash = 59 * hash + Objects.hashCode(this.procedimento_sus);
        hash = 59 * hash + Objects.hashCode(this.quantidade);
        hash = 59 * hash + this.posicao;
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
        final Formulario_has_procedimento_sus other = (Formulario_has_procedimento_sus) obj;
        if (this.posicao != other.posicao) {
            return false;
        }
        if (!Objects.equals(this.formulario, other.formulario)) {
            return false;
        }
        if (!Objects.equals(this.procedimento_sus, other.procedimento_sus)) {
            return false;
        }
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formulario_has_procedimento_sus{" + "formulario=" + formulario + ", procedimento_sus=" + procedimento_sus + ", quantidade=" + quantidade + ", posicao=" + posicao + '}';
    }

    

    
    
}
