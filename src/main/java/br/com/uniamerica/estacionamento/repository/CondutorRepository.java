package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;




public interface CondutorRepository extends JpaRepository<Condutor,Long> {


    Condutor findByCpf(String cpf);

    List<Condutor> findByAtivo(boolean ativo);

    Condutor findByNome(String nome);

    Condutor findByTelefone(String telefone);

    @Query("from Movimentacao where condutor = :condutor")
    public List<Movimentacao> findByMovimentacao (@Param("condutor") final Condutor condutor);


}
