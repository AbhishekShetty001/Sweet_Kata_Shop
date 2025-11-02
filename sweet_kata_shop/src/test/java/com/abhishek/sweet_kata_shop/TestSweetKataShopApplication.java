package com.abhishek.sweet_kata_shop;

import org.springframework.boot.SpringApplication;

public class TestSweetKataShopApplication {

	public static void main(String[] args) {
		SpringApplication.from(SweetKataShopApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
