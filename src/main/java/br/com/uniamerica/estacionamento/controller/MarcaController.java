package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping (value = "/api/marca")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRep;

    @Autowired
    private MarcaService marcaServ;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Marca marca = this.marcaRep.findById(id).orElse(null);
        return ResponseEntity.ok(marca);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListCompCondutor(){
        return ResponseEntity.ok(this.marcaRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(marcaRep.findByAtivo(false));
        }
        return ResponseEntity.ok(marcaRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Marca marca){
        try {
            this.marcaServ.createMarca(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try {
            final Marca marca1 = this.marcaRep.findById(id).orElse(null);

            if (marca1 == null || !marca1.getId().equals(marca.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            this.marcaServ.editarMarca(marca);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletaCondutor(@PathVariable("id") final Long id) {
       try {
           return this.marcaServ.deletar(id);
       }
       catch (RuntimeException e){
           return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
       }
    }
}
