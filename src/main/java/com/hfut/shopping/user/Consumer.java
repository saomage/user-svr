package com.hfut.shopping.user;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Consumer{

	private Long id;
	private String name;
	private String phone;
	private String email;
	private String aboutme;
	private String passwd;
	private Integer type;

	private String avatar;
	
	private Integer enable;
}
