package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.service.Service;
import com.hotel.mgmt.domain.service.ServiceType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findByServiceType(ServiceType type);
}
