package app.controllers;

import app.entities.Route;
import app.services.interfaces.RouteService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
@Slf4j
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService service) {
        this.routeService = service;
    }

    @PostMapping
    @ApiOperation(value = "Add new route")
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        log.info("addRoute : new Route with id={} added", route.getId());
        return new ResponseEntity<>(routeService.saveRoute(route), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get all routes")
    public ResponseEntity<List<Route>> getAllRoutes() {
        log.info("getAllRoutes : all routes returned");
        return new ResponseEntity<>(routeService.getAllRoutes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get route by \"Id\"")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        if (routeService.getRouteById(id) == null) {
            log.error("getRouteById: route with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getRouteById : returned route with id={}", id);
        return new ResponseEntity<>(routeService.getRouteById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Edit route by \"Id\"")
    public ResponseEntity<Route> editRoute(@PathVariable("id") Long id,
                                           @RequestBody @Valid Route route) {

        if (routeService.getRouteById(id) == null) {
            log.error("editRoute: route with id={} doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        route.setId(id);
        log.info("editRoute: the route with id={} has been edited.", id);
        return new ResponseEntity<>(routeService.saveRoute(route), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete route by \"Id\"")
    public ResponseEntity<HttpStatus> deleteRoute(@PathVariable("id") Long id) {
        try {
            routeService.deleteRouteById(id);
            log.info("deleteRoute: the route with id={} has been deleted.", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("deleteRoute: error of deleting - route with id={} not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
