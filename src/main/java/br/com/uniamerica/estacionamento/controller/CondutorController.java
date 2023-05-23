package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorRepository condutorRep;

    @Autowired
    private CondutorService condutorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Condutor condutor = this.condutorRep.findById(id).orElse(null);
        return ResponseEntity.ok(condutor);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListCompCondutor(){
        return ResponseEntity.ok(this.condutorRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(condutorRep.findByAtivo(false));
        }
        return ResponseEntity.ok(condutorRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrarCondutor(@RequestBody final Condutor condutor){
        try {
            this.condutorService.createCondutor(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editarCondutor(@RequestParam("id") final Long id, @RequestBody final Condutor condutor){
        try {
            final Condutor condutor1 = this.condutorRep.findById(id).orElse(null);

            if (condutor1 == null || !condutor1.getId().equals(condutor1.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            condutorService.atualizaCondutor(condutor);
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

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletaCondutor(@PathVariable Long id){
        Condutor condutor = this.condutorRep.findById(id).orElse(null);

        if (condutor == null || condutor.getId().equals(condutor.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        if(condutor.isAtivo()){
            condutor.setAtivo(false);
            return ResponseEntity.ok ("Desativado com sucesso");
        }

        condutorRep.deleteById(id);
        return ResponseEntity.ok ("Deletado com sucesso");
    }

}