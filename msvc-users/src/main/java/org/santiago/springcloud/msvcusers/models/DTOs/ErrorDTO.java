package org.santiago.springcloud.msvcusers.models.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorDTO {
        //Atributos de ErrorDTO
    private String error;
    private String message;
    private int status;
    private LocalDateTime date; //Este es el que genera el timestamp

    //Constructores de ErrorDTO
    //Asignadores de atributos de ErrorDTO (setters)
    //Lectores de atributos de ErrorDTO (getters)
    //Métodos de ErrorDTO
    
}
