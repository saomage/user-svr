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
import com.hfut.shopping.service.ConsumerService;
import com.hfut.shopping.user.Consumer;

@RestController
public class ConsumerController {

	@Autowired
	StringRedisTemplate redis;
	
	@Autowired
	ConsumerService cservice;
	
	public static final String NAMESERVER = "192.168.216.128:9876;192.168.216.130:9876";
	
	private final DefaultMQProducer MQproducer = new  DefaultMQProducer("consumerRegister");
	
	{
		MQproducer.setNamesrvAddr(NAMESERVER);
		try {
			MQproducer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("consumer/login")
	public Consumer Login(@RequestBody Consumer consumer) {
		consumer.setEnable(1);
		List<Consumer> list = cservice.select(consumer);
		if (list.size() != 1 || list.get(0) == null) {
			return null;
		}
		System.out.println(list);
		return list.get(0);
	}
	
	@PostMapping("consumer/register")
	public ResultMsg register(@RequestBody Consumer consumer) {
		try {
			if(redis.opsForValue().get(consumer.getEmail())!=null) {
				throw new Exception("2分钟内不可重复注册");
			}
			cservice.chackBeforeInsert(consumer.getEmail());
			cservice.insert(consumer);
			Integer key = ThreadLocalRandom.current().nextInt(10000);
			redis.opsForValue().set(key.toString(), consumer.getEmail(), Duration.ofSeconds(120));
			redis.opsForValue().set(consumer.getEmail(),key.toString() , Duration.ofSeconds(120));
			Message message= new Message("registerEmail", "consumerRegister", key.toString(), consumer.getEmail().getBytes());
			MQproducer.send(message);
			return ResultMsg.successMsg.setMsg("注册申请成功");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage()==null?ResultMsg.errorMsg.setMsg("注册申请失败"):ResultMsg.errorMsg.setMsg(e.getMessage());
		}
	}
	
	@PostMapping("consumer/email")
	public ResultMsg email(@RequestBody String email) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		try {
			Integer key = ThreadLocalRandom.current().nextInt(10000);
			redis.opsForValue().set(key.toString(), email, Duration.ofSeconds(120));
			redis.opsForValue().set(email,key.toString() , Duration.ofSeconds(120));
			Message message= new Message("register_email", "consumerRegister", key.toString(), email.getBytes());
			MQproducer.send(message);
			return ResultMsg.successMsg.setMsg("重新发送成功");
		} catch (Exception e) {
			return ResultMsg.successMsg.setMsg("重新发送失败");
		}
	}
}
