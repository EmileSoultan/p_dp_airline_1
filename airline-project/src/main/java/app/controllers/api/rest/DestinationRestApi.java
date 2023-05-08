package app.controllers.api.rest;

import app.dto.DestinationDTO;
import app.entities.Destination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Destination REST")
@Tag(name = "Destination REST", description = "API для операций с пунктами назначения (прилет/вылет)")
@RequestMapping("/api/destinations")
public interface DestinationRestApi {

    @GetMapping
    @ApiOperation(value = "Get list of all Destinations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Destinations found"),
            @ApiResponse(code = 404, message = "Destinations not found")
    })
    ResponseEntity<List<DestinationDTO>> getAll(@PageableDefault(sort = {"id"}) Pageable p);

    @ApiOperation(value = "Gets list of Destinations by cityName or countryName")
    @ApiResponse(code = 200, message = "Found the Destinations")
    @GetMapping("/filter")
    ResponseEntity<List<DestinationDTO>> getAllByCityNameOrCountryName(
            @ApiParam(
                    name = "cityName",
                    value = "cityName",
                    example = "Волгоград"
            )
            @RequestParam(value = "cityName", required = false) String cityName,
            @ApiParam(
                    name = "countryName",
                    value = "countryName"
            )
            @RequestParam(value = "countryName", required = false) String countryName);

    @ApiOperation(value = "Create new Destination")
    @ApiResponse(code = 201, message = "Destination created")
    @PostMapping
    ResponseEntity<DestinationDTO> create(
            @ApiParam(
                    name = "Destination",
                    value = "Destination"
            )
            @RequestBody DestinationDTO destinationDTO);

    @ApiOperation(value = "Edit Destination by id")
    @ApiResponse(code = 200, message = "Destination has been updated")
    @PatchMapping("/{id}")
    ResponseEntity<Destination> update(
            @ApiParam(
                    name = "id",
                    value = "Destination.id"
            ) @PathVariable Long id,
            @ApiParam(
                    name = "Destination",
                    value = "Destination"
            )
            @RequestBody DestinationDTO destinationDTO);

    @ApiOperation(value = "Delete Destination by \"id\"")
    @ApiResponse(code = 200, message = "Destination has been removed")
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable @ApiParam("id") Long id);
}