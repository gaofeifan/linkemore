package cn.linkmore.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class OpenTokenUtil {

	static String Secret = "123456789123456789";

	private static final long EXPIRE_TIME = 50 * 60 * 1000;

	public static String createToken() throws Exception {

		long now = System.currentTimeMillis();
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create()
				.withHeader(map)
				.withClaim("uid", "183101")
				.withClaim("mobile", "18310151719")
				.withClaim("plates", "")
				.withExpiresAt(date)         
				.withIssuedAt(new Date(now))
				.sign(Algorithm.HMAC256(Secret));
		return token;
	}

	// 解析token
	public static Map<String, Claim> verifyToken(String token, String secret) throws Exception {

		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Secret)).build();

		DecodedJWT jwt = verifier.verify(token);

		return jwt.getClaims();

	} 

	public static void main(String[] args) {
		try {
			String token = createToken();
			System.out.println(token);
			/*
			 * String stt =
			 * "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXgiOiIxIiwiZXhwIjoxNTUxOTUzNzA3LCJ1c2VySWQiOiIxODMxMDE1MTcxNiJ9.VVJ6_N1-Ux6U2yQrGSJUIOcMYWpC2HWt8dz6xxqf3yw\r\n";
			 * 
			 * Map<String, Claim> map = verifyToken(stt, Secret);
			 * System.out.println(map.get("uid").asString());
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
