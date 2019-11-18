package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "imagem")
public class Imagem implements Serializable {

    public Imagem() {
    }

    public Imagem(int id, String nome, String caminho, String tipo, int idProduto) {
        this.id = id;
        this.nome = nome;
        this.caminho = caminho;
        this.tipo = tipo;
        this.idProduto = idProduto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    @NotBlank(message = "Campo nome é obrigatório")
    private String nome;

    @Column(name = "caminho", nullable = false)
    @NotBlank(message = "Campo nome é obrigatório")
    private String caminho;

    @Column(name = "tipo", nullable = false)
    @NotBlank(message = "Campo nome é obrigatório")
    private String tipo;

    @Column(name = "idproduto", nullable = false)
    private int idProduto;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Produto produto;

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
     * @return the caminho
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * @param caminho the caminho to set
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the idProduto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
}
