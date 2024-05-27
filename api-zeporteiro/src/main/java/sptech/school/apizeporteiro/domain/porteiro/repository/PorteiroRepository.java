package sptech.school.apizeporteiro.domain.porteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

@Repository
public interface PorteiroRepository extends JpaRepository<Porteiro, Integer> {

    // SELECT * FROM porteiro WHERE nome LIKE '%XPTO%'
    List<Porteiro> findByNomeIgnoreCase(String nome);
    // Utilizado no JWT
    Optional<Porteiro> findByRg(String rg);
}
