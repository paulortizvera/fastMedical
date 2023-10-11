package com.dioblazer.fast.medical.service;

import java.util.List;

import com.dioblazer.fast.medical.model.User;

public interface IUserService {
	public List<User> findAll();
	public int save(User user);
	public int update(User user);
	public int deleteById(User user);
}
