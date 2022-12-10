package app.entities;

import app.entities.user.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"bookingNumber", "passenger"})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_booking")
    @SequenceGenerator(name = "seq_booking", initialValue = 1000, allocationSize = 1)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "booking_number", unique = true, nullable = false)
    @Size(min = 9, max = 9, message = "Length of Booking Number should be 9 characters")
    private String bookingNumber;

    @NotNull
    @Column(name = "booking_data_time")
    private LocalDateTime bookingData;

    @NotNull
    @OneToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @NotNull
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
