package com.dioblazer.fast.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.repository.IUserRepository;
import com.dioblazer.fast.medical.service.IUserService;

@Service
public class UserService implements IUserService{
	
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
	public int save(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
