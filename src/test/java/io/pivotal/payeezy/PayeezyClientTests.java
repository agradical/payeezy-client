package io.pivotal.payeezy;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PayeezyClientApplication.class)
public class PayeezyClientTests {
	
	private static Logger logger = Logger.getLogger(PayeezyClientTests.class);

	@Autowired
    PayeezyClient payeezyClient;

	
    @Test
    public void purchaseTransaction()throws Exception {
        TransactionRequest request = createPrimaryTransaction();
        System.out.println("request: " + request.toString());
        ResponseEntity<TransactionResponse> responseEntity = this.payeezyClient.post(request);
        TransactionResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("approved", response.getTransactionStatus());
    }
    
    private TransactionRequest createPrimaryTransaction() {
        TransactionRequest request=new TransactionRequest();
        request.setAmount("1100");
        request.setCurrency("USD");
        request.setPaymentMethod("credit_card");
        request.setTransactionType("PURCHASE");
        Card card=new Card();
        card.setCvv("123");
        card.setExpiryDt("1219");
        card.setName("Test data ");
        card.setType("visa");
        card.setNumber("4788250000028291");
        request.setCard(card);
        Address address=new Address();
        request.setBilling(address);
        address.setState("NY");
        address.setAddressLine1("sss");
        address.setZip("11747");
        address.setCountry("US");
        //request.setTa_token(null);
        return request;
    }
    
    @Test
    public void voidPayment() throws Exception {
        TransactionRequest request = createPrimaryTransaction();
        System.out.println("request: " + request.toString());
        ResponseEntity<TransactionResponse> responseEntity = this.payeezyClient.post(request);
        TransactionResponse response = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("approved", response.getTransactionStatus());    	
        
        TransactionRequest trans=new TransactionRequest();
        trans.setPaymentMethod(response.getMethod());
        trans.setAmount(response.getAmount());
        trans.setCurrency(response.getCurrency());
        trans.setTransactionTag(response.getTransactionTag());
        trans.setId(response.getTransactionId());
        trans.setTransactionType("VOID");
        
        logger.info("Secondary Request: " + trans);
        responseEntity = this.payeezyClient.post(trans, response.getTransactionId());
        logger.info("Secondary Response: " + responseEntity.getBody().toString());
    }

}
