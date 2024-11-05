package sptech.school.apizeporteiro.domain.morador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.morador.Morador;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Integer> {
    List<Morador> findByApartamentoId(int apartamentoId);
    Optional<Morador> findByEmail(String email);
    Optional<Morador> findByEmailAndSenha(String email, String senha);
}

