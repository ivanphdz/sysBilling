package com.billing.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.billing.demo.service.UploadFileService;

@SpringBootApplication
public class SysBillingApplication implements CommandLineRunner{

	@Autowired
	UploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SysBillingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}
}
