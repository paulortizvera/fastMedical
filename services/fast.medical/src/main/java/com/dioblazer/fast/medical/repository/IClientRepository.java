package com.dioblazer.fast.medical.repository;

import com.dioblazer.fast.medical.model.Client;
import com.dioblazer.fast.medical.model.ClientResponse;
import com.dioblazer.fast.medical.utils.BusinessException;

public interface IClientRepository {

	ClientResponse findAll();

	ClientResponse findActives();

	ClientResponse clientByLogin(String login);

	ClientResponse clientByEmail(String email);

	int save(Client client) throws BusinessException;

	int updateByLogin(Client client) throws BusinessException;

	int deleteByLogin(String login) throws BusinessException;

	int updateDocumentation(String loginAdm, Client client) throws BusinessException;

	int updateStatus(String loginAdm, Client client) throws BusinessException;
}
