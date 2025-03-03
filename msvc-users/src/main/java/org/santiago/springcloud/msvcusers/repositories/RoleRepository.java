package org.santiago.springcloud.msvcusers.repositories;

import org.santiago.springcloud.msvcusers.models.entities.Role;
import org.santiago.springcloud.msvcusers.models.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //Atributos de RoleRepository
    //Constructores de RoleRepository
    //Asignadores de atributos de RoleRepository (setters)
    //Lectores de atributos de RoleRepository (getters)
        //Métodos de RoleRepository
    Optional<Role> findByName(RoleName role);
}
