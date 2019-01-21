package com.hfut.shopping.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hfut.shopping.user.Productor;

@Component
public interface ProductorService {
	
	Productor selectById(Long id);

	List<Productor> select(Productor productor);

	int update(Productor productor);

	int insert(Productor productor);

	int delete(String email);
	
	void chackBeforeInsert(String email);
	
}
