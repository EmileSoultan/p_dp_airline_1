package app.dto;

import app.entities.Booking;
import app.enums.CategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BookingDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank
    @Size(min = 9, max = 9, message = "Length of Booking Number should be 9 characters")
    private String bookingNumber;

    @NotNull
    private LocalDateTime bookingDate;

    @NotNull
    private Long passengerId;

    @NotNull
    private Long flightId;

    @NotNull
    private CategoryType categoryType;

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.bookingNumber = booking.getBookingNumber();
        this.bookingDate = booking.getBookingDate();
        this.passengerId = booking.getPassenger().getId();
        this.flightId = booking.getFlight().getId();
        this.categoryType = booking.getCategory().getCategoryType();
    }
}
