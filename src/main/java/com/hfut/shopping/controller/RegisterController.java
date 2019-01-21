package com.hfut.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hfut.shopping.service.ConsumerService;
import com.hfut.shopping.service.ProductorService;
import com.hfut.shopping.user.Consumer;
import com.hfut.shopping.user.Productor;

@RestController
public class RegisterController {
	
	@Autowired
	ProductorService pservice;
	
	@Autowired
	ConsumerService cservice;
	
	@Autowired
	StringRedisTemplate redis;

	@RequestMapping(path = "register/{tag}/{key}")
	public String register(@PathVariable String key,@PathVariable String tag) {
		String email = redis.opsForValue().get(key);
		if(email==null) {
			return "激活超时";
		}
		if("producterRegister".equals(tag)) {
			Productor productor=new Productor();
			productor.setEmail(email);
			productor.setEnable(1);
			pservice.update(productor);
		}else {
			Consumer consumer=new Consumer();
			consumer.setEmail(email);
			consumer.setEnable(1);
			cservice.update(consumer);
		}
		return "激活成功";
	}
}
