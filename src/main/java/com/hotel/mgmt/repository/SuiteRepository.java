package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.room.Suite;
import com.hotel.mgmt.domain.room.SuiteStyle;
import com.hotel.mgmt.domain.room.SuiteType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuiteRepository extends JpaRepository<Suite, Integer> {
    List<Suite> findBySuiteType(SuiteType suiteType);

    List<Suite> findBySuiteStyle(SuiteStyle suiteStyle);

    List<Suite> findByPriceIsLessThanEqual(long price);

    Optional<Suite> findBySuiteTypeAndSuiteStyle(SuiteType suiteType, SuiteStyle suiteStyle);
}
