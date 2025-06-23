package br.ufscar.dc.dsw.controller;

import java.util.List;

import br.ufscar.dc.dsw.security.UsuarioDetails;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Controller
@RequestMapping("/veiculo")
public class VeiculoController {

	@Autowired
	private IVeiculoService veiculoService;

	@Autowired
	private ILojaService lojaService;

	@GetMapping("/cadastrar")
	public String cadastrar(Veiculo veiculo) {

		return "veiculo/cadastro";
	}

	@GetMapping("/listarVeiculosLoja")
	public String listarVeiculosDaLoja(ModelMap model) {
		UsuarioDetails usuario = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Loja loja = usuario.getLoja();

		return String.format("redirect:/veiculo/listar/%d", loja.getId());
	}


	@GetMapping("/listar/{id}")
	public String listar(@PathVariable("id") Long idLoja, ModelMap model) {
		model.addAttribute("veiculos", veiculoService.buscarTodosPorLoja(idLoja));

		System.out.println("------------------------ LISTANDO --------------------------");
		System.out.println(veiculoService.buscarTodosPorLoja(idLoja));
		return "veiculo/lista";
	}

	@GetMapping("/listarTodos")
	public String listarTodos(ModelMap model) {
		model.addAttribute("veiculos", veiculoService.buscarTodos());
		model.addAttribute("listarTodos", "true");
		System.out.println(veiculoService.buscarTodos());
		return "veiculo/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr, ModelMap model) {

		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				String campo = error.getField();
				if (!campo.equals("loja")) {
					return "/error";
				}
			}

		}
		UsuarioDetails usuario = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Loja loja = usuario.getLoja();
		veiculo.setLoja(loja);
		veiculoService.salvar(veiculo);
		attr.addFlashAttribute("success", "veiculo.create.success");
		return "veiculo/lista";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("veiculo", veiculoService.buscarPorId(id));
		return "veiculo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				String campo = error.getField();
				if (!campo.equals("loja")) {
					return "/error";
				}
			}

		}

		UsuarioDetails usuario = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Loja loja = usuario.getLoja();
		veiculo.setLoja(loja);

		veiculoService.salvar(veiculo);
		attr.addFlashAttribute("success", "veiculo.edit.success");
		return String.format("redirect:/veiculo/listar/%d", loja.getId());
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		veiculoService.excluir(id);
		attr.addFlashAttribute("success", "veiculo.delete.success");
		return "redirect:/veiculo/listar";
	}
}


