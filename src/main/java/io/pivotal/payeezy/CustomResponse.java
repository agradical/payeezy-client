package io.pivotal.payeezy;

import java.util.ArrayList;
import java.util.List;

public class CustomResponse {

	public CustomResponse() {
		// TODO Auto-generated constructor stub
		this.response = new ArrayList<TransactionResponse>();
	}
	
	List<TransactionResponse> response;

	public List<TransactionResponse> getResponse() {
		return response;
	}

	public void setResponse(List<TransactionResponse> response) {
		this.response = response;
	}
	
	public void addResponse(TransactionResponse response) {
		this.response.add(response);
	}
	
	
}
