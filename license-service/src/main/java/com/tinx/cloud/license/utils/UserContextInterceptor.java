package com.tinx.cloud.license.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        UserContext context = UserContextHolder.getContext();
        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, context.getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, context.getAuthToken());
        LOG.info("UserContextInterceptor correlation id: {}", context.getCorrelationId());
        return execution.execute(request, body);
    }
}
