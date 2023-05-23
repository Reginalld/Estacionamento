package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "condutores", schema = "public")
public class Condutor extends abstractEntity{

    @Getter @Setter
    @Column(name = "none",nullable = false,length = 50)
    private String nome;
    @Getter @Setter
    @Column (name = "cpf",nullable = false,unique = true,length = 11)
    private String cpf;
    @Getter @Setter
    @Column (name = "telefone",nullable = false,length =11)
    private String telefone;
    @Getter @Setter
    @Column(name = "tempo_pago",nullable = false)
    private LocalTime tempoPago;
    @Getter @Setter
    @Column (name = "tempo_desconto",nullable = false)
    private LocalTime tempoDesconto;


}