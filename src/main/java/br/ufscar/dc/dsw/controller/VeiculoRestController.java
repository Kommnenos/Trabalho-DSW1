package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import br.ufscar.dc.dsw.domain.Loja;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@CrossOrigin
@RestController
public class VeiculoRestController {

    @Autowired
    private IVeiculoService service;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Loja loja, JSONObject json) {
        Object id = json.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                loja.setId(((Integer) id).longValue());
            } else {
                loja.setId((Long) id);
            }
        }

        loja.setNome((String) json.get("nome"));
        loja.setEmail((String) json.get("email"));
        loja.setEnabled((Boolean) json.get("enabled"));
        loja.setDescricao((String) json.get("descricao"));
        loja.setSenha((String) json.get("senha"));
    }

    private void parse(Veiculo veiculo, JSONObject json) {

        Object id = json.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                veiculo.setId(((Integer) id).longValue());
            } else {
                veiculo.setId((Long) id);
            }
        }

        veiculo.setId( (Long) json.get("id") );
        veiculo.setAno((Integer) json.get("ano"));
        veiculo.setDescricao((String) json.get("descricao"));
        veiculo.setChassi((String) json.get("chassi"));
        veiculo.setModelo((String) json.get("modelo"));
        veiculo.setQuilometragem((Integer) json.get("quilometragem"));
        veiculo.setValor((BigDecimal) json.get("valor"));
        veiculo.setPlaca((String) json.get("placa"));

        Loja loja = new Loja();
        parse(loja, json);
        veiculo.setLoja(loja);
    }


    @GetMapping(path = "/api/veiculos")
    public ResponseEntity<List<Veiculo>> lista() {
        List<Veiculo> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/api/veiculos/{id}")
    public ResponseEntity<Veiculo> lista(@PathVariable("id") long id) {
        Veiculo veiculo = service.buscarPorId(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping(path = "/api/veiculos")
    @ResponseBody
    public ResponseEntity<Veiculo> cria(@RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Veiculo veiculo = new Veiculo();
                parse(veiculo, json);
                service.salvar(veiculo);
                return ResponseEntity.ok(veiculo);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/api/veiculos/{id}")
    public ResponseEntity<Veiculo> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Veiculo veiculo = service.buscarPorId(id);
                if (veiculo == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(veiculo, json);
                    service.salvar(veiculo);
                    return ResponseEntity.ok(veiculo);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/api/veiculos/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

        Veiculo veiculo = service.buscarPorId(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}