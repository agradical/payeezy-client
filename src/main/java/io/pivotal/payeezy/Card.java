package io.pivotal.payeezy;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Card implements Cloneable  {

	public Card() {
	}
	@JsonProperty("type")
	private String type;
	@JsonProperty("cardholder_name")
	private String name;
	@JsonProperty("card_number")
	private String number;
	@JsonProperty("exp_date")
	private String expiryDt;
    @JsonProperty("cvv")
	private String cvv;
	
	
	
	public Card(String type, String name, String number, String expiryDt,
			String cvv) {
		super();
		this.type = type;
		this.name = name;
		this.number = number;
		this.expiryDt = expiryDt;
		this.cvv = cvv;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String nmber) {
		this.number = nmber;
	}
	
	public String getExpiryDt() {
		return expiryDt;
	}
	
	public void setExpiryDt(String expiryDt) {
		this.expiryDt = expiryDt;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	@Override
	public Card clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Card)super.clone();
	}

}
