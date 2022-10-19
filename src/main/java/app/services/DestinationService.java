package app.services;

import app.entities.Destination;

import java.util.List;

public interface DestinationService {
    List<Destination> findDestinationByName(String cityName, String countryName);

    void saveDestination(Destination destination);

    void updateDestination(Destination destination);

    void deleteDestinationById(Long id);
}
