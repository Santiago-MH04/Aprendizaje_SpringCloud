package org.santiago.springcloud.gatewayserver.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {
    //Atributos de AppController
    //Constructores de AppController
    //Asignadores de atributos de AppController (setters)
    //Lectores de atributos de AppController (getters)
        //Métodos de AppController
    @GetMapping("/authorized")
    public Map<String, String> authorisedMap(@RequestParam String code){
        Map<String, String> authorisationMap = new HashMap<>();
            authorisationMap.put("code", code);

        return authorisationMap;
    }

    @PostMapping("/logout")
    public Map<String, String> logoutMap(){
        return Collections.singletonMap("logout", "Has cerrado sesión con éxito");
    }
}
