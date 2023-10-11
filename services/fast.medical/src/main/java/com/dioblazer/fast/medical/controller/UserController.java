package com.dioblazer.fast.medical.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.service.IUserService;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private IUserService iUserService;

	@GetMapping("/list")
	public ResponseEntity<List<User>> list() {
		var result = iUserService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/*
	 * @Autowired private IUserRepository UserRepo;
	 * 
	 * @GetMapping public ResponseEntity<List<User>> getUser() { try { List<User>
	 * usr = UserRepo.findAll(); if (usr == null) { return new
	 * ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST); } else { return new
	 * ResponseEntity<List<User>>(usr, HttpStatus.OK); } } catch (Exception e) {
	 * return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST); } }
	 * 
	 * @GetMapping(value = "/{id}") public ResponseEntity<Optional<User>>
	 * getUserById(@PathVariable("id") String id) { try { Optional<User> acc =
	 * null;//UserRepo.findById(id); if (acc == null) { return new
	 * ResponseEntity<Optional<User>>(HttpStatus.BAD_REQUEST); } else { return new
	 * ResponseEntity<Optional<User>>(acc, HttpStatus.OK); } } catch (Exception e) {
	 * return new ResponseEntity<Optional<User>>(HttpStatus.BAD_REQUEST); } }
	 * 
	 * @PostMapping public void insertUser(@RequestBody User acc) {
	 * UserRepo.save(acc); }
	 * 
	 * @PutMapping public void updateUser(@RequestBody User acc) {
	 * UserRepo.save(acc); }
	 * 
	 * @DeleteMapping(value = "/{id}") public void deleteUser(@PathVariable("id")
	 * String id) { //UserRepo.deleteById(id); }
	 */
}
