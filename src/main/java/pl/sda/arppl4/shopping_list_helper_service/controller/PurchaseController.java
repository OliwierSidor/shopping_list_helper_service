package pl.sda.arppl4.shopping_list_helper_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddPurchaseRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.PurchaseDTO;
import pl.sda.arppl4.shopping_list_helper_service.service.PurchaseService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPurchase(@RequestBody AddPurchaseRequest addPurchaseRequest) {
        log.info("Wywołano metodę addPurchase");
        purchaseService.addPurchase(addPurchaseRequest);
    }

    @PostMapping("/addpurchases")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPurchases(@RequestBody List<AddPurchaseRequest> requests){
        log.info("Wywołano metodę addPurchases");
        purchaseService.addPurchases(requests);
    }

    @GetMapping
    public List<PurchaseDTO> listPurchase() {
        log.info("Wywołano metodę listPurchase");
        return purchaseService.listAllPurchase();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchase(@PathVariable(name = "id") Long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
    }
}
