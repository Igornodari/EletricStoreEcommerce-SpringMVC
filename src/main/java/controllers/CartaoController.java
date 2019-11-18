package controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entidades.CartaoCredito;
import repositorios.ICartaoCreditoRepositorio;

@Controller
@RequestMapping("/cartao")
public class CartaoController {
	private final ICartaoCreditoRepositorio cartaoRepositorio;
	
	public CartaoController(ICartaoCreditoRepositorio cartaoRepositorio) {
		this.cartaoRepositorio = cartaoRepositorio;
	}
	
	@GetMapping("/salvar/{id}")
	public ModelAndView salvar(CartaoCredito cartao, @PathVariable("id") Integer id){
        ModelAndView view = new ModelAndView("cartao/incluir-cartao");
        cartao.setIdCliente(id);
        view.addObject("cartao", cartao);
        view.addObject("clienteId", id);
        return view;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute("cartao") @Valid CartaoCredito cartao,
            BindingResult bindingResult, RedirectAttributes redirAttr){
		if(bindingResult.hasErrors())
			return new ModelAndView("cartao/incluir-cartao");
		else
			cartaoRepositorio.save(cartao);
		
		ModelAndView view = new ModelAndView("redirect:/cliente/perfil/" + cartao.getIdCliente());
		return view;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView salvar(@PathVariable("id") Integer id){
		ModelAndView view = new ModelAndView("cartao/alterar-cartao");
		view.addObject("cartao", cartaoRepositorio.findById(id));
        return view;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@ModelAttribute("cartao") @Valid CartaoCredito cartao,
            BindingResult bindingResult, RedirectAttributes redirAttr) {
        if (bindingResult.hasErrors()) 
            return new ModelAndView("cartao/alterar-cartao");
        else 
            cartaoRepositorio.save(cartao);
        
        ModelAndView view = new ModelAndView("redirect:/cliente/perfil/" + cartao.getIdCliente());
        redirAttr.addFlashAttribute("cartao", cartao);
        return view;
	}
}
