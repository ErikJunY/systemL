package br.com.lorenci.systeml.modules.user.controllers;

import br.com.lorenci.systeml.modules.user.models.UserModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("")
    public void createUser( @Valid @RequestBody UserModel user) {
        System.out.println(user.getNumeroResidencial());
    }
}

