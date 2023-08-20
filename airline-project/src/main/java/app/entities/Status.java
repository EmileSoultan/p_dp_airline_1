package app.entities;


import app.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Статус бронированя.
 */
    @Entity
    @Table(name = "status")
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    public class Status {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_status")
        @SequenceGenerator(name = "seq_status", initialValue = 1000, allocationSize = 1)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id;

        @Column(name = "booking_status")
        @Enumerated(EnumType.STRING)
        private BookingStatus bookingStatus;
}
