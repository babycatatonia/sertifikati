package com.bsep.proj;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bezbednostKlase.Storeing;

@SpringBootApplication

public class BsepprojApplication {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		SpringApplication.run(BsepprojApplication.class, args);
	}
}
