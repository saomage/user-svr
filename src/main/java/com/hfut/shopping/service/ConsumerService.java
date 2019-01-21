package com.hfut.shopping.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hfut.shopping.user.Consumer;

@Component
public interface ConsumerService {

	Consumer selectById(Long id);

	List<Consumer> select(Consumer consumer);

	int update(Consumer consumer);

	int insert(Consumer consumer);

	int delete(String email);
	
	void chackBeforeInsert(String email);
}
