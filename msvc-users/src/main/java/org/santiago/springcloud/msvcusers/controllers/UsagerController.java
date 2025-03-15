package org.santiago.springcloud.msvcusers.controllers;

import jakarta.ws.rs.NotFoundException;
import org.santiago.springcloud.msvcusers.models.entities.Usager;

import org.santiago.springcloud.msvcusers.services.abstractions.UsagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/usagers")
public class UsagerController {
        //Atributos de UsagerController
    private final UsagerService usagerService;

        //Constructores de UsagerController
    public UsagerController(UsagerService usagerService) {
        this.usagerService = usagerService;
    }

    //Asignadores de atributos de UsagerController (setters)
    //Lectores de atributos de UsagerController (getters)
        //Métodos de UsagerController
    @GetMapping
    public ResponseEntity<List<Usager>> findAllUsagers(){
        return ResponseEntity.ok(this.usagerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usager> findUsagerById(@PathVariable Long id) throws NoSuchElementException{
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(this.usagerService.findById(id)
                        .orElseThrow(
                            () -> new NoSuchElementException(String.format("El usuario con id %d no existe en nuestro sistema", id))
                        )
                );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usager> findUsagerByUsername(@PathVariable String username) throws NotFoundException{
            //Se cambia para manejar el try-catch del método de autenticación
        return this.usagerService.findByUsername(username)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usager> createUsager(@RequestBody Usager usager) throws MethodArgumentNotValidException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.usagerService.save(usager));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Usager> updateUsagerParams(@PathVariable Long id, @RequestBody Usager usager) throws NoSuchElementException {    //Me aseguro luego desde la implementación de Security que el usuario sí exista
        return this.usagerService.update(id, usager)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException(String.format("El usuario con id %d no existe en nuestro sistema", id)));
    }

    @PutMapping("/disable/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity disableUsager(@PathVariable Long id){ //Me aseguro luego desde la implementación de Security que el usuario sí exista
        this.usagerService.findById(id)
            .ifPresent(u -> this.usagerService.disableUser(u.getId()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws NotFoundException{
        this.usagerService.findById(id)
            .ifPresentOrElse(
                this.usagerService::delete,
                () -> { throw new NotFoundException(String.format("El usuario con id %d no existe en nuestro sistema", id));}
            );
        return ResponseEntity.noContent().build();
    }
}
