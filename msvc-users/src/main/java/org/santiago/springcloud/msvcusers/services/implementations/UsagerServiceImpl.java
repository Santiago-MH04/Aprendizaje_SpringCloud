package org.santiago.springcloud.msvcusers.services.implementations;


import org.santiago.springcloud.msvcusers.models.entities.Role;
import org.santiago.springcloud.msvcusers.models.entities.Usager;
import org.santiago.springcloud.msvcusers.models.utils.RoleName;
import org.santiago.springcloud.msvcusers.repositories.RoleRepository;
import org.santiago.springcloud.msvcusers.repositories.UsagerRepository;
import org.santiago.springcloud.msvcusers.services.abstractions.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsagerServiceImpl implements UsagerService {
        //Atributos de UsagerServiceImpl
    private final UsagerRepository repoUsager;
    private final RoleRepository repoRole;

    @Autowired
    private PasswordEncoder passwordEncoder;

        //Constructores de UsagerServiceImpl
    public UsagerServiceImpl(UsagerRepository repoUsager, RoleRepository repoRole) {
        this.repoUsager = repoUsager;
        this.repoRole = repoRole;
    }

    //Asignadores de atributos de UsagerServiceImpl (setters)
    //Lectores de atributos de UsagerServiceImpl (getters)
        //Métodos de UsagerServiceImpl
    @Override
    @Transactional(readOnly = true)
    public List<Usager> findAll() {
        return this.repoUsager.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usager> findById(Long id) {
        return this.repoUsager.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usager> findByUsername(String username) {
        return this.repoUsager.findByUsername(username)
                   .filter(Usager::isEnabled);
    }

    @Override
    @Transactional
    public Usager save(Usager usager) {
        usager.setPassword(passwordEncoder.encode(usager.getPassword()));
        this.usagerRoleAssigner(usager);

        return this.repoUsager.save(usager);
    }


    @Override
    public Optional<Usager> update(Long id, Usager usager) {
        return this.repoUsager.findById(id)
                   .map(u -> {
                        if(usager.getUsername() != null || !usager.getUsername().isBlank()){
                            u.setUsername(usager.getUsername());
                        }
                        if(usager.getEmail() != null || !usager.getEmail().isBlank()){
                            u.setEmail(usager.getEmail());
                        }
                        if(usager.isAdmin()){
                            u.setAdmin(true);
                        }
                        if(usager.getRoles() == null || usager.getRoles().isEmpty()){
                            this.usagerRoleAssigner(u);
                        }
                       return this.repoUsager.save(u);
                   });
    }

    @Override
    @Transactional
    public void disableUser(Long id) {
        this.repoUsager.findById(id)
                .map(u -> {
                    u.setEnabled(false);
                    return this.repoUsager.save(u);
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repoUsager.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Usager usager) {
        this.repoUsager.delete(usager);
    }

    @Transactional(readOnly = true)
    private void usagerRoleAssigner(Usager usager) {
        List<Role> roles = new ArrayList<>();

        this.repoRole.findByName(RoleName.ROLE_USER)
            .ifPresent(roles::add);

        if(usager.isAdmin()){
            this.repoRole.findByName(RoleName.ROLE_ADMIN)
                .ifPresent(roles::add);
        }

        usager.setRoles(roles);
    }
}
