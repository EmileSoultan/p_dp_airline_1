package app.controllers;

import app.entities.Category;
import app.enums.CategoryType;
import app.services.interfaces.CategoryService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Category REST")
@Tag(name = "Category REST", description = "API операция с классом перелета(эконом, бизнесс и т.д.)")
@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    @ApiOperation(value = "Get list of all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "categories found"),
            @ApiResponse(code = 404, message = "categories not found")
    })
    public ResponseEntity<List<Category>> getAllCategories() {

        List<Category> categories = categoryService.findAll();

        if (categories != null) {
            log.info("getAllCategories: find all categories");
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            log.info("getAllCategories: list categories is null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{category_type}")
    @ApiOperation(value = "Get category by CategoryType")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "category found"),
            @ApiResponse(code = 404, message = "category not found")
    })
    public ResponseEntity<Category> getCategoryByCategoryType(
            @ApiParam(
                    name = "category_type",
                    value = "CategoryType model"
            )
            @PathVariable("category_type") CategoryType categoryType) {

        Category category = categoryService.findByCategoryType(categoryType);
        if (category != null) {
            log.info("getCategoryByCategoryType: find category with category type = {}. id = {} ", categoryType, category.getId());
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            log.info("getCategoryByCategoryType: not find category with category type = {}", categoryType);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
