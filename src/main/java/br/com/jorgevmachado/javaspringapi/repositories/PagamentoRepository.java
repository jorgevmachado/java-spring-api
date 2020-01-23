package br.com.jorgevmachado.javaspringapi.repositories;

import br.com.jorgevmachado.javaspringapi.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
