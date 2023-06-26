package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Modelos", schema = "public")
public class Modelo extends abstractEntity{
    @Getter @Setter
    @Column(name = "nomeModelo", nullable = false, unique = true, length = 50)
    private String nome;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id",nullable = false)
    private Marca marca;


}
