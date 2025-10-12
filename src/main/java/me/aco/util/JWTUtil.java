package me.aco.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.RandomStringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JWTUtil {
	
	public static String createToken() {
		try {
		    Algorithm algorithm = Algorithm.HMAC512("a85e024c34cbc78f559627e9d36cece0bb2bf1df7be8ca7eb606405e410fda4fbcac05cb8fa79bcabf21f8a9f6a48d0cb16eb95b52f44709e6f40b5aeb604909");
		    String token = JWT.create()
		        .withIssuer("MarketplaceBackendApp")
		        .withAudience("MarketplaceBackendApp")
		        .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException exception){
		    // Invalid Signing configuration / Couldn't convert Claims.
			return null;
		}
	}

}
