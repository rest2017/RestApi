package com.app.ProjectRESTApp;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {

	static final long EXPIRATIONTIME = 3600000; // 1 hour
	static final String SECRET = "Dhh3GjhkJ3ytfgF3DsT0K6LGF#!";

	public static String generateToken(String username) {
		String token = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); //HMAC 

		return token;
	}

	public static String authenticateJWT(String token) 
	{
		String user = null;
		     
		if (token != null)
	    {
			try
			{
	        // parse the token.
	       user = Jwts.parser()
	                .setSigningKey(SECRET)
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	    }
	
	 	return user;
	}
}
