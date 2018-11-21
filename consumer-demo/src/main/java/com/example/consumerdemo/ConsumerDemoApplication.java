package com.example.consumerdemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RestController
public class ConsumerDemoApplication {
	@Value("${current_profile}")
	String profile;

    @Autowired
    private RestTemplate restTemplate;
    
	@Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

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

	@RequestMapping("/testproducer")
	public String testProducer() {
        return getProducer();
    }   

    @HystrixCommand(fallbackMethod = "getBackupProducer")
    public String getProducer() {
      return restTemplate.getForObject("https://producer-demo/testeureka", String.class);
    }

    String getBackupProducer() {
        return "Producer is not available! This is backup!";
    }    
}
