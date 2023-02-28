package app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"seatNumber", "aircraft"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_seat")
    @SequenceGenerator(name = "seq_seat", initialValue = 1000, allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank(message = "Field seat number cannot be null")
    @Size(min = 2, max = 5, message = "Seat number must be between 2 and 5 characters")
    @Column(name = "seat_number")
    private String seatNumber;

    @NotNull(message = "Field isNearEmergencyExit cannot be null")
    @Column(name = "is_near_emergency_exit")
    private Boolean isNearEmergencyExit;

    @NotNull(message = "Field isLockedBack cannot be null")
    @Column(name = "is_locked_back")
    private Boolean isLockedBack;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    //@NotNull(message = "Field aircraft cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id")
    @JsonBackReference
    private Aircraft aircraft;

}
