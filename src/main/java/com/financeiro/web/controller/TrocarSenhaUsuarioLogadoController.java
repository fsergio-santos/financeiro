package com.financeiro.web.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.dto.UpdatePassword;
import com.financeiro.security.UsuarioSistema;
import com.financeiro.service.UsuarioSenhaService;

@Controller
@RequestMapping(value="trocar")
public class TrocarSenhaUsuarioLogadoController {

	@Autowired
	private UsuarioSenhaService usuarioSenhaService;

	public ModelAndView loginSistema() {
		return new ModelAndView("/login");
	}
	
	
	@RequestMapping(value="/senha", method=RequestMethod.GET)
	public ModelAndView pegarUsuarioLogado(@AuthenticationPrincipal UsuarioSistema usuario_logado, UpdatePassword password) {
		if (Objects.isNull(usuario_logado)) {
			return loginSistema();
		}
		ModelAndView mv = new ModelAndView("/registrar/trocar-senha");
		mv.addObject("password", password);
		return mv;
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.GET)
	public ModelAndView salvarSenhaUsuarioLogado(@AuthenticationPrincipal UsuarioSistema usuario_logado, 
			                                      UpdatePassword password, RedirectAttributes attr,
			                                      HttpServletRequest request,
			                                      HttpServletResponse response,
			                                      Model model) {
		if (Objects.isNull(usuario_logado)) {
			return loginSistema();
		}
		if (usuarioSenhaService.buscarUsuarioSenhaPorId(password)==true) {
			attr.addFlashAttribute("fail", "Senha já foi utilizada, troque por favor!.");
		} else {
			usuarioSenhaService.salvarUsuarioSenha(usuario_logado,password);
		    sairSistema(request, response, model);
		}
		ModelAndView mv = new ModelAndView("/registrar/trocar-senha");
		mv.addObject("password", password);
		return mv;
	}


	private String sairSistema(HttpServletRequest request, HttpServletResponse response, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("message", "Senha atualizada com sucesso. Faça o Login! ");
		return "redirect:/login";
	}


	
	
	
}
