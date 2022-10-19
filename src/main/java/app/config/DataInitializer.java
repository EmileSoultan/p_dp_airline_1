package app.config;

import app.entities.Destination;
import app.enums.Airport;
import app.services.DestinationService;
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
    DestinationService destinationService;

    public DataInitializer(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Transactional
    @PostConstruct
    public void init() {
        System.out.println("DataInitializer сработал!");
        initDbWithDestination();
    }

    public void initDbWithDestination() {
        Destination destination1 = new Destination(1L, Airport.VKO, "Внуково", "Москва", "GMT +3", "Россия");
        destinationService.saveDestination(destination1);

        Destination destination2 = new Destination(2L, Airport.VOG, "Гумрак", "Волгоград", "GMT +3", "Россия");
        destinationService.saveDestination(destination2);

        Destination destination3 = new Destination(3L, Airport.MQF, "Магнитогорск", "Магнитогорск", "GMT +5", "Россия");
        destinationService.saveDestination(destination3);

        Destination destination4 = new Destination(4L, Airport.OMS, "Омск", "Омск", "GMT +6", "Россия");
        destinationService.saveDestination(destination4);

        destinationService.deleteDestinationById(3L);

        destinationService.updateDestination(new Destination(4L, Airport.GDX, "Сокол", "Магадан", "GMT +11", "Россия"));

        System.out.println(destinationService.findDestinationByName("волг", ""));
    }
}