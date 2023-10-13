package com.dioblazer.fast.medical.repository.param;

import java.sql.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SolicitudeParam {
	NUMBER("@i_numero", Types.INTEGER),
	CLIENT("@i_cliente", Types.INTEGER),
	PROFESIONAL("@i_profesional", Types.INTEGER),
	LOGIN_CLI("@i_login_cli", Types.VARCHAR),
	LOGIN_PRO("@i_login_pro", Types.VARCHAR),
	LATITUDE("@i_latitude", Types.VARCHAR),
	LONGITUDE("@i_longitude", Types.VARCHAR),
	PROPUSED_VALUE("@i_valor_propuesto", Types.DOUBLE),
	ACEPTED_VALUE("@i_valor_aceptado", Types.DOUBLE),
	ADDRESS("@i_direccion", Types.VARCHAR),
	DESCRIPTION("@i_descripcion", Types.VARCHAR);

	private String name;
	private int type;
}
