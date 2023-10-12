package com.dioblazer.fast.medical.service;

import com.dioblazer.fast.medical.model.Profesional;
import com.dioblazer.fast.medical.model.ProfesionalResponse;
import com.dioblazer.fast.medical.utils.BusinessException;

public interface IProfesionalService {
	ProfesionalResponse findAll();

	ProfesionalResponse findActives();

	ProfesionalResponse ProfesionalByLogin(String login);

	ProfesionalResponse ProfesionalByEmail(String email);

	int save(Profesional profesional) throws BusinessException;

	int updateByLogin(Profesional profesional) throws BusinessException;

	int deleteByLogin(String login) throws BusinessException;

	int updateDocumentation(String loginAdm, Profesional profesional) throws BusinessException;

	int updateStatus(String loginAdm, Profesional profesional) throws BusinessException;
}
