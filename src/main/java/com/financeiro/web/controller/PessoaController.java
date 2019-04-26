package com.financeiro.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.financeiro.model.estatico.ModeloTelefone;
import com.financeiro.model.estatico.TipoPessoa;
import com.financeiro.model.estatico.TipoTelefone;
import com.financeiro.model.estatico.UF;
import com.financeiro.model.negocio.Pessoa;
import com.financeiro.repository.filtros.PessoaFiltro;
import com.financeiro.service.PessoaService;
import com.financeiro.service.exception.CnpjCpfExistente;
import com.financeiro.service.exception.ValorSalarioInvalido;
import com.financeiro.service.storage.FotoStorage;
import com.financeiro.web.page.PageWrapper;

@Controller
@RequestMapping(value="/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.GET)
	public ModelAndView listarPessoa(PessoaFiltro pessoaFiltro, BindingResult result, @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest) {
		/*List<Pessoa> listaPessoa = pessoaService.listarTodasPessoas();*/
		ModelAndView model = new ModelAndView("/pessoa/lista");
		PageWrapper<Pessoa> pagina = new PageWrapper<>(pessoaService.listPessoaWithPagination(pessoaFiltro, pageable), httpServletRequest);
		model.addObject("pagina", pagina);
		return model;
	}
	
	@RequestMapping(value="/cadastrar")
	public ModelAndView cadastrarPessoa(Pessoa pessoa) {
		ModelAndView model = new ModelAndView("/pessoa/cadastro");
		model.addObject("pessoa", pessoa);
		return model;
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("pessoa", pessoaService.listarTodasPessoas());
		return "/pessoa/lista"; 
	}
		
	@RequestMapping(value="/salvar", method=RequestMethod.POST, params="action=salvar")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return cadastrarPessoa(pessoa);
		}
		try {
			pessoa = pessoaService.salvar(pessoa);
		} catch (CnpjCpfExistente e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return cadastrarPessoa(pessoa);
		} catch (ValorSalarioInvalido e) {
			result.rejectValue("salario", e.getMessage(), e.getMessage());
			return cadastrarPessoa(pessoa);
		}
		ModelAndView mv = new ModelAndView("/pessoa/cadastroTelefone");
		pessoa = pessoaService.adicionaNovoTelefonePessoa(pessoa);
		mv.addObject("pessoa", pessoa);
		attr.addFlashAttribute("success", "Registro inserido com sucesso.");
		return mv;
	}
	
	@RequestMapping(value="/salvar",method=RequestMethod.POST, params="action=addrow")
    public ModelAndView cadastrarTelefonePessoa(@Valid Pessoa pessoa, BindingResult result) {
		ModelAndView mv = new ModelAndView("/pessoa/cadastroTelefone");
		pessoa = pessoaService.adicionaNovoTelefonePessoa(pessoa);
		mv.addObject("pessoa", pessoa);
      	return mv;
    }
	
	@RequestMapping(value="/salvar",method=RequestMethod.POST, params="removerow")
    public ModelAndView excluirTelefonePessoa(@Valid Pessoa pessoa, BindingResult result, Model model,HttpServletRequest request ) {
    	int index = Integer.valueOf(request.getParameter("removerow"));
    	pessoa = pessoaService.removeTelefonePessoa(pessoa, index);
        ModelAndView modelAndView = new ModelAndView("/pessoa/cadastroTelefone");
    	modelAndView.addObject("pessoa",pessoa);
      	return modelAndView;
    }
	
	@RequestMapping(value="/salvar",method=RequestMethod.POST, params="action=fone")
    public ModelAndView insertPessoaTelefone(@Valid Pessoa pessoa, BindingResult result, Model model,RedirectAttributes attributes,HttpServletRequest request ) {
    	pessoaService.salvarPessoaTelefone(pessoa);
      	return new ModelAndView("redirect:/pessoa/pesquisar");
    }
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Integer id ) {
		Pessoa pessoa = pessoaService.findById(id);
		ModelAndView mv = new ModelAndView("/pessoa/cadastro");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	

	@RequestMapping(value="/editar", method=RequestMethod.POST, params="action=salvar")
	public ModelAndView editarPessoa(@Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return cadastrarPessoa(pessoa);
		}
		int id = pessoa.getId();
		try {
			pessoaService.update(pessoa);
		} catch (ValorSalarioInvalido e) {
			result.rejectValue("salario", e.getMessage(), e.getMessage());
			return cadastrarPessoa(pessoa);
		}
		ModelAndView mv = new ModelAndView("/pessoa/cadastroUpdateTelefone");
		pessoa = pessoaService.findByPessoaIdWithTelefone(pessoa.getId());
		if (Objects.isNull(pessoa)) {
			pessoa = pessoaService.findById(id);
		}
		mv.addObject("pessoa", pessoa);
		attr.addFlashAttribute("success", "Resgistro alterado com sucesso.");
		return mv;
	}
	
	@RequestMapping(value="/editar",method=RequestMethod.POST, params="action=addrow")
    public ModelAndView editarTelefonePessoaAddRow(@Valid Pessoa pessoa, BindingResult result) {
		ModelAndView mv = new ModelAndView("/pessoa/cadastroUpdateTelefone");
		pessoa = pessoaService.adicionaNovoTelefonePessoa(pessoa);
		mv.addObject("pessoa", pessoa);
      	return mv;
    }
	
	@RequestMapping(value="/editar",method=RequestMethod.POST, params="removerow")
    public ModelAndView editarTelefonePessoaRemoveRow(@Valid Pessoa pessoa, BindingResult result, Model model,HttpServletRequest request ) {
    	int index = Integer.valueOf(request.getParameter("removerow"));
    	pessoa = pessoaService.removeTelefonePessoa(pessoa, index);
        ModelAndView modelAndView = new ModelAndView("/pessoa/cadastroTelefone");
    	modelAndView.addObject("pessoa",pessoa);
      	return modelAndView;
    }
	
	@RequestMapping(value="/editar",method=RequestMethod.POST, params="action=fone")
    public ModelAndView updatePessoaTelefone(@Valid Pessoa pessoa, BindingResult result, Model model,RedirectAttributes attributes,HttpServletRequest request ) {
    	pessoaService.salvarPessoaTelefone(pessoa);
      	return new ModelAndView("redirect:/pessoa/pesquisar");
    }
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable("id") Integer id) {
		ModelAndView model = null;
		Pessoa pessoa = pessoaService.findById(id);
		if (pessoa.getTelefones().size()!=-1) {
			model = new ModelAndView("/pessoa/cadastroExcluirTelefone");
		} else {
			model = new ModelAndView("/pessoa/excluir");
		}
		model.addObject("pessoa", pessoa);
		return model;
	}
	
	@RequestMapping(value="/excluir", method=RequestMethod.POST, params="action=excluir" )
	public ModelAndView excluirPessoa(Pessoa pessoa, RedirectAttributes attr) {
		pessoaService.delete(pessoa.getId());
		attr.addFlashAttribute("success", "Registro removido com sucesso.");
		return new ModelAndView("redirect:/pessoa/pesquisar");
	}
	
	@RequestMapping(value="/excluir",method=RequestMethod.POST, params="action=fone")
    public ModelAndView excluirPessoaETelefone(Pessoa pessoa, BindingResult result) {
		pessoaService.delete(pessoa.getId());
		ModelAndView model = new ModelAndView("redirect:/pessoa/pesquisar");
      	return model;
    }
	
	
	@RequestMapping(value="/excluir",method=RequestMethod.POST, params="removerow")
	public ModelAndView excluirPessoaTelefone(Pessoa pessoa, BindingResult result, Model model,HttpServletRequest request ) {
	     Integer index = Integer.valueOf(request.getParameter("removerow"));
	     pessoa = pessoaService.removeTelefonePessoaTabela(pessoa, index);
	     ModelAndView mv = new ModelAndView("/pessoa/cadastroExcluirTelefone");
	     mv.addObject("pessoa",pessoa);
	     return mv;
	}
	
	@RequestMapping(value="/consultar/{id}", method=RequestMethod.GET)
	public String consulta(@PathVariable("id") Integer id,  ModelMap model) {
		Pessoa pessoa = pessoaService.findById(id);
		model.addAttribute("pessoa", pessoa);
		return "/pessoa/consultar";
	}
		
	@RequestMapping(value= {"/salvar","/editar","/excluir","/consultar"}, method=RequestMethod.POST, params="action=cancelar")
	public String cancelarCadastroPessoa() {
		return "redirect:/pessoa/pesquisar";
	}
	
	@ModelAttribute("estados")
	public UF[] getUFs() {
		return UF.values();
	}
	
	@ModelAttribute("tiposPessoa")
	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}
	
	@ModelAttribute("tiposTelefone")
	public TipoTelefone[] getTipoTelefone() {
		return TipoTelefone.values();
	}
	
	@ModelAttribute("modelosTelefone")
	public ModeloTelefone[] getModeloTelefone() {
		return ModeloTelefone.values();
	}
	
	public byte[] recuperar(String nome) {
		return fotoStorage.recuperar(nome);
	}

}
