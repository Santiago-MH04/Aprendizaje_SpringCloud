package org.santiago.springcloud.msvcoauth2.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.santiago.springcloud.msvcoauth2.models.utils.RoleName;

@AllArgsConstructor
@NoArgsConstructor
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
