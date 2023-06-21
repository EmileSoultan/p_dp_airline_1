package app.util.mappers;

import app.dto.TimezoneDTO;
import app.entities.Timezone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimezoneMapper {

    public Timezone convertToTimezoneEntity(TimezoneDTO timezoneDTO) {
        Timezone timezone = new Timezone();

        timezone.setId(timezoneDTO.getId());
        timezone.setCountryName(timezoneDTO.getCountryName());
        timezone.setCityName(timezoneDTO.getCityName());
        timezone.setGmt(timezoneDTO.getGmt());
        timezone.setGmtWinter(timezoneDTO.getGmtWinter());

        return timezone;
    }

}
