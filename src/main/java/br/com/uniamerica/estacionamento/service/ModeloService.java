package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
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



    public void deletar(Long id,Modelo modelo){
        this.modeloRep.deleteById(id);
    }



}
