package com.gofirst.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ThiredApiPlantInfo implements Serializable{
	private String total="http://localhost:8100/search/allCount";
	private String url="http://localhost:8100/search/all";
	
}
