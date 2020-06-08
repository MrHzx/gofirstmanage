package com.gofirst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gofirst.model.ThiredApiPlantInfo;
import com.gofirst.service.TransApiService;

@RestController
public class TransApiController {

	@Autowired
	private TransApiService transApiService;

	@PostMapping("/insert/getFileStorePath")
	@Scheduled(initialDelay = 10000, fixedRate = 15000)
	public String getFileStorePath() {
		ThiredApiPlantInfo thiredApiPlantInfo = new ThiredApiPlantInfo();
		transApiService.getPlanInfo(thiredApiPlantInfo);
		return "1";

	}

}
