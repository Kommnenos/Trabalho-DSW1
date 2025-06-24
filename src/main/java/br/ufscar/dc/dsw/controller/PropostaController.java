package br.ufscar.dc.dsw.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import br.ufscar.dc.dsw.domain.StatusProposta;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.impl.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
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
    @Autowired
    private EmailService emailService;


    @GetMapping("/cadastrar/{id}")
    public String cadastrar(@PathVariable("id") Long idVeiculo, ModelMap model) {
        Proposta proposta = new Proposta();
        Veiculo veiculo = veiculoService.buscarPorId(idVeiculo);
        proposta.setVeiculo(veiculo);
        model.addAttribute("proposta", proposta);
        return "proposta/cadastro";
    }

    @GetMapping("/cliente/listar")
    public String listar(ModelMap model) {
        model.addAttribute("propostas",service.buscarTodosPorCliente(getCliente()));
        return "proposta/lista";
    }

    @GetMapping("/listar")
    public String listar() {
        if(getCliente()==null){
            return "redirect:/proposta/loja/listar";
        }
        else{
            return "redirect:/proposta/cliente/listar";
        }

    }

    @GetMapping("/loja/listar")
    public String listarRecebidas(ModelMap model) {

        List<Proposta> propostasRecebidas = service.buscarTodosPorLoja(getLoja());
        model.addAttribute("propostas", propostasRecebidas);
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

            if(service.temPropostaAbertaParaCliente(getCliente())){
                result.rejectValue("", "proposta.erro.propostaEmAberto");
                return "proposta/cadastro";
            }

            if (!errosPermitidos) {
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
        if(proposta.getStatus() == null){
            proposta.setStatus(StatusProposta.ABERTO);
        }

        service.salvar(proposta);
        attr.addFlashAttribute("success", "proposta.create.success");

        return "redirect:/proposta/cliente/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.excluir(id);
        attr.addFlashAttribute("success", "proposta.delete.success");
        return "redirect:/proposta/cliente/listar";
    }

    @GetMapping("/aceitar/{id}")
    public String aceitar(@PathVariable("id") Long id, RedirectAttributes attr) throws UnsupportedEncodingException {
        Proposta proposta = service.buscarPorId(id);
        proposta.setStatus(StatusProposta.ACEITO);
        service.salvar(proposta);
        attr.addFlashAttribute("success", "proposta.aceitar.success");

        String nomeLoja = proposta.getVeiculo().getLoja().getNome();
        String placaVeiculo = proposta.getVeiculo().getPlaca();

        String assunto = "Proposta aceita";
        String corpo = "Parabéns, a proposta sobre o veículo de placa: "+placaVeiculo+" da loja "+nomeLoja+" foi aceita!";
        String enderecoCliente = proposta.getCliente().getEmail();
        String nomeCliente = proposta.getCliente().getNome();
        enviarEmail(assunto, corpo, enderecoCliente, nomeCliente);

        return "redirect:/proposta/cliente/listar";
    }

    @GetMapping("/rejeitar/{id}")
    public String rejeitar(@PathVariable("id") Long id, RedirectAttributes attr) throws UnsupportedEncodingException {
        Proposta proposta = service.buscarPorId(id);
        proposta.setStatus(StatusProposta.NAO_ACEITO);
        service.salvar(proposta);
        attr.addFlashAttribute("success", "proposta.rejeitar.success");

        String nomeLoja = proposta.getVeiculo().getLoja().getNome();
        String placaVeiculo = proposta.getVeiculo().getPlaca();

        String assunto = "Proposta rejeitada";
        String corpo = "A proposta sobre o veículo de placa: "+placaVeiculo+" da loja "+nomeLoja+" foi rejeitada.";
        String enderecoCliente = proposta.getCliente().getEmail();
        String nomeCliente = proposta.getCliente().getNome();
        enviarEmail(assunto, corpo, enderecoCliente, nomeCliente);

        return "redirect:/proposta/cliente/listar";
    }


    private Cliente getCliente() {
        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioDetails.getCliente();
    }

    private Loja getLoja() {
        UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioDetails.getLoja();
    }

    private void enviarEmail(String assunto, String corpo, String paraEndereco, String paraNome) throws UnsupportedEncodingException {

        InternetAddress to = new InternetAddress(paraEndereco, paraNome);
        InternetAddress from = new InternetAddress("dcmotorsbrasil@gmail.com", "DC Motors");
        emailService.send(from, to, assunto, corpo);


    }

}
