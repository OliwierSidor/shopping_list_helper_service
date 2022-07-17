package pl.sda.arppl4.shopping_list_helper_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddCategoriesRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.CategoryDTO;
import pl.sda.arppl4.shopping_list_helper_service.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(@RequestBody AddCategoriesRequest addCategoriesRequest) {
        log.info("Wywołano metodę addCategory");
        categoryService.addCategory(addCategoriesRequest);
    }

    @PostMapping("/addcategories")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategories(@RequestBody List<AddCategoriesRequest> listCategories){
        log.info("Wywołano metodę addCategories");
        categoryService.addCategories(listCategories);
    }

    @GetMapping
    public List<CategoryDTO> categoryList(){
        log.info("Wywołano metodę categoryList");
        return categoryService.categoryList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable (name = "id") Long categoryId){
        categoryService.deleteCategory(categoryId);
    }
}
