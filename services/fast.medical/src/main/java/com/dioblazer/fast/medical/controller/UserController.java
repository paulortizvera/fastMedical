package com.dioblazer.fast.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;
import com.dioblazer.fast.medical.service.IUserService;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private IUserService iUserService;

	@GetMapping
	public ResponseEntity<List<User>> list() {
		var result = iUserService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/listActives")
	public ResponseEntity<List<User>> listActives() {
		var result = iUserService.findActives();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{login}")
	public ResponseEntity<UserResponse> userByLogin(@PathVariable("login") String login) {
		var result = iUserService.userByLogin(login);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<UserResponse> userByEmail(@PathVariable("email") String email) {
		var result = iUserService.userByEmail(email);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	public void save(@RequestBody User user) {
		iUserService.save(user);
	}

	@PutMapping(value = "/{login}")
	public void updateByLogin(@RequestBody User user) {
		iUserService.updateByLogin(user);
	}
	
}
