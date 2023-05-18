package app.controllers.rest;

import app.controllers.api.rest.CategoryRestApi;
import app.entities.Category;
import app.enums.CategoryType;
import app.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryRestController implements CategoryRestApi {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.findAll();
        if (categories != null) {
            log.info("getAll: find all Categories");
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            log.info("getAll: Categories not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiIgnore
    @Override
    @Deprecated
    public ResponseEntity<Category> getByCategoryType(CategoryType categoryType) {
        Category category = categoryService.findByCategoryType(categoryType);
        if (category != null) {
            log.info("getByCategoryType: get by Category type = {}. id = {} ", categoryType, category.getId());
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            log.info("getByCategoryType: not found by Category type = {}", categoryType);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}