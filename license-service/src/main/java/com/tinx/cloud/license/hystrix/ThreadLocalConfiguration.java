package com.tinx.cloud.license.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ThreadLocalConfiguration {

    @Autowired(required = false)
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    @PostConstruct
    public void init() {
        HystrixPlugins plugins = HystrixPlugins.getInstance();
        HystrixEventNotifier eventNotifier = plugins.getEventNotifier();
        HystrixMetricsPublisher publisher = plugins.getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = plugins.getPropertiesStrategy();
        HystrixCommandExecutionHook hook = plugins.getCommandExecutionHook();
        HystrixPlugins.reset();
        plugins.registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
        plugins.registerEventNotifier(eventNotifier);
        plugins.registerMetricsPublisher(publisher);
        plugins.registerPropertiesStrategy(propertiesStrategy);
        plugins.registerCommandExecutionHook(hook);
    }
}
