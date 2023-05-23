package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca,Long>{



    Marca findByNome(String nome);

    List<Marca> findByAtivo(boolean ativo);
}
