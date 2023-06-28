package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
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
import java.util.Optional;

@Service
public class MovimentacaoService  {

    @Autowired
    private MovimentacaoRepository movimentacaoRep;

    @Autowired
    private ConfiguracaoRepository configuracaoRep;

    private Movimentacao movimentacao;


    @Transactional(rollbackFor = Exception.class)
    public void createMovimentacao(Movimentacao movimentacao){
        Assert.isTrue(movimentacao.getVeiculo() != null,"Veiculo não pode ser nulo");

        Assert.isTrue(movimentacao.getCondutor() != null,  "Condutor não pode ser nulo");

        movimentacao.setAtivo(true);

        movimentacao.setEntrada(LocalDateTime.now());

        //entradaConta = LocalDateTime.now();

        this.movimentacaoRep.save(movimentacao);




    }


    @Transactional(rollbackFor = Exception.class)
    public void editarMovimentacao(Movimentacao movimentacao,Long id){
        Assert.isTrue(movimentacao.getVeiculo() != null,"Veiculo não pode ser nulo");

        Assert.isTrue(movimentacao.getCondutor() != null,  "Condutor não pode ser nulo");

        movimentacao.setAtivo(true);

        //entradaConta = LocalDateTime.now();

        this.movimentacaoRep.save(movimentacao);

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> finalizarMovimentacao(Movimentacao movimentacao,Long id) {

        final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

        Configuracao configuracao = this.configuracaoRep.findById(1L).orElse(null);

        Assert.isTrue(configuracao.getValorHora() != 0, "Adiciona um valor para valorHora");

        movimentacao.setSaida(LocalDateTime.now());

        Assert.isTrue(movimentacao1.getSaida() == null,"Essa movimentacao ja foi finalizada. ID:" + movimentacao1.getId());


        Assert.isTrue(movimentacao.getVeiculo() != null,"Veiculo não pode ser nulo");

        Assert.isTrue(movimentacao.getCondutor() != null,  "Condutor não pode ser nulo");


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


        //float miniFoda = configuracaoServ.souFoda;


        float novo = configuracao.getValorHora();

        float taDevendoTrouxa = (secondsToHours + minutosToHours + paraHorasH) * novo;

        System.out.println(taDevendoTrouxa);
        movimentacao.setAtivo(false);

        movimentacao.setValorTotal(taDevendoTrouxa);

        movimentacao.setValorHora(configuracao.getValorHora());



        this.movimentacaoRep.save(movimentacao);

       return ResponseEntity.ok("Horas a pagar: " + (secondsToHours + minutosToHours + paraHorasH) + "-----------------------------"+"\nTotal a pagar: " + taDevendoTrouxa
                + "\n Hora da entrada: " + movimentacao.getEntrada() + "\n Hora da saida: " + movimentacao.getSaida() +
                "\n Placa do veiculo: " + movimentacao1.getVeiculo().getPlaca()+ "\n Modelo do veiculo: "+ movimentacao1.getVeiculo().getModelo().getNome()+
              "\n Ano do veiculo: "+ movimentacao1.getVeiculo().getAno()+"\n Cor do veiculo: "+ movimentacao1.getVeiculo().getCor()+ "\n Tipo do veiculo: "+movimentacao1.getVeiculo().getTipo()
       +"\n Nome do condutor: "+ movimentacao1.getCondutor().getNome());

    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletar(Long id) {

        Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

        movimentacao1.setAtivo(false);

        if(!movimentacao1.isAtivo()){
            return ResponseEntity.ok("Já desativado");

        }
        this.movimentacaoRep.save(movimentacao);
        return ResponseEntity.ok("Desativado com sucesso");
    }
}
