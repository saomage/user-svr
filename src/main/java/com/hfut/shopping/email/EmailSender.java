package com.hfut.shopping.email;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements ApplicationRunner{
	
	@Autowired
	JavaMailSender sender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Value("${domain.name}")
	private String domainName;
	
	public static final String NAMESERVER = "192.168.216.128:9876;192.168.216.130:9876";
	
	private static final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("emailConsumer");
	static {
		consumer.setNamesrvAddr(NAMESERVER);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		try {
			consumer.subscribe("registerEmail","producterRegister || consumerRegister");
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		consumer.setMessageModel(MessageModel.BROADCASTING);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		consumer.registerMessageListener(new MessageListenerConcurrently() {
					
					@Override
					public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
						MessageExt messageExt = msgs.get(0);
						try {
							String keys = messageExt.getKeys();
							String tag = messageExt.getTags();
							String body = new String(messageExt.getBody(),RemotingHelper.DEFAULT_CHARSET);
							SimpleMailMessage message=new SimpleMailMessage();
							message.setFrom(from);
							message.setTo(body);
							message.setText("http://"+domainName+"/register/"+tag+"/"+keys);
							sender.send(message);
						} catch (Exception e) {
							e.printStackTrace();
							return ConsumeConcurrentlyStatus.RECONSUME_LATER;
						}
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
					
				});
				consumer.start();
			}

	
}
