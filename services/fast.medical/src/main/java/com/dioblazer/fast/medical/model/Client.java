package com.dioblazer.fast.medical.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

	private static final long serialVersionUID = -2329509194194280450L;

	@Id
	@Column(value = "id")
	private int id;

	@Column(value = "enfermedad")
	private String disease;

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
