package com.hfut.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfut.shopping.mapper.ProductorMapper;
import com.hfut.shopping.service.ProductorService;
import com.hfut.shopping.user.Productor;

@Component
public class ProductorServiceImpl implements ProductorService{
	
	@Autowired
	ProductorMapper pmapper;

	@Override
	public Productor selectById(Long id) {
		return pmapper.selectById(id);
	}

	@Override
	public List<Productor> select(Productor productor) {
		return pmapper.select(productor);
	}

	@Override
	public int update(Productor productor) {
		return pmapper.update(productor);
	}

	@Override
	public int insert(Productor productor) {
		return pmapper.insert(productor);
	}

	@Override
	public int delete(String email) {
		return pmapper.delete(email);
	}

	@Override
	public void chackBeforeInsert(String email) {
		pmapper.chackBeforeInsert(email);
	}

}
