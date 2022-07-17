package pl.sda.arppl4.shopping_list_helper_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.arppl4.shopping_list_helper_service.model.Product;
import pl.sda.arppl4.shopping_list_helper_service.model.Purchase;
import pl.sda.arppl4.shopping_list_helper_service.model.Utilization;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAmountStateDTO {
    private Long id;
    private Double amountState;
    private Purchase purchase;
    private Utilization utilization;
    private Product product;
}
