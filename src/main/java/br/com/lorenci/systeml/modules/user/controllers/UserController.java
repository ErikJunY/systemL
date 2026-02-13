package br.com.lorenci.systeml.modules.user.controllers;

import br.com.lorenci.systeml.modules.user.models.UserModel;
import br.com.lorenci.systeml.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel userModel) {
        UserModel newUser = service.saveUser(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = service.findAllUsers();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
    }

    @GetMapping("/{id}") // user/1
    public ResponseEntity<Optional<UserModel>> getById(@PathVariable Long id) {
        Optional<UserModel> users = service.findUserById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
    }

    @GetMapping("/search") // user/search?email=email@email.com POR CONTA DO @RequestParam
    public ResponseEntity<Optional<UserModel>> getByEmailOrCpf(@RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf) {

        Optional<UserModel> users = Optional.empty();

        if (email != null) {
            users = service.findUserByEmail(email);
        }
        if (cpf != null) {
            users = service.findUserByCpf(cpf);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserModel userModel) {

        UserModel updatedUser = service.updateUser(id, userModel);

        return ResponseEntity.ok(updatedUser);
    }

}
