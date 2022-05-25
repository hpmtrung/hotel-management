package com.hotel.mgmt.repository;

import com.hotel.mgmt.domain.checkout.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {}
