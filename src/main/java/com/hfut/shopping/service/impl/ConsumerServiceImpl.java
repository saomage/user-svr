package com.hfut.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfut.shopping.mapper.ConsumerMapper;
import com.hfut.shopping.service.ConsumerService;
import com.hfut.shopping.user.Consumer;

@Component
public class ConsumerServiceImpl implements ConsumerService{
	
	@Autowired
	ConsumerMapper cmapper;

	@Override
	public Consumer selectById(Long id) {
		return cmapper.selectById(id);
	}

	@Override
	public List<Consumer> select(Consumer consumer) {
		return cmapper.select(consumer);
	}

	@Override
	public int update(Consumer consumer) {
		return cmapper.update(consumer);
	}

	@Override
	public int insert(Consumer consumer) {
		return cmapper.insert(consumer);
	}

	@Override
	public int delete(String email) {
		return cmapper.delete(email);
	}
	
	@Override
	public void chackBeforeInsert(String email) {
		cmapper.chackBeforeInsert(email);
	}

}
