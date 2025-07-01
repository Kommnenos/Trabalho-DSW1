package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;
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
@RequestMapping("/admin/cliente")
public class ClienteAdminController {

	@Autowired
	private IUsuarioService service;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/cadastrar")
	public String cadastrar(Cliente cliente) {
		return "cliente/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("clientes", service.buscarTodosClientes());
		return "cliente/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "cliente/cadastro";
		}

		System.out.println("senha = " + cliente.getSenha());

		cliente.setSenha(encoder.encode(cliente.getSenha()));
		cliente.setEnabled(true);
		service.salvar(cliente);
		attr.addFlashAttribute("success", "Cliente inserido com sucesso.");
		return "redirect:/admin/cliente/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", service.buscarPorId(id));
		return "cliente/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			boolean errosPermitidos = true;

			for (FieldError error : result.getFieldErrors()) {
				String campo = error.getField();
				if (!campo.equals("CPF") && !campo.equals("email") && !campo.equals("senha")) {
					errosPermitidos = false;
					break;
				}
			}

			if (!errosPermitidos) {
				return "cliente/cadastro";
			}
		}

		if (cliente.getSenha() == null || cliente.getSenha().isEmpty()) {
			Cliente clienteExistente = (Cliente) service.buscarPorId(cliente.getId());
			cliente.setSenha(clienteExistente.getSenha());
		} else {
			cliente.setSenha(encoder.encode(cliente.getSenha()));
		}

		service.salvar(cliente);
		attr.addFlashAttribute("success", "Cliente editado com sucesso.");
		return "redirect:/admin/cliente/listar";
	}


	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		service.excluir(id);
		model.addAttribute("success", "Cliente excluído com sucesso.");
		return listar(model);
	}
}