package app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "aircrafts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"aircraftNumber", "model", "modelYear", "flightRange"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aircraft")
    @SequenceGenerator(name = "seq_aircraft", initialValue = 1000, allocationSize = 1)
    private Long id;

    @NotBlank(message = "field \"aircraftNumber\" should not be empty!")
    @Column(name = "aircraft_number")
    @Size(min = 4, max = 15, message = "Length of Aircraft Number should be between 4 and 15 characters")
    private String aircraftNumber;

    @NotBlank(message = "field \"model\" should not be empty!")
    private String model;

    @NotNull(message = "field \"modelYear\" should not be empty!")
    @Min(value = 2000, message = "modelYear should be later than 2000")
    @Column(name = "model_year")
    private int modelYear;

    @NotNull(message = "field \"flightRange\" should not be empty!")
    @Column(name = "flight_range")
    private int flightRange;

    @OneToMany(mappedBy = "aircraft", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Seat> seatSet = new HashSet<>();

    @PreRemove
    public void removeAircraft() {
        seatSet.forEach(seat -> seat.setAircraft(null));
    }

}