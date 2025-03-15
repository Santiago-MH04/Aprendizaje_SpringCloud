package org.santiago.springcloud.libs.msvccommons.entities;

import lombok.*;
import org.santiago.springcloud.libs.msvccommons.utils.RoleName;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@Builder
public class Role {
        //Atributos de Role
    private Long id;
    private RoleName name;

    //Constructores de Role
    //Asignadores de atributos de Role (setters)
    //Lectores de atributos de Role (getters)
    //Métodos de Role
    
}
