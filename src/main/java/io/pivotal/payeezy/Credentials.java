package io.pivotal.payeezy;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Credentials {

	private final static String NONCE_HEADER = "nonce";

	private final static String APIKEY_HEADER = "apikey";

	private final static String APISECRET_HEADER = "pzsecret";

	private final static String TOKEN_HEADER = "token";

	private final static String TIMESTAMP_HEADER = "timestamp";

	private final static String AUTHORIZE_HEADER = "Authorization";

	private static SecureRandom random;

	static {
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA1PRNG algorithm not available.");
		}
	}

	private static Charset UTF_8 = Charset.forName("UTF-8");


	private String apiKey;
	
	private String apiSecret;

	private String merchantToken;


	public Credentials(String apiKey, String apiSecret, String merchantToken) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.merchantToken = merchantToken;
	}


	public Map<String, String> initHeaders(String payload) {

		String nonce = String.valueOf(Math.abs(random.nextLong()));
		String timestamp = String.valueOf(System.currentTimeMillis());

		String data = this.apiKey + nonce + timestamp + this.merchantToken + payload;
		String macValue = calculateMacValue(this.apiSecret, data);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put(NONCE_HEADER, nonce);
		headers.put(TIMESTAMP_HEADER, timestamp);
		headers.put(APISECRET_HEADER, this.apiSecret);
		headers.put(APIKEY_HEADER, this.apiKey);
		headers.put(TOKEN_HEADER, this.merchantToken);
		headers.put(AUTHORIZE_HEADER, macValue);

		return headers;
	}

	private String calculateMacValue(String secret, String data) {
		Mac mac = initMacInstance(secret);
		byte[] hash = mac.doFinal(data.getBytes(UTF_8));
		byte[] hex = Hex.encodeHexString(hash).getBytes(UTF_8);
		return Base64.encodeBase64String(hex);
	}

	private Mac initMacInstance(String secret) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
			return mac;
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("HmacSHA256 algorithm not available.");
		}
		catch (InvalidKeyException e) {
			throw new IllegalStateException("HmacSHA256 not available");
		}
	}

}
	

