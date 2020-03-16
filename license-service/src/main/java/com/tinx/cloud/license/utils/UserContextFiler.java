package com.tinx.cloud.license.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "userContextFilter")
public class UserContextFiler implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextFiler.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserContext context = UserContextHolder.getContext();
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        context.setCorrelationId(request.getHeader(UserContext.CORRELATION_ID));
        context.setAuthToken(request.getHeader(UserContext.AUTH_TOKEN));
        context.setUserId(request.getHeader(UserContext.USER_ID));
        context.setOrgId(request.getHeader(UserContext.ORG_ID));
        LOG.info("UserContextFilter 1 correlation id: {}", context.getCorrelationId());
        filterChain.doFilter(servletRequest, servletResponse);
        LOG.info("UserContextFilter 2 correlation id: {}", context.getCorrelationId());
    }
}
