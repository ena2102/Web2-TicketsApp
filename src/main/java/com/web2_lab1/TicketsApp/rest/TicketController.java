package com.web2_lab1.TicketsApp.rest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.web2_lab1.TicketsApp.domain.TicketDTO;
import com.web2_lab1.TicketsApp.service.TicketService;
import com.web2_lab1.TicketsApp.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public String home(Model model) {
        int ticketCount = ticketService.countTickets();
        model.addAttribute("ticketCount", ticketCount);
        return "index";
    }


    @PostMapping(value = "/generate_QR_Code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateTicketWithQRCode(@RequestBody TicketDTO ticketDTO) {
        try {

            Ticket ticket = ticketService.createTicket(ticketDTO);

            String qrContent = "https://web2-ticketsapp-1.onrender.com/ticket/" + ticket.getUuid();

            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            return ResponseEntity.ok(qrImage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/ticket/{uuid}")
    public String getTicketByUuid(Model model, @PathVariable UUID uuid, @AuthenticationPrincipal OidcUser principal) {

        Ticket ticket = ticketService.getTicketByUuid(uuid);
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("ticket", ticket);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedCreatedAt = ticket.getCreatedAt().format(formatter);
        model.addAttribute("formattedCreatedAt", formattedCreatedAt);
        return "ticketData";
    }
}
