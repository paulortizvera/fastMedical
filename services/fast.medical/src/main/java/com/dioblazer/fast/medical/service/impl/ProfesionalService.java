package com.dioblazer.fast.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.Profesional;
import com.dioblazer.fast.medical.model.ProfesionalResponse;
import com.dioblazer.fast.medical.repository.IProfesionalRepository;
import com.dioblazer.fast.medical.service.IProfesionalService;
import com.dioblazer.fast.medical.utils.BusinessException;

@Service
public class ProfesionalService implements IProfesionalService {

	@Autowired
	private IProfesionalRepository iProfesionalRepository;

	@Override
	public ProfesionalResponse findAll() {
		ProfesionalResponse profesionalList;
		try {
			profesionalList = iProfesionalRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		return profesionalList;
	}

	@Override
	public ProfesionalResponse findActives() {
		ProfesionalResponse profesionalList;
		try {
			profesionalList = iProfesionalRepository.findActives();
		} catch (Exception e) {
			throw e;
		}
		return profesionalList;
	}

	@Override
	public ProfesionalResponse ProfesionalByLogin(String login) {
		ProfesionalResponse profesional;
		try {
			profesional = iProfesionalRepository.ProfesionalByLogin(login);
		} catch (Exception e) {
			throw e;
		}
		return profesional;
	}

	@Override
	public ProfesionalResponse ProfesionalByEmail(String email) {
		ProfesionalResponse profesional;
		try {
			profesional = iProfesionalRepository.ProfesionalByEmail(email);
		} catch (Exception e) {
			throw e;
		}
		return profesional;
	}
	
	@Override
	public int save(Profesional profesional) throws BusinessException {
		return iProfesionalRepository.save(profesional);
	}

	@Override
	public int updateByLogin(Profesional profesional) throws BusinessException {
		return iProfesionalRepository.updateByLogin(profesional);
	}

	@Override
	public int deleteByLogin(String login) throws BusinessException {
		return iProfesionalRepository.deleteByLogin(login);
	}

	@Override
	public int updateDocumentation(String loginAdm, Profesional profesional) throws BusinessException {
		return iProfesionalRepository.updateDocumentation(loginAdm, profesional);
	}

	@Override
	public int updateStatus(String loginAdm, Profesional profesional) throws BusinessException {
		return iProfesionalRepository.updateStatus(loginAdm, profesional);
	}
}
