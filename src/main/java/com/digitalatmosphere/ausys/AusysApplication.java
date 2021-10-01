package com.digitalatmosphere.ausys;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.digitalatmosphere.ausys.controllers.MainController;

@SpringBootApplication
public class AusysApplication {

	public static void main(String[] args) {
		new File(MainController.DirectorioArchivos).mkdir();
		SpringApplication.run(AusysApplication.class, args);
	}

}
