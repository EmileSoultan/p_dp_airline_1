package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "seat")
@Data
@NoArgsConstructor
public class Seat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 5, message = "Seat number must be between 2 and 5 characters")
    @NotBlank(message = "Field seat number cannot be null")
    @Column(name = "seat_number")
    private String seatNumber;

    @NotNull(message = "Field isNearEmergencyExit cannot be null")
    @Column(name = "is_near_emergency_exit")
    private Boolean isNearEmergencyExit;


    //TODO: add fields
//    @Column(name = "category")
//    private Category category;

//    @Column(name = "flight")
//    private Flight flight;

//    @NotNull(message = "Field aircraft cannot be null")
    @Column(name = "aircraft")
    @ManyToMany(mappedBy = "seatList")
    @JsonIgnore
    private List<Aircraft> aircraftList;

    @PositiveOrZero(message = "Fare must be positive")
    @Column(name = "fare")
    private Integer fare;

    @Column(name = "is_registered")
    private Boolean isRegistered;

    @Column(name = "is_sold")
    private Boolean isSold;

    public Seat(String seatNumber, Boolean isNearEmergencyExit, Integer fare, Boolean isRegistered, Boolean isSold) {
        this.seatNumber = seatNumber;
        this.isNearEmergencyExit = isNearEmergencyExit;
        this.fare = fare;
        this.isRegistered = isRegistered;
        this.isSold = isSold;
    }
}
