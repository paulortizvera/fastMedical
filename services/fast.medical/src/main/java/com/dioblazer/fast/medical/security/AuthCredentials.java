package com.dioblazer.fast.medical.security;

import lombok.Data;

@Data
public class AuthCredentials {
	private String login;
	private String password;
}
