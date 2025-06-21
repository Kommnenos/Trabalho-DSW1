package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import br.ufscar.dc.dsw.security.UsuarioDetails;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.security.ClienteDetails;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Controller
@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private IPropostaService service;

    @Autowired
    private IVeiculoService veiculoService;

    @GetMapping("/cadastrar")
    public String cadastrar(Proposta proposta) {
        LocalDateTime data = LocalDateTime.parse(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        proposta.setCliente(this.getCliente());
        proposta.setData(data);
        return "proposta/cadastro";
    }

    private Cliente getCliente() {
        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioDetails.getCliente();
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {

        model.addAttribute("compras",service.buscarTodos(this.getCliente()));

        return "proposta/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Proposta proposta, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "proposta/cadastro";
        }

        service.salvar(proposta);
        attr.addFlashAttribute("sucess", "proposta.create.success");
        return "redirect:/proposta/listar";
    }

    @ModelAttribute("veiculos")
    public List<Veiculo> listaVeiculos() {
        return veiculoService.buscarTodos();
    }
}
