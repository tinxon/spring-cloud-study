package com.tinx.cloud.orgservice.controller;

import com.tinx.cloud.orgservice.model.Organization;
import com.tinx.cloud.orgservice.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        LOGGER.info("Looking up data for org {}", organizationId);
        Organization organization = organizationService.getOrg(organizationId);
        LOGGER.info("The result is: {}", organization.getContactName());
        return organization;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization organization) {
        organizationService.saveOrg(organization);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteOrganization(@RequestBody Organization organization) {
        organizationService.deleteOrg(organization);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateOrganization(@RequestBody Organization organization) {
        organizationService.updateOrg(organization);
    }
}
