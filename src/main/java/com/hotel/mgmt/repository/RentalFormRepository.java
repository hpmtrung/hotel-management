package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.Customer;
import com.hotel.mgmt.domain.checkin.RentalForm;
import com.hotel.mgmt.domain.checkin.ReservationForm;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalFormRepository extends PagingAndSortingRepository<RentalForm, Long> {
    Optional<RentalForm> findByCheckInForm(ReservationForm reservationForm);

    Page<RentalForm> findByCustomer(Customer customer, Pageable pageable);
}
