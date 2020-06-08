package com.gofirst.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gofirst.entity.Order;
import com.gofirst.model.Parms;

public interface OrderService extends IService<Order>{
	/**
	 * 根据id查询
	 * @param id
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	Order findOrderById(Integer id);
	
	/**
	 * 根据字段模糊查询
	 * @param order
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */

	Map<String, Object> findOrderAllAndLike(Parms parms, Integer pageNum, Integer pageSize);
	
}