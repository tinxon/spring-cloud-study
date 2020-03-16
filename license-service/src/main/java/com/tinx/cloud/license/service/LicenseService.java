package com.tinx.cloud.license.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tinx.cloud.license.client.OrganizationDiscoveryClient;
import com.tinx.cloud.license.client.OrganizationFeignClient;
import com.tinx.cloud.license.client.OrganizationRestTemplateClient;
import com.tinx.cloud.license.config.AmazonProperties;
import com.tinx.cloud.license.model.License;
import com.tinx.cloud.license.model.Organization;
import com.tinx.cloud.license.repository.LicenseRepository;
import com.tinx.cloud.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LicenseService {

    private static final Logger LOG = LoggerFactory.getLogger(LicenseService.class);

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private AmazonProperties prop;

    @Autowired
    private OrganizationDiscoveryClient discoveryClient;

    @Autowired
    private OrganizationRestTemplateClient restTemplateClient;

    @Autowired
    private OrganizationFeignClient feignClient;

    @HystrixCommand(
        commandProperties = {
            @HystrixProperty(
                name = "execution.isolation.thread.timeoutInMilliseconds",
                value = "3000"
            )
        },
        fallbackMethod = "buildFallbackLicense",
        threadPoolKey = "getLicenseByOrgIdThreadPool",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5"),
            @HystrixProperty(name = "maxQueueSize", value = "30")
        }
    )
    public License getLicenseByOrgId(String licenseId, String organizationId) {
        LOG.info("LicenseService Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setComment(prop.getAssociateId());
        return license;
    }

    private License buildFallbackLicense(String licenseId, String organizationId) {
        License license = new License();
        license.setLicenseId("0");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        return license;
    }

    public List<License> getLicenseByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String id) {
        return licenseRepository.findById(id).get();
    }

    private void randomlyRunLong() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        if (randomNum == 3) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void update(License license) {
        licenseRepository.save(license);
    }

    public void delete(String licenseId) {
        License license = getLicense(licenseId);
        licenseRepository.delete(license);
    }

    private Organization retrieveOrgInf(String organizationId, String clientType) {
        Organization organization = null;
        switch (clientType) {
            case "discovery":
                System.out.println("I'm using the discovery client");
                organization = discoveryClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I'm using the rest template client");
                organization = restTemplateClient.getOrganization(organizationId);
                break;
            case "feign":
                System.out.println("I'm using the feign client");
                organization = feignClient.getOrganization(organizationId);
        }
        return organization;
    }

    @HystrixCommand(
        commandProperties = {
            @HystrixProperty(
                name = "execution.isolation.thread.timeoutInMilliseconds",
                value = "5000"
            )
        }
    )
    public License getLicense(String licenseId, String organizationId, String clientType) {
        LOG.info("LicenseService getLicense() Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
        LOG.info("LicenseService getLicense() thread: {}",
                Thread.currentThread().getName());
        Organization organization = retrieveOrgInf(organizationId, clientType);
        License license = licenseRepository.
                findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (organization != null) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }
        return license;
    }
}
