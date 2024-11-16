package sptech.school.apizeporteiro.domain.porteiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.porteiro.Porteiro;

import java.util.List;
import java.util.Optional;

@Repository
public interface PorteiroRepository extends JpaRepository<Porteiro, Integer> {
    List<Porteiro> findByCondominioId(Integer condominioId);
}