package org.santiago.springcloud.msvcusers.services.abstractions;

import org.santiago.springcloud.msvcusers.models.entities.Usager;

import java.util.List;
import java.util.Optional;

public interface UsagerService {
    //Atributos de UsagerService
    //Constructores de UsagerService
    //Asignadores de atributos de UsagerService (setters)
    //Lectores de atributos de UsagerService (getters)
        //Métodos de UsagerService
    List<Usager> findAll();
    Optional<Usager> findById(Long id);
    Optional<Usager> findByUsername(String username);
    Usager save(Usager usager);
    Optional<Usager> update(Long id, Usager usager);
    void disableUser(Long id);
    void deleteById(Long id);
    void delete(Usager usager);
}
