package com.bjfu.jtap;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.bjfu.jtap.mapper")
@SpringBootApplication
public class JtapApplication {

	public static void main(String[] args) {
		SpringApplication.run(JtapApplication.class, args);
	}

}

