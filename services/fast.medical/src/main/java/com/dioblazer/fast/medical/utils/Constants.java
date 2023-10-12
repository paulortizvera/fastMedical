package com.dioblazer.fast.medical.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constants {
	/* ERROR */
	INTERNAL_ERROR("Error interno de la aplicación, consulte con el administrador."),
	/* Operaciones */
	OPERATION_S("S"),
	
	/* SPs */
	SP_LOGIN("sp_fm_login"),
	SP_CONS_USER("sp_fm_cons_usuario"),
	SP_ADM_USER("sp_fm_adm_usuario"),
	SP_CONS_PROFESIONAL("sp_fm_cons_profesional"),
	SP_ADM_PROFESIONAL("sp_fm_adm_profesional");
	
	private String name;
}
