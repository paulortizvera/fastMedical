package com.dioblazer.fast.medical.repository.param;

import java.sql.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralParam {
	S_LOGIN("@s_login", Types.DATE),
	S_DATE("@s_date", Types.DATE),
	OPERATION("@i_operacion", Types.VARCHAR),
	MODE("@i_modo", Types.INTEGER),
	LOGIN("@i_login", Types.VARCHAR),
	EMAIL("@i_correo", Types.VARCHAR), 
	ROL("@i_rol", Types.VARCHAR),
	STATUS("@i_estado", Types.VARCHAR);

	private String name;
	private int type;
}
