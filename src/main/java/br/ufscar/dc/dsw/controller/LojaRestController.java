package br.ufscar.dc.dsw.controller;

import java.io.IOException;
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
import br.ufscar.dc.dsw.service.spec.ILojaService;

@CrossOrigin
@RestController
public class LojaRestController {

    @Autowired
    private ILojaService service;

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


    @GetMapping(path = "/lojas")
    public ResponseEntity<List<Loja>> lista() {
        List<Loja> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/lojas/{id}")
    public ResponseEntity<Loja> lista(@PathVariable("id") long id) {
        Loja loja = service.buscarPorId(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loja);
    }

    @PostMapping(path = "/lojas")
    @ResponseBody
    public ResponseEntity<Loja> cria(@RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Loja loja = new Loja();
                parse(loja, json);
                service.salvar(loja);
                return ResponseEntity.ok(loja);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/lojas/{id}")
    public ResponseEntity<Loja> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Loja loja = service.buscarPorId(id);
                if (loja == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(loja, json);
                    service.salvar(loja);
                    return ResponseEntity.ok(loja);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/lojas/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

        Loja loja = service.buscarPorId(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}