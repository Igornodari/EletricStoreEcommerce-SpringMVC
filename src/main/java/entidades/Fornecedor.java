package entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "fornecedor")
@Data
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    public Fornecedor() {
    }

    public Fornecedor(int id, String nome, String cnpj,
            boolean ativo, String cep, String logradouro,
            String numero, String complemento, String bairro,
            String cidade, String estado) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    @NotBlank(message = "Nome obrigatório")
    @Size(min = 5, max = 150)
    private String nome;

    @Column(name = "cnpj")
    @NotBlank(message = "Cnpj obrigatório")
    @Size(min = 14, max = 18, message = "O campo cnpj deve ter {max} caracteres")
    private String cnpj;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "cep")
    @NotBlank(message = "Cep obrigatório")
    @Size(min = 8, max = 8, message = "O campo cep deve ter {max} caracteres")
    private String cep;

    @Column(name = "logradouro")
    @NotBlank(message = "Logradouro obrigatório")
    @Size(min = 2, max = 80, message = "O campo logradouro deve ter entre {min} e {max} caracteres")
    private String logradouro;

    @Column(name = "numero")
    @NotBlank(message = "Numero obrigatório")
    private String numero;

    @Column
    private String complemento;

    @Column
    @NotBlank(message = "Bairro obrigatório")
    private String bairro;

    @Column
    @NotBlank(message = "Cidade obrigatório")
    private String cidade;

    @Column
    @NotBlank(message = "Estado obrigatório")
    private String estado;
    
    
        /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the ativo
     */
    public boolean getAtivo() {
        return ativo;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }
}
