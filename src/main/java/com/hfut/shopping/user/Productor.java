package com.hfut.shopping.user;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Component
@EqualsAndHashCode(callSuper=false)
public class Productor extends Consumer{
	
	List<Shop> shops;

}
