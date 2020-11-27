package br.com.desafio.repository;

import br.com.desafio.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Optional<Associado> findByNome(String nome);
    Optional<Associado> findByCpf(String cpf);
}
