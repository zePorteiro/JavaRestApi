package sptech.school.apizeporteiro.domain.morador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.morador.Morador;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Integer> {
}

