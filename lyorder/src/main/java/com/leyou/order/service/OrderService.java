package com.leyou.order.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.common.utils.IdWorker;
import com.leyou.order.client.SkuClient;
import com.leyou.order.interceptor.UserInfoInterceptor;
import com.leyou.order.mapper.OrderDetailMapper;
import com.leyou.order.mapper.OrderMapper;
import com.leyou.order.mapper.OrderStatusMapper;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderDetail;
import com.leyou.order.pojo.OrderStatus;

import com.leyou.order.utils.WXPayUtil;
import com.leyou.order.vo.Cart;
import com.leyou.pojo.Sku;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2019/1/21-9:42
 */
@Service
public class OrderService {
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderDetailMapper orderDetailMapper;
	@Autowired
	OrderStatusMapper orderStatusMapper;
@Autowired
	IdWorker idWorker;
@Autowired
	SkuClient skuClient;
	@Autowired
	WXPayUtil wxPayUtil;
@Transactional
	public Long creatOrder(Order order) {
		//1.1雪花算法生成id
		long id = idWorker.nextId();
	UserInfo userInfo = UserInfoInterceptor.getUserInfo();
	order.setUserId(userInfo.getId());
	order.setBuyerNick(userInfo.getUsername());
	order.setOrderId(id);
	OrderStatus status=new OrderStatus();
	status.setOrderId(id);
	status.setStatus(1);
orderStatusMapper.insertSelective(status);
	orderMapper.insertSelective(order);

	return order.getOrderId();
	}

	public String getPayURL(Long id) {
		Order order = orderMapper.selectByPrimaryKey(id);
		Long totalPay = order.getTotalPay();
		String desc="正在付款，请注意财产安全";
		String url = wxPayUtil.createOrder(id, totalPay, desc);
		System.out.println(url);
		return url;
	}

	public void notify2(Map<String, String> result) {
		String ids = result.get("out_trade_no");
		Long id = Long.valueOf(ids);
		OrderStatus status=new OrderStatus();
		status.setOrderId(id);
		status.setStatus(2);
		orderStatusMapper.updateByPrimaryKeySelective(status);

	}

	public Integer getState(Long id) {

	return orderStatusMapper.selectByPrimaryKey(id).getStatus();
	}
}
