package com.dioblazer.fast.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.Client;
import com.dioblazer.fast.medical.model.ClientResponse;
import com.dioblazer.fast.medical.repository.IClientRepository;
import com.dioblazer.fast.medical.service.IClientService;
import com.dioblazer.fast.medical.utils.BusinessException;

@Service
public class ClientService implements IClientService {

	@Autowired
	private IClientRepository iClientRepository;

	@Override
	public ClientResponse findAll() {
		ClientResponse clientList;
		try {
			clientList = iClientRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		return clientList;
	}

	@Override
	public ClientResponse findActives() {
		ClientResponse clientList;
		try {
			clientList = iClientRepository.findActives();
		} catch (Exception e) {
			throw e;
		}
		return clientList;
	}

	@Override
	public ClientResponse clientByLogin(String login) {
		ClientResponse client;
		try {
			client = iClientRepository.clientByLogin(login);
		} catch (Exception e) {
			throw e;
		}
		return client;
	}

	@Override
	public ClientResponse clientByEmail(String email) {
		ClientResponse client;
		try {
			client = iClientRepository.clientByEmail(email);
		} catch (Exception e) {
			throw e;
		}
		return client;
	}
	
	@Override
	public int save(Client client) throws BusinessException {
		return iClientRepository.save(client);
	}

	@Override
	public int updateByLogin(Client client) throws BusinessException {
		return iClientRepository.updateByLogin(client);
	}

	@Override
	public int deleteByLogin(String login) throws BusinessException {
		return iClientRepository.deleteByLogin(login);
	}

	@Override
	public int updateDocumentation(String loginAdm, Client client) throws BusinessException {
		return iClientRepository.updateDocumentation(loginAdm, client);
	}

	@Override
	public int updateStatus(String loginAdm, Client client) throws BusinessException {
		return iClientRepository.updateStatus(loginAdm, client);
	}
}
