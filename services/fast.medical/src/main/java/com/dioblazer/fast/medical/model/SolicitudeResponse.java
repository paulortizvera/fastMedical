package com.dioblazer.fast.medical.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SolicitudeResponse extends ServiceResponse {
	private static final long serialVersionUID = -6589552909240760066L;
	private List<Solicitude> solicitude;
}
