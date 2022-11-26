package app.services;

import app.entities.search.SearchResult;
import app.repositories.SearchResultRepository;
import app.services.interfaces.SearchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SearchResultServiceImpl implements SearchResultService {

    SearchResultRepository searchResultRepository;

    @Autowired
    public SearchResultServiceImpl(SearchResultRepository searchResultRepository) {
        this.searchResultRepository = searchResultRepository;
    }

    @Override
    @Transactional
    public void saveSearchResult(SearchResult searchResult) {
        searchResultRepository.save(searchResult);
    }

    @Override
    public SearchResult findById(long id) {
        return searchResultRepository.findById(id).orElse(null);
    }
}
