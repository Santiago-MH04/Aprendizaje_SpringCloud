package org.santiago.springcloud.msvcoauth2.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Usager {
        //Atributos de Usager
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private boolean admin;
    private List<Role> roles;

    //Constructores de Usager
    //Asignadores de atributos de Usager (setters)
    //Lectores de atributos de Usager (getters)
    //Métodos de Usager
}
