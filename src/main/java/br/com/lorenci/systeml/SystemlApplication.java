package br.com.lorenci.systeml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class SystemlApplication {

	public static void main(String[] args) {
        SpringApplication.run(SystemlApplication.class, args);
	}

}
