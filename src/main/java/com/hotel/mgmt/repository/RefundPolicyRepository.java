package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.room.RefundPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundPolicyRepository extends JpaRepository<RefundPolicy, Integer> {}
