package com.gofirst.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gofirst.entity.Order;
import com.gofirst.model.CommonResult;
import com.gofirst.model.ListModel;
import com.gofirst.model.Parms;
import com.gofirst.service.OrderService;
import com.gofirst.utils.BaseController;

@RestController
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/search/{id}")
	public CommonResult<Order> searchId(@PathVariable("id")Integer id) {
		CommonResult<Order> commonResult = new CommonResult<>();
		Order order = orderService.findOrderById(id);
		commonResult.success(order);
		return commonResult;
	}
	
	@GetMapping("/search/allCount")
	public int searchAllCount(){
		return orderService.count(null);
	}
	
	@GetMapping("/search/all")
	public ListModel searchAll(){
		List<Order> list = orderService.list(null);
		ListModel listModel = new ListModel();
		listModel.setList(list);
		listModel.setId(1l);
		return listModel;
				
	}
	
	@PostMapping("/search/allAndLike/{pageNum}/{pageSize}")
	public CommonResult<Map<String, Object>> searchAllAndLike(@RequestBody(required = false)Parms parms,@PathVariable("pageNum")Integer pageNum,@PathVariable("pageSize")Integer pageSize) {
		System.out.println("order"+parms);
		System.out.println("pageNum"+pageNum);
		System.out.println("pageSize"+pageSize);
		CommonResult<Map<String, Object>> commonResult = new CommonResult<>();
		Map<String, Object> map = orderService.findOrderAllAndLike(parms, pageNum, pageSize);
		commonResult.success(map);
		return commonResult;
	}
	
}