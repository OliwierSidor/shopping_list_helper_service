package pl.sda.arppl4.shopping_list_helper_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sda.arppl4.shopping_list_helper_service.exception.BadRequestException;
import pl.sda.arppl4.shopping_list_helper_service.exception.NotFoundException;
import pl.sda.arppl4.shopping_list_helper_service.model.Product;
import pl.sda.arppl4.shopping_list_helper_service.model.ProductAmountState;
import pl.sda.arppl4.shopping_list_helper_service.model.Utilization;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddUtilizationRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.UtilizationDTO;
import pl.sda.arppl4.shopping_list_helper_service.repository.ProductAmountStateRepository;
import pl.sda.arppl4.shopping_list_helper_service.repository.ProductRepository;
import pl.sda.arppl4.shopping_list_helper_service.repository.UtilizationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UtilizationService {
    private final UtilizationRepository utilizationRepository;
    private final ProductRepository productRepository;
    private final ProductAmountStateRepository productAmountStateRepository;

    public void add(AddUtilizationRequest addUtilizationRequest) {
        Long productId = addUtilizationRequest.getProductId();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (addUtilizationRequest.getAmount() < 0) {
                throw new BadRequestException("Ilość nie może być ujemna");
            }
            if (canUtilize(productAmountStateRepository.findAll(), addUtilizationRequest.getAmount())) {
                Utilization newUtilization = utilizationRepository.save(new Utilization(addUtilizationRequest.getAmount(), product));
                productAmountStateRepository.save(new ProductAmountState(addUtilizationRequest.getAmount(), null, newUtilization, product));
            }
        } else {
            throw new NotFoundException("Nie ma takiego produktu");
        }
    }

    private boolean canUtilize(List<ProductAmountState> listAll, Double amount) {
        Double amountState = listAll.stream().mapToDouble(productAmountState -> {
            Double productsAmount = productAmountState.getAmountState();
            if (productAmountState.getUtilization() != null) {
                return productsAmount * -1;
            } else {
                return productsAmount;
            }

        }).sum();
        return (amountState - amount) > 0;
    }

    public void addList(List<AddUtilizationRequest> list) {
        list.forEach(this::add);
    }

    public List<UtilizationDTO> getAll() {
        List<Utilization> utilizationList = utilizationRepository.findAll();
        return utilizationList.stream().map(Utilization::mapToDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional<Utilization> optionalPurchase = utilizationRepository.findById(id);
        if (optionalPurchase.isPresent()) {
            Utilization utilization = optionalPurchase.get();
            utilizationRepository.delete(utilization);
            log.info("usunięto: " + utilization);
        } else {
            throw new NotFoundException("Nie znaleziono utylizacji do usunięcia");
        }
    }
}
