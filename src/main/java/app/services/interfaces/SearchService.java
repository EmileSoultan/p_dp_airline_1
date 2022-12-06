package app.services.interfaces;

import app.entities.search.Search;

public interface SearchService {

    Long saveSearch(Search search);
    Search findSearchById(long id);

}
