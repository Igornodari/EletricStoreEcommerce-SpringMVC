/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import utils.FormaPagamento;
import utils.StatusPedido;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	public Pedido() { };
	
    public Pedido(Integer id, int status, LocalDateTime data, int formaPagamento, int idCliente, double desconto,
			String cepEntrega, double frete) {
		this.id = id;
		this.status = status;
		this.data = data;
		this.formaPagamento = formaPagamento;
		this.idCliente = idCliente;
		this.desconto = desconto;
		this.cepEntrega = cepEntrega;
		this.frete = frete;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    @NotNull(message = "O status é obrigatório")
    private int status;

    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime data;

    @Column(name = "parcela")
    private int parcela;

    @Column(name = "formapagamento")
    private int formaPagamento;

    @NotNull(message = "O cliente é obrigatório")
    @Column(name = "idcliente")
    private int idCliente;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Cliente cliente;

	@Column(name = "desconto")
    private double desconto;
	
	@Column(name = "frete")
    private double frete;
    
    @Column(name = "cepentrega")
    private String cepEntrega;
    
    @Column(name = "numerocartao")
    private String numeroCartao;
    
    @OneToMany(mappedBy="pedido")
    List<ItensPedido> itensPedido;

    public double getFrete() {
		return frete;
	}

	public void setFrete(double frete) {
		this.frete = frete;
	}

	public List<ItensPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItensPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    /**
     * @return the parcela
     */
    public int getParcela() {
        return parcela;
    }

    /**
     * @param parcela the parcela to set
     */
    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    /**
     * @return the formaPagamento
     */
    public int getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * @param formaPagamento the formaPagamento to set
     */
    public void setFormaPagamento(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getCepEntrega() {
		return cepEntrega;
	}

	public void setCepEntrega(String cepEntrega) {
		this.cepEntrega = cepEntrega;
	}
	
	public double getValorTotal() {
		double total = 0;
		for(ItensPedido i : this.getItensPedido()) {
			total += i.getValorUnitario() * i.getQuantidade();
		}
		return (total + frete) - desconto;
	}
	
	public double getSubTotal() {
		double total = 0;
		for(ItensPedido i : this.getItensPedido()) {
			total += i.getValorUnitario() * i.getQuantidade();
		}
		return total;
	}
	
	public String getFormaPagamentoExtenso() {
		return FormaPagamento.formaPagamento(formaPagamento);
	}
	
	public String getStatusExtenso() {
		return StatusPedido.statusPedido(status);
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
		
}
