package br.ufscar.dc.dsw.controller;

import java.util.List;

import br.ufscar.dc.dsw.domain.ImagemVeiculo;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import jakarta.validation.Valid;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

	@GetMapping("/cadastrar")
	public String cadastrar(Veiculo veiculo) {
		return "veiculo/cadastro";
	}

	@GetMapping("/listarVeiculosLoja")
	public String listarVeiculosDaLoja(@RequestParam(value = "modelo", required = false) String modelo, ModelMap model) {
		List<Veiculo> veiculos;
		Loja loja = getLoja();

		if (modelo == null || modelo.isBlank()) {
			veiculos = veiculoService.buscarTodosPorLoja(loja.getId());
		} else {
			modelo = modelo.replaceAll("\\s+$", ""); //removendo espaços a direita do ultimo caractere da string usada pra filtrar
			veiculos = veiculoService.buscarTodosPorLojaEModelo(loja.getId(), modelo);
		}

		model.addAttribute("veiculos", veiculos);
		model.addAttribute("listarTodos", "false");
		model.addAttribute("modelo", modelo);

		return "veiculo/lista";
	}


	@GetMapping("/listarTodos")
	public String listarTodos(@RequestParam(value = "modelo", required = false) String modelo, ModelMap model) {
		List<Veiculo> veiculos;

		if (modelo == null || modelo.isBlank()) {
			veiculos = veiculoService.buscarTodos();
		} else {
			modelo = modelo.replaceAll("\\s+$", ""); //removendo espaços a direita do ultimo caractere da string usada pra filtrar
			veiculos = veiculoService.buscarTodosPorModelo(modelo);
		}

		model.addAttribute("veiculos", veiculos);
		model.addAttribute("listarTodos", "true");
		model.addAttribute("modelo", modelo);

		return "veiculo/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr, @RequestParam(name ="file") MultipartFile file, ModelMap model) throws IOException {

		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				String campo = error.getField();
				if (!campo.equals("loja")) {
					return "/error";
				}
			}
		}

		if (!file.getOriginalFilename().isBlank()) {
			ImagemVeiculo img = veiculo.getImagem();
			if (img == null) {
				img = new ImagemVeiculo();
				img.setVeiculo(veiculo);
				veiculo.setImagem(img);
			}
			img.setDados(file.getBytes());
		}

		veiculo.setLoja(getLoja());
		veiculoService.salvar(veiculo);
		attr.addFlashAttribute("success", "veiculo.create.success");
		return "redirect:/veiculo/listarVeiculosLoja";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("veiculo", veiculoService.buscarPorId(id));
		return "veiculo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Veiculo veiculo, BindingResult result, @RequestParam(name ="file") MultipartFile file, RedirectAttributes attr) throws IOException {

		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				String campo = error.getField();
				if (!campo.equals("loja")) {
					return "/error";
				}
			}
		}

		Veiculo veiculoExistente = veiculoService.buscarPorId(veiculo.getId());
		if (veiculoExistente == null) {
			return "/error";
		}

		veiculoExistente.setPlaca(veiculo.getPlaca());
		veiculoExistente.setModelo(veiculo.getModelo());
		veiculoExistente.setChassi(veiculo.getChassi());
		veiculoExistente.setAno(veiculo.getAno());
		veiculoExistente.setQuilometragem(veiculo.getQuilometragem());
		veiculoExistente.setDescricao(veiculo.getDescricao());
		veiculoExistente.setValor(veiculo.getValor());
		veiculoExistente.setLoja(getLoja());

		if (!file.getOriginalFilename().isBlank()) {
			ImagemVeiculo img = veiculoExistente.getImagem();
			System.out.println("-------------------------------------------");
			System.out.printf("imagem: " + img);
			System.out.println("-------------------------------------------");
			if (img == null) {
				img = new ImagemVeiculo();
				img.setVeiculo(veiculoExistente);
				veiculoExistente.setImagem(img);
			}
			img.setDados(file.getBytes());
		}

		veiculoService.salvar(veiculoExistente);
		attr.addFlashAttribute("success", "veiculo.edit.success");
		return "redirect:/veiculo/listarVeiculosLoja";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		veiculoService.excluir(id);
		attr.addFlashAttribute("success", "veiculo.delete.success");
		return "redirect:/veiculo/listarVeiculosLoja";
	}

	private Loja getLoja(){
		UsuarioDetails usuario = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuario.getLoja();
	}

	@Cacheable(cacheNames = "imagens", key="#id")
	private byte[] getImagem(Long id) {
		Veiculo veiculo = veiculoService.buscarPorId(id);
		return veiculo.getImagem().getDados();
	}

	@GetMapping(value = "/download/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
		
		// set content type
		response.setContentType("image/png");
		
		try {
			// copies all bytes to an output stream
			response.getOutputStream().write(getImagem(id));

			// flushes output stream
			response.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println("Error :- " + e.getMessage());
		}
	}
}


