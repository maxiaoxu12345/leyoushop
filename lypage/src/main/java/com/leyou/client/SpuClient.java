package com.leyou.client;

import com.leyou.api.SpuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/11-21:08
 */
@FeignClient("item-service")
public interface SpuClient extends SpuApi{
}
