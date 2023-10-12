package com.dioblazer.fast.medical.repository.param;

import java.sql.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfesionalParam {
	SPECIALITY("@i_especialidad", Types.VARCHAR);

	private String name;
	private int type;
}
