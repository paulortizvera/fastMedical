package com.dioblazer.fast.medical.model;

import java.util.Date;

import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private static final long serialVersionUID = -3263711262516142627L;

	@Column(value = "login")
	private String login;
	@Column(value = "contrasena")
	private String password;
	@Column(value = "nombre")
	private String name;
	@Column(value = "sexo")
	private String gender;
	@Column(value = "pais")
	private String country;
	@Column(value = "ciudad")
	private String city;
	@Column(value = "direccion")
	private String address;
	@Column(value = "telefono")
	private String phone;
	@Column(value = "correo")
	private String email;
	@Column(value = "fecha_nacimiento")
	private Date birthDate;
	@Column(value = "fecha_registro")
	private Date registrationDate;
	@Column(value = "fecha_modificacion")
	private Date modificiationDate;
	@Column(value = "rol")
	private String rol;
	@Column(value = "estado")
	private String status;

}
