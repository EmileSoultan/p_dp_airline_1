package app.controllers.api.rest;

import app.dto.account.PassengerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Passenger REST")
@Tag(name = "Passenger REST", description = "API для операций с пассажирами")
@RequestMapping("/api/passengers")
public interface PassengerRestApi {

    @ApiOperation(value = "Get list of all Passengers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping
    ResponseEntity<Page<PassengerDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    );

    @ApiOperation(value = "Get Passenger by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<PassengerDTO> getById(
            @ApiParam(
                    name = "id",
                    value = "User.id",
                    required = true
            )
            @PathVariable Long id);

    @ApiOperation(value = "Find Passenger by Email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/email/{email}")
    ResponseEntity<PassengerDTO> getByEmail(
            @ApiParam(
                    name = "email",
                    example = "passenger@mail.ru",
                    required = true
            )
            @PathVariable String email);

    @ApiOperation(value = "Get list of Passenger by first name", notes = "Be careful with letter 'ё' ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping("/firstName/{passengerFirstName}")
    ResponseEntity<List<PassengerDTO>> getByFirstName(
            @ApiParam(
                    name = "passengerFirstName",
                    example = " \"Иван\", \"Пётр\"",
                    required = true
            )
            @PathVariable String passengerFirstName);

    @ApiOperation(value = "Get list of Passenger by last name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping("/lastName/{passengerLastName}")
    ResponseEntity<List<PassengerDTO>> getByLastName(
            @ApiParam(
                    name = "passengerLastName",
                    value = "Passenger last name",
                    example = "\"Иванов\" \"Петров\" \"Евдокимов\"",
                    required = true
            )
            @PathVariable String passengerLastName);

    @ApiOperation(value = "Get list of Passengers by any name (firstname, lastname, middlename")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passengers found"),
            @ApiResponse(code = 404, message = "Passengers not found")
    })
    @GetMapping("/anyName/{passengerAnyName}")
    ResponseEntity<List<PassengerDTO>> getByAnyName(
            @ApiParam(
                    name = "passengerAnyName",
                    value = "Passenger any name, custom format\n",
                    example = "\"Пётр\" \"Петрович Пётр\" \"Пётр Петров Петрович\"",
                    required = true
            )
            @PathVariable String passengerAnyName);

    @ApiOperation(value = "Get Passenger by passportSerialNumber")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passenger found"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @GetMapping("/passport/{serialNumber}")
    ResponseEntity<PassengerDTO> getByPassportSerialNumber(
            @ApiParam(
                    name = "passportSerialNumber",
                    value = "Passenger serial and number of passport\n",
                    example = "3333 333333",
                    required = true
            )
            @PathVariable String serialNumber);

    @ApiOperation(value = "Create new Passenger", notes = "Create method requires in model field \"@type\": \"Passenger\"")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Passenger created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping
    ResponseEntity<PassengerDTO> create(
            @ApiParam(
                    name = "Passenger",
                    value = "Passenger model"
            )
            @RequestBody @Valid PassengerDTO passengerDTO);

    @ApiOperation(value = "Edit Passenger", notes = "Update method requires in model field \"@type\": \"Passenger\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passenger updated"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @PutMapping(value = "/{id}")
    ResponseEntity<PassengerDTO> update(
            @ApiParam(
                    name = "id",
                    value = "User.id",
                    required = true
            )
            @PathVariable("id") Long id,
            @ApiParam(
                    name = "Passenger",
                    value = "Passenger model"
            )
            @RequestBody @Valid PassengerDTO passengerDTO);

    @ApiOperation(value = "Delete Passenger by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Passenger deleted"),
            @ApiResponse(code = 404, message = "Passenger not found")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<HttpStatus> delete(
            @ApiParam(
                    name = "id",
                    value = "User.id",
                    required = true
            )
            @PathVariable Long id);
}