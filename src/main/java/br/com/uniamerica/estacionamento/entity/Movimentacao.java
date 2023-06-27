package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Movimentações", schema = "public")
public class Movimentacao extends abstractEntity{
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculo",nullable = false)
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condutor", nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "Entrada")
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "Saida")
    private LocalDateTime saida;
    @Getter @Setter
    @Column(name = "tempo")
    private LocalTime tempo;
    @Getter @Setter
    @Column(name = "tempoDesconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempoMulta")
    private LocalTime tempoMulta;
    @Getter @Setter
    @Column(name = "valorDesconto")
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name = "valorMulta")
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "valorTotal")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valorHora")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valorHoraMulta")
    private BigDecimal valorHoraMulta;

}
