package com.dioblazer.fast.medical.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profesional implements Serializable {

	private static final long serialVersionUID = 5162988243865783249L;

	@Id
	@Column(value = "codigo")
	private int code;

	@Column(value = "tipo")
	private String type;

	@Column(value = "especialidad")
	private String specialty;

	@Column(value = "documentacion")
	private String documentation;

	@Column(value = "comentario")
	private String comentary;

	@Column(value = "fecha_modificacion")
	private Date modificationDate;

	@Column(value = "estado")
	private String status;

	@Column(value = "login")
	private String login;

}
