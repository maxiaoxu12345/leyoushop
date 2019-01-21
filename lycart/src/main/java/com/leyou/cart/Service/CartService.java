package com.leyou.cart.Service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.interceptor.UserInfoInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @date 2019/1/20-4:24
 */
@Service
public class CartService {
	@Autowired
	private StringRedisTemplate template;
	public static final String CART_PREFIX ="cart:uid:";
	private  List<Cart> carts=new ArrayList<>();
	public void addCart(Cart cart) {
		UserInfo userInfo = UserInfoInterceptor.getUserInfo();
		String hashKey=cart.getSkuId().toString();
		BoundHashOperations<String, Object, Object> hashOps =
				template.boundHashOps(CART_PREFIX +userInfo
						.getId().toString());
		if (!hashOps.hasKey(hashKey)){
			hashOps.put(hashKey, JsonUtils.toString(cart));
		}else {

			String cacheCart  = hashOps.get(hashKey).toString();
			Cart bean = JsonUtils.toBean(cacheCart, Cart.class);
			bean.setNum(cart.getNum()+bean.getNum());
			hashOps.put(hashKey, JsonUtils.toString(bean));
		}
	}

	public List<Cart> getCart() {
		carts.clear();
		UserInfo userInfo = UserInfoInterceptor.getUserInfo();
		BoundHashOperations<String, Object, Object> hashOps =
				template.boundHashOps(CART_PREFIX +userInfo
						.getId().toString());
		List<Object> list = hashOps.values();
		if (CollectionUtils.isEmpty(list)){
			throw new LyException(ExceptionEnum.CARTS_IS_NULL);
		}
		for (Object o : list) {

			Cart cart = JsonUtils.toBean((String) o, Cart.class);
			carts.add(cart);
		}
		return carts;
	}

	public void putCart(Long skuId, Integer num) {
		UserInfo userInfo = UserInfoInterceptor.getUserInfo();
		BoundHashOperations<String, Object, Object> hashOps =
				template.boundHashOps(CART_PREFIX +userInfo
						.getId().toString());
		Object o = hashOps.get(skuId.toString());
		Cart cart = JsonUtils.toBean((String) o, Cart.class);
		cart.setNum(num);
		String s = JsonUtils.toString(cart);
		hashOps.put(skuId.toString(), s);
	}

	public void deleteCart(Long skuId) {
		UserInfo userInfo = UserInfoInterceptor.getUserInfo();
		BoundHashOperations<String, Object, Object> hashOps =
				template.boundHashOps(CART_PREFIX +userInfo
						.getId().toString());
		if(hashOps.hasKey(skuId.toString())){
		hashOps.delete(skuId.toString());
	}
	}
}
