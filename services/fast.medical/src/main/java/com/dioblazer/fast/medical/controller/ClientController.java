package com.dioblazer.fast.medical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.dioblazer.fast.medical.model.Client;
import com.dioblazer.fast.medical.model.ClientResponse;
import com.dioblazer.fast.medical.service.IClientService;
import com.dioblazer.fast.medical.utils.BusinessException;

@RestController
@RequestMapping("api/v1/client")
@CrossOrigin("*")
public class ClientController {
	@Autowired
	private IClientService iClientService;

	@GetMapping
	public ResponseEntity<ClientResponse> list() {
		ClientResponse result = iClientService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/listActives")
	public ResponseEntity<ClientResponse> listActives() {
		ClientResponse result = iClientService.findActives();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/{login}")
	public ResponseEntity<ClientResponse> ClientByLogin(@PathVariable("login") String login) {
		ClientResponse result = iClientService.clientByLogin(login);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<ServiceResponse> ClientByEmail(@PathVariable("email") String email) {
		ClientResponse result = iClientService.clientByEmail(email);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ServiceResponse> save(@RequestBody Client client) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iClientService.save(client);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResponse> updateByLogin(@RequestBody Client client) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iClientService.updateByLogin(client);
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
			iClientService.deleteByLogin(login);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/documentation/{loginAdm}")
	public ResponseEntity<ServiceResponse> updateDocumentation(@PathVariable("loginAdm") String loginAdm, @RequestBody Client client) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iClientService.updateDocumentation(loginAdm, client);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/status/{loginAdm}")
	public ResponseEntity<ServiceResponse> updateStatus(@PathVariable("loginAdm") String loginAdm, @RequestBody Client client) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iClientService.updateStatus(loginAdm, client);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
