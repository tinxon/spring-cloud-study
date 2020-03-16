package com.tinx.cloud.service;

import com.tinx.cloud.model.Organization;
import com.tinx.cloud.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId) {
        return organizationRepository.findById(organizationId).get();
    }

    public void save(Organization organization) {
        organization.setId(UUID.randomUUID().toString());

        organizationRepository.save(organization);
    }

    public void update(Organization organization) {
        organizationRepository.save(organization);
    }

    public void delete(Organization organization) {
        organizationRepository.delete(organization);
    }
}
