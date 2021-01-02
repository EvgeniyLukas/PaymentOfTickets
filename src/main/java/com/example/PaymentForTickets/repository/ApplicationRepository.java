package com.example.PaymentForTickets.repository;

import com.example.PaymentForTickets.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    public Application findByRouteNumber(int routeNumber);
}
