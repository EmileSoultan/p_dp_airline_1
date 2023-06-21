package app.services.interfaces;

import app.entities.Timezone;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface TimezoneService {

    Timezone saveTimezone(Timezone timezone);

    Timezone update(Timezone timezone);

    Page<Timezone> findAll(int page, int size);

    Optional<Timezone> getTimezoneById(Long id);

    void deleteById(Long id);

}