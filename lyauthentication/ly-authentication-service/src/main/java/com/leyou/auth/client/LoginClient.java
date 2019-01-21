package com.leyou.auth.client;

import com.leyou.order.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @date 2019/1/18-13:15
 */
@FeignClient("user-service")
public interface LoginClient extends UserApi {
}
