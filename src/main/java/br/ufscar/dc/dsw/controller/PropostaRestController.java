package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.dsw.domain.StatusProposta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

public class PropostaRestController {

    private IPropostaService service;
    private IClienteService clienteService;
    private IVeiculoService veiculoService;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Cliente cliente, JSONObject json) {

        Object id = json.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                cliente.setId(((Integer) id).longValue());
            } else {
                cliente.setId((Long) id);
            }
        }

        cliente.setNome((String) json.get("nome"));
        cliente.setCPF((String) json.get("cpf"));
        cliente.setEmail((String) json.get("email"));
        cliente.setSenha((String) json.get("senha"));
        cliente.setTelefone((String) json.get("telefone"));
        cliente.setSexo((String) json.get("sexo"));
        cliente.setRole((String) json.get("role"));
        cliente.setDataNasc((LocalDate) json.get("dataNasc"));
        cliente.setEnabled((Boolean) json.get("enabled"));
    }

    private void parse(Proposta proposta, JSONObject json) {
        Object id = json.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                proposta.setId(((Integer) id).longValue());
            } else {
                proposta.setId((Long) id);
            }
        }
        proposta.setStatus((StatusProposta) json.get("status"));
        proposta.setData((String) json.get("data"));
        proposta.setDataReuniao((LocalDateTime) json.get("dataReuniao"));
        proposta.setCondicoesContraproposta((String) json.get("condicoesContraproposta"));
        proposta.setCondicoesPagamento((String) json.get("condicoesPagamento"));
        proposta.setLinkVideoconferencia((String) json.get("linkVideoconferencia"));
        proposta.setValor((BigDecimal) json.get("valor"));
        proposta.setValorContraproposta((BigDecimal) json.get("valorContraproposta"));

        Cliente cliente = new Cliente();
        parse(cliente, json);
        proposta.setCliente(cliente);

    }

    @GetMapping(path = "/api/clientes/propostas/{id}")
    public ResponseEntity<List<Proposta>> listaPorCliente(@PathVariable("id") long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        List<Proposta> lista = service.buscarTodosPorCliente(cliente);

        if (lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(lista);

    }

    @GetMapping(path="/api/veiculos/proposta/{id}")
    public ResponseEntity<List<Proposta>> listaPorVeiculo(@PathVariable("id") long id){
        Veiculo veiculo = veiculoService.buscarPorId(id);
        List<Proposta> lista = service.buscarTodosPorVeiculo(veiculo);

        if (lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);


    }



}
