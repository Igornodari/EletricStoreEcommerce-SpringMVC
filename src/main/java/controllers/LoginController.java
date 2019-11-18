/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entidades.Cliente;
import entidades.Imagem;
import entidades.Login;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import repositorios.IClienteRepositorio;

import java.util.List;


@Controller
public class LoginController {

    private final IClienteRepositorio repositorio;

    @Autowired
    public LoginController(IClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    @GetMapping("/login")
    public ModelAndView efetuaLogin(Login cliente){
        ModelAndView view = new ModelAndView("login");
        view.addObject("cliente", cliente);
        return view;
    }

    @PostMapping("/login")
    public ModelAndView efetuaLogin(@ModelAttribute("cliente") @Valid Login cliente, 
    		HttpSession session, BindingResult bindingResult) {
    	
    	CarrinhoController carrinho = (CarrinhoController) session.getAttribute("carrinhoController");

    	if(cliente.getEmail().isEmpty())
    		bindingResult.reject("email", "O e-mail é obrigatório");
    	
    	if(cliente.getSenha().isEmpty())
    		bindingResult.reject("senha", "A senha é obrigatória");
    	
    	if (!cliente.getEmail().isEmpty()){
    		if(repositorio.findByEmail(cliente.getEmail()) == null)
    			bindingResult.reject("email", "Não encontramos um cadastro com esse e-mail");
    	}
    	
        if(bindingResult.hasErrors()){
            return new ModelAndView("login");
        }
        
        Cliente clienteLogado = autenticar(cliente.getEmail(), cliente.getSenha());
        ModelAndView view;
        if(clienteLogado != null){
            session.setAttribute("usuarioLogado", clienteLogado);
            if(carrinho != null && carrinho.getItensSelecionados().size() != 0) {
            	view = new ModelAndView("redirect:/carrinho");
            	return view;
            }
            view = new ModelAndView("redirect:/");
            view.addObject("usuarioLogado", clienteLogado);
            return view;
        } else {
            view = new ModelAndView("login");
            bindingResult.reject("login", "Login inválido, verifique seu e-mail ou senha");
            return view;
        }
    }
    
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
    	session.invalidate();
    	return new ModelAndView("redirect:/");
    }
    
	private final Cliente autenticar(String email, String senha){
    	Cliente cliente = repositorio.findByEmail(email);
    	if(cliente != null && cliente.validarSenha(senha))
    		return cliente;
    	return null;
    }


}
