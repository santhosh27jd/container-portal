package com.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * Class ContainerPortalApplication
 * @author santhoshkumardurairaj
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.portal","com.portal.repository", "com.portal.model","com.portal.config","com.portal.exception,com.portal.service"})
public class ContainerPortalApplication {

	/**
	 * RestTemplate Bean initialization
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ContainerPortalApplication.class, args);
	}

}
