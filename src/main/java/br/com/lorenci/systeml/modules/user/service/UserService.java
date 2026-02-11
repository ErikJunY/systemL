package br.com.lorenci.systeml.modules.user.service;

import br.com.lorenci.systeml.modules.user.exceptions.ResourceNotFoundException;
import br.com.lorenci.systeml.modules.user.models.UserModel;
import br.com.lorenci.systeml.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<UserModel> findUserById(Long id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario nao encontrado");
        }

        return user;
    }

    public Optional<UserModel> findUserByEmail(String email) {
        Optional<UserModel> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario nao encontrado");
        }

        return user;
    }

    public Optional<UserModel> findUserByCpf(String cpf) {
        Optional<UserModel> user = userRepository.findByCpf(cpf);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario nao encontrado");
        }

        return user;
    }

    public Optional<UserModel> updateUser(Long id, UserModel userModel) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario nao encontrado");
        }

        return user;
    }
}
