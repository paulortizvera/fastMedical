package com.dioblazer.fast.medical.repository;

import com.dioblazer.fast.medical.model.Profesional;
import com.dioblazer.fast.medical.model.ProfesionalResponse;
import com.dioblazer.fast.medical.utils.BusinessException;

public interface IProfesionalRepository {

	ProfesionalResponse findAll();

	ProfesionalResponse findActives();

	ProfesionalResponse profesionalByLogin(String login);

	ProfesionalResponse profesionalByEmail(String email);

	int save(Profesional profesional) throws BusinessException;

	int updateByLogin(Profesional profesional) throws BusinessException;

	int deleteByLogin(String login) throws BusinessException;

	int updateDocumentation(String loginAdm, Profesional profesional) throws BusinessException;

	int updateStatus(String loginAdm, Profesional profesional) throws BusinessException;
}
