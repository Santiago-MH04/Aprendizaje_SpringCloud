package org.santiago.springcloud.msvcusers.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.santiago.springcloud.msvcusers.models.utils.RoleName;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role {
        //Atributos de Role
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RoleName name;

    //Constructores de Role
    //Asignadores de atributos de Role (setters)
    //Lectores de atributos de Role (getters)
    //Métodos de Role
    
}
