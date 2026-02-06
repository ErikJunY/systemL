package br.com.lorenci.systeml.modules.user.controllers;

import br.com.lorenci.systeml.modules.user.models.UserModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private List<UserModel> users = new ArrayList<>();
    private Long nextId = 1L;

    @PostMapping("/create")
    public UserModel createUser(@Valid @RequestBody UserModel user) {
        user.setId(nextId);
        nextId++;

        users.add(user);
        return user;
    }

    @GetMapping("/findAll")
    public List<UserModel> findAllUsers() {
        return users;
    }

    @GetMapping("/find/{id}")
    public UserModel findUserById(@PathVariable Long id) {
        for (UserModel user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @PutMapping("/update/{id}")
    public UserModel updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserModel updatedUser
    ) {
        for (UserModel user : users) {
            if (user.getId().equals(id)) {
                user.setNome(updatedUser.getNome());
                user.setEmail(updatedUser.getEmail());
                user.setSenha(updatedUser.getSenha());
                user.setSexo(updatedUser.getSexo());
                user.setCpf(updatedUser.getCpf());
                user.setCep(updatedUser.getCep());
                user.setRua(updatedUser.getRua());
                user.setCidade(updatedUser.getCidade());
                user.setUf(updatedUser.getUf());
                user.setNumeroResidencial(updatedUser.getNumeroResidencial());
                user.setNumeroTel(updatedUser.getNumeroTel());
                user.setAdministrador(updatedUser.getAdministrador());

                return user;
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        for (UserModel user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return "Usuário removido com sucesso";
            }
        }
        return "Usuário não encontrado";
    }
}
