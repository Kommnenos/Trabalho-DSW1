package br.ufscar.dc.dsw.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IEditoraService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private IVeiculoService veiculoService;

	@Autowired
	private IEditoraService editoraService;

	@GetMapping("/cadastrar")
	public String cadastrar(Veiculo veiculo) {
		return "veiculo/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("veiculos", veiculoService.buscarTodos());
		return "veiculo/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "veiculo/cadastro";
		}

		veiculoService.salvar(veiculo);
		attr.addFlashAttribute("sucess", "veiculo.create.sucess");
		return "redirect:/veiculos/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("veiculo", veiculoService.buscarPorId(id));
		return "veiculo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "veiculo/cadastro";
		}

		veiculoService.salvar(veiculo);
		attr.addFlashAttribute("sucess", "veiculo.edit.sucess");
		return "redirect:/veiculos/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		veiculoService.excluir(id);
		attr.addFlashAttribute("sucess", "veiculo.delete.sucess");
		return "redirect:/veiculos/listar";
	}

	@ModelAttribute("editoras")
	public List<Editora> listaEditoras() {
		return editoraService.buscarTodos();
	}
}
