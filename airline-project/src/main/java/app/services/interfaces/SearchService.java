package app.services.interfaces;

import app.entities.search.Search;
import app.entities.search.SearchResult;
import app.repositories.SearchResultProjection;

public interface SearchService {

    SearchResult saveSearch(Search search);
    Search findSearchById(long id);
    void saveSearchResult(SearchResult searchResult);
    SearchResultProjection findSearchResultByID(Long id);
}
