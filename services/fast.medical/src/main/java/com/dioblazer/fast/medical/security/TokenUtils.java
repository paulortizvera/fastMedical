package com.dioblazer.fast.medical.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
	private final static String ACCESS_TOKEN_SECRET = "SIYntMaGsNFuYFKaupNAHQAYOeiONLKHqheEgp07gylN16H=Z?hXvO9UOh8aXO5omd7fvTYG!VaQ8YSO2xLX?JznAw!GUQeHYllKla2n=mE1a=Hfw=MIe/lYT9/v?V9P2zdeGbSqRjLQM=SHPi3=JX4JuADjkw3GU8hKoM6NGg5ajkKZ4/rDXXuYKc6fICQ6HTRqUT?IHb7ufFc2a9nodpf/AE5rqIkS4ACP3wDSnyifGk4isxznDJ1oFEKV0ZKy";
	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 86_400L;

	public static String createToken(String name, String login) {
		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

		Map<String, Object> extra = new HashMap<>();
		extra.put("name", name);
		return Jwts.builder()
				.setSubject(name)
				.setExpiration(expirationDate)
				.addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())).compact();
	}

	public static UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();

			String email = claims.getSubject();

			return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
		} catch (JwtException e) {
			return null;
		}
	}

}
