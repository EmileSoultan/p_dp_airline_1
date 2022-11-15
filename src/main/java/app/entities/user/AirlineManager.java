package app.entities.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class AirlineManager extends User {
}
