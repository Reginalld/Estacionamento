package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca,Long>{


    @Query("from Modelo where marca = :marca")
    public List<Modelo> findModelo (@Param("marca") final Marca marca);

    Marca findByNome(String nome);

    List<Marca> findByAtivo(boolean ativo);
}
