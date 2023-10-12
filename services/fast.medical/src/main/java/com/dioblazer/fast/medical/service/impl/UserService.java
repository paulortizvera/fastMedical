package com.dioblazer.fast.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;
import com.dioblazer.fast.medical.repository.IUserRepository;
import com.dioblazer.fast.medical.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public List<User> findAll() {
		List<User> userList;
		try {
			userList = iUserRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		return userList;
	}

	@Override
	public List<User> findActives() {
		List<User> userList;
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
	public int save(User user) {
		return iUserRepository.save(user);
	}

	@Override
	public int updateByLogin(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
