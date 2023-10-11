package com.dioblazer.fast.medical.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse implements Serializable {
	private static final long serialVersionUID = 6972071715387756998L;
	Boolean success;
	String messString;
}
