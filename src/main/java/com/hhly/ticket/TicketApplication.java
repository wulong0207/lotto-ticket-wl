package com.hhly.ticket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
@MapperScan("com.hhly.ticket.persistence.dao")
@Configuration
public class TicketApplication {
	public static void main(String[] args) {
        SpringApplication.run(TicketApplication.class, args);
    }
}
