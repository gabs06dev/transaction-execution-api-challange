package com.gabsDev.transactionExecution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionExecutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionExecutionApplication.class, args);
	}

}
