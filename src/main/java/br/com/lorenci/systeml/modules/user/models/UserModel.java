package br.com.lorenci.systeml.modules.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_users")
public class UserModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Usado em java para persistencia do ID e fazer um autoincremento na base de dados
	private Long id;
	
	@Column(name = "administrador")
	private Boolean administrador;
	
	@Column(name = "numero_tel")
	private String numeroTel;
	
	@Column(name = "numero_residencial")
	private Integer numeroResidencial;

    @Email(message = "O campo deve conter um email válido")
	private String email;

    private String nome;
	private String senha;
	private String sexo;
	private String cpf;
	private String cep;
	private String rua;
	private String cidade;
	private String uf;

}
