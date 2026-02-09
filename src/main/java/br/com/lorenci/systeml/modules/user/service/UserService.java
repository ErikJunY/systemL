package br.com.lorenci.systeml.modules.user.service;

import br.com.lorenci.systeml.modules.user.exceptions.ResourceNotFoundException;
import br.com.lorenci.systeml.modules.user.models.UserModel;
import br.com.lorenci.systeml.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel saveUser(UserModel userModel) {
        if (userRepository.existsByCpf(userModel.getCpf())) {
            throw new DataIntegrityViolationException("CPF ja cadastrado");
        } else if (userRepository.existsByEmail(userModel.getEmail())) {
            throw new DataIntegrityViolationException("Email ja cadastrado");
        }

        return userRepository.save(userModel); // RETORNA NOSSO OBJETO ATUALIZADO INCLUSIVE COM O ID
    }

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario nao encontrado");
        }

        return userRepository.findById(id);
    }
}
