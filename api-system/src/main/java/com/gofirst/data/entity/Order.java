package com.gofirst.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gofirst
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
