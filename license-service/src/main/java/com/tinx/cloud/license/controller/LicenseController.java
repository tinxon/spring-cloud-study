package com.tinx.cloud.license.controller;

import com.tinx.cloud.license.model.License;
import com.tinx.cloud.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations/{organizationId}/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicenseByOrg(organizationId);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable String organizationId, @PathVariable String licenseId) {
        return licenseService.getLicense(licenseId, organizationId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void save(@RequestBody License license) {
        licenseService.save(license);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void update(@RequestBody License license) {
        licenseService.update(license);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String licenseId) {
        licenseService.delete(licenseId);
    }
}
