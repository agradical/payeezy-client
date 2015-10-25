package io.pivotal.payeezy;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;




@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Token implements Cloneable  {

	public Token() {
	}
	@JsonProperty("token_type")
	private String tokenType;
		    
	@JsonProperty("token_data")
	private Transarmor tokenData;
		    
	public String getTokenType() {
	    return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		    this.tokenType = tokenType;
	}

	public Transarmor getTokenData() {
	     return tokenData;
	}
	    
    public void setTokenData(Transarmor tokenData) {
        this.tokenData = tokenData;
    }
}