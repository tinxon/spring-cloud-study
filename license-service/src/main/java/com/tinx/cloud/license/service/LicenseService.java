package com.tinx.cloud.license.service;

import com.tinx.cloud.license.config.AmazonProperties;
import com.tinx.cloud.license.model.License;
import com.tinx.cloud.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private AmazonProperties prop;

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(prop.getAssociateId());
    }

    public List<License> getLicenseByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String id) {
        return licenseRepository.findById(id).get();
    }

    public void save(License license) {
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void update(License license) {
        licenseRepository.save(license);
    }

    public void delete(String licenseId) {
        License license = getLicense(licenseId);
        licenseRepository.delete(license);
    }
}
