package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRep;

    
    public float souFoda;

    public void valorHoraFunc(Configuracao configuracao){


        Configuracao configuracao1 = configuracaoRep.findByvalorHora(configuracao.getValorHora());

        souFoda = configuracao1.getValorHora();

        System.out.println(configuracao1.getValorHora());

    }

}
