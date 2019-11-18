package entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoSelecionado {
	private Produto produto;

	private int quantidade;

	private BigDecimal precoFinal;

	private LocalDateTime dataHoraInclusao;
	
	public ProdutoSelecionado() {

	}

	public ProdutoSelecionado(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataHoraInclusao = LocalDateTime.now();
	}

	public Produto getItem() {
		return produto;
	}

	public void setItem(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}

	public LocalDateTime getDataHoraInclusao() {
		return dataHoraInclusao;
	}

	public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
		this.dataHoraInclusao = dataHoraInclusao;
	}

	public double getSubtotal() {
		return produto.getPrecoVenda() * quantidade;
	}
}
