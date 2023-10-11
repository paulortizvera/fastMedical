package com.dioblazer.fast.medical.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP implements Serializable {

	private static final long serialVersionUID = -8068231625430576889L;

	@Column(value = "id")
	private int id;

	@Column(value = "correo")
	private String email;

	@Column(value = "otp")
	private int otp;

	@Column(value = "fecha_registro")
	private Date registrationDate;

	@Column(value = "tipo")
	private String type;

	@Column(value = "estado")
	private String status;

	@Column(value = "login")
	private String login;

}
