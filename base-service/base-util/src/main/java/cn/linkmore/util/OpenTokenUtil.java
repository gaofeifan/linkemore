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
	private static final long EXPIRE_TIME = 48 * 60 * 1000;

	public static String createToken() throws Exception {
		long now = System.currentTimeMillis();
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create().withHeader(map)
				.withClaim("uid", "2009")
				.withClaim("mobile", "18514410536")
			    .withClaim("plates", "[京M92977,京Z63692]").withExpiresAt(date).withIssuedAt(new Date(now))
				.sign(Algorithm.HMAC256(Secret));
		return token;
	}

	// 解析token
	public static Map<String, Claim> verifyToken(String token, String secret) throws Exception {

		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();

		DecodedJWT jwt = verifier.verify(token);

		return jwt.getClaims();

	}

	public static void main(String[] args) {
		try {
			String token = createToken();
			System.out.println(token);
			Map<String, Claim> map = verifyToken(token, Secret);
			System.out.println(map.get("uid").asString());
			System.out.println(map.get("mobile").asString());
			System.out.println(map.get("plates").asString());
			String plates = map.get("plates").asString();
			if (plates.startsWith("[")) {
				plates = plates.substring(1);
	        }
	        if (plates.endsWith("]")) {
	        	plates = plates.substring(0,plates.length() - 1);
	        }
	        if(plates.length()>0) {
	        	String[] plateArr = plates.split(",");
	        	for(String plate : plateArr) {
	        		System.out.println(plate);
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
