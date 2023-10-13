package com.dioblazer.fast.medical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dioblazer.fast.medical.model.ServiceResponse;
import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;
import com.dioblazer.fast.medical.service.IUserService;
import com.dioblazer.fast.medical.utils.BusinessException;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private IUserService iUserService;

	@GetMapping
	public ResponseEntity<UserResponse> list() {
		UserResponse result = iUserService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/listActives")
	public ResponseEntity<UserResponse> listActives() {
		UserResponse result = iUserService.findActives();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/{login}")
	public ResponseEntity<UserResponse> userByLogin(@PathVariable("login") String login) {
		UserResponse result = iUserService.userByLogin(login);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<ServiceResponse> userByEmail(@PathVariable("email") String email) {
		UserResponse result = iUserService.userByEmail(email);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ServiceResponse> save(@RequestBody User user) {
		ServiceResponse resp = new ServiceResponse();
		encryptPass(user);
		resp.setSuccess(true);
		try {
			iUserService.save(user);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResponse> updateByLogin(@RequestBody User user) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			encryptPass(user);
			iUserService.updateByLogin(user);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{login}")
	public ResponseEntity<ServiceResponse> deleteByLogin(@PathVariable("login") String login) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iUserService.deleteByLogin(login);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/rol/{loginAdm}")
	public ResponseEntity<ServiceResponse> updateRol(@PathVariable("loginAdm") String loginAdm, @RequestBody User user) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iUserService.updateRol(loginAdm, user);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/status/{loginAdm}")
	public ResponseEntity<ServiceResponse> updateStatus(@PathVariable("loginAdm") String loginAdm, @RequestBody User user) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iUserService.updateStatus(loginAdm, user);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	/* Encriptar contraseña */
	private void encryptPass(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	}
}
