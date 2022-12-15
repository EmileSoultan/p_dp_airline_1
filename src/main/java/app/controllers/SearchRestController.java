package app.controllers;

import app.entities.search.Search;
import app.entities.search.SearchResult;
import app.services.interfaces.SearchResultService;
import app.services.interfaces.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/search")
@Slf4j
public class SearchRestController {
    private final SearchService searchService;
    private final SearchResultService searchResultService;

    @Autowired
    public SearchRestController(SearchService searchService,
                                SearchResultService searchResultService) {
        this.searchService = searchService;
        this.searchResultService =searchResultService;
    }

    @PostMapping
    @ApiOperation(value = "Create new search")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "returned search result id 4 other methods"),
            @ApiResponse(code = 400, message = "search return error")
    })
    public ResponseEntity<Long> saveSearch(@RequestBody @Valid Search search) {

        Long result_id = searchService.saveSearch(search);
        log.info("saveSearch: new search result saved with id= {}", result_id);
        return new ResponseEntity<Long>(result_id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get search result by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search result found"),
            @ApiResponse(code = 404, message = "search result not found")
    })
    public ResponseEntity<SearchResult> getSearchResultById(@PathVariable("id") long id) {

        SearchResult searchResult = searchResultService.findById(id);

        if (searchResult != null) {
            log.info("getSearchResultById: find search result with id = {}", id);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } else {
            log.info("getSearchResultById: not find search result with id = {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}