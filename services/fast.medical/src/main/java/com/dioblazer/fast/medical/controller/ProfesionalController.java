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
import com.dioblazer.fast.medical.model.Profesional;
import com.dioblazer.fast.medical.model.ProfesionalResponse;
import com.dioblazer.fast.medical.service.IProfesionalService;
import com.dioblazer.fast.medical.utils.BusinessException;

@RestController
@RequestMapping("api/v1/profesional")
@CrossOrigin("*")
public class ProfesionalController {
	@Autowired
	private IProfesionalService iProfesionalService;

	@GetMapping
	public ResponseEntity<ProfesionalResponse> list() {
		ProfesionalResponse result = iProfesionalService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/listActives")
	public ResponseEntity<ProfesionalResponse> listActives() {
		ProfesionalResponse result = iProfesionalService.findActives();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/{login}")
	public ResponseEntity<ProfesionalResponse> ProfesionalByLogin(@PathVariable("login") String login) {
		ProfesionalResponse result = iProfesionalService.ProfesionalByLogin(login);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<ServiceResponse> ProfesionalByEmail(@PathVariable("email") String email) {
		ProfesionalResponse result = iProfesionalService.ProfesionalByEmail(email);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ServiceResponse> save(@RequestBody Profesional profesional) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iProfesionalService.save(profesional);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/{login}")
	public ResponseEntity<ServiceResponse> updateByLogin(@RequestBody Profesional profesional) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iProfesionalService.updateByLogin(profesional);
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
			iProfesionalService.deleteByLogin(login);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/documentation/{loginAdm}")
	public ResponseEntity<ServiceResponse> updateDocumentation(@PathVariable("loginAdm") String loginAdm, @RequestBody Profesional profesional) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iProfesionalService.updateDocumentation(loginAdm, profesional);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/status/{loginAdm}")
	public ResponseEntity<ServiceResponse> updateStatus(@PathVariable("loginAdm") String loginAdm, @RequestBody Profesional profesional) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iProfesionalService.updateStatus(loginAdm, profesional);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
