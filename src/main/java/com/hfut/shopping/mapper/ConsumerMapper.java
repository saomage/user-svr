package com.hfut.shopping.mapper;

import java.util.List;

import com.hfut.shopping.user.Consumer;

public interface ConsumerMapper {

	Consumer selectById(Long id);

	List<Consumer> select(Consumer consumer);

	int update(Consumer consumer);

	int insert(Consumer consumer);

	int delete(String email);
	
	void chackBeforeInsert(String email);
}
