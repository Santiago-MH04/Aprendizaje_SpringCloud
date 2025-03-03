package org.santiago.springcloud.msvcusers.controllers;

import jakarta.ws.rs.NotFoundException;
import org.santiago.springcloud.msvcusers.models.DTOs.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class HandlerExceptionController {
    //Atributos de HandlerExceptionController
    //Constructores de HandlerExceptionController
    //Asignadores de atributos de HandlerExceptionController (setters)
    //Lectores de atributos de HandlerExceptionController (getters)
        //Métodos de HandlerExceptionController
    @ExceptionHandler({NoSuchElementException.class, NotFoundException.class})
    public ResponseEntity<ErrorDTO> handleNotFound(Exception ex){
        ErrorDTO error = ErrorDTO.builder()
                .error("Error: usuario no encontrado")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> body = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(fe -> body.put(fe.getField() ,fe.getDefaultMessage()));

        /*return ResponseEntity.badRequest().body(body);*/
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        /*return body;*/
    }
}

