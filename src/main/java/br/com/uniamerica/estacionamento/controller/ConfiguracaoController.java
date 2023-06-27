package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/configuracao")

public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRep;

    @Autowired
    private ConfiguracaoService configuracaoServ;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Configuracao configuracao = this.configuracaoRep.findById(id).orElse(null);
        return ResponseEntity.ok(configuracao);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListCompCondutor(){
        return ResponseEntity.ok(this.configuracaoRep.findAll());

    }

    @PostMapping
    public ResponseEntity <?> cadastrarCondutor(@RequestBody final Configuracao configuracao){
        try {
            this.configuracaoRep.save(configuracao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCondutor(@PathVariable("id") final Long id, @RequestBody final Configuracao configuracao){
        try {
            final Configuracao configuracao1 = this.configuracaoRep.findById(id).orElse(null);

            if (configuracao1 == null || configuracao1.getId() != (configuracao1.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            this.configuracaoRep.save(configuracao);
            return ResponseEntity.ok("Registro Cadastrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }


}
