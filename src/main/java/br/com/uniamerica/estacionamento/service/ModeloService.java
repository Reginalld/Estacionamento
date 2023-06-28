package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@Service
public class ModeloService {

    @Autowired

    private ModeloRepository modeloRep;

    @Autowired
    private MarcaRepository marcaRep;


    @Transactional(rollbackFor = Exception.class)
    public void createModelo(Modelo modelo){

        Assert.isTrue(modelo.getNome() != null,"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() >=2 && modelo.getNome().length() <= 50, "Deve conter até 50 digitos e no minimo 2");
        Modelo modeloExistente = modeloRep.findByNome(modelo.getNome());
        Assert.isTrue(modeloExistente == null || modeloExistente.equals(modelo), "Modelo já existente");


        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");



        modelo.setAtivo(true);

        this.modeloRep.save(modelo);


    }

    @Transactional(rollbackFor = Exception.class)
    public void editarModelo(final Modelo modelo){

        Assert.isTrue(modelo.getNome() != null,"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() >=2 && modelo.getNome().length() <= 50, "Deve conter até 50 digitos e no minimo 2");

        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");


        this.modeloRep.save(modelo);


    }

    public ResponseEntity<?> deletar(Long id) {
        Modelo modelo = this.modeloRep.findById(id).orElse(null);

        if (modelo == null || modelo.getId() != (modelo.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Veiculo> veiculoLista = this.modeloRep.findVeiculo(modelo);

        if (veiculoLista.isEmpty()) {
            if(modelo.isAtivo()) {
                modelo.setAtivo(false);
                this.modeloRep.save(modelo);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.modeloRep.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!modelo.isAtivo()) {
            Assert.isTrue(veiculoLista.isEmpty(), "Modelo vinculado a um veiculo");
            this.marcaRep.deleteById(id);
        }


        modelo.setAtivo(false);
        this.modeloRep.save(modelo);
        return ResponseEntity.ok("Desativado com sucesso");

    }


    }




