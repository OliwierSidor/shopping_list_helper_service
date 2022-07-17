package pl.sda.arppl4.shopping_list_helper_service.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.PurchaseDTO;

import javax.persistence.*;
import java.time.LocalDateTime;






@Data
@Entity
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

    public PurchaseDTO mapToDTO() {
        return new PurchaseDTO(id, amount, product.getId());
    }

    public Purchase(Double amount, Product product) {
        this.amount = amount;
        this.product = product;
    }
}
