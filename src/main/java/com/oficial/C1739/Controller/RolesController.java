package com.oficial.C1739.Controller;


import com.oficial.C1739.Entity.Permiso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rol")

public class RolesController {


    @PostMapping
    public ResponseEntity<?> crearAutorizacion(@RequestBody Permiso permission){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
