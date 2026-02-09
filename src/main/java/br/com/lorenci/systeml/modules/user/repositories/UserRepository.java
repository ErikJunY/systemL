package br.com.lorenci.systeml.modules.user.repositories;

import br.com.lorenci.systeml.modules.user.models.UserModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserModel, Long> {
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);

    Optional<UserModel> findByCpf(String cpf);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findById(Long id);
}
