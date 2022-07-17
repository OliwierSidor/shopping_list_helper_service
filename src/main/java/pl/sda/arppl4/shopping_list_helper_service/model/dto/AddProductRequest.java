package pl.sda.arppl4.shopping_list_helper_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private String name;
    private String unit; //TODO: ograniczenie na rodzaje jednostek
    private Long categoryId;
}
