package com.dioblazer.fast.medical.repository.param;

import java.sql.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientParam {
	DISEASE("@i_enfermedad", Types.VARCHAR);

	private String name;
	private int type;
}
