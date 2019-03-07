package hujbb.informatica.apac.entidades;

import hujbb.informatica.apac.util.F;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Paciente implements Serializable {

    //variaveis do banco de dados
    private Integer id_paciente = -1;
    private String num_prontuario = "";
    private String nome = "";
    private String sexo = "";
    private String cns = "";
    private Date data_nascimento = new Date("01/01/1900");
   
    private String cor = "";
    private String etnia = "";
    private String nome_mae = "";
    private String telefone_mae = "";
    private String nome_responsavel = "";
    private String telefone_responsavel = "";
    private Float peso = (float) -1;
    private Float altura = (float) -1;
    private String logradouro = "";
    private String num_residencia = "";
    private String bairro = "";
    private String municipio = "";
    private String cod_ibge_municipio = "";
    private String uf = "";
    private String cep = "";
    private String mascaraCep = "";
    private String mascaraCns = "";
    private String mascaraTelmae = "";

    private Date data_obito = null;

    //variaveis para preencher a tela
    private String data_nascimento_s;
    private String endereco;

    //paviaveis para preenche pdf ireport
    private String sexoM;
    private String sexoF;
    private String corPdf;
    private String etiniaPdf;

    public Paciente(Integer id_paciente, String num_prontuario, String nome, String sexo, String cns, Date data_nascimento, String cor, String etnia, String nome_mae, String telefone_mae, String nome_responsavel, String telefone_responsavel, Float peso, Float altura, String logradouro, String num_residencia, String bairro, String municipio, String cod_ibge_municipio, String uf, String cep, Date data_obito) {
        this.id_paciente = id_paciente;
        this.num_prontuario = num_prontuario;
        this.nome = nome;
        this.sexo = sexo;
        this.cns = cns;
        this.data_nascimento = data_nascimento;
        this.cor = cor;
        this.etnia = etnia;
        this.nome_mae = nome_mae;
        this.telefone_mae = telefone_mae;
        this.nome_responsavel = nome_responsavel;
        this.telefone_responsavel = telefone_responsavel;
        this.peso = peso;
        this.altura = altura;
        this.logradouro = logradouro;
        this.num_residencia = num_residencia;
        this.bairro = bairro;
        this.municipio = municipio;
        this.cod_ibge_municipio = cod_ibge_municipio;
        this.uf = uf;
        this.cep = cep;
        this.data_obito = data_obito;

    }

    public Paciente() {
         this.id_paciente = -1;
        this.num_prontuario = "";
        this.nome = "";
        this.sexo = "";
        this.cns = "";
        this.data_nascimento = null;
        this.cor = "";
        this.etnia = "";
        this.nome_mae = "";
        this.telefone_mae = "";
        this.nome_responsavel = "";
        this.telefone_responsavel = "";
        this.peso = (float)0;
        this.altura = (float)0;
        this.logradouro = "";
        this.num_residencia = "";
        this.bairro = "";
        this.municipio = "";
        this.cod_ibge_municipio = "";
        this.uf = "";
        this.cep = "";
        this.data_obito = null;
    }

    //retorna o numedo da entidade na tabela entidade no banco de dados
    public int nun_entidade_bd() {
        return 9;
    }

    public String getEndereco() {
        endereco = logradouro + getNum_residencia() + getBairro();
        return endereco.toUpperCase();
    }

    public void setEndereco(String endereco) {
        if (endereco == null) {
            endereco = "";
        }
        this.endereco = endereco;
    }

    public Integer getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(Integer id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNum_prontuario() {
        return num_prontuario.toUpperCase();
    }

    public void setNum_prontuario(String num_prontuario) {
        if (num_prontuario == null) {
            num_prontuario = "";
        }
        this.num_prontuario = num_prontuario;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        if (nome == null) {
            nome = "";
        }
        this.nome = nome;
    }

    public String getSexo() {
        return sexo.toUpperCase();
    }

    public void setSexo(String sexo) {
        if (sexo == null) {
            sexo = "";
        }
        this.sexo = sexo;
    }

    public String getCns() {
        if (cns == null) {
            cns = "";
        }
        return cns;
    }

    public void setCns(String cns) {
        if (cns == null) {
            cns = "";
        }
        this.cns = cns;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {

        this.data_nascimento = data_nascimento;
    }

    public String getCor() {
        return cor.toUpperCase();
    }

    public void setCor(String cor) {
        if (cor == null) {
            cor = "";
        }
        this.cor = cor;
    }

    public String getCorPdf() {
        String retorno = "";

        switch (cor.toLowerCase()) {
            case "m": {
                retorno = "Preta";
                break;
            }
            case "b": {
                retorno = "Branca";
                break;
            }
            case "p": {
                retorno = "Parda";
                break;
            }
            case "a": {
                retorno = "Amarela";
                break;
            }
            case "i": {
                retorno = "Indígena";
                break;
            }
            default:
                break;
        }
        return retorno.toUpperCase();
    }

    public void setCorPdf(String corPdf) {
        cor = corPdf;
        /*if(corPdf ==  null){
            corPdf = "";
        }*/
        this.corPdf = corPdf;
    }

    public String getEtnia() {

        return etnia.toUpperCase();
    }

    public void setEtnia(String etnia) {

        if (etnia == null) {
            etnia = "";
        }

        this.etnia = etnia;
    }

    public String getNome_mae() {
        return nome_mae.toUpperCase();
    }

    public void setNome_mae(String nome_mae) {
        this.nome_mae = nome_mae;
    }

    public String getTelefone_mae() {
        return telefone_mae.toUpperCase();
    }

    public void setTelefone_mae(String telefone_mae) {
        this.telefone_mae = telefone_mae;
    }

    public String getNome_responsavel() {
        return nome_responsavel.toUpperCase();
    }

    public void setNome_responsavel(String nome_responsavel) {
        this.nome_responsavel = nome_responsavel;
    }

    public String getTelefone_responsavel() {
        return telefone_responsavel.toUpperCase();
    }

    public void setTelefone_responsavel(String telefone_responsavel) {
        this.telefone_responsavel = telefone_responsavel;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {

        this.altura = altura;
    }

    public String getLogradouro() {
        return logradouro.toUpperCase();
    }

    public void setLogradouro(String logradouro) {
        if (logradouro == null) {
            logradouro = "";
        }
        this.logradouro = logradouro;
    }

    public String getNum_residencia() {

        if (!num_residencia.isEmpty()) {
            return " Nº" + num_residencia.toUpperCase().replace("Nº", "");
        }
        return num_residencia.toUpperCase();
    }

    public void setNum_residencia(String num_residencia) {
        if (num_residencia == null) {
            num_residencia = "";
        }
        this.num_residencia = num_residencia;
    }

    public String getBairro() {
        return " " + bairro.toUpperCase();
    }

    public void setBairro(String bairro) {
        if (bairro == null) {
            bairro = "";
        }
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio.toUpperCase();
    }

    public void setMunicipio(String municipio) {
        if (municipio == null) {
            municipio = "";
        }
        this.municipio = municipio;
    }

    public String getCod_ibge_municipio() {
        if (cod_ibge_municipio.equals("0")) {
            cod_ibge_municipio = "";
        }
        return cod_ibge_municipio.toUpperCase();
    }

    public void setCod_ibge_municipio(String cod_ibge_municipio) {
        if (cod_ibge_municipio == null) {
            cod_ibge_municipio = "";
        }
        this.cod_ibge_municipio = cod_ibge_municipio;
    }

    public String getUf() {

        return uf.toUpperCase();
    }

    public void setUf(String uf) {
        if (uf == null) {
            uf = "";
        }
        this.uf = uf;
    }

    public String getCep() {
        if (cep.equals("0")) {
            cep = "";
        }
        
        return cep.toUpperCase();
    }

    public void setCep(String cep) {
        if (cep == null) {
            cep = "";
        }
        this.cep = cep;
    }
    
    public String getMascaraTelmae() {
        if (!getTelefone_mae().isEmpty()) {
            if("0 0".equals(getTelefone_mae())){
                
                mascaraTelmae = "";
            }else{
                mascaraTelmae=getTelefone_mae();
            }
        } else {
            mascaraTelmae = "";
        }
        return mascaraTelmae;
    }

    public void setMascaraTelmae(String mascaraTelmae) {
        this.mascaraTelmae = mascaraTelmae;
    }
    
    public String getMascaraCep() {
        if (!getCep().isEmpty()) {
            String mscep = getCep();
           

            StringBuilder stringBuilder = new StringBuilder(mscep);
            stringBuilder.insert(mscep.length() - 6, '.');
            stringBuilder.insert(mscep.length() - 2, '-');//
            mascaraCep = stringBuilder.toString();

        } else {
            mascaraCep = "";
        }
        return mascaraCep;
    }

    public void setMascaraCep(String mascaraCep) {
        this.mascaraCep = mascaraCep;
    }

    public String getMascaraCns() {
        if(!getCns().isEmpty()){
            String mscns=getCns();
            StringBuilder stringBuilder = new StringBuilder(mscns);
            stringBuilder.insert(mscns.length() - 4, '.');
            stringBuilder.insert(mscns.length() - 8, '.');
            stringBuilder.insert(mscns.length() - 12, '.');
            mascaraCns = stringBuilder.toString();
        
        }else{
            mascaraCns="";
        }
        
        return mascaraCns;
    }

    public void setMascaraCns(String mascaraCns) {
        this.mascaraCns = mascaraCns;
    }
    

    
    public String getData_nascimento_s() {
        data_nascimento_s = F.dataString(data_nascimento);
        if (data_nascimento_s.equals("01/01/1900")) {
            data_nascimento_s = "";
        }
        return data_nascimento_s.toUpperCase();
    }

    public void setData_nascimento_s(String data_nascimento_s) {
        if (data_nascimento_s == null) {
            data_nascimento_s = "";
        }
        this.data_nascimento_s = data_nascimento_s;
    }

    public String getSexoM() {
        sexoM = "";
        if (getSexo().equals("M")) {
            sexoM = "x";
        }
        return sexoM.toUpperCase();
    }

    public void setSexoM(String sexoM) {
        if (sexoM == null) {
            sexoM = "";
        }
        this.sexoM = sexoM;
    }

    public String getSexoF() {
        sexoF = "";
        if (getSexo().equals("F")) {
            sexoF = "x";
        }
        return sexoF.toUpperCase();
    }

    public void setSexoF(String sexoF) {
        if (sexoF == null) {
            sexoF = "";
        }
        this.sexoF = sexoF;
    }

    public Date getData_obito() {
        return data_obito;
    }

    public void setData_obito(Date data_obito) {
        this.data_obito = data_obito;
    }

    public String getEtiniaPdf() {
        return etiniaPdf;
    }

    public void setEtiniaPdf(String etiniaPdf) {
        this.etiniaPdf = etiniaPdf;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id_paciente);
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
        final Paciente other = (Paciente) obj;
        if (!Objects.equals(this.num_prontuario, other.num_prontuario)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.cns, other.cns)) {
            return false;
        }
        if (!Objects.equals(this.cor, other.cor)) {
            return false;
        }
        if (!Objects.equals(this.etnia, other.etnia)) {
            return false;
        }
        if (!Objects.equals(this.nome_mae, other.nome_mae)) {
            return false;
        }
        if (!Objects.equals(this.telefone_mae, other.telefone_mae)) {
            return false;
        }
        if (!Objects.equals(this.nome_responsavel, other.nome_responsavel)) {
            return false;
        }
        if (!Objects.equals(this.telefone_responsavel, other.telefone_responsavel)) {
            return false;
        }
        if (!Objects.equals(this.logradouro, other.logradouro)) {
            return false;
        }
        if (!Objects.equals(this.num_residencia, other.num_residencia)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.municipio, other.municipio)) {
            return false;
        }
        if (!Objects.equals(this.cod_ibge_municipio, other.cod_ibge_municipio)) {
            return false;
        }
        if (!Objects.equals(this.uf, other.uf)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.mascaraCep, other.mascaraCep)) {
            return false;
        }
        if (!Objects.equals(this.mascaraCns, other.mascaraCns)) {
            return false;
        }
        if (!Objects.equals(this.mascaraTelmae, other.mascaraTelmae)) {
            return false;
        }
        if (!Objects.equals(this.data_nascimento_s, other.data_nascimento_s)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.sexoM, other.sexoM)) {
            return false;
        }
        if (!Objects.equals(this.sexoF, other.sexoF)) {
            return false;
        }
        if (!Objects.equals(this.corPdf, other.corPdf)) {
            return false;
        }
        if (!Objects.equals(this.etiniaPdf, other.etiniaPdf)) {
            return false;
        }
        if (!Objects.equals(this.id_paciente, other.id_paciente)) {
            return false;
        }
        if (!Objects.equals(this.data_nascimento, other.data_nascimento)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.altura, other.altura)) {
            return false;
        }
        if (!Objects.equals(this.data_obito, other.data_obito)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "Paciente{" + "id_paciente=" + id_paciente + ", num_prontuario=" + num_prontuario + ", nome=" + nome + ", sexo=" + sexo + ", cns=" + cns + ", data_nascimento=" + data_nascimento + ", cor=" + cor + ", etnia=" + etnia + ", nome_mae=" + nome_mae + ", telefone_mae=" + telefone_mae + ", nome_responsavel=" + nome_responsavel + ", telefone_responsavel=" + telefone_responsavel + ", peso=" + peso + ", altura=" + altura + ", logradouro=" + logradouro + ", num_residencia=" + num_residencia + ", bairro=" + bairro + ", municipio=" + municipio + ", cod_ibge_municipio=" + cod_ibge_municipio + ", uf=" + uf + ", cep=" + cep + ", data_nascimento_s=" + data_nascimento_s + ", endereco=" + endereco + ", sexoM=" + sexoM + ", sexoF=" + sexoF + ", corPdf=" + corPdf + '}';
    }

}
