package br.com.lorenci.systeml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SystemlApplication {

	public static void main(String[] args) {
        SpringApplication.run(SystemlApplication.class, args);
	}

}
