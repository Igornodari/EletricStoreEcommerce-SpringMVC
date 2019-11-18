package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import entidades.Cliente;
import entidades.ItensPedido;
import entidades.Pedido;
import repositorios.IClienteRepositorio;
import repositorios.IItensPedidoRepositorio;
import repositorios.IPedidoRepositorio;
import repositorios.IProdutoRepositorio;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	private final IPedidoRepositorio pedidoRepositorio;
	private final IItensPedidoRepositorio itensPedidoRepositorio;
	private final IProdutoRepositorio produtoRepositorio;
	private final IClienteRepositorio clienteRepositorio;
	
	@Autowired
	public PedidoController( IPedidoRepositorio pedidoRepositorio, IItensPedidoRepositorio itensPedidoRepositorio,
			IProdutoRepositorio produtoRepositorio, IClienteRepositorio clienteRepositorio) {
		this.pedidoRepositorio = pedidoRepositorio;
		this.itensPedidoRepositorio = itensPedidoRepositorio;
		this.produtoRepositorio = produtoRepositorio;
		this.clienteRepositorio = clienteRepositorio;
	}
	
	@GetMapping
	public ModelAndView criado(@RequestParam("idPedido") Integer idPedido) {
		ModelAndView view = new ModelAndView("pedido/pedido-finalizado");
		view.addObject("idPedido", idPedido);
		return view;
	}
	
	@GetMapping("/lista")
	public ModelAndView lista() {
		ModelAndView view = new ModelAndView("pedido/lista-pedidos");
		
		List<Pedido> pedidos = pedidoRepositorio.findAll();
		for(Pedido p : pedidos) {
			p.setCliente(clienteRepositorio.obterPorId(p.getIdCliente()));
		}
		
		view.addObject("pedidos", pedidos);
		return view;
	}
	
	@GetMapping("/resumo/{id}")
	public ModelAndView resumo(@PathVariable("id") int id, HttpSession session) {
		ModelAndView view = new ModelAndView("pedido/resumo");
        Pedido pedido = pedidoRepositorio.obterPorId(id);
        
        Cliente cliente = (Cliente)session.getAttribute("usuarioLogado");
        pedido.setCliente(cliente);
        
        pedido.setItensPedido(itensPedidoRepositorio.obterPorIdPedido(id));
        view.addObject("pedido", pedido);
		
		List<ItensPedido> itens = itensPedidoRepositorio.obterPorIdPedido(id);
		for(ItensPedido i : itens) {
			if(i.getProduto() == null)
				i.setProduto(produtoRepositorio.obterPorId(i.getIdProduto()));
			if(i.getPedido() == null)
				i.setPedido(pedidoRepositorio.obterPorId(i.getIdPedido()));
		}
		
		view.addObject("itensPedido", itens);
		return view;
	}
}
