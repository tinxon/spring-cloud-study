package com.tinx.cloud.filters;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

public class CustomRouteLocator extends SimpleRouteLocator {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    public Route getMatchingRoute(final String path) {
        logger.info("The path is: {}", path);
        Route route = new Route("orgservice-new",
                "/v1/organizations/1",
                "orgservice-new",
                "/api/orgservice-new",
                false, null);
        logger.info("CustomRouteLocator route is: {}", route);
        return route;
    }
}
