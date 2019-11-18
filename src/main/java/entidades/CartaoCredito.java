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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "cartaocredito")
public class CartaoCredito implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CartaoCredito() { }
	
	public CartaoCredito(Integer id, String nome, String numero, String validade, String codigoSeguranca, Integer idCliente) {
		this.id = id;
		this.nome = nome;
		this.numero = numero;
		this.validade = validade;
		this.codigoSeguranca = codigoSeguranca;
		this.idCliente = idCliente;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nome")
    @NotBlank(message = "Nome do titular do cartão é obrigatório")
    private String nome;
    
    @Column(name = "numero")
    @NotBlank(message = "Número do cartão é obrigatório")
    private String numero;
    
    @Column(name = "validade")
    @NotBlank(message = "Validade do cartão é obrigatório")
    private String validade;
    
    @Column(name = "codigoseguranca")
    @NotBlank(message = "CVV do cartão é obrigatório")
    private String codigoSeguranca;
    
    @Column(name = "idcliente")
    @NotNull
    private Integer idCliente;
    
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    public Cliente cliente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
    
}
