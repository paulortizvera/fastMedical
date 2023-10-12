package com.dioblazer.fast.medical.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constants {
	/* Operaciones */
	OPERATION_S("S"),
	
	/* SPs */
	SP_LOGIN("sp_fm_login"),
	SP_CONS_USER("sp_fm_cons_usuario"),
	SP_ADM_USER("sp_fm_adm_usuario");
	
	private String name;
}
