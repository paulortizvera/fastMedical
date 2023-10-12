package com.dioblazer.fast.medical.repository.param;

import java.sql.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserParam { 
	PASSWORD("@i_contrasena", Types.VARCHAR), 
	OTP("@i_otp", Types.INTEGER), 
	OTP_TYPE("@i_tipo_otp", Types.VARCHAR), 
	NAME("@i_nombre", Types.VARCHAR),
	GENDER("@i_sexo", Types.VARCHAR),
	COUNTRY("@i_pais", Types.VARCHAR),
	CITY("@i_ciudad", Types.VARCHAR),
	ADDRESS("@i_direccion", Types.VARCHAR),
	PHONE("@i_telefono", Types.VARCHAR),
	BIRTHDATE("@i_fecha_nacimiento", Types.DATE),;

	private String name;
	private int type;
}
