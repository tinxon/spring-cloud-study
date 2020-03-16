package com.tinx.cloud.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORG_ID = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        String relationId = ctx.getRequest().getHeader(CORRELATION_ID);
        if (relationId != null) {
            return relationId;
        }
        return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
    }

    public void setCorrelationId(String correlationId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public String getOrgId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String orgId = ctx.getRequest().getHeader(ORG_ID);
        if (orgId != null) {
            return orgId;
        }
        return ctx.getZuulRequestHeaders().get(ORG_ID);
    }

    public void setOrgId(String orgId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(ORG_ID, orgId);
    }

    public String getUserId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String userId = ctx.getRequest().getHeader(USER_ID);
        if (userId != null) {
            return userId;
        }
        return ctx.getZuulRequestHeaders().get(USER_ID);
    }

    public void setUserId(String userId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(USER_ID, userId);
    }

    public String getAuthToken() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(AUTH_TOKEN);
    }

    public String getServiceId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.get("serviceId") == null) {
            return "";
        }
        return ctx.get("serviceId").toString();
    }
}
