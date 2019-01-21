package com.leyou.order.utils;

import com.github.wxpay.sdk.WXPay;
import com.leyou.order.config.PayConfig;
import com.leyou.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @date 2019/1/21-16:43
 */
@Component
public class WXPayUtil {
	@Autowired
	private WXPay wxPay;
	@Autowired
	PayConfig payConfig;

	private static final Logger logger = LoggerFactory.getLogger(WXPayUtil.class);


	public String createOrder(Long orderId,Long totalPay,String desc) {

		try {
			Map<String, String> data = new HashMap<>();
			// 商品描述
			data.put("body", "淘东测试");
			// 订单号
			data.put("out_trade_no", orderId.toString());
			//金额，单位是分
			data.put("total_fee", "1");
			//调用微信支付的终端IP（estore商城的IP）
			data.put("spbill_create_ip", "127.0.0.1");
			//回调地址
			data.put("notify_url", payConfig.getNotifyUrl());
			// 交易类型为扫码支付
			data.put("trade_type", "NATIVE");
			Map<String, String> result = this.wxPay.unifiedOrder(data);
			String url = result.get("code_url");
			return url;
		} catch (Exception e) {
			logger.error("创建预交易订单异常", e);
			return null;
		}
	}
}