package com.dioblazer.fast.medical.repository;

import java.util.Optional;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;
import com.dioblazer.fast.medical.utils.BusinessException;

public interface IUserRepository {
	Optional<User> findOneByLogin(String login);

	UserResponse findAll();

	UserResponse findActives();

	UserResponse userByLogin(String login);

	UserResponse userByEmail(String email);

	int save(User user) throws BusinessException;

	int updateByLogin(User user) throws BusinessException;

	int deleteByLogin(String login) throws BusinessException;

	int updateRol(String loginAdm, User user) throws BusinessException;

	int updateStatus(String loginAdm, User user) throws BusinessException;
}
