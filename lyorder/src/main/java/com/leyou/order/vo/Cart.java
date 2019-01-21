package com.leyou.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2019/1/21-9:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	private Long skuId;
	private Integer num;
}
