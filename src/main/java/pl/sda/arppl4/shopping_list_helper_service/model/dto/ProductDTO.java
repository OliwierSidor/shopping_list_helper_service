package pl.sda.arppl4.shopping_list_helper_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.arppl4.shopping_list_helper_service.model.Category;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private LocalDateTime dateAdded;
    private LocalDateTime dateModified;
    private String unit;
    private Category category;
}
