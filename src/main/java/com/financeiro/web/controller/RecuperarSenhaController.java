package com.financeiro.web.controller;

import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.dto.ChangePassword;
import com.financeiro.model.dto.Email;
import com.financeiro.model.security.Usuario;
import com.financeiro.service.RegistrarUsuarioService;
import com.financeiro.service.UserService;
import com.financeiro.util.email.CriarMensagemEmail;

@Controller
@RequestMapping(value="/recuperar")
public class RecuperarSenhaController {
	
	@Autowired
	private RegistrarUsuarioService registrarUsuarioService;
	
	@Autowired
	private UserService usuarioService;
	
	@Autowired
	private CriarMensagemEmail criarMensagemEmail;

	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ModelAndView pegarEmail(Email email) {
		ModelAndView mv = new ModelAndView("/registrar/email");
		mv.addObject("email", email);
		return mv;
	}
	
	
    @RequestMapping(value = "/reset-senha/{mail}", method = RequestMethod.POST)
    public  ModelAndView resetPassword( @Valid Email email, 
    		                            HttpServletRequest request, 
    		                            @PathVariable("mail") String mail, RedirectAttributes attr) {
    	
        Usuario usuario = usuarioService.findEmail(email.getEmail());
        if (Objects.isNull(usuario)) {
        	attr.addFlashAttribute("fail", "E-mail fornecido não foi encontrado.");
        } else {
	        final String token = UUID.randomUUID().toString();
            registrarUsuarioService.criarNovaSenhaComTokenParaUsuario(usuario, token);
            criarMensagemEmail.criarRenvioTokenPorEmail(criarMensagemEmail.getAppUrl(request), token, usuario);
	        attr.addAttribute("success", "Enviamos link no seu e-mail para fazer a troca da senha");
        }
        return new ModelAndView("/recuperar/email");
    }
    
    @RequestMapping(value = "/trocar-senha", method = RequestMethod.GET)
    public String showChangePasswordPage(Model model, 
    		                             @RequestParam("id") final long id, 
    		                             @RequestParam("token") final String token, RedirectAttributes attributes) {
        final String result = registrarUsuarioService.validarSenhaAlteradaComToken(id, token);
        if (result != null) {
            model.addAttribute("message", "Faça o Login " + result);
            return "redirect:/login";
        }
        return "redirect:/registro/esqueceu-senha";
    }
	
   
    @RequestMapping(value = "/salvar-senha", method = RequestMethod.POST)
    public ModelAndView savePassword(@Valid ChangePassword password, BindingResult result, RedirectAttributes attr) {
        ModelAndView mv = new ModelAndView("/registro/trocarsenha");
    	if ( result.hasErrors()) {
    		attr.addAttribute("fail","Erro no processamento para salvar a senha");
   	    } else {
	        Usuario usuario= (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        registrarUsuarioService.alterarUsuarioSenha(usuario, password.getNewPassword());
	        attr.addAttribute("success","Senha salva com sucesso");
   	    }
        return mv;
    }
        
    
}
