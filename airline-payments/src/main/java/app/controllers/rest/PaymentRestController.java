package app.controllers.rest;

import app.dto.PaymentDto;
import app.enums.State;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Api(tags = "Payment REST")
@Tag(name = "Payment REST", description = "API для операций с оплатой (Payment)")
@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentRestController {

    @ApiOperation(value = "Create Payment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment added")
    })
    @PostMapping
    public ResponseEntity<PaymentDto> addPayment(@RequestBody @Valid PaymentDto paymentDto) {
        try{
            paymentDto.setPaymentState(State.CREATED);
            log.info("addPayment: post request");
        } catch (NoSuchElementException e) {
            log.error("addPayment: paymentDto.setPaymentState doesn't save anything");
        }
        return ResponseEntity.ok(paymentDto);
    }
}
