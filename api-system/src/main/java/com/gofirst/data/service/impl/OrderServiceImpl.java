package com.gofirst.data.service.impl;

import com.gofirst.data.entity.Order;
import com.gofirst.data.mapper.OrderMapper;
import com.gofirst.data.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gofirst
 * @since 2020-06-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
