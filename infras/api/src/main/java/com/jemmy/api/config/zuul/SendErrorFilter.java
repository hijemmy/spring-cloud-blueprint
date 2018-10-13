package com.jemmy.api.config.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * 自定义错误格式
 */
@Component
public class SendErrorFilter extends ZuulFilter {

	protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String filterType() {
		return ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return SEND_ERROR_FILTER_ORDER-1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return ctx.getThrowable() != null
				&& !ctx.getBoolean(SEND_ERROR_FILTER_RAN, false);
	}

	@Override
	public Object run() {
		try {
			RequestContext context = RequestContext.getCurrentContext();
			ZuulException exception = findZuulException(context.getThrowable());
			MVCResultMsg<String> result=new MVCResultMsg<>();
			result.setCode(ResultCode.FAIL);
			result.setMessage(exception.errorCause);
			context.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			context.setResponseBody(objectMapper.writeValueAsString(result));
			context.setSendZuulResponse(false);
			return null;
		}
		catch (Exception ex) {
			ReflectionUtils.rethrowRuntimeException(ex);
		}
		return null;
	}

	ZuulException findZuulException(Throwable throwable) {
		if (throwable.getCause() instanceof ZuulRuntimeException) {
			// this was a failure initiated by one of the local filters
			return (ZuulException) throwable.getCause().getCause();
		}

		if (throwable.getCause() instanceof ZuulException) {
			// wrapped zuul exception
			return (ZuulException) throwable.getCause();
		}

		if (throwable instanceof ZuulException) {
			// exception thrown by zuul lifecycle
			return (ZuulException) throwable;
		}

		// fallback, should never get here
		return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
	}



}
