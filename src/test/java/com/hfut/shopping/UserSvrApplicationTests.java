package com.hfut.shopping;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.hfut.shopping.controller.ProductorController;
import com.hfut.shopping.massage.ResultMsg;
import com.hfut.shopping.user.Productor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSvrApplicationTests {

//	private final Logger logger= LoggerFactory.getLogger(UserSvrApplicationTests.class);
	
	@Autowired
	ProductorController pc;
	
	Productor p;
	
	@Autowired
	JavaMailSender sender;
	
	@Before
	public void before() {
		p=new Productor();
		p.setEmail("18856332070@163.com");
		p.setPhone("phone");
		p.setPasswd("passwd");
		p.setName("name");
		p.setAboutme("aboutme");
		p.setAvatar("avatar");
	}
	
	@Test
	public void contextLoads() throws InterruptedException {
		
		 ResultMsg register = pc.register(p);
		 System.out.println(register+register.getMsg());
		 Thread.currentThread().join();
	}

}

