package com.leyou.filter;

import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.config.AllowsConfig;
import com.leyou.config.CookieConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @date 2018/12/25-9:10
 */
@Component
@EnableConfigurationProperties({CookieConfig.class, AllowsConfig.class})
public class LoginFilter extends ZuulFilter {

	@Autowired
	private CookieConfig jwtProp;

	@Autowired
	private AllowsConfig filterProp;

	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 5;
	}

	@Override
	public boolean shouldFilter() {
		// 获取上下文
		RequestContext ctx = RequestContext.getCurrentContext();
		// 获取request
		HttpServletRequest req = ctx.getRequest();
		// 获取路径
		String requestURI = req.getRequestURI();
		// 判断白名单
		return !isAllowPath(requestURI);
	}

	private boolean isAllowPath(String requestURI) {
		// 定义一个标记
		boolean flag = false;
		// 遍历允许访问的路径
		for (String path : this.filterProp.getAllowPaths()) {
			// 然后判断是否是符合
			if(requestURI.startsWith(path)){
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public Object run() throws ZuulException {
		// 获取上下文
		RequestContext ctx = RequestContext.getCurrentContext();
		// 获取request
		HttpServletRequest request = ctx.getRequest();
		// 获取token
		String token = CookieUtils.getCookieValue(request, jwtProp.getCookieName());
		// 校验
		try {
			// 校验通过什么都不做，即放行
			JwtUtils.getInfoFromToken(token, jwtProp.getPublicKey());
		} catch (Exception e) {
			// 校验出现异常，返回403
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(403);
			logger.error("非法访问，未登录，地址：{}", request.getRemoteHost(), e );
		}
		return null;
	}
}