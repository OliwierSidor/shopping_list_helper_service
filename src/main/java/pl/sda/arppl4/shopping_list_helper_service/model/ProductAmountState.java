package pl.sda.arppl4.shopping_list_helper_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ProductAmountState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amountState;

    @OneToOne
    private Purchase purchase;

    @OneToOne
    private Utilization utilization;

    @ManyToOne
    private Product product;

    public ProductAmountState(Double amountState, Purchase purchase, Utilization utilization, Product product) {
        this.amountState = amountState;
        this.purchase = purchase;
        this.utilization = utilization;
        this.product = product;
    }
}
