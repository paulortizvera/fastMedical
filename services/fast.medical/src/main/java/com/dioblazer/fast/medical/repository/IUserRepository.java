package com.dioblazer.fast.medical.repository;

import java.util.List;
import java.util.Optional;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;

public interface IUserRepository {
	Optional<User> findOneByLogin(String login);

	public List<User> findAll();

	List<User> findActives();

	UserResponse userByLogin(String login);

	UserResponse userByEmail(String email);

	public int save(User user);

	public int update(User user);

	public int deleteById(User user);
}
