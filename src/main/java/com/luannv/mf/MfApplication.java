package com.luannv.mf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableMethodSecurity
public class MfApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfApplication.class, args);
	}

}
