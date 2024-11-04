package sptech.school.apizeporteiro.domain.condominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.apizeporteiro.domain.condominio.Condominio;

@Repository
public interface CondominioRepository extends JpaRepository<Condominio, Integer> {

    Condominio findByCep(String cep);
}
