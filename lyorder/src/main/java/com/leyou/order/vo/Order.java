package com.leyou.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @date 2019/1/21-9:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@NotNull
	private Long addressId;
	@NotNull
	private Integer paymentType;
	@NotNull
	private List<Cart> carts;
}
