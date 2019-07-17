package com.sm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.config.PropertiesConfig;

@RestController
public class OrderController {
	@Autowired
	PropertiesConfig propertiesConfig;

	@GetMapping("/order")
	public String getOrders() throws URISyntaxException, IOException {
		final URI userUri = ResourceUtils.getURL(propertiesConfig.getDbUser()).toURI();
		final URI passUri = ResourceUtils.getURL(propertiesConfig.getDbPassword()).toURI();
		final byte[] encodedUser = Files.readAllBytes(Paths.get(userUri));
        final byte[] encodedPasas = Files.readAllBytes(Paths.get(passUri));

        String user = sanitize(encodedUser);
        String pass = sanitize(encodedPasas);
		System.out.println("user from yml :"+user);
		System.out.println("pass from yml :"+pass);
		return "DB name :"+propertiesConfig.getDbName()+" | DB user :"+user+" | DB pass :"+pass+" |v2.0";
	}
	
	private String sanitize(byte[] strBytes) {
        return new String(strBytes)
            .replace("\r", "")
            .replace("\n", "");
    }
	
}
