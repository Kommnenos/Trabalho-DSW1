package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/loja")

public class LojaAdminController {
	
	@Autowired
	private IUsuarioService service;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Loja loja) {
		return "loja/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lojas",service.buscarTodos());
		return "loja/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "loja/cadastro";
		}

		loja.setSenha(encoder.encode(loja.getSenha()));
		loja.setEnabled(true);
		service.salvar(loja);
		attr.addFlashAttribute("success", "loja.create.success");
		return "redirect:/admin/loja/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("loja", service.buscarPorId(id));
		return "loja/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			boolean errosPermitidos = true;

			for (FieldError error : result.getFieldErrors()) {
				String campo = error.getField();
				if (!campo.equals("CNPJ") && !campo.equals("email") && !campo.equals("senha")) {
					errosPermitidos = false;
					break;
				}
			}

			if (!errosPermitidos) {
				return "loja/cadastro";
			}
		}

		if (loja.getSenha() == null || loja.getSenha().isEmpty()) {
			Loja lojaExistente = (Loja) service.buscarPorId(loja.getId());
			loja.setSenha(lojaExistente.getSenha());
		} else {
			loja.setSenha(encoder.encode(loja.getSenha()));
		}

		service.salvar(loja);
		attr.addFlashAttribute("success", "Loja editada com sucesso.");
		return "redirect:/admin/loja/listar";
	}


	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if (service.lojaTemVeiculos(id)) {
			model.addAttribute("fail", "loja.delete.fail");
		} else {
			service.excluir(id);
			model.addAttribute("success", "loja.delete.sucess");
		}
		return listar(model);
	}
}
