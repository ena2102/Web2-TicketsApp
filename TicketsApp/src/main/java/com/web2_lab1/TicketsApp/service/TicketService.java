package com.web2_lab1.TicketsApp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.web2_lab1.TicketsApp.dao.TicketRepository;
import com.web2_lab1.TicketsApp.domain.Ticket;
import com.web2_lab1.TicketsApp.domain.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(TicketDTO ticketDTO) {
        if (ticketRepository.countByVatin(ticketDTO.getVatin()) >= 3) {
            throw new IllegalStateException("Ne smijete generirati više od 3 ulaznice!");
        }

        Ticket ticket = new Ticket();
        ticket.setVatin(ticketDTO.getVatin());
        ticket.setFirstName(ticketDTO.getFirstName());
        ticket.setLastName(ticketDTO.getLastName());
        ticket.setCreatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);
        return ticket;
    }

    public int countTickets() {
        return (int) ticketRepository.count();
    }


    public Ticket getTicketByUuid (UUID uuid) {
        Ticket ticket = ticketRepository.findByUuid(uuid);
        return ticket;
    }
}
