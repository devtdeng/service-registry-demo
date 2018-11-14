package com.example.producerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ProducerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerDemoApplication.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        String cfInstanceAddr = System.getenv("CF_INSTANCE_ADDR");
        String cfInstanceInternalIP = System.getenv("CF_INSTANCE_INTERNAL_IP");
        String cfInstnaceIndex = System.getenv("CF_INSTANCE_INDEX");
    
        return "Response from:" + "<br />" + "CF_INSTANCE_ADDR: " + cfInstanceAddr + "<br />" + "CF_INSTANCE_INTERNAL_IP: " + cfInstanceInternalIP + "<br />" + "CF_INSTANCE_INDEX " + cfInstnaceIndex;
    }

    @RequestMapping("/test_header")
    public ResponseEntity<String> testHeader() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Cache-Control", "public");
        responseHeaders.add("sync-control", "max-age=1");
        responseHeaders.add("ALLCAPS", "ALLCAPS");
        responseHeaders.add("alllower", "alllower");
        responseHeaders.add("CamelCase", "CamelCase");
        responseHeaders.add("UpDoWn", "UpDoWn");
        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
    }
}