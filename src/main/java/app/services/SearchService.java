package app.services;

import app.entities.search.Search;

public interface SearchService {

    void saveSearch(Search search);
    Search findSearchById(long id);

}
