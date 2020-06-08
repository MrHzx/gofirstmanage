package com.gofirst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gofirst.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gofirst.dao.OrderMapper;
import com.gofirst.entity.Order;
import com.gofirst.model.Parms;

@Transactional
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public Order findOrderById(Integer id) {
		Order order = orderMapper.selectById(id);
		return order;
	}

	@Override
	public Map<String, Object> findOrderAllAndLike(Parms parms, Integer pageNum, Integer pageSize) {
		Page<Order> page = new Page<Order>(pageNum, pageSize);
		System.out.println(page);
		QueryWrapper<Order> wrapper = new QueryWrapper<>();
		String orderId = parms.getOrderId();
		String buyerUname = parms.getBuyerUname();
		String detail = parms.getDetail();
		   if(!StringUtils.isEmpty(detail)) {
	            //构建条件
	            wrapper.like("detail",detail);
	        }
	        if(!StringUtils.isEmpty(orderId)) {
	            wrapper.eq("order_id",orderId);
	        }
	        if(!StringUtils.isEmpty(buyerUname)) {
	            wrapper.eq("buyer_uname",buyerUname);
	        }
		orderMapper.selectPage(page, wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageCount", page.getPages());
		map.put("list", page.getRecords());
		return map;
	}
	
	

}