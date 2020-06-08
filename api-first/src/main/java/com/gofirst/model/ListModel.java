package com.gofirst.model;

import java.util.List;

import com.gofirst.entity.Order;

import lombok.Data;
@Data
public class ListModel {
	private Long id;

	private List<Order> list;



	
}
