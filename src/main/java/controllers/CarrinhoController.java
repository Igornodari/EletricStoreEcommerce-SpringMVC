/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entidades.CartaoCredito;
import entidades.Cliente;
import entidades.Endereco;
import entidades.Imagem;
import entidades.ItensPedido;
import entidades.Pedido;
import entidades.Produto;
import entidades.ProdutoSelecionado;

import java.awt.Image;
import java.io.Console;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import repositorios.IClienteRepositorio;
import repositorios.IEnderecoRepositorio;
import repositorios.IImagemRepositorio;
import repositorios.IItensPedidoRepositorio;
import repositorios.IPedidoRepositorio;
import repositorios.IProdutoRepositorio;
import utils.FormaPagamento;

@Controller
@Scope("session")
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final IProdutoRepositorio produtoRepositorio;
    private final IItensPedidoRepositorio itensPedidoRepositorio;
    private final IPedidoRepositorio pedidoRepositorio;
    private final IEnderecoRepositorio enderecoRepositorio;
    private final IImagemRepositorio imagemRepositorio;
    
    private List<ProdutoSelecionado> produtosSelecionados = new ArrayList<>();
	private int valorFrete = 0;
	private int valorDesconto = 0;
	private Endereco endereco = null;
	private String cepEntrega = "0";
	private CartaoCredito cartao = new CartaoCredito();
	private int idUsuario = 0;
	private int formaPagamento = 0;
	private int parcela = 0;

    @Autowired
    public CarrinhoController(IProdutoRepositorio produtoRepositorio, IItensPedidoRepositorio itensPedidoRepositorio,
    		IPedidoRepositorio pedidoRepositorio, IEnderecoRepositorio enderecoRepositorio,IImagemRepositorio imagemRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
        this.itensPedidoRepositorio = itensPedidoRepositorio;
        this.pedidoRepositorio = pedidoRepositorio;
        this.enderecoRepositorio = enderecoRepositorio;
        this.imagemRepositorio= imagemRepositorio;
    }

    @GetMapping
    public ModelAndView mostrar() {
        return new ModelAndView("carrinho");
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhesProduto(@PathVariable("id") Integer id) {
        ModelAndView view = new ModelAndView("detalhes-produto");
        Produto produto = (produtoRepositorio.obterPorId(id));
        produto.setImagePath(produto.getImagens().get(0).getCaminho());

        view.addObject("produto", produto);
        
        return view;
    }
    

	@PostMapping
	public ModelAndView adicionar(@RequestParam("itemId") int itemId, RedirectAttributes redirAttr) {
		Produto p = produtoRepositorio.obterPorId(itemId);
		produtosSelecionados.add(new ProdutoSelecionado(p, 1));
		return new ModelAndView("redirect:/carrinho");
	}
	
	@PostMapping("{listIndex}/alterar")
	public ModelAndView alterarQtd(@PathVariable("listIndex") int listIndex, @RequestParam("qtd") int quantidade, RedirectAttributes redirAttr) {
		ProdutoSelecionado sel = produtosSelecionados.get(listIndex);
		if (quantidade > 0) {
			sel.setQuantidade(quantidade);
			redirAttr.addFlashAttribute("msg", "Quantidade do item '" + sel.getItem().getNome() + "' alterada");
		} else {
			produtosSelecionados.remove(listIndex);
			redirAttr.addFlashAttribute("msg", "Item '" + sel.getItem().getNome() + "' removido");
		}
		return new ModelAndView("redirect:/carrinho");
	}
	
	@PostMapping("{listIndex}/incrementar")
	public ModelAndView incrementarQtd(@PathVariable("listIndex") int listIndex, RedirectAttributes redirAttr) {
		ProdutoSelecionado sel = produtosSelecionados.get(listIndex);
		int quantidadeAtual = sel.getQuantidade();
		quantidadeAtual++;
		sel.setQuantidade(quantidadeAtual);
		redirAttr.addFlashAttribute("msg", "Quantidade do produto '" + sel.getItem().getNome() + "' incrementada");
		return new ModelAndView("redirect:/carrinho");
	}
	
	@PostMapping("{listIndex}/decrementar")
	public ModelAndView decrementarQtd(@PathVariable("listIndex") int listIndex, RedirectAttributes redirAttr) {
		ProdutoSelecionado sel = produtosSelecionados.get(listIndex);
		int quantidadeAtual = sel.getQuantidade();
		quantidadeAtual--;
		if (quantidadeAtual > 0) {
			sel.setQuantidade(quantidadeAtual);
			redirAttr.addFlashAttribute("msg", "Quantidade do item '" + sel.getItem().getNome() + "' decrementada");
		} else {
			produtosSelecionados.remove(listIndex);
			redirAttr.addFlashAttribute("msg", "Item '" + sel.getItem().getNome() + "' removido");
		}
		return new ModelAndView("redirect:/carrinho");
	}
	
	@PostMapping("{listIndex}/remover")
	public ModelAndView remover(@PathVariable("listIndex") int listIndex, RedirectAttributes redirAttr) {
		ProdutoSelecionado sel = produtosSelecionados.remove(listIndex);
		redirAttr.addFlashAttribute("msg", "Produto '" + sel.getItem().getNome() + "' removido");
		return new ModelAndView("redirect:/carrinho");
	}
	
	@PostMapping("/frete")
	public ModelAndView calcularFrete(@RequestParam("cep") String cep, HttpSession session) {
		if ("04863-450".equals(cep)) {
			valorFrete = 30;
		} else if ("04578-910".equals(cep)) {
			valorFrete = 20;
		} else if ("22222-222".equals(cep)) {
			valorFrete = 50;
		} else if ("04583-110".equals(cep)) {
			valorFrete = 10;
		} else {
			valorFrete = 25;
		}
		
		return new ModelAndView("redirect:/carrinho");
	}
	
	@PostMapping("/cupom")
	public ModelAndView adicionarCupom(@RequestParam("cupom") String cupom, RedirectAttributes redirAttr) {
		if ("CATDOG20OFF".equals(cupom)) {
			valorDesconto = 20;
		} else {
			redirAttr.addFlashAttribute("msg", "Cupom inválido, verifique o código digitado");
		}
		return new ModelAndView("redirect:/carrinho/resumo-pedido");
	}
	
	@GetMapping("/resumo-pedido")
	public ModelAndView resumo(HttpSession session, RedirectAttributes redirAttr) {
		ModelAndView view = new ModelAndView("venda/resumo-venda");

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		if(cliente != null) {
			cliente.setEnderecos(enderecoRepositorio.findByIdCliente(cliente.getId()));
			view.addObject("enderecos", cliente.getEnderecos());
			return view;
		} else {
			return new ModelAndView("redirect:/login");
		}
	}
	
	@PostMapping("/seleciona-endereco")
	public ModelAndView selecionaEndereco(@RequestParam("cep") String cep, HttpSession session) {
		ModelAndView view = new ModelAndView("venda/resumo-venda");
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		if(cliente != null) {
			this.endereco = enderecoRepositorio.findByCepIdCliente(cliente.getId(), cep.replace("-", ""));
			cepEntrega = endereco.getCep();
			valorFrete = 10;
			return new ModelAndView("redirect:/carrinho/resumo-pedido");
		}
		return view;
	}
	
	@GetMapping("/forma-pagamento")
	public ModelAndView mostraFormaPagamento(CartaoCredito cartao) {
		return new ModelAndView("venda/forma-pagamento");
	}
	
	@PostMapping("/forma-pagamento")
	public ModelAndView selecionaFormaPagamento(HttpSession session, @RequestParam("cartao") String cartao, 
			@RequestParam("boleto") String boleto, @RequestParam("parcela") int parcela) {
		if(boleto.equals("")) {
			this.formaPagamento = 1;
			this.cartao.setNumero(cartao);
			this.setParcela(parcela);
		} else {
			this.formaPagamento = 3;
			this.setParcela(0);
		}
		return new ModelAndView("redirect:/carrinho/resumo-pedido");
	}
	
	@PostMapping("/finalizar")
	public ModelAndView finalizarPedido(RedirectAttributes redirAttr, HttpSession session) {
		Cliente clienteLogado = (Cliente) session.getAttribute("usuarioLogado");

		if(clienteLogado == null) {
			redirAttr.addFlashAttribute("msg", "Por favor faça o login antes de finalizar o pedido");
			return new ModelAndView("redirect:/login");
		}
		
		Pedido pedido = new Pedido(0, 1, LocalDateTime.now(), formaPagamento, 1, valorDesconto, endereco.getCep(), this.getValorFrete()+this.getValorTotal());
		pedido.setParcela(parcela);
		List<ItensPedido> itens = new ArrayList<ItensPedido>();
		try {
			pedidoRepositorio.save(pedido);
			int idPedido = pedidoRepositorio.findByLastId();
			
			for(ProdutoSelecionado sel : produtosSelecionados) {
				ItensPedido item = new ItensPedido(0, sel.getItem().getId(), sel.getItem().getPrecoVenda(), sel.getQuantidade(), idPedido);
				itens.add(item);
			}

			for(ItensPedido i : itens) {
				itensPedidoRepositorio.save(i);
			}

			return new ModelAndView("redirect:/pedido?&idPedido=" + idPedido);

		} catch(Exception e) {
			redirAttr.addFlashAttribute("msg", "Ocorreu um erro ao salvar o pedido");
			return new ModelAndView("redirect:/carrinho");
		}
		finally {
			session.setAttribute("carrinhoController", null);
		}
	}
	
	public int getFormaPagamento() {
		return this.formaPagamento;
	}
	
	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public List<ProdutoSelecionado> getItensSelecionados() {
		return produtosSelecionados;
	}

	public void setItensSelecionados(List<ProdutoSelecionado> produtosSelecionados) {
		this.produtosSelecionados = produtosSelecionados;
	}

	public int getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(int valorFrete) {
		this.valorFrete = valorFrete;
	}
	
	public int getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(int valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	
	public int getQtdeItens() {
		return produtosSelecionados.size();
	}
	
	public Endereco getEndereco() {
		return this.endereco;
	}
	
	public void setCepEntrega(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public int getIdUsuario() {
		return this.idUsuario;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public double getValorTotal() {
		double valor = 0;
		for (ProdutoSelecionado itemSel : produtosSelecionados) {
			valor += itemSel.getSubtotal();
		}
		return valor;
	}
	
	public double getValorFinal() {
		double valor = getValorTotal();
		valor += valorFrete;
		valor -= valorDesconto;
		return valor;
	}
	
	public String getFormaPagamentoExtenso() {
		return FormaPagamento.formaPagamento(formaPagamento);
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public String getCepEntrega() {
		return cepEntrega;
	}

	public void setCepEntrega(String cepEntrega) {
		this.cepEntrega = cepEntrega;
	}
	
}
