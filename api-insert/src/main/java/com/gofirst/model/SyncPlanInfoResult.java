package com.gofirst.model;

import java.util.Date;

import lombok.Data;

@Data
public class SyncPlanInfoResult {

	    private Integer id;

	    private String orderId;

	    private Integer pId;

	    private String buyerUname;

	    private Integer sellerId;

	    private String detail;

	    private String img;

	    private Double lastPrice;

	    private Date purchaseTime;

	    private Date countDown;

	    private String address;

	    private String mobile;

	    private String consignee;

	    private Integer orderStatus;
	    

	   
	
}
