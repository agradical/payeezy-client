package io.pivotal.payeezy;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;


public class PayeezyClient {
	
	private static Logger logger = Logger.getLogger(PayeezyClient.class);

	private static Charset UTF_8 = Charset.forName("UTF-8");


	private final RestTemplate restTemplate;

	private final String transactionsUrl;
	
	private final String secondaryTransactionUrl;


	public PayeezyClient(Credentials credentials, String transactionsUrl) {
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new PayeezyRequestInterceptor(credentials));
		this.restTemplate = new RestTemplate();
		this.restTemplate.setInterceptors(interceptors);
		this.transactionsUrl = transactionsUrl;
		this.secondaryTransactionUrl = this.transactionsUrl + "/{id}";
	}

	public ResponseEntity<TransactionResponse> post(TransactionRequest request) {
		return this
				.restTemplate
				.postForEntity(this.transactionsUrl,request, TransactionResponse.class);
	}

	public ResponseEntity<TransactionResponse> post(TransactionRequest request, String id) {
		logger.info("Secondary Transaction: " + this.secondaryTransactionUrl + request.toString());
		return this.restTemplate.postForEntity(this.secondaryTransactionUrl, request, 
				TransactionResponse.class, id);
	}
	
	/**
	 * Adds headers required for Payeezy transactions.
	 */
	private static class PayeezyRequestInterceptor implements ClientHttpRequestInterceptor {

		private final Credentials credentials;


		public PayeezyRequestInterceptor(Credentials credentials) {
			this.credentials = credentials;
		}

		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add(HttpHeaders.USER_AGENT, System.getProperty("java.version"));

			String payload = new String(body, UTF_8);
			Map<String, String> headerValues = this.credentials.initHeaders(payload);
			for (Map.Entry<String, String> entry : headerValues.entrySet()) {
				headers.add(entry.getKey(), entry.getValue());
			}

			request.getHeaders().putAll(headers);
			return execution.execute(request, body);
		}
	}

}
