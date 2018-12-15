package com.example.consumerdemo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RestController
public class ConsumerDemoApplication {
	@Value("${current_profile}")
	String profile;

	public static void main(String[] args) {
		SpringApplication.run(ConsumerDemoApplication.class, args);
    }

	@RequestMapping("/")
    public String home() {    
        return "Consumer Demo!";
    }    
    
	@RequestMapping("/testconfig")
    public String testConfig() {    
        return "The current profile from config server is: " + profile;
    }

	private final Agent agent;

	public ConsumerDemoApplication(Agent agent){
		this.agent = agent;
	}	

	@RequestMapping("/testproducer")
	public String testproducer(){
		return agent.callProducer();
	}    
}
