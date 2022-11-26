package app.services.interfaces;

import app.entities.Route;

import java.util.List;

public interface RouteService {

    Route saveRoute(Route route);
    List<Route> getAllRoutes();
    Route getRouteById(Long id);
    Route updateRoute(Route route);
    void deleteRouteById(Long id);
}
