package com.dioblazer.fast.medical.repository;

import com.dioblazer.fast.medical.model.Solicitude;
import com.dioblazer.fast.medical.model.SolicitudeResponse;
import com.dioblazer.fast.medical.utils.BusinessException;

public interface ISolicitudeRepository {
	SolicitudeResponse findAll();

	SolicitudeResponse findComplete();

	SolicitudeResponse solicitudeByNumber(Integer number);

	SolicitudeResponse solicitudeByClient(Integer client);

	SolicitudeResponse solicitudeByClientLogin(String loginCli);

	SolicitudeResponse solicitudeByProfesional(Integer profesional);

	SolicitudeResponse solicitudeByProfesionalLogin(String loginPro);

	int save(Solicitude solicitude) throws BusinessException;

	int updateByNumber(Solicitude solicitude) throws BusinessException;

	int deleteByNumber(Integer number) throws BusinessException;

	int aceptSolicitude(Solicitude solicitude) throws BusinessException;

	int updateStatus(Solicitude solicitude) throws BusinessException;
}
