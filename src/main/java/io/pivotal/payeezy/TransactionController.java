
package io.pivotal.payeezy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TransactionController {
	
	@Autowired
	PayeezyClient payeezyClient;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CreditCardDAO creditCardDAO;
	
    @RequestMapping("/transact")
    public CustomResponse transact(@RequestParam(value="detail", defaultValue = "1") String detail){
    
    	
    	CustomResponse response = new CustomResponse();
    	TransactionRequest request  = new TransactionRequest();
        TransactionResponse tres  = new TransactionResponse();

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
    
    @RequestMapping(value="/transactv2",
    		method = RequestMethod.POST,
    		consumes = "application/json")
    public CustomResponse transact (@RequestBody Purchase detail) {
    	
    	Long consumer_id = detail.getConsumer_id();
    	Long delivery_id = detail.getDelivery_id();
    	Long shopkeeper_id = detail.getShopkeeper_id();
    	
    	CustomResponse response = new CustomResponse();
    	TransactionRequest request  = new TransactionRequest();
        TransactionResponse tres  = new TransactionResponse();
    	
    	User shopkeeper = userDAO.findById(shopkeeper_id).get(0);
    	User delivery = userDAO.findById(delivery_id).get(0);
    	User consumer = userDAO.findById(consumer_id).get(0);

    	Long amount = detail.getTotal_amount();
    	Long shopkeeper_cut = detail.getShopkeeper_cut();
    	Long delivery_cut = detail.getDelivery_cut();
    
        
        CreditCard consumercc = creditCardDAO.findByCc(consumer.getCc()).get(0);
        CreditCard deliverycc = creditCardDAO.findByCc(delivery.getCc()).get(0);
        CreditCard shopkeepercc = creditCardDAO.findByCc(shopkeeper.getCc()).get(0);

        Card card=new Card();
        card.setCvv(consumercc.getCvv());
        card.setExpiryDt(consumercc.getExpiry_date());
        card.setName(consumer.getName());
        card.setType(consumercc.getType());
        card.setNumber(consumer.getCc());
        

    	request.setAmount(amount.toString());
        
    	request.setCurrency("USD");
        request.setPaymentMethod("credit_card");
        request.setTransactionType("PURCHASE");
        
        request.setCard(card);
        Address address=new Address();
        request.setBilling(address);

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
        	request.setAmount(shopkeeper_cut.toString());
            request.setCurrency("USD");
            request.setPaymentMethod("credit_card");
            request.setTransactionType("REFUND");
            
            Card shopkeepercard=new Card();
            shopkeepercard.setCvv(shopkeepercc.getCvv());
            shopkeepercard.setExpiryDt(shopkeepercc.getExpiry_date());
            shopkeepercard.setName(shopkeeper.getName());
            shopkeepercard.setType(shopkeepercc.getType());
            shopkeepercard.setNumber(shopkeepercc.getCc());
            
            request.setCard(shopkeepercard);
            
        	ResponseEntity<TransactionResponse> refundtoshopkeeper = payeezyClient.post(request, transaction_id);
        	TransactionResponse shopKeeperResponse = refundtoshopkeeper.getBody();
        		
        	response.addResponse(shopKeeperResponse);
        	
        }catch(Exception e){
        	
        }
        
        try {
        	//Delivery
        	request.setAmount(delivery_cut.toString());
            request.setCurrency("USD");
            request.setPaymentMethod("credit_card");
            request.setTransactionType("REFUND");
            
            Card deliveryCard=new Card();
            deliveryCard.setCvv(deliverycc.getCvv());
            deliveryCard.setExpiryDt(deliverycc.getExpiry_date());
            deliveryCard.setName(delivery.getName());
            deliveryCard.setType(deliverycc.getType());
            deliveryCard.setNumber(deliverycc.getCc());
            
            request.setCard(deliveryCard);
            
            ResponseEntity<TransactionResponse> refundToDelivery = payeezyClient.post(request, transaction_id);
        	TransactionResponse deliveryResponse = refundToDelivery.getBody();
            
        	response.addResponse(deliveryResponse);

        }catch (Exception e){
        	
        }
    	return response;
    }
    
    
}
