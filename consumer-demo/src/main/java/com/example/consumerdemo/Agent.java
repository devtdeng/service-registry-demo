package com.example.consumerdemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Agent {
	@Autowired
	private RestTemplate restx;

	@Bean
	@LoadBalanced
	public RestTemplate rest(){
		return new RestTemplate();
	}

	@HystrixCommand(fallbackMethod="fallbackMethod")
	public String callProducer() {
		return restx.getForObject("https://producer-demo/", String.class);
	}

	String fallbackMethod(){
		return "fallbackMethod on Consumer";
	}
}
