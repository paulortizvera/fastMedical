package com.dioblazer.fast.medical.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.repository.IUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = iUserRepository
				.findOneByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario con login " + login + "no existe."));
		return new UserDetailsImpl(user);
	}

}
