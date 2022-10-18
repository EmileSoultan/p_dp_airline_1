package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bookingNumber", nullable = false, unique = true)
    @NotNull
    private String bookingNumber;

    @Column(name = "passenger", nullable = false)
    // @OneToOne(fetch = FetchType.LAZY) - Entity "Passenger" missing, will add later
    private String passenger;

    @Column(name = "flight", nullable = false)
    // @OneToOne(fetch = FetchType.LAZY) - Entity "Flight" missing, will add later
    private String flight;

    @Column(name = "seat", nullable = false)
    // @OneToOne(fetch = FetchType.LAZY) - Entity "Seat" missing, will add later
    private String seat;
}
