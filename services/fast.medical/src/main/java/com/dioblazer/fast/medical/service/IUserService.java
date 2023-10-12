package com.dioblazer.fast.medical.service;

import java.util.List;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;

public interface IUserService {
	public List<User> findAll();

	List<User> findActives();

	UserResponse userByLogin(String login);

	UserResponse userByEmail(String email);

	public int save(User user);

	public int updateByLogin(User user);

	public int deleteById(User user);
}
