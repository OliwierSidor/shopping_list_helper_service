package pl.sda.arppl4.shopping_list_helper_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddCategoriesRequest {
    private String categoryName;
}
