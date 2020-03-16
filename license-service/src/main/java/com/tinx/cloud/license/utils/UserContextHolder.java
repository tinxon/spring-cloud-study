package com.tinx.cloud.license.utils;


import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> USER_CONTEXT = new ThreadLocal<>();

    public static UserContext getContext() {
        UserContext context = USER_CONTEXT.get();
        if (context == null) {
            context = createEmptyContext();
            USER_CONTEXT.set(context);
            return context;
        }
        return USER_CONTEXT.get();
    }

    public static void setUserContext(UserContext userContext) {
        Assert.notNull(userContext, "Only non-null UserContext instance are permitted");
        USER_CONTEXT.set(userContext);
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }
}
