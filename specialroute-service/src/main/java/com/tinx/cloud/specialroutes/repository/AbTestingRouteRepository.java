package com.tinx.cloud.specialroutes.repository;

import com.tinx.cloud.specialroutes.model.AbTestingRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbTestingRouteRepository extends CrudRepository<AbTestingRoute, String> {
    AbTestingRoute findByServiceName(String serviceName);
}
