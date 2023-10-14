package com.dioblazer.fast.medical.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constants {
	/* ERROR */
	INTERNAL_ERROR("Error interno de la aplicación, consulte con el administrador."),
	NO_REGISTERS("No se encontraron registros."),
	/* Operaciones */
	SEARCH("S"),
	QUERY("Q"),
	INSERT("I"),
	UPDATE("U"),
	DELETE("D"),
	/* Parametros */
	RESULT("result"),
	/* SPs */
	SP_LOGIN("sp_fm_login"),
	SP_CONS_USER("sp_fm_cons_usuario"),
	SP_ADM_USER("sp_fm_adm_usuario"),
	SP_CONS_PROFESIONAL("sp_fm_cons_profesional"),
	SP_ADM_PROFESIONAL("sp_fm_adm_profesional"),
	SP_CONS_CLIENT("sp_fm_cons_cliente"),
	SP_ADM_CLIENT("sp_fm_adm_cliente"),
	SP_CONS_SOLICITUDE("sp_fm_cons_solicitud"),
	SP_ADM_SOLICITUDE("sp_fm_adm_solicitud");
	
	private String name;
}
