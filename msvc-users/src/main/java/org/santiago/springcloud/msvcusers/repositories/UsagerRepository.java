package org.santiago.springcloud.msvcusers.repositories;

import org.santiago.springcloud.msvcusers.models.entities.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsagerRepository extends JpaRepository<Usager, Long> {
    //Atributos de UsagerRepository
    //Constructores de UsagerRepository
    //Asignadores de atributos de UsagerRepository (setters)
    //Lectores de atributos de UsagerRepository (getters)
        //Métodos de UsagerRepository
    Optional<Usager> findByUsername(String username);
}
