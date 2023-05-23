package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRep;

    @Autowired
    private VeiculoService veiculoServ;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Veiculo veiculo = this.veiculoRep.findById(id).orElse(null);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListCompCondutor(){
        return ResponseEntity.ok(this.veiculoRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(veiculoRep.findByAtivo(false));
        }
        return ResponseEntity.ok(veiculoRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Veiculo veiculo){
        try {
            this.veiculoServ.createVeiculo(veiculo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
        try {
            final Veiculo veiculo1 = this.veiculoRep.findById(id).orElse(null);

            if (veiculo1 == null || veiculo1.getId().equals(veiculo.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
            this.veiculoRep.save(veiculo);
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
    public void deletaCondutor(@PathVariable Long id){
        veiculoRep.deleteById(id);
    }

}
