package com.dioblazer.fast.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;
import com.dioblazer.fast.medical.repository.IUserRepository;
import com.dioblazer.fast.medical.service.IUserService;
import com.dioblazer.fast.medical.utils.BusinessException;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public UserResponse findAll() {
		UserResponse userList;
		try {
			userList = iUserRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		return userList;
	}

	@Override
	public UserResponse findActives() {
		UserResponse userList;
		try {
			userList = iUserRepository.findActives();
		} catch (Exception e) {
			throw e;
		}
		return userList;
	}

	@Override
	public UserResponse userByLogin(String login) {
		UserResponse user;
		try {
			user = iUserRepository.userByLogin(login);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	@Override
	public UserResponse userByEmail(String email) {
		UserResponse user;
		try {
			user = iUserRepository.userByEmail(email);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}
	
	@Override
	public int save(User user) throws BusinessException {
		return iUserRepository.save(user);
	}

	@Override
	public int updateByLogin(User user) throws BusinessException {
		return iUserRepository.updateByLogin(user);
	}

	@Override
	public int deleteByLogin(String login) throws BusinessException {
		return iUserRepository.deleteByLogin(login);
	}

	@Override
	public int updateRol(String loginAdm, User user) throws BusinessException {
		return iUserRepository.updateRol(loginAdm, user);
	}

	@Override
	public int updateStatus(String loginAdm, User user) throws BusinessException {
		return iUserRepository.updateStatus(loginAdm, user);
	}
}
