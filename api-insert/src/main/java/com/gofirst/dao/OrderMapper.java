package com.gofirst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gofirst.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
	
	Integer selectCountsByDemand(String orderId, Integer getpId);
}