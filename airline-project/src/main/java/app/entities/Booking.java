package app.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Бронирование сидений на рейс, формируется перед оплатой.
 */
@Entity
@Table(name="booking")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"bookingNumber", "passenger"})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_booking")
    @SequenceGenerator(name = "seq_booking", initialValue = 1000, allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "booking_number", unique = true, nullable = false)
    private String bookingNumber;

    @Column(name = "booking_data_time")
    private LocalDateTime bookingDate;

    @OneToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

//    @ManyToOne
//    @JoinColumn(name = "flight_id")
//    private Flight flight;

    @OneToOne
    @JoinColumn(name = "flight_seat_id")
    private FlightSeat flightSeat;      // ??

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @Column(name = "is_createTime")
//    LocalDateTime createTime;   // ??
//
//    @Column(name = "is_status")
//    @Enumerated(EnumType.STRING)
//    private BookingStatus status;       // ??
}