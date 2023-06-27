package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Year;
import java.util.List;

@Service
public class VeiculoService {
    
    @Autowired
    private VeiculoRepository veiculoRep;

    private int qualquer = Year.now().getValue() + 1;

    @Transactional(rollbackFor = Exception.class)
    public void createVeiculo(final Veiculo veiculo){


        Assert.isTrue(veiculo.getPlaca() != null,"Placa não pode ser nulo!");
        Assert.isTrue(veiculo.getPlaca().length() == 10,"Deve conter 10 dígitos");
        Veiculo veiculoExistente = veiculoRep.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(veiculo),"Placa já existente");

        Assert.isTrue(veiculo.getModelo() != null,"Modelo não pode ser nulo");

        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        Assert.isTrue(veiculo.getTipo() != null,"Tipo não pode ser nulo");
        //Assert.isTrue(veiculo.getTipo().length() <= 15, "Deve conter até 15 digitos");

        Assert.isTrue(veiculo.getCor() != null,"Cor não pode ser nulo");
        //Assert.isTrue(veiculo.getTipo().length() <= 20, "Deve conter até 20 digitos);


        veiculo.setAtivo(true);

        this.veiculoRep.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizaVeiculo(final Veiculo veiculo){


        Assert.isTrue(veiculo.getPlaca() != null,"Placa não pode ser nulo!");
        Assert.isTrue(veiculo.getPlaca().length() == 10,"Placa Deve conter 10 dígitos");

        Assert.isTrue(veiculo.getModelo() != null,"Modelo não pode ser nulo");

        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        Assert.isTrue(veiculo.getTipo() != null,"Tipo não pode ser nulo");
        //Assert.isTrue(veiculo.getTipo().length() <= 15, "Deve conter até 15 digitos");

        Assert.isTrue(veiculo.getCor() != null,"Cor não pode ser nulo");
        //Assert.isTrue(veiculo.getTipo().length() <= 20, "Deve conter até 20 digitos);


        veiculo.setAtivo(true);

        this.veiculoRep.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletar(Long id) {
        Veiculo veiculo = this.veiculoRep.findById(id).orElse(null);

        if (veiculo == null || veiculo.getId() != (veiculo.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Movimentacao> movimentacaoLista = this.veiculoRep.findByMovimentacao(veiculo);

        if (movimentacaoLista.isEmpty()) {
            if(veiculo.isAtivo()) {
                veiculo.setAtivo(false);
                this.veiculoRep.save(veiculo);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.veiculoRep.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!veiculo.isAtivo()) {
            Assert.isTrue(movimentacaoLista.isEmpty(), "Veiculo vinculado a uma movimentação");
            this.veiculoRep.deleteById(id);
        }


        veiculo.setAtivo(false);
        this.veiculoRep.save(veiculo);
        return ResponseEntity.ok("Desativado com sucesso");

    }
}
