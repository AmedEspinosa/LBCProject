package com.kenzie.appserver;


import com.kenzie.appserver.service.ArtworkService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //took this code as an example from unit 4 project, not sure what we have to do here yet -Laurie
//        ReservedTicketService reservedTicketService = event.getApplicationContext()
//                .getBean(ReservedTicketService.class);
//        ConcurrentLinkedQueue queue = event.getApplicationContext().getBean(ConcurrentLinkedQueue.class);
//        List<ReservedTicket> reservedTicketList = reservedTicketService.findAllUnclosedReservationTickets();
//        queue.addAll(reservedTicketList);
    }
}
