package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.configs.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRep;

    @Autowired
    private ValidaCPF validaCPF;

    @Transactional(rollbackFor = Exception.class)
    public void createCondutor(final Condutor condutor){

        Assert.isTrue(!condutor.getNome().equals(""),"Nome não pode ser nulo!");
        Assert.isTrue(condutor.getNome().length() <= 50,"Máximo de 50 caracteres");

       Assert.isTrue(!condutor.getCpf().equals(""),"Cpf não pode ser nulo");
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

        Assert.isTrue(!condutor.getTelefone().equals(""),"Telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11, "Telefone deve conter 11 digítos, incluindo o DDD e o 9 extra");
        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas Números");

        Assert.isTrue(condutor.getTempoPago() != null,"TempoPago não pode ser nulo");
        Assert.isTrue(condutor.getTempoDesconto() != null, "TempoDesconto não pode ser nulo");


        this.condutorRep.save(condutor);
    }

    public void atualizaCondutor (Condutor condutor){
        Assert.isTrue(!condutor.getNome().equals(""),"Nome não pode ser nulo!");
        Assert.isTrue(condutor.getNome().length() <= 50,"Máximo de 50 caracteres");

        Assert.isTrue(!condutor.getCpf().equals(""),"Cpf não pode ser nulo");
        Assert.isTrue(condutor.getCpf().length() == 11, "Cpf deve conter 11 digitos");


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

        Assert.isTrue(condutor.getTempoPago() != null,"TempoPago não pode ser nulo");
        Assert.isTrue(condutor.getTempoDesconto() != null, "TempoDesconto não pode ser nulo");

        condutor.setAtivo(true);

        this.condutorRep.save(condutor);
    }

    public ResponseEntity<?> deletar(Long id){
        Condutor condutor = this.condutorRep.findById(id).orElse(null);

        if (condutor == null || condutor.getId() != (condutor.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        if(condutor.isAtivo()){
            condutor.setAtivo(false);
            condutorRep.save(condutor);
            return ResponseEntity.ok ("Desativado com sucesso");
        }
            condutorRep.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
    }
    }

