package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import br.ufscar.dc.dsw.domain.StatusProposta;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

        return "proposta/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("propostas",service.buscarTodosPorCliente(this.getCliente()));

        return "proposta/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Proposta proposta, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            boolean errosPermitidos = true;

            for (FieldError error : result.getFieldErrors()) {
                String campo = error.getField();
                if (!campo.equals("data") && !campo.equals("cliente") && !campo.equals("veiculo")) {
                    errosPermitidos = false;
                    break;
                }
            }

            if (!errosPermitidos) {
                System.out.println(result.getAllErrors());
                return "proposta/cadastro";
            }
        }

        String data = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));

        if(proposta.getCliente() == null){
            proposta.setCliente(getCliente());
        }
        if(proposta.getData() == null || proposta.getData().isEmpty()){
            proposta.setData(data);
        }
        if(proposta.getVeiculo() == null){
            //proposta.setVeiculo(new Veiculo());
        }
        if(proposta.getStatus() == null){
            proposta.setStatus(StatusProposta.ABERTO);
        }

        service.salvar(proposta);
        attr.addFlashAttribute("success", "proposta.create.success");

        return "redirect:/proposta/listar";
    }

    private Cliente getCliente() {
        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioDetails.getCliente();
    }

}
