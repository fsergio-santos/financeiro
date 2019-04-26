package com.financeiro.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.estatico.ModeloTelefone;
import com.financeiro.model.estatico.TipoTelefone;
import com.financeiro.model.negocio.Pessoa;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.service.TelefoneService;
import com.financeiro.web.page.PageWrapper;

@Controller
@RequestMapping(value="/telefone")
public class TelefoneController {

	@Autowired
	private TelefoneService telefoneService;
	
	@RequestMapping(value="/cadastrar")
	public ModelAndView cadastrarTelefonePessoa(Pessoa pessoa) {
		ModelAndView model = new ModelAndView("/telefone/cadastroUpdateTelefone");
		model.addObject("pessoa", pessoa);
		return model;
	}
	
	@RequestMapping(value="/cadastrar/{id}")
	public ModelAndView cadastrarPessoa(@PathVariable("id") Integer id) {
		ModelAndView model = new ModelAndView("/telefone/cadastroUpdateTelefone");
		Pessoa pessoa = telefoneService.findByTelefoneIdPessoa(id);
		model.addObject("pessoa", pessoa);
		return model;
	}
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public ModelAndView listarPessoa(PessoaFiltro pessoaFiltro, BindingResult result, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView model = new ModelAndView("/telefone/lista");
		PageWrapper<Pessoa> pagina = new PageWrapper<>(telefoneService.listPessoaWithTelefonePagination(pessoaFiltro, pageable), httpServletRequest);
		model.addObject("pagina", pagina);
		return model;
	}

	@RequestMapping(value="/editar/{id}",method=RequestMethod.GET)
	public ModelAndView buscaByTelefoneIdPessoa(@PathVariable("id") Integer id) {
	    Pessoa pessoa = telefoneService.findByTelefoneIdPessoa(id);
	    ModelAndView mv = new ModelAndView("/telefone/cadastroUpdateTelefone");
	    mv.addObject("pessoa", pessoa);
	    return mv;
	}
	
	@RequestMapping(value="/editar",method=RequestMethod.POST, params="action=addrow")
    public ModelAndView editarTelefonePessoaAddRow(@Valid Pessoa pessoa, BindingResult result) {
		ModelAndView mv = new ModelAndView("/telefone/cadastroUpdateTelefone");
		pessoa = telefoneService.adicionaNovoTelefonePessoa(pessoa);
		mv.addObject("pessoa", pessoa);
      	return mv;
    }
	
	@RequestMapping(value="/editar",method=RequestMethod.POST, params="removerow")
    public ModelAndView editarTelefonePessoaRemoveRow(@Valid Pessoa pessoa, BindingResult result, Model model,HttpServletRequest request ) {
    	int index = Integer.valueOf(request.getParameter("removerow"));
    	pessoa = telefoneService.removeTelefonePessoa(pessoa, index);
        ModelAndView modelAndView = new ModelAndView("/telefone/cadastroUpdateTelefone");
    	modelAndView.addObject("pessoa",pessoa);
      	return modelAndView;
    }
	
	@RequestMapping(value="/editar",method=RequestMethod.POST, params="action=fone")
    public ModelAndView updatePessoaTelefone(@Valid Pessoa pessoa, BindingResult result, Model model,RedirectAttributes attributes,HttpServletRequest request ) {
    	telefoneService.salvarTelefonePessoa(pessoa);
      	return new ModelAndView("redirect:/telefone/pesquisar");
    }
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("id") Integer id) {
		ModelAndView model = null;
		Pessoa pessoa = telefoneService.findByTelefoneIdPessoa(id);
		model = new ModelAndView("/telefone/cadastroExcluirTelefone");
		model.addObject("pessoa", pessoa);
		return model;
	}
	
	@RequestMapping(value="/excluir",method=RequestMethod.POST, params="removerow")
	public ModelAndView excluirPessoaTelefone(Pessoa pessoa, BindingResult result, Model model,HttpServletRequest request ) {
	     Integer index = Integer.valueOf(request.getParameter("removerow"));
	     pessoa = telefoneService.removeTelefonePessoaTabela(pessoa, index);
	     ModelAndView mv = new ModelAndView("/pessoa/cadastroExcluirTelefone");
	     mv.addObject("pessoa",pessoa);
	     return mv;
	}
	
	@RequestMapping(value="/excluir",method=RequestMethod.POST, params="action=fone")
    public ModelAndView excluirPessoaETelefone(Pessoa pessoa, BindingResult result) {
		telefoneService.deleteTelefonePessoa(pessoa);
		ModelAndView model = new ModelAndView("redirect:/telefone/pesquisar");
      	return model;
    }
	
	
	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=cancelar")
	public String cancelarCadastroPessoaTelefone() {
		return "redirect:/telefone/pesquisar";
	}
	
	@ModelAttribute("tiposTelefone")
	public TipoTelefone[] getTipoTelefone() {
		return TipoTelefone.values();
	}
	
	@ModelAttribute("modelosTelefone")
	public ModeloTelefone[] getModeloTelefone() {
		return ModeloTelefone.values();
	}
	
}
