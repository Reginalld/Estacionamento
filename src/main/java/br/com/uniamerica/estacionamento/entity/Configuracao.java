package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "configurações", schema = "public")
public class Configuracao extends abstractEntity {

    @Getter @Setter
    @Column(name = "valorHora")
    private float valorHora;
    @Getter @Setter
    @Column(name = "valorMinutoMulta")
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicioExpediente")
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fimExpediente")
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempoParaDesconto")
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempoDeDesconto")
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerarDesconto")
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagasMoto")
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "vagasCarro")
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "vagasVan")
    private int vagasVan;


}
