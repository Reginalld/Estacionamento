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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "modelo_marca",
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {
                            "modelo_id",
                            "marca_id"
                    }
            ),
            joinColumns = @JoinColumn(
                    name = "modelo_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "marca_id"
            )
    )
    private Marca marca;


}
