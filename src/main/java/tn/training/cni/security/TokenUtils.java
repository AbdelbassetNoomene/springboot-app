package tn.training.cni.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tn.training.cni.dto.UserDTO;

@SuppressWarnings("deprecation")
@Component
public class TokenUtils {

	/*@Value("${token.secret}")
	*/private String secret;

	/*@Value("${token.expiration}")
	*/private Long expiration;

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	public String generateToken(UserDTO userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", userDetails);
		claims.put("created", this.generateCurrentDate());
		return this.generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}

	public String[] extractAndDecodeToken(String header) throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded = Base64.decode(base64Token);
		String token = new String(decoded, "UTF-8");
		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new IOException();
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}