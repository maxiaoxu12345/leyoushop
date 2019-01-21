package com.leyou.client;

import com.leyou.api.SkuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/11-17:31
 */
@FeignClient("item-service")
public interface SkuClient extends SkuApi {
}
