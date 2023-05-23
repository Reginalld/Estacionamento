package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class VeiculoService {
    
    @Autowired

    private VeiculoRepository veiculoRep;

    @Transactional(rollbackFor = Exception.class)

    public void createVeiculo(final Veiculo veiculo){


        Assert.isTrue(!veiculo.getPlaca().equals(""),"Placa não pode ser nulo!");
        Assert.isTrue(veiculo.getPlaca().length() == 10,"Deve conter 10 dígitos");
        Veiculo veiculoExistente = veiculoRep.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(veiculo),"Placa já existente");

        Assert.isTrue(veiculo.getModelo() != null,"Modelo não pode ser nulo");

        Assert.isTrue(veiculo.getTipo() != null,"Tipo não pode ser nulo");
        //Assert.isTrue(veiculo.getTipo().length() <= 15, "Deve conter até 15 digitos");

        Assert.isTrue(veiculo.getCor() != null,"Cor não pode ser nulo");
        //Assert.isTrue(veiculo.getTipo().length() <= 20, "Deve conter até 20 digitos);

        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        veiculo.setAtivo(true);

        this.veiculoRep.save(veiculo);
    }

    public void deletar(Long id,Veiculo veiculo){
        this.veiculoRep.deleteById(id);
    }
}