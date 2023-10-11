package com.dioblazer.fast.medical.repository.param;

import java.sql.Types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralParameters {
	OPERATION("@i_operacion", Types.VARCHAR);

	private String name;
	private int types;
}
