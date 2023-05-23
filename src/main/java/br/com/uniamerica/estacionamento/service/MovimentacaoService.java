package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MovimentacaoService  {

    @Autowired
    private MovimentacaoRepository movimentacaoRep;

    @Autowired
    private ConfiguracaoService configuracaoServ;

    @Autowired
    private VeiculoRepository veiculoRep;



    @Transactional(rollbackFor = Exception.class)
    public void createMovimentacao(Movimentacao movimentacao){
       Assert.isTrue(movimentacao.getVeiculo() != null,"Veiculo não pode ser nulo");
        Movimentacao movimentacaoExistente = movimentacaoRep.findByVeiculo(movimentacao.getVeiculo());
        Assert.isTrue(movimentacaoExistente == null || movimentacaoExistente.equals(movimentacao), "Veículo já existente");

        Assert.isTrue(movimentacao.getCondutor() != null,  "Condutor não pode ser nulo");

        Assert.isTrue(movimentacao.getEntrada() != null, "Entrada não pode ser nulo");

        movimentacao.setAtivo(true);

        //entradaConta = LocalDateTime.now();

        this.movimentacaoRep.save(movimentacao);




    }

    public ResponseEntity<?> finalizarMovimentacao(Movimentacao movimentacao) {

        Assert.isTrue(movimentacao.getVeiculo() != null,"Veiculo não pode ser nulo");

        Assert.isTrue(movimentacao.getCondutor() != null,  "Condutor não pode ser nulo");

        Assert.isTrue(movimentacao.getEntrada() != null, "Entrada não pode ser nulo");

        Assert.isTrue(movimentacao.getSaida() != null, "Saida não pode ser nulo na finalização");

        movimentacao.setAtivo(false);

        Duration valorEntre = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());

        String formattedElapsedTime = String.format("%02d%02d%02d", valorEntre.toHoursPart(), valorEntre.toMinutesPart(),
              valorEntre.toSecondsPart());

        String stringHoras = String.format("%02d" , valorEntre.toHoursPart());
        String stringMinutos = String.format("%02d" , valorEntre.toMinutesPart());
        String stringSegundos = String.format("%02d" , valorEntre.toSecondsPart());

        float paraHorasM = Float.parseFloat(stringMinutos);
        float minutosToHours = paraHorasM / 60;

        float paraHorasS = Float.parseFloat(stringSegundos);
        float secondsToHours = paraHorasS / 3600;

        float paraHorasH = Float.parseFloat(stringHoras);


        float miniFoda = configuracaoServ.souFoda;

        Assert.isTrue(miniFoda != 0.0, "Adiciona um valor para valorHora");


        float taDevendoTrouxa = (secondsToHours + minutosToHours + paraHorasH) * miniFoda;

        System.out.println(taDevendoTrouxa);

        this.movimentacaoRep.save(movimentacao);

        return ResponseEntity.ok("Horas a pagar: "+ (secondsToHours + minutosToHours + paraHorasH) +"\n Total a pagar: " + taDevendoTrouxa
        + "\n Hora da entrada: " + movimentacao.getEntrada() + "\n Hora da saida: " + movimentacao.getSaida() +
        "\n Veiculo: "+ movimentacao.getVeiculo());

    }



    public void deletar(Long id,Movimentacao movimentacao){
        this.movimentacaoRep.deleteById(id);
    }
}
