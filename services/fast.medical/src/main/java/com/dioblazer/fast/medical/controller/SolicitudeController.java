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
import com.dioblazer.fast.medical.model.Solicitude;
import com.dioblazer.fast.medical.model.SolicitudeResponse;
import com.dioblazer.fast.medical.service.ISolicitudeService;
import com.dioblazer.fast.medical.utils.BusinessException;

@RestController
@RequestMapping("api/v1/solicitude")
@CrossOrigin("*")
public class SolicitudeController {
	@Autowired
	private ISolicitudeService iSolicitudeService;

	@GetMapping
	public ResponseEntity<SolicitudeResponse> list() {
		SolicitudeResponse result = iSolicitudeService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/listComplete")
	public ResponseEntity<SolicitudeResponse> listComplete() {
		SolicitudeResponse result = iSolicitudeService.findComplete();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/{number}")
	public ResponseEntity<SolicitudeResponse> solicitudeByLogin(@PathVariable("number") Integer number) {
		SolicitudeResponse result = iSolicitudeService.solicitudeByNumber(number);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/client/{client}")
	public ResponseEntity<ServiceResponse> solicitudeByClient(@PathVariable("client") Integer client) {
		SolicitudeResponse result = iSolicitudeService.solicitudeByClient(client);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/client/login/{loginCli}")
	public ResponseEntity<ServiceResponse> solicitudeByClientLogin(@PathVariable("loginCli") String loginCli) {
		SolicitudeResponse result = iSolicitudeService.solicitudeByClientLogin(loginCli);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/profesional/{profesional}")
	public ResponseEntity<ServiceResponse> solicitudeByProfesional(@PathVariable("profesional") Integer client) {
		SolicitudeResponse result = iSolicitudeService.solicitudeByProfesional(client);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/profesional/login/{loginCli}")
	public ResponseEntity<ServiceResponse> solicitudeByProfesionalLogin(@PathVariable("loginCli") String loginCli) {
		SolicitudeResponse result = iSolicitudeService.solicitudeByProfesionalLogin(loginCli);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ServiceResponse> save(@RequestBody Solicitude solicitude) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iSolicitudeService.save(solicitude);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ServiceResponse> updateByNumber(@RequestBody Solicitude solicitude) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iSolicitudeService.updateByNumber(solicitude);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{number}")
	public ResponseEntity<ServiceResponse> deleteByLogin(@PathVariable("number") Integer number) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iSolicitudeService.deleteByNumber(number);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/acept")
	public ResponseEntity<ServiceResponse> aceptSolicitude(@RequestBody Solicitude solicitude) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iSolicitudeService.aceptSolicitude(solicitude);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PutMapping(value = "/status")
	public ResponseEntity<ServiceResponse> updateStatus(@RequestBody Solicitude solicitude) {
		ServiceResponse resp = new ServiceResponse();
		resp.setSuccess(true);
		try {
			iSolicitudeService.updateStatus(solicitude);
		} catch (BusinessException e) {
			resp.setSuccess(false);
			resp.setMessString(e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
