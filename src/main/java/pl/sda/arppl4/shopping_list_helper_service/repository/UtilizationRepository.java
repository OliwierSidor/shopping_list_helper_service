package pl.sda.arppl4.shopping_list_helper_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.arppl4.shopping_list_helper_service.model.Utilization;

@Repository
public interface UtilizationRepository extends JpaRepository<Utilization, Long> {
}
