package app.services;

import app.entities.search.SearchResult;


public interface SearchResultService {

    void saveSearchResult(SearchResult searchResult);
    SearchResult findById(long id);
}
