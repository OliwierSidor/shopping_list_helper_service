package pl.sda.arppl4.shopping_list_helper_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddProductRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.ProductDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    private String name;
    @CreationTimestamp
    private LocalDateTime dateAdded;
    @UpdateTimestamp
    private LocalDateTime dateModified;
    private String unit;

    @ManyToOne
    private Category category;



    public ProductDTO mapToDTO() {
        return new ProductDTO(id, name, dateAdded, dateModified, unit, category);
    }

    public Product(AddProductRequest addProductRequest,Category category) {
        this.name = addProductRequest.getName();
        this.unit = addProductRequest.getUnit();
        this.category = category;
    }
}
