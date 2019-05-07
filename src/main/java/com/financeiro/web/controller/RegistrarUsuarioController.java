package com.financeiro.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.security.ResetarSenhaToken;
import com.financeiro.model.security.Usuario;
import com.financeiro.service.RegistrarUsuarioService;
import com.financeiro.service.exception.EmailUsuarioCadastradoException;
import com.financeiro.util.email.CriarMensagemEmail;
import com.financeiro.web.registrar.RegistrarUsuario;

@Controller
@RequestMapping(value="/registrar")
public class RegistrarUsuarioController {
	
	public static final String TOKEN_INVALID = "invalido";
    public static final String TOKEN_EXPIRED = "expirado";
    public static final String TOKEN_VALID = "valido";
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private RegistrarUsuarioService registrarUsuarioService;

	@Autowired
	private CriarMensagemEmail criarMensagemEmail;
	
	@RequestMapping(value="/cadastrar")
	public ModelAndView registrarUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView("/registrar/cadastro");
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST, params="action=salvar")
	public ModelAndView salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/registrar/cadastro");
		if (result.hasErrors()) {
			registrarUsuario(usuario);
		}
		try {
			registrarUsuarioService.registrarUsuario(usuario);
			eventPublisher.publishEvent(new RegistrarUsuario(usuario, request.getLocale(), criarMensagemEmail.getAppUrl(request)));
		} catch (EmailUsuarioCadastradoException e) {
		  result.rejectValue("email", e.getMessage(), e.getMessage());
		  return registrarUsuario(usuario);
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/confirmar-registro-usuario", method = RequestMethod.GET)
    public String confirmarRegistroUsuario( final Model model, @RequestParam("token") final String token, RedirectAttributes attributes) throws UnsupportedEncodingException {
        final String result = registrarUsuarioService.verificarValidacaoDoToken(token);
        if (result.equals(TOKEN_VALID)) {
            Usuario usuario = registrarUsuarioService.getUsuario(token);
            if ( usuario != null ) {
	            attributes.addFlashAttribute("mensagem", "conta registrada com sucesso!");
	            return "redirect:/login";
            }
        }
        if ( result.equals(TOKEN_INVALID)) {
        	 model.addAttribute("mensagem","Usuário inválido ou não existe");
        } else if ( result.equals(TOKEN_EXPIRED) ) {
        	 model.addAttribute("mensagem", "Seu token de registro está expirado, Registre-se novamente ");
        }
        return "redirect:/registro/naoencontrado";
    }
	
	
    @RequestMapping(value = "/enviar-token-de-registro", method = RequestMethod.GET)
    public ModelAndView re_enviarRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken, RedirectAttributes attr) {
    	ResetarSenhaToken token = registrarUsuarioService.gerarNovaValidacaoParaToken(existingToken);
        Usuario usuario = registrarUsuarioService.getUsuario(token.getToken());
        criarMensagemEmail.ReenviarEmailTokenConfirmado(criarMensagemEmail.getAppUrl(request), token, usuario);
        attr.addFlashAttribute("success","Enviamos mensagem para seu e-mail com link para realizar um novo registro ");
        return registrarUsuario(usuario);
    }
    
    
    
    

}
