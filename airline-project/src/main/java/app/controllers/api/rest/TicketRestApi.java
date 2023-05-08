package app.controllers.api.rest;

import app.dto.TicketDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Ticket REST")
@Tag(name = "Ticket REST", description = "API для операций с билетами")
@RequestMapping("/api/tickets")
public interface TicketRestApi {

    @GetMapping
    @ApiOperation(value = "Get list of all Tickets")
    ResponseEntity<List<TicketDTO>> getAll(Pageable pageable);

    @ApiOperation(value = "Get Ticket by ticketNumber")
    @ApiResponse(code = 200, message = "Found the ticket")
    @GetMapping("/{ticketNumber}")
    ResponseEntity<TicketDTO> getByNumber(
            @ApiParam(
                    name = "ticketNumber",
                    value = "ticketNumber",
                    example = "SD-2222"
            )
            @PathVariable("ticketNumber") String ticketNumber);

    @ApiOperation(value = "Create new Ticket")
    @ApiResponse(code = 201, message = "Ticket created")
    @PostMapping
    ResponseEntity<TicketDTO> create(
            @ApiParam(
                    name = "ticket",
                    value = "Ticket model"
            )
            @RequestBody @Valid TicketDTO ticketDTO);

    @ApiOperation(value = "Edit Ticket by \"id\"")
    @ApiResponse(code = 200, message = "Ticket has been updated")
    @PatchMapping("/{id}")
    ResponseEntity<?> update(
            @ApiParam(
                    name = "id",
                    value = "Ticket.id"
            ) @PathVariable Long id,
            @ApiParam(
                    name = "ticket",
                    value = "Ticket model"
            )
            @RequestBody @Valid TicketDTO ticketDTO);

    @ApiOperation(value = "Delete Ticket by \"id\"")
    @ApiResponse(code = 200, message = "Ticket has been removed")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(
            @ApiParam(
                    name = "id",
                    value = "Ticket.id"
            )
            @PathVariable Long id);
}