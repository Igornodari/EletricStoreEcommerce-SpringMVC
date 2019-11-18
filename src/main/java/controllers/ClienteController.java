package controllers;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import entidades.Cliente;
import entidades.Pedido;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import repositorios.ICartaoCreditoRepositorio;
import repositorios.IClienteRepositorio;
import repositorios.IEnderecoRepositorio;
import repositorios.IPedidoRepositorio;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	private final IClienteRepositorio repositorio;
	private final IEnderecoRepositorio enderecoRepositorio;
	private final ICartaoCreditoRepositorio cartaoRepositorio;
	private final IPedidoRepositorio pedidoRepositorio;

	@Autowired
	public ClienteController(IClienteRepositorio repositorio, IEnderecoRepositorio enderecoRepositorio,
			ICartaoCreditoRepositorio cartaoRepositorio, IPedidoRepositorio pedidoRepositorio) {
		this.repositorio = repositorio;
		this.enderecoRepositorio = enderecoRepositorio;
		this.cartaoRepositorio = cartaoRepositorio;
		this.pedidoRepositorio = pedidoRepositorio;
	}

	@GetMapping("/lista")
	public ModelAndView listaClientes() {
		List<Cliente> clientes = repositorio.findAll();
		ModelAndView view = new ModelAndView("cliente/lista-cliente");
		view.addObject("cliente", clientes);
		return view;
	}

	@GetMapping("/salvar")
	public ModelAndView salvar(Cliente cliente) {
		ModelAndView view = new ModelAndView("clientes/incluir-cliente");
		view.addObject("cliente", cliente);
		return view;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult bindingResult,
			RedirectAttributes redirAttr) {
		cliente.setAtivo(true);
		
		if(!validarCpf(cliente.getCpf()))
			bindingResult.reject("cpf","CPF inválido");

		if (repositorio.count() != 0) {
			if (repositorio.findByEmail(cliente.getEmail()) != null) {
				bindingResult.reject("email", "E-mail já cadastrado");
			}

			if (repositorio.findByCpf(cliente.getCpf()) != null) {
				bindingResult.reject("cpf", "CPF já cadastrado");
			}
		}

		if (bindingResult.hasErrors()) {
			return new ModelAndView("clientes/incluir-cliente");
		} else {
			cliente.setSenhaEncriptada(cliente.getSenha());
			repositorio.save(cliente);
		}

		ModelAndView view = new ModelAndView("redirect:/");
		redirAttr.addFlashAttribute("cliente", cliente);
		return view;
	}

	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Integer id) {
		ModelAndView view = new ModelAndView("clientes/alterar-cliente");
		view.addObject("cliente", repositorio.findById(id));
		return view;
	}

	@PostMapping("/alterar")
	public ModelAndView alterar(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult bindingResult,
			RedirectAttributes redirAttr) {
		if (bindingResult.hasErrors())
			return new ModelAndView("clientes/alterar-cliente");
		else
			repositorio.save(cliente);

		ModelAndView view = new ModelAndView("redirect:/cliente/perfil/" + cliente.getId());
		redirAttr.addFlashAttribute("cliente", cliente);
		return view;
	}

	@GetMapping("/perfil/{id}")
	public ModelAndView perfil(@PathVariable("id") Integer id) {
		ModelAndView view = new ModelAndView("clientes/meuPerfil");
		view.addObject("cliente", repositorio.findById(id));
		view.addObject("enderecosCliente", enderecoRepositorio.findByIdCliente(id));
		view.addObject("cartoesCliente", cartaoRepositorio.findByIdCliente(id));
		
		List<Pedido> pedidos = pedidoRepositorio.obterPorIdCliente(id);

		for(Pedido p : pedidos) {
			p.setCliente(repositorio.obterPorId(p.getIdCliente()));
		}
		
		view.addObject("pedidos", pedidos);
		return view;
	}

	private boolean validarCpf(String cpf) {
		// considera-se erro cpf's formados por uma sequencia de numeros iguais
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11)) {
			return (false);
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do cpf em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48); // converte no respectivo caractere
											// numerico
			}
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}

			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
				return (true);
			} else {
				return (false);
			}
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
}
