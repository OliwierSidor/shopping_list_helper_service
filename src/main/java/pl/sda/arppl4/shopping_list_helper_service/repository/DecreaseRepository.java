package pl.sda.arppl4.shopping_list_helper_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.arppl4.shopping_list_helper_service.model.Utilization;

public interface DecreaseRepository extends JpaRepository<Utilization, Long> {

}
