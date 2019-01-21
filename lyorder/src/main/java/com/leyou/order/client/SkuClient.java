package com.leyou.order.client;

import com.leyou.api.SkuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/21-10:53
 */
@FeignClient("item-service")
public interface SkuClient extends SkuApi {
}
