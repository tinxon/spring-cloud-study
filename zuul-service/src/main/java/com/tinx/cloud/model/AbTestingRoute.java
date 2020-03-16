package com.tinx.cloud.model;

import lombok.Data;

@Data
public class AbTestingRoute {

    private String serviceName;

    private String active;

    private String endpoint;

    private Integer weight;
}
