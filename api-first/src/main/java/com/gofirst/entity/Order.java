package com.gofirst.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("`order`")
public class Order implements Serializable {
	@TableId(value = "id", type = IdType.ID_WORKER)
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

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return p_id
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * @param pId
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }

    /**
     * @return buyer_uname
     */
    public String getBuyerUname() {
        return buyerUname;
    }

    /**
     * @param buyerUname
     */
    public void setBuyerUname(String buyerUname) {
        this.buyerUname = buyerUname;
    }

    /**
     * @return seller_id
     */
    public Integer getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return last_price
     */
    public Double getLastPrice() {
        return lastPrice;
    }

    /**
     * @param lastPrice
     */
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    /**
     * @return purchase_time
     */
    public Date getPurchaseTime() {
        return purchaseTime;
    }

    /**
     * @param purchaseTime
     */
    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    /**
     * @return count_down
     */
    public Date getCountDown() {
        return countDown;
    }

    /**
     * @param countDown
     */
    public void setCountDown(Date countDown) {
        this.countDown = countDown;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * @param consignee
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * @return order_status
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderId=" + orderId + ", pId=" + pId + ", buyerUname=" + buyerUname
				+ ", sellerId=" + sellerId + ", detail=" + detail + ", img=" + img + ", lastPrice=" + lastPrice
				+ ", purchaseTime=" + purchaseTime + ", countDown=" + countDown + ", address=" + address + ", mobile="
				+ mobile + ", consignee=" + consignee + ", orderStatus=" + orderStatus + "]";
	}
    
}