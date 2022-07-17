package pl.sda.arppl4.shopping_list_helper_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilizationDTO {
    private Long utilizationId;
    private Double amount;
    private Long productId;
}
