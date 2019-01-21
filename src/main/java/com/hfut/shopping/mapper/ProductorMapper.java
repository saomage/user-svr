package com.hfut.shopping.mapper;

import java.util.List;

import com.hfut.shopping.user.Productor;

public interface ProductorMapper {

	Productor selectById(Long id);

	List<Productor> select(Productor productor);

	int update(Productor productor);

	int insert(Productor productor);

	int delete(String email);
	
	void chackBeforeInsert(String email);
}
