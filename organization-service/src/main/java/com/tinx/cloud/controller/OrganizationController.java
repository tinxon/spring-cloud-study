package com.tinx.cloud.controller;

import com.tinx.cloud.model.Organization;
import com.tinx.cloud.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable String orgId) {
        return organizationService.getOrg(orgId);
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable String orgId, @RequestBody Organization organization) {
        organizationService.update(organization);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization organization) {
        organizationService.save(organization);
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.DELETE)
    public void deleteOrganization(@PathVariable String orgId, @RequestBody Organization organization) {
        organizationService.delete(organization);
    }
}
