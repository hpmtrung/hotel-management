package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.Customer;
import com.hotel.mgmt.domain.checkin.ReservationForm;
import com.hotel.mgmt.domain.checkin.ReservationFormStatus;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInFormRepository extends PagingAndSortingRepository<ReservationForm, Long> {
    Page<ReservationForm> findByCustomer(Customer customer, Pageable pageable);

    Page<ReservationForm> findByStatusAndCreatedAtAfter(ReservationFormStatus status, Instant dateTimeToCreate, Pageable pageable);
}
