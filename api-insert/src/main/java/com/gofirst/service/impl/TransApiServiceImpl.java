package com.gofirst.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gofirst.dao.OrderMapper;
import com.gofirst.entity.Order;
import com.gofirst.model.SyncPlanInfoResponse;
import com.gofirst.model.SyncPlanInfoResult;
import com.gofirst.model.ThiredApiPlantInfo;
import com.gofirst.service.TransApiService;
import com.gofirst.utils.HttpUtils;

import lombok.experimental.PackagePrivate;
import lombok.extern.slf4j.Slf4j;

/**
 * ThirdPlanInfo是本地接口入参，与第三方约定好的参数
 * SyncPlanInfoResponse是本地接收的实体类对象，与第三方返回数据对应。
 * SyncPlanInfoResult是response里面的数据结果集。 Order是数据库实体类
 *
 */

@Slf4j
@Service
@Transactional
public class TransApiServiceImpl extends ServiceImpl<OrderMapper, Order> implements TransApiService {

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void getPlanInfo(ThiredApiPlantInfo thiredApiPlantInfo) {
		log.info("计划的基础信息同步接口start..");
		String url = thiredApiPlantInfo.getTotal();
		// 获取总条数
		String total = HttpUtils.get(url, 30000);
		// 获取总条数
		int totals = 0;
		if (!StringUtils.isBlank(total)) {
			totals = Integer.parseInt(total);
		}
		int pageSize = 4;
		// 页数
		int pageCount = totals % pageSize == 0 ? totals / pageSize : totals / pageSize + 1;
		ExecutorService executor = Executors.newFixedThreadPool(10);
//	        for (int i = 0; i < pageCount; i++) {
		planInfoRun run = new planInfoRun(thiredApiPlantInfo, pageSize, 1);
		executor.execute(run);
//	        }

	}

	/**
	 * 计划线程处理
	 */
	class planInfoRun implements Runnable {
		private ThiredApiPlantInfo thiredApiPlantInfo;
		private int pageSize;
		private int i;

		private planInfoRun(ThiredApiPlantInfo thiredApiPlantInfo, int pageSize, int i) {
			this.thiredApiPlantInfo = thiredApiPlantInfo;
			this.i = i;
			this.pageSize = pageSize;
		}

		@Override
		public void run() {
			log.info("线程执行start...." + Thread.currentThread().getName());
			SyncPlanInfoResponse response = sendPost(thiredApiPlantInfo, pageSize, i);
			List<SyncPlanInfoResult> list = response.getList();
			if (list != null && list.size() > 0) {
				selectResult(list);
			}
		}
	}

	/**
	 * 计划基础信息发送请求
	 *
	 * @param thirdPlanInfoDTO
	 * @param pageNumber
	 * @return
	 */
	private SyncPlanInfoResponse sendPost(ThiredApiPlantInfo thiredApiPlantInfo, int pageSize, int pageNumber) {
		String url = thiredApiPlantInfo.getUrl();
		long timeMillis = System.currentTimeMillis();
		String result = HttpUtils.get(url, 30000);
		long endTime = System.currentTimeMillis();
		log.info("调用时间：" + (endTime - timeMillis) / 1000 + " ; 请求结果：" + result + " ; 当前页数" + pageNumber);
		SyncPlanInfoResponse syncPlanInfoResponse = null;
		try {
			log.info("开始转换数据");
			syncPlanInfoResponse = JSON.parseObject(result, new TypeReference<SyncPlanInfoResponse>() {
			});
			log.info("结束转换数据");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("预代料库信息数据序列化错误，页数" + pageNumber + "；" + "数据为：" + JSON.toJSON(result));
		}
		return syncPlanInfoResponse;
	}

	/**
	 * 查询、更新基础数据
	 *
	 * @param rows
	 * @return
	 */
	private void selectResult(List<SyncPlanInfoResult> rows) {
		final AtomicInteger a = new AtomicInteger();
		rows.forEach(x -> {
			String orderId = x.getOrderId();
			Integer pId = x.getPId();
			QueryWrapper<Order> wrapper = new QueryWrapper<>();
			wrapper.eq("order_id", orderId);
			wrapper.eq("p_id", pId);
			Integer selectCount = orderMapper.selectCount(wrapper);
			if (selectCount > 0) {
				QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("order_id", orderId);
				queryWrapper.eq("p_id", pId);
				orderMapper.update(copyParams(x), queryWrapper);
			} else {
				log.info("开始插入数据");
				insertResult(x);
				a.incrementAndGet();
				log.info("正在插入第" + a + "条数据");
			}
		});
	}

	/**
	 * 计划基础信息组装参数
	 *
	 * @param rows
	 */
	private void insertResult(SyncPlanInfoResult rows) {
		Order order = null;
		try {
			order = copyParams(rows);
			int insertCount = orderMapper.insert(order);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("计划基础信息数据插入失败" + JSON.toJSON(order));
		}
	}

	/**
	 * 计划的基础信息参数转换
	 *
	 * @param rows
	 * @return
	 */
	private Order copyParams(SyncPlanInfoResult rows) {
		Order order = new Order();
		order.setId(rows.getId());
		order.setpId(rows.getPId());
		order.setOrderId(rows.getOrderId());
		order.setBuyerUname(rows.getBuyerUname());
		order.setSellerId(rows.getSellerId());
		order.setDetail(rows.getDetail());
		order.setImg(rows.getImg());
		order.setLastPrice(rows.getLastPrice());
		order.setPurchaseTime(rows.getPurchaseTime());
		order.setCountDown(rows.getCountDown());
		order.setAddress(rows.getAddress());
		order.setMobile(rows.getMobile());
		order.setConsignee(rows.getConsignee());
		order.setOrderStatus(rows.getOrderStatus());
		return order;
	}

}
