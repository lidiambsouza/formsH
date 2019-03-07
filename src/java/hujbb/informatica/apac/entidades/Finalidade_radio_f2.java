package hujbb.informatica.apac.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Finalidade_radio_f2 implements Serializable {

    private int id;
    private String finalidade;

///variaveis para o pdf
    private String radical;
    private String paliativa;
    private String adjuvante;
    private String previa;
    private String antialgica;
    private String antiHemorragica;

    public Finalidade_radio_f2() {
        setId(-1);
        this.finalidade = "";

    }

    public Finalidade_radio_f2(int id, String finalidade) {
        setId(id);
        this.finalidade = finalidade;
    }

    public int getId() {
        if (id < 1) {
            id = -1;
        }
        return id;
    }
    
    public String otlFinalidadeString(){
        switch (id) {
            case 1: {//adjuvante
                return "ADJUVANTE";
            }
            case 2: {//antialgica
                return "ANTIÁLGICA";
            }
            case 3: {//antiemorragica
                return "ANTIHEMORRÁGICA";
            }
            case 4: {//paliativa
                return "PALIATIVA";
            }
            case 5: {//previa
                return "PRÉVIA";
            }
            case 6: {//radical
                return "RADICAL";
            }
            default: {
                return "";
            }
    }
    }

    public void setId(int id) {
        adjuvante = "";
        antialgica = "";
        antiHemorragica = "";
        paliativa = "";
        previa = "";
        radical = "";
        switch (id) {
            case 1: {//adjuvante
                adjuvante = "X";
                break;
            }
            case 2: {//antialgica
                antialgica = "X";
                break;
            }
            case 3: {//antiemorragica
                antiHemorragica = "X";
                break;
            }
            case 4: {//paliativa
                paliativa = "X";
                break;
            }
            case 5: {//previa
                previa = "X";
                break;
            }
            case 6: {//radical
                radical = "X";
                break;
            }
            default: {
                break;
            }

        }
        this.id = id;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = (finalidade==null)? "": finalidade.trim();
    }

    public String getRadical() {
        return radical;
    }

    public void setRadical(String radical) {
        this.radical = (radical==null)? "":radical.trim();
    }

    public String getPaliativa() {
        return paliativa;
    }

    public void setPaliativa(String paliativa) {
        this.paliativa = (paliativa==null)? "": paliativa.trim();
    }

    public String getAdjuvante() {
        return adjuvante;
    }

    public void setAdjuvante(String adjuvante) {
        this.adjuvante = (adjuvante==null)? "": adjuvante.trim();
    }

    public String getPrevia() {
        return previa;
    }

    public void setPrevia(String previa) {
        this.previa = (previa==null)? "": previa.trim();
    }

    public String getAntialgica() {
        return antialgica;
    }

    public void setAntialgica(String antialgica) {
        this.antialgica =(antialgica==null)? "": antialgica.trim();
    }

    public String getAntiHemorragica() {
        return antiHemorragica;
    }

    public void setAntiHemorragica(String antiHemorragica) {
        this.antiHemorragica = (antiHemorragica==null)? "": antiHemorragica.trim();
    }
//retorna true se a finalidade for fazia

    public boolean isVazio() {
        return (id <= 0);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.finalidade);
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
        final Finalidade_radio_f2 other = (Finalidade_radio_f2) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.finalidade, other.finalidade)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Finalidade_radio_f2{" + "id=" + id + ", finalidade=" + finalidade + '}';
    }

}
