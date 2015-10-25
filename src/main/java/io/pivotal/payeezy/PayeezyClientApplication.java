package io.pivotal.payeezy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class PayeezyClientApplication {

	@Autowired
	Environment env;


	@Bean
	public PayeezyClient payeezyClient(){
		String key = this.env.getProperty("api_key");
		String secret = this.env.getProperty("api_secret");
		String token = this.env.getProperty("merchant_token");
		String url = this.env.getProperty("transactions_url");
		return new PayeezyClient(new Credentials(key, secret, token), url);
	}


	public static void main(String[] args) {
		SpringApplication.run(PayeezyClientApplication.class, args);
	}

}
