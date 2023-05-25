package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping (value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRep;

    @Autowired
    private MovimentacaoService movimentacaoServ;

    @Autowired
    private ConfiguracaoService configuracaoServ;



    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id) {
        final Movimentacao movimentacao = this.movimentacaoRep.findById(id).orElse(null);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> ListCompCondutor(){
        return ResponseEntity.ok(this.movimentacaoRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(movimentacaoRep.findByAtivo(false));
        }
        return ResponseEntity.ok(movimentacaoRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try {
            this.movimentacaoServ.createMovimentacao(movimentacao);
            return ResponseEntity.ok(" Registro cadastrado com sucesso" + movimentacao.getEntrada());

        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try {
            final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

            if (movimentacao1 == null || !movimentacao1.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }
           return  movimentacaoServ.finalizarMovimentacao(movimentacao,id);
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
    public ResponseEntity<?> deletaCondutor(@PathVariable Long id,Movimentacao movimentacao){
        try {

            final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

            if (movimentacao1 == null || movimentacao1.getId() != movimentacao.getId()){
                throw new RuntimeException("Nao foi possivel indentificar o registro informado");
            }

            movimentacao.setAtivo(false);
            return ResponseEntity.ok("Desativado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

}
