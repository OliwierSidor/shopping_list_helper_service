package pl.sda.arppl4.shopping_list_helper_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sda.arppl4.shopping_list_helper_service.exception.BadRequestException;
import pl.sda.arppl4.shopping_list_helper_service.exception.AlreadyExistsException;
import pl.sda.arppl4.shopping_list_helper_service.model.Category;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddCategoriesRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.CategoryDTO;
import pl.sda.arppl4.shopping_list_helper_service.repository.CategoryRepository;
import pl.sda.arppl4.shopping_list_helper_service.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public void addCategory(AddCategoriesRequest addCategoriesRequest) {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            String subjectNoSpaces = category.getName().replace(" ", "");
            String receivedSubjectNoSpaces = addCategoriesRequest.getCategoryName().replace(" ", "");
            if (subjectNoSpaces.equalsIgnoreCase(receivedSubjectNoSpaces)) {
                throw new AlreadyExistsException("Nie mogę dodać kategorii bo już taka istnieje");
            }
        }
        Category newCategory = new Category(addCategoriesRequest.getCategoryName());
        categoryRepository.save(newCategory);

    }

    public void addCategories(List<AddCategoriesRequest> listCategories) {
        listCategories.forEach(this::addCategory);
    }

    public List<CategoryDTO> categoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(Category::mapToDTO).collect(Collectors.toList());
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            if (productRepository.findAllByCategoryId(categoryId).isEmpty()) {
                categoryRepository.delete(category);
                log.info("Usunięto kategorię: " + category);
            } else {
                log.info("Nie można usunąć " + category + " bo ma dodane produkty");
                throw new BadRequestException("Nie można usunąć kategorii w której są produkty");
            }
        } else {
            throw new EntityNotFoundException("Nie znaleziono kategorii o id: " + categoryId);
        }
    }
}
