
package io.pivotal.payeezy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TransactionController {
	
	@Autowired
	PayeezyClient payeezyClient;
	
    @RequestMapping("/transact")
    public CustomResponse transact(@RequestParam(value="detail", defaultValue="World") String detail){
    	
    	CustomResponse response = new CustomResponse();
    	TransactionRequest request  = new TransactionRequest();
        TransactionResponse tres  = new TransactionResponse();
        /*
        String amount = "100";

        
        
        Card c = new Card();
        	c.setName("john Smith");
        	c.setNumber("4788250000028291");
        	c.setCvv("123");
        	c.setType("visa");
        	c.setExpiryDt("2016-01-01");
        
        request.setPaymentMethod("credit_card");
        request.setAmount(amount);
        request.setCard(c);
        request.setCurrency("USD");
        request.setTransactionType("PURCHASE");
        
        Address address=new Address();
        request.setBilling(address);
        address.setState("NY");
        address.setAddressLine1("sss");
        address.setZip("11747");
        address.setCountry("US");
        */
        
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
        
    	String transaction_id="";

        try {
        	ResponseEntity<TransactionResponse> responseEntity = payeezyClient.post(request);
        	tres = responseEntity.getBody();
        	transaction_id = tres.getTransactionId();
        	
        	response.addResponse(tres);
        	
        } catch(Exception e) {
        }

        try {
        	//Shopkeeper
        	request.setAmount("1000");
            request.setCurrency("USD");
            request.setPaymentMethod("credit_card");
            request.setTransactionType("REFUND");
            Card shopkeepercard=new Card();
            shopkeepercard.setCvv("123");
            shopkeepercard.setExpiryDt("1219");
            shopkeepercard.setName("ad as");
            shopkeepercard.setType("visa");
            //shopkeepercard.setNumber("40055192000000004");
            shopkeepercard.setNumber("4012000033330026");
            
            request.setCard(shopkeepercard);
            
        	ResponseEntity<TransactionResponse> refundtoshopkeeper = payeezyClient.post(request, transaction_id);
        	TransactionResponse shopKeeperResponse = refundtoshopkeeper.getBody();
        		
        	response.addResponse(shopKeeperResponse);
        	
        }catch(Exception e){
        	
        }
        
        try {
        	//Delivery
        	request.setAmount("100");
            request.setCurrency("USD");
            request.setPaymentMethod("credit_card");
            request.setTransactionType("REFUND");
            Card deliveryCard=new Card();
            deliveryCard.setCvv("123");
            deliveryCard.setExpiryDt("1219");
            //deliveryCard.setName("john smith");
            deliveryCard.setType("visa");
            deliveryCard.setNumber("40055192000000004");
            //deliveryCard.setNumber("4012000033330026");
            
            request.setCard(deliveryCard);
            
            ResponseEntity<TransactionResponse> refundToDelivery = payeezyClient.post(request, transaction_id);
        	TransactionResponse deliveryResponse = refundToDelivery.getBody();
            
        	response.addResponse(deliveryResponse);
        	
        }catch (Exception e){
        	
        }
    	return response;
    }
    
    
}
