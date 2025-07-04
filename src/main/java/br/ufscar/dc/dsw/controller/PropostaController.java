package br.ufscar.dc.dsw.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if(proposta.getStatus().equals(StatusProposta.ABERTO)) {

            //Rejeitando as demais propostas
            List<Proposta> propostas =  service.buscarTodosPorVeiculo(proposta.getVeiculo());
            for (Proposta p : propostas) {
                if(p.getId().longValue() != proposta.getId().longValue()){ // se não for a proposta que está sendo aceita
                    p.setStatus(StatusProposta.NAO_ACEITO);
                    service.salvar(p);
                }
            }
            
            proposta.setStatus(StatusProposta.ACEITO);
            service.salvar(proposta);
            attr.addFlashAttribute("success", "proposta.aceitar.success");

            String nomeLoja = proposta.getVeiculo().getLoja().getNome();
            String placaVeiculo = proposta.getVeiculo().getPlaca();
            String diaReuniao = proposta.getDataReuniao().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String horaReuniao = String.valueOf(proposta.getDataReuniao().toLocalTime());
            String link = proposta.getLinkVideoconferencia();

            String assunto = "Proposta aceita";
            String corpo = "Parabéns, a proposta sobre o veículo de placa: " + placaVeiculo + " da loja " + nomeLoja + " foi aceita!\n"+
                            "Reunião marcada para: " + diaReuniao + " às " + horaReuniao + " pelo link: " +  link;
            String enderecoCliente = proposta.getCliente().getEmail();
            String nomeCliente = proposta.getCliente().getNome();
            enviarEmail(assunto, corpo, enderecoCliente, nomeCliente);
        }

        return "redirect:/proposta/loja/listar";

    }

    @GetMapping("/rejeitar/{id}")
    public String rejeitar(@PathVariable("id") Long id, RedirectAttributes attr) throws UnsupportedEncodingException {
        Proposta proposta = service.buscarPorId(id);
        if(proposta.getStatus() == StatusProposta.ABERTO) {
            proposta.setStatus(StatusProposta.NAO_ACEITO);
            service.salvar(proposta);
            attr.addFlashAttribute("success", "proposta.rejeitar.success");

            String nomeLoja = proposta.getVeiculo().getLoja().getNome();
            String placaVeiculo = proposta.getVeiculo().getPlaca();
            BigDecimal valorContraproposta = proposta.getValorContraproposta();
            String condicoesContraproposta = proposta.getCondicoesContraproposta();

            String assunto = "Proposta rejeitada";
            String corpo_ = "A proposta sobre o veículo de placa: " + placaVeiculo + " da loja " + nomeLoja + " foi rejeitada.\n";
            String contraproposta = "";
            if(valorContraproposta != null || (condicoesContraproposta !=null&& !condicoesContraproposta.isBlank()) ){
                contraproposta = "A loja enviou uma contraproposta, com valor: R$ " + valorContraproposta + " e condições: " + condicoesContraproposta;
            }
            String corpo = corpo_ + contraproposta;


            String enderecoCliente = proposta.getCliente().getEmail();
            String nomeCliente = proposta.getCliente().getNome();
            enviarEmail(assunto, corpo, enderecoCliente, nomeCliente);
        }

        return "redirect:/proposta/loja/listar";
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

    @GetMapping("/reuniao/form/{id}")
    public String preencherReuniao(@PathVariable("id") Long id, ModelMap model) {
        Proposta proposta = service.buscarPorId(id);
        model.addAttribute("proposta", proposta);
        return "proposta/form_reuniao";
    }

    @PostMapping("/reuniao/salvar")
    public String salvarReuniao(@RequestParam("id") Long id, @RequestParam(required = true) String dataReuniao, @RequestParam(required = true) String linkVideoconferencia, RedirectAttributes attr) {

        Proposta proposta = service.buscarPorId(id);
        if (dataReuniao != null && !dataReuniao.isBlank()) {
            proposta.setDataReuniao(LocalDateTime.parse(dataReuniao));
        }
        if (linkVideoconferencia != null && !linkVideoconferencia.isBlank()) {
            proposta.setLinkVideoconferencia(linkVideoconferencia);
        }
        service.salvar(proposta);
        attr.addFlashAttribute("success", "proposta.reuniao.salva");
        return "redirect:/proposta/aceitar/"+id;
    }

    @GetMapping("/contraproposta/{id}")
    public String formContraproposta(@PathVariable("id") Long id, ModelMap model) {
        Proposta proposta = service.buscarPorId(id);
        model.addAttribute("proposta", proposta);
        return "proposta/contraproposta";
    }

    @PostMapping("/contraproposta/salvar")
    public String salvarContraproposta(@RequestParam("id") Long id, @RequestParam(value = "valorContraproposta", required = false) BigDecimal valorContraproposta, @RequestParam(value = "condicoesContraproposta", required = false) String condicoesContraproposta, RedirectAttributes attr) {
        Proposta proposta = service.buscarPorId(id);
        if(valorContraproposta != null) {
            proposta.setValorContraproposta(valorContraproposta);
        }
        if (condicoesContraproposta != null) {
            proposta.setCondicoesContraproposta(condicoesContraproposta);
        }

        service.salvar(proposta);
        attr.addFlashAttribute("success", "Contraproposta enviada com sucesso.");
        return "redirect:/proposta/rejeitar/" + id;
    }




}
