package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import entidades.Endereco;
import repositorios.IEnderecoRepositorio;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {
	
	private final IEnderecoRepositorio repositorio;
	
	@Autowired
	public EnderecoController(IEnderecoRepositorio repositorio){
		this.repositorio = repositorio;
	}
	
	@GetMapping("/salvar/{id}")
	public ModelAndView salvar(Endereco endereco, @PathVariable("id") Integer id){
        ModelAndView view = new ModelAndView("endereco/incluir-endereco");
        endereco.setIdCliente(id);
        view.addObject("endereco", endereco);
        view.addObject("clienteId", id);
        return view;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@ModelAttribute("endereco") @Valid Endereco endereco,
            BindingResult bindingResult, RedirectAttributes redirAttr){
		if(bindingResult.hasErrors())
			return new ModelAndView("endereco/incluir-endereco");
		else
			repositorio.save(endereco);
		
		ModelAndView view = new ModelAndView("redirect:/cliente/perfil/" + endereco.getIdCliente());
		return view;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView salvar(@PathVariable("id") Integer id){
		ModelAndView view = new ModelAndView("endereco/alterar-endereco");
		view.addObject("endereco", repositorio.findById(id));
        return view;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@ModelAttribute("endereco") @Valid Endereco endereco,
            BindingResult bindingResult, RedirectAttributes redirAttr) {
        if (bindingResult.hasErrors()) 
            return new ModelAndView("endereco/alterar-endereco");
        else 
            repositorio.save(endereco);
        
        ModelAndView view = new ModelAndView("redirect:/cliente/perfil/" + endereco.getIdCliente());
        redirAttr.addFlashAttribute("endereco", endereco);
        return view;
	}
}
