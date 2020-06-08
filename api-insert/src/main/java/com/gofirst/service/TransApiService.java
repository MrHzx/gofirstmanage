package com.gofirst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gofirst.entity.Order;
import com.gofirst.model.ThiredApiPlantInfo;

public interface TransApiService extends IService<Order>{

	void getPlanInfo(ThiredApiPlantInfo thiredApiPlantInfo);
	
	
}
