package com.hfut.shopping.controller;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hfut.shopping.massage.ResultMsg;
import com.hfut.shopping.service.ProductorService;
import com.hfut.shopping.user.Productor;

@RestController
public class ProductorController {

	@Autowired
	StringRedisTemplate redis;

	@Autowired
	ProductorService pservice;
	
	public static final String NAMESERVER = "192.168.216.128:9876;192.168.216.130:9876";
	
	private final DefaultMQProducer MQproducer = new  DefaultMQProducer("producterRegister");
	
	{
		MQproducer.setNamesrvAddr(NAMESERVER);
		try {
			MQproducer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("productor/login")
	public Productor Login(@RequestBody Productor productor) {
		productor.setEnable(1);
		List<Productor> list = pservice.select(productor);
		if (list.size() != 1 || list.get(0) == null) {
			return null;
		}
		return list.get(0);
	}

	@PostMapping("productor/register")
	public ResultMsg register(@RequestBody Productor productor) {
		try {
			if(redis.opsForValue().get(productor.getEmail())!=null) {
				throw new Exception("2分钟内不可重复注册");
			}
			pservice.chackBeforeInsert(productor.getEmail());
			pservice.insert(productor);
			Integer key = ThreadLocalRandom.current().nextInt(10000);
			redis.opsForValue().set(key.toString(), productor.getEmail(), Duration.ofSeconds(120));
			redis.opsForValue().set(productor.getEmail(),key.toString() , Duration.ofSeconds(120));
			Message message= new Message("registerEmail", "producterRegister", key.toString(), productor.getEmail().getBytes());
			MQproducer.send(message);
			return ResultMsg.successMsg.setMsg("注册申请成功");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage()==null?ResultMsg.errorMsg.setMsg("注册申请失败"):ResultMsg.errorMsg.setMsg(e.getMessage());
		}

	}
	
	@PostMapping("productor/email")
	public ResultMsg email(@RequestBody String email) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		try {
			Integer key = ThreadLocalRandom.current().nextInt(10000);
			redis.opsForValue().set(key.toString(), email, Duration.ofSeconds(120));
			redis.opsForValue().set(email,key.toString() , Duration.ofSeconds(120));
			Message message= new Message("registerEmail", "producterRegister", key.toString(), email.getBytes());
			MQproducer.send(message);
			return ResultMsg.successMsg.setMsg("重新发送成功");
		} catch (Exception e) {
			return ResultMsg.successMsg.setMsg("重新发送失败");
		}
	}
	
}
