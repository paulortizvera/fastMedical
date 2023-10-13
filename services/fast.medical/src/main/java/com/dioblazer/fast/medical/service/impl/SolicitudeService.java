package com.dioblazer.fast.medical.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.Solicitude;
import com.dioblazer.fast.medical.model.SolicitudeResponse;
import com.dioblazer.fast.medical.repository.ISolicitudeRepository;
import com.dioblazer.fast.medical.service.ISolicitudeService;
import com.dioblazer.fast.medical.utils.BusinessException;

@Service
public class SolicitudeService implements ISolicitudeService {

	@Autowired
	private ISolicitudeRepository iSolicitudeRepository;

	@Override
	public SolicitudeResponse findAll() {
		SolicitudeResponse solicitudeList;
		try {
			solicitudeList = iSolicitudeRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		return solicitudeList;
	}

	@Override
	public SolicitudeResponse findComplete() {
		SolicitudeResponse solicitudeList;
		try {
			solicitudeList = iSolicitudeRepository.findComplete();
		} catch (Exception e) {
			throw e;
		}
		return solicitudeList;
	}

	@Override
	public SolicitudeResponse solicitudeByNumber(Integer number) {
		SolicitudeResponse solicitude;
		try {
			solicitude = iSolicitudeRepository.solicitudeByNumber(number);
		} catch (Exception e) {
			throw e;
		}
		return solicitude;
	}

	@Override
	public SolicitudeResponse solicitudeByClient(Integer client) {
		SolicitudeResponse solicitude;
		try {
			solicitude = iSolicitudeRepository.solicitudeByClient(client);
		} catch (Exception e) {
			throw e;
		}
		return solicitude;
	}

	@Override
	public SolicitudeResponse solicitudeByClientLogin(String loginCli) {
		SolicitudeResponse solicitude;
		try {
			solicitude = iSolicitudeRepository.solicitudeByClientLogin(loginCli);
		} catch (Exception e) {
			throw e;
		}
		return solicitude;
	}

	@Override
	public SolicitudeResponse solicitudeByProfesional(Integer profesional) {
		SolicitudeResponse solicitude;
		try {
			solicitude = iSolicitudeRepository.solicitudeByProfesional(profesional);
		} catch (Exception e) {
			throw e;
		}
		return solicitude;
	}

	@Override
	public SolicitudeResponse solicitudeByProfesionalLogin(String loginPro) {
		SolicitudeResponse solicitude;
		try {
			solicitude = iSolicitudeRepository.solicitudeByProfesionalLogin(loginPro);
		} catch (Exception e) {
			throw e;
		}
		return solicitude;
	}
	
	@Override
	public int save(Solicitude solicitude) throws BusinessException {
		return iSolicitudeRepository.save(solicitude);
	}

	@Override
	public int updateByNumber(Solicitude solicitude) throws BusinessException {
		return iSolicitudeRepository.updateByNumber(solicitude);
	}

	@Override
	public int deleteByNumber(Integer number) throws BusinessException {
		return iSolicitudeRepository.deleteByNumber(number);
	}

	@Override
	public int aceptSolicitude(Solicitude solicitude) throws BusinessException {
		return iSolicitudeRepository.aceptSolicitude(solicitude);
	}

	@Override
	public int updateStatus(Solicitude solicitude) throws BusinessException {
		return iSolicitudeRepository.updateStatus(solicitude);
	}
}
