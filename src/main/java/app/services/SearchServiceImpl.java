package app.services;

import app.entities.search.Search;
import app.entities.search.SearchResult;
import app.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final SearchResultService searchResultService;
    private final FlightService flightService;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository,
                             SearchResultService searchResultService,
                             FlightService flightService) {
        this.searchRepository = searchRepository;
        this.searchResultService = searchResultService;
        this.flightService = flightService;
    }

    @Override
    @Transactional
    public void saveSearch(Search search) {
        searchRepository.save(search);
        searchNonstopFlight(search);
    }

    @Override
    public Search findSearchById(long id) {
        return searchRepository.findById(id).orElse(null);
    }

    private void searchNonstopFlight(Search search) {
        SearchResult searchResult = new SearchResult();
        searchResult.setSearch(search);
        searchResult.setDepartFlight(
                flightService.getFlightsByDestinationsAndDepartureDate(
                        search.getFrom(),
                        search.getTo(),
                        search.getDepartureDate()
                )
        );
        if (search.getReturnDate() == null) {
            searchResult.setReturnFlight(null);
        } else {
            searchResult.setReturnFlight(
                    flightService.getFlightsByDestinationsAndDepartureDate(
                            search.getTo(),
                            search.getFrom(),
                            search.getReturnDate()
                    )
            );
        }
        searchResultService.saveSearchResult(searchResult);
    }
}
