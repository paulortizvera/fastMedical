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
public class Solicitude implements Serializable {

	private static final long serialVersionUID = -8262053509485539129L;

	@Id
	@Column(value = "numero")
	private int numero;

	@Column(value = "direccion")
	private String address;

	@Column(value = "latitude")
	private String latitude;

	@Column(value = "longitude")
	private String longitude;

	@Column(value = "tipo")
	private String type;

	@Column(value = "valor_propuesto")
	private double proposedValue;

	@Column(value = "valor_aceptado")
	private double aceptedValue;

	@Column(value = "descripcion")
	private String description;

	@Column(value = "fecha_registro")
	private Date registrationDate;

	@Column(value = "fecha_modificacion")
	private Date modificationDate;

	@Column(value = "comentario")
	private String comentary;

	@Column(value = "estado")
	private String status;

	@Column(value = "id")
	private int id;

	@Column(value = "codigo")
	private int code;

}
