package com.leyou.client;

import com.leyou.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/11-15:04
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
