package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;




public interface CondutorRepository extends JpaRepository<Condutor,Long> {


    Condutor findByCpf(String cpf);

    boolean findByAtivo(boolean ativo);
}
