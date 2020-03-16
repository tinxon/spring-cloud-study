package com.tinx.cloud.license.hystrix;

import com.tinx.cloud.license.utils.UserContext;
import com.tinx.cloud.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<V> implements Callable<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelegatingUserContextCallable.class);

    private final Callable<V> delegate;

    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate,
                                         UserContext originalUserContext) {
        this.delegate = delegate;
        this.originalUserContext = originalUserContext;
    }

    @Override
    public V call() throws Exception {
        LOGGER.info("DelegatingUserContextCallable thread: " + Thread.currentThread().getName());
        UserContextHolder.setUserContext(originalUserContext);
        try {
            return delegate.call();
        } finally {
            originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext originalUserContext) {
        return new DelegatingUserContextCallable<>(delegate, originalUserContext);
    }
}
