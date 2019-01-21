package com.leyou.client;

import com.leyou.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/11-15:32
 */
@FeignClient("item-service")
public interface SpecClient extends SpecApi{
}
