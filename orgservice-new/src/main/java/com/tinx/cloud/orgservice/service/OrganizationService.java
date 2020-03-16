package com.tinx.cloud.orgservice.service;

import com.tinx.cloud.orgservice.model.Organization;
import com.tinx.cloud.orgservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {

    private OrganizationRepository orgRepository;

    @Autowired
    public OrganizationService(OrganizationRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public Organization getOrg(String organizationId) {
        Optional<Organization> organizationOptional = orgRepository.findById(organizationId);
        if (organizationOptional.isPresent()) {
            Organization organization = organizationOptional.get();
            organization.setContactName("NEW::" + organization.getContactName());
            return organization;
        }
        return null;
    }

    public void saveOrg(Organization organization) {
        String organizationId = UUID.randomUUID().toString();
        organization.setId(organizationId);
        orgRepository.save(organization);
    }

    public void updateOrg(Organization organization) {
        orgRepository.save(organization);
    }

    public void deleteOrg(Organization organization) {
        orgRepository.delete(organization);
    }
}
