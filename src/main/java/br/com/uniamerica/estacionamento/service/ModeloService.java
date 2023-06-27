package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



@Service
public class ModeloService {

    @Autowired

    private ModeloRepository modeloRep;

    @Autowired
    private MarcaRepository marcaRep;


    @Transactional(rollbackFor = Exception.class)
    public void createModelo(Modelo modelo){

        Assert.isTrue(!modelo.getNome().equals(""),"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() >=2 && modelo.getNome().length() <= 50, "Deve conter até 50 digitos e no minimo 2");
        Modelo modeloExistente = modeloRep.findByNome(modelo.getNome());
        Assert.isTrue(modeloExistente == null || modeloExistente.equals(modelo), "Modelo já existente");


        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");



        modelo.setAtivo(true);

        this.modeloRep.save(modelo);


    }

    public void editarModelo(Modelo modelo){

        Assert.isTrue(!modelo.getNome().equals(""),"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() >=2 && modelo.getNome().length() <= 50, "Deve conter até 50 digitos e no minimo 2");

        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");


        this.modeloRep.save(modelo);


    }

        public ResponseEntity<?> deletar(Long id){
            Modelo modelo = this.modeloRep.findById(id).orElse(null);

            if (modelo == null || modelo.getId() != (modelo.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }


            if(modelo.isAtivo()){
                modelo.setAtivo(false);
                modeloRep.save(modelo);
                return ResponseEntity.ok ("Desativado com sucesso");
            }

            modelo.setAtivo(true);
            modeloRep.deleteById(id);
            return ResponseEntity.ok ("Deletado com sucesso");
        }
    }




