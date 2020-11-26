package br.com.desafio.repository;

import br.com.desafio.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    Optional<Voto> findByAssociadoIdAndSessaoId(Long associadoId, Long sessaoId);
}
