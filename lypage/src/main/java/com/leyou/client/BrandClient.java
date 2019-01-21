package com.leyou.client;

import com.leyou.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/11-15:28
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi{
}
