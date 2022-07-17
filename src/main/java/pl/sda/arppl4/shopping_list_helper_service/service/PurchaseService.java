package pl.sda.arppl4.shopping_list_helper_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sda.arppl4.shopping_list_helper_service.exception.BadRequestException;
import pl.sda.arppl4.shopping_list_helper_service.exception.NotFoundException;
import pl.sda.arppl4.shopping_list_helper_service.model.Product;
import pl.sda.arppl4.shopping_list_helper_service.model.ProductAmountState;
import pl.sda.arppl4.shopping_list_helper_service.model.Purchase;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddPurchaseRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.PurchaseDTO;
import pl.sda.arppl4.shopping_list_helper_service.repository.ProductAmountStateRepository;
import pl.sda.arppl4.shopping_list_helper_service.repository.ProductRepository;
import pl.sda.arppl4.shopping_list_helper_service.repository.PurchaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final ProductAmountStateRepository productAmountStateRepository;

    public void addPurchase(AddPurchaseRequest addPurchaseRequest) {
        Long productId = addPurchaseRequest.getProductId();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            validateAmounts(addPurchaseRequest, product);
            Purchase newPurchase = purchaseRepository.save(new Purchase(addPurchaseRequest.getAmount(), product));
            productAmountStateRepository.save(new ProductAmountState(addPurchaseRequest.getAmount(), newPurchase, null, product));
        } else {
            throw new NotFoundException("Nie ma takiego produktu");
        }
    }

    //TODO: pomyśleć nad enumem
    private void validateAmounts(AddPurchaseRequest addPurchaseRequest, Product product) {
        if (addPurchaseRequest.getAmount() < 0) {
            throw new BadRequestException("Ilość nie może być ujemna");
        }
        if (product.getUnit().equalsIgnoreCase("kg")) {
            if (addPurchaseRequest.getAmount() > 10) {
                throw new BadRequestException("Podałeś za duża ilość");
            }
        } else if (product.getUnit().equalsIgnoreCase("l")) {
            if (addPurchaseRequest.getAmount() > 10) {
                throw new BadRequestException("Podałeś za duża ilość");
            }
        } else if (product.getUnit().equalsIgnoreCase("szt")) {
            if (addPurchaseRequest.getAmount() > 1000) {
                throw new BadRequestException("Podałeś za duża ilość");
            }
        }
    }

    public List<PurchaseDTO> listAllPurchase() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        return purchaseList.stream().map(Purchase::mapToDTO).collect(Collectors.toList());
    }

    public void deletePurchase(Long purchaseId) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseId);
        if (optionalPurchase.isPresent()) {
            Purchase purchase = optionalPurchase.get();
            purchaseRepository.delete(purchase);
            log.info("usunięto: " + purchase);
        } else {
            throw new NotFoundException("Nie znaleziono zakupu do usunięcia");
        }
    }

    public void addPurchases(List<AddPurchaseRequest> requests) {
        requests.stream().forEach(this::addPurchase);
    }
}
