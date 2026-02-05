package br.com.lorenci.systeml.modules.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_users")
public class UserModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID) //Usado em java para persistencia do ID e fazer um autoincremento na base de dados
	private UUID id;
	
	@Column(name = "administrador")
	private Boolean administrador;
	
	@Column(name = "numero_tel")
	private String numeroTel;
	
	@Column(name = "numero_residencial")
	private Integer numeroResidencial;

    @Email(message = "O campo deve conter um email válido")
    @Column(unique = true)
	private String email;

    @NotBlank
    private String nome;

    @NotBlank
	private String senha;

	private String sexo;

    @NotBlank
    @Column(unique = true)
	private String cpf;

	private String cep;
	private String rua;
	private String cidade;
	private String uf;

}
