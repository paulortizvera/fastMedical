package com.dioblazer.fast.medical.repository;

import java.util.List;

import com.dioblazer.fast.medical.model.User;

public interface IUserRepository{
	public List<User> findAll();
	public int save(User user);
	public int update(User user);
	public int deleteById(User user);
}
