package com.tinx.cloud.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//@Component
public class TokenFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String INVALID_TOKEN = "The token is null.";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getHeader("authorization");
        logger.info("the request is {}", request);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            logger.info("{} = {}", header, request.getHeader(header));
        }
        String method = request.getMethod();
        String requestURL = request.getRequestURL().toString();
        logger.info("token = {}", token);
        logger.info("the request url is {}", requestURL);
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("the parameterMap is {}", parameterMap);
        if (parameterMap == null) {
            return null;
        }
        Map<String, List<String>> requestQueryParams = currentContext.getRequestQueryParams();
        logger.info("the requestQueryParams is {}", requestQueryParams);
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>(parameterMap.size() * 2);
        }
        for (String key : parameterMap.keySet()) {
            String[] values = parameterMap.get(key);
            List<String> valueList = new LinkedList<>();
            for (String value : values) {
                valueList.add(value);
            }
            requestQueryParams.put(key, valueList);
        }
        List<String> valueList = new LinkedList<>();
        valueList.add(token);
        requestQueryParams.put("Authorization", valueList);
        currentContext.setRequestQueryParams(requestQueryParams);
        if (StringUtils.isEmpty(token)) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody(INVALID_TOKEN);
            currentContext.setResponseStatusCode(401);
            return null;
        }
        return null;
    }
}
