package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.configs.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRep;

    @Autowired
    private ValidaCPF validaCPF;

    @Transactional(rollbackFor = Exception.class)
    public void createCondutor(final Condutor condutor){

        Assert.isTrue(condutor.getNome() != null,"Nome não pode ser nulo!");
        Assert.isTrue(condutor.getNome().length() <= 50,"Máximo de 50 caracteres");

       Assert.isTrue(condutor.getCpf() != null,"Cpf não pode ser nulo");
        Assert.isTrue(condutor.getCpf().length() == 11, "Cpf deve conter 11 digitos");
        Condutor condutorExistente = condutorRep.findByCpf(condutor.getCpf());
        Assert.isTrue( condutorExistente == null || condutorExistente.equals(condutor.getCpf()),"Cpf já existente");

        if (this.validaCPF.isCPF(condutor.getCpf()) == true) {
            System.out.printf("%s\n", this.validaCPF.imprimeCPF(condutor.getCpf()));
        }
        else {
            int x = 2;
            System.out.printf("Erro, CPF invalido !!!\n");
            Assert.isTrue(x == 1, "Cpf inválido");

        }

        Assert.isTrue(condutor.getTelefone() != null ,"Telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11, "Telefone deve conter 11 digítos, incluindo o DDD e o 9 extra");
        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas Números");

        Condutor condutorTelefoneExistente = condutorRep.findByTelefone(condutor.getTelefone());
        Assert.isTrue(condutorTelefoneExistente == null || condutorTelefoneExistente.equals(condutor.getTelefone()), "Telefone já existente");

        condutor.setTempoPago(LocalTime.parse("00:00:00"));
        condutor.setTempoDesconto(LocalTime.parse("00:00:00"));


        condutor.setAtivo(true);

        this.condutorRep.save(condutor);
    }

    public void atualizaCondutor (Condutor condutor){
        Assert.isTrue(!condutor.getNome().equals(""),"Nome não pode ser nulo!");
        Assert.isTrue(condutor.getNome().length() <= 50,"Máximo de 50 caracteres");

        Assert.isTrue(!condutor.getCpf().equals(""),"Cpf não pode ser nulo");
        Assert.isTrue(condutor.getCpf().length() == 11, "Cpf deve conter 11 digitos");
        /*Condutor condutorExistente = condutorRep.findByCpf(condutor.getCpf());
        Assert.isTrue( condutorExistente == null || condutorExistente.equals(condutor.getCpf()),"Cpf já existente");*/


        if (this.validaCPF.isCPF(condutor.getCpf()) == true) {
            System.out.printf("%s\n", this.validaCPF.imprimeCPF(condutor.getCpf()));
        }
        else {
            int x = 2;
            System.out.printf("Erro, CPF invalido !!!\n");
            Assert.isTrue(x == 1, "Cpf inválido");

        }

        Assert.isTrue(!condutor.getTelefone().equals(""),"Telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11, "Telefone deve conter 11 digítos, incluindo o DDD e o 9 extra");
        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas Números");

       /* Condutor condutorTelefoneExistente = condutorRep.findByTelefone(condutor.getTelefone());
        Assert.isTrue(condutorTelefoneExistente == null || condutorTelefoneExistente.equals(condutor.getTelefone()), "Telefone já existente");*/

        Assert.isTrue(condutor.getTempoPago() != null,"TempoPago não pode ser nulo");
        Assert.isTrue(condutor.getTempoDesconto() != null, "TempoDesconto não pode ser nulo");

        condutor.setAtivo(true);

        this.condutorRep.save(condutor);
    }

    public ResponseEntity<?> deletar(Long id) {
        Condutor condutor = this.condutorRep.findById(id).orElse(null);

        if (condutor == null || condutor.getId() != (condutor.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Movimentacao> movimentacaoLista = this.condutorRep.findByMovimentacao(condutor);

        if (movimentacaoLista.isEmpty()) {
            if(condutor.isAtivo()) {
                condutor.setAtivo(false);
                this.condutorRep.save(condutor);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.condutorRep.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!condutor.isAtivo()) {
            Assert.isTrue(movimentacaoLista.isEmpty(), "Condutor vinculado a uma movimentação");
            this.condutorRep.deleteById(id);
        }


        condutor.setAtivo(false);
        this.condutorRep.save(condutor);
        return ResponseEntity.ok("Desativado com sucesso");

    }
}

