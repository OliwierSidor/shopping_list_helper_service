package pl.sda.arppl4.shopping_list_helper_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.sda.arppl4.shopping_list_helper_service.exception.NotFoundException;
import pl.sda.arppl4.shopping_list_helper_service.model.Category;
import pl.sda.arppl4.shopping_list_helper_service.model.Product;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddProductRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.ProductDTO;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.UpdateProductRequest;
import pl.sda.arppl4.shopping_list_helper_service.repository.CategoryRepository;
import pl.sda.arppl4.shopping_list_helper_service.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> productsList() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(Product::mapToDTO).collect(Collectors.toList());
    }

    public void addProduct(AddProductRequest request) {
        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategoryId());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Product newProduct = new Product(request, category);
            productRepository.save(newProduct);
        } else {
            throw new NotFoundException("Nie znaleziono kategorii");
        }

    }

    public void addProducts(List<AddProductRequest> productsList) {
        productsList.stream().forEach(this::addProduct);
    }

    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productRepository.delete(product);
        } //TODO: nie usuwaj kiedy masz purchase i utilization
    }

    public void updateProduct(UpdateProductRequest updateProductRequest
    ) {
        Long id = updateProductRequest.getId();
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product productToUpdate = optionalProduct.get();
            if (updateProductRequest.getName() == null && updateProductRequest.getUnit() == null) {
                throw new NotFoundException("Nie podałeś żadnego pola do edycji");
            } else if (updateProductRequest.getName() != null) {
                productToUpdate.setName(updateProductRequest.getName());
            } else if (updateProductRequest.getUnit() != null) {
                productToUpdate.setUnit(updateProductRequest.getUnit());
            }
            productRepository.save(productToUpdate);
            log.info("Produkt został zaktualizowany");
        } else {
            throw new NotFoundException("Nie znaleziono produktu o id: " + id);
        }
    }
}
