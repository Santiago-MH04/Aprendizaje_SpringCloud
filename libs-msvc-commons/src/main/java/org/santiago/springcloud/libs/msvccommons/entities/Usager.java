package org.santiago.springcloud.libs.msvccommons.entities;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
