package org.santiago.springcloud.msvcusers.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Usager {
        //Atributos de Usager
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @NotNull(message = "La contraseña no puede ser un campo nulo")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico debe ser válido")
    @Column(unique = true)
    private String email;

    private boolean enabled;

    @Transient
    @JsonIgnore
    private boolean admin;

    @JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})  //Para evitar la relación circular. Si hago la relación bidireccional, debo añadir la tabla de los usuarios al arreglo, es decir, "users"
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
    )
    private List<Role> roles;

        //Constructores de Usager
    @PrePersist
    public void prePersist(){
        this.enabled = true;
        this.admin = !this.admin ? this.admin : true;
    }

    //Asignadores de atributos de Usager (setters)
    //Lectores de atributos de Usager (getters)
    //Métodos de Usager
       
}
