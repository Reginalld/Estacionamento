package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
public class MarcaService {

    @Autowired

    private MarcaRepository marcaRep;

    @Transactional(rollbackFor = Exception.class)

    public void createMarca(final Marca marca){


        Assert.isTrue(!marca.getNome().equals(""),"Marca não pode ser nulo!");
        Assert.isTrue(marca.getNome().length() <= 50 && marca.getNome().length() >= 3,"Máximo de 50 caracteres e minimo de 3");

       // Assert.isTrue(marca.getNome().substring(0,50).matches("[A-Z]*"),"Nenhuma marca tem número no nome");

        Marca marcaExistente = marcaRep.findByNome(marca.getNome());
        Assert.isTrue(marcaExistente == null || marcaExistente.equals(marca),"Marca já existente");

        marca.setAtivo(true);

        this.marcaRep.save(marca);
    }

    public void deletar(Long id,Marca marca){
        this.marcaRep.deleteById(id);
    }




}