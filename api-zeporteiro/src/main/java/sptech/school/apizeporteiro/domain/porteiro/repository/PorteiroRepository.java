package sptech.school.apizeporteiro.domain.porteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;

@Repository
public interface PorteiroRepository extends JpaRepository<Porteiro, Integer> {
}
