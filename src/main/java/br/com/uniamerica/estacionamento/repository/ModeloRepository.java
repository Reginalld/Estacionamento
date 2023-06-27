package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long>{


    @Query("from Veiculo where modelo = :modelo")
    public List<Veiculo> findVeiculo (@Param("modelo") final Modelo modelo);

    Modelo findByNome(String nome);

    List<Modelo> findByAtivo(boolean ativo);
}
