package com.tinx.cloud.specialroutes.controller;

import com.tinx.cloud.specialroutes.model.AbTestingRoute;
import com.tinx.cloud.specialroutes.repository.AbTestingRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/route/")
public class AbTestingRouteController {

    @Autowired
    private AbTestingRouteRepository abTestingRouteRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbTestingRouteController.class);

    @RequestMapping(value = "abtesting/{serviceName}", method = RequestMethod.GET)
    public AbTestingRoute abtesting(@PathVariable("serviceName") String serviceName) {
        LOGGER.info("service name is {}.", serviceName);
        AbTestingRoute route = abTestingRouteRepository.findByServiceName(serviceName);
        LOGGER.info("result is: {}.", route);
        return route;
    }
}
