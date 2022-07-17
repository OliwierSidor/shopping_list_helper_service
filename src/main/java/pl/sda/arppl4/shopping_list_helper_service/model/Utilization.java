package pl.sda.arppl4.shopping_list_helper_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.UtilizationDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor

public class Utilization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;

    @CreationTimestamp
    private LocalDateTime utilizationDate;

    @ManyToOne
    private Product product;

    @OneToOne
    private ProductAmountState productAmountState;

    public Utilization(Double amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

    public UtilizationDTO mapToDTO() {
        return new UtilizationDTO(id, amount, product.getId());
    }
}
