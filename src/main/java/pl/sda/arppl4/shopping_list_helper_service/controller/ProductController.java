package pl.sda.arppl4.shopping_list_helper_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddProductRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.ProductDTO;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.UpdateProductRequest;
import pl.sda.arppl4.shopping_list_helper_service.service.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody AddProductRequest product) {
        log.info("Wywołano metodę addProduct");
        productService.addProduct(product);
    }

    @PostMapping("/addproducts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProducts(@RequestBody List<AddProductRequest> productsList){
        log.info("Wywołano metodę addProducts");
        productService.addProducts(productsList);
    }


    @GetMapping
    public List<ProductDTO> productsList() {
        log.info("Wywołano metodę productList");
        return productService.productsList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "id") Long productId) {
        log.info("Wywołano metodę deleteProduct");
        productService.deleteProduct(productId);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
        log.info("Wywołano metodę updateProduct");
        productService.updateProduct(updateProductRequest);
    }


}
