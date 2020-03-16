package com.tinx.cloud.license.controller;

import com.tinx.cloud.license.model.License;
import com.tinx.cloud.license.service.LicenseService;
import com.tinx.cloud.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/organizations/{organizationId}/license")
public class LicenseController {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseController.class);

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicenseByOrg(organizationId);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(HttpServletRequest request, @PathVariable String organizationId, @PathVariable String licenseId) {
        LOG.info("LicenseController header: {}", request.getHeader("tmx-correlation-id"));
        LOG.info("LicenseController Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicenseByOrgId(licenseId, organizationId);
    }

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getLicense(@PathVariable String organizationId,
                              @PathVariable String licenseId, @PathVariable String clientType) {
        LOG.info("LicenseController getLicense() thread: {}",
                Thread.currentThread().getName());
        return licenseService.getLicense(licenseId, organizationId, clientType);
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
