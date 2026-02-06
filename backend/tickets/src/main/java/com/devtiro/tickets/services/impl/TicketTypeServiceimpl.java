package com.devtiro.tickets.services.impl;

import com.devtiro.tickets.domain.entities.Ticket;
import com.devtiro.tickets.domain.entities.TicketStatusEnum;
import com.devtiro.tickets.domain.entities.TicketType;
import com.devtiro.tickets.domain.entities.User;
import com.devtiro.tickets.exceptions.TicketSoldOutException;
import com.devtiro.tickets.exceptions.TicketNotFoundException;
import com.devtiro.tickets.exceptions.UserNotFoundException;
import com.devtiro.tickets.repositories.TicketRepository;
import com.devtiro.tickets.repositories.TicketTypeRepository;
import com.devtiro.tickets.repositories.UserRepository;
import com.devtiro.tickets.services.QrCodeService;
import com.devtiro.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceimpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository  ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;


    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(String.format("User not found with id: " , userId))
        );

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(
                        () -> new TicketNotFoundException(
                                String.format("Ticket type not found with id %s", ticketTypeId)
                        )
                );


        int purchaseTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchaseTickets+1 >totalAvailable) {
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);


        return ticketRepository.save(savedTicket);
    }


}
