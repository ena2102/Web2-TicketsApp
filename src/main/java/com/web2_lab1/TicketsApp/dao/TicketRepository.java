package com.web2_lab1.TicketsApp.dao;

import com.web2_lab1.TicketsApp.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    int countByVatin(String vatin);
    Ticket findByUuid(UUID uuid);
}