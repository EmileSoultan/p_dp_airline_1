package app.config;

import app.entities.Ticket;
import app.services.TicketService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
public class DataInitializer {
    private final TicketService ticketService;

    public DataInitializer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public DataInitializer(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Transactional
    @PostConstruct
    public void init() {
        System.out.println("DataInitializer сработал!");
        initDbWithTicket();
    }

    public void initDbWithTicket() {
        Ticket ticket1 = new Ticket(1L, "TL-1111", "Олег", "Боинг-747", "1F");
        ticketService.saveTicket(ticket1);

        Ticket ticket2 = new Ticket(2L, "SD-2222", "Иван", "Боинг-747", "9A");
        ticketService.saveTicket(ticket2);

        Ticket ticket3 = new Ticket(3L, "ZX-3333", "Андрей", "Боинг-747", "6D");
        ticketService.saveTicket(ticket3);
    }
}
