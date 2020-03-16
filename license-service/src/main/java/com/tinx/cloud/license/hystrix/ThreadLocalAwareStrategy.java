package com.tinx.cloud.license.hystrix;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.tinx.cloud.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalAwareStrategy.class);

    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingConcurrencyStrategy) {
        this.existingConcurrencyStrategy = existingConcurrencyStrategy;
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return existingConcurrencyStrategy != null ?
                existingConcurrencyStrategy.getBlockingQueue(maxQueueSize) :
                super.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(
            HystrixRequestVariableLifecycle<T> rv) {
        return existingConcurrencyStrategy != null ?
                existingConcurrencyStrategy.getRequestVariable(rv) :
                super.getRequestVariable(rv);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
              HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize,
              HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
              BlockingQueue<Runnable> workQueue) {
        return existingConcurrencyStrategy != null ?
                existingConcurrencyStrategy.getThreadPool(
                    threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) :
                super.getThreadPool(
                    threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        LOGGER.info("ThreadLocalAwareStrategy thread: " + Thread.currentThread().getName());
        DelegatingUserContextCallable delegate =
                new DelegatingUserContextCallable(callable,
                        UserContextHolder.getContext());
        return existingConcurrencyStrategy != null ?
                existingConcurrencyStrategy.wrapCallable(delegate) :
                super.wrapCallable(delegate);
    }
}
