package pl.sda.arppl4.shopping_list_helper_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.AddUtilizationRequest;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.UtilizationDTO;
import pl.sda.arppl4.shopping_list_helper_service.service.UtilizationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/utilization")
@RequiredArgsConstructor
public class UtilizationController {
    private final UtilizationService utilizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUtilization(@RequestBody AddUtilizationRequest addUtilizationRequest) {
        log.info("Wywołano metodę addUtilization");
        utilizationService.add(addUtilizationRequest);
    }

    @PostMapping("/addutilizations")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUtilizations(@RequestBody List<AddUtilizationRequest> list) {
        log.info("Wywołano metodę addUtilizations");
        utilizationService.addList(list);
    }

    @GetMapping
    public List<UtilizationDTO> utilizationList() {
        log.info("Wywołano metodę utilizationList");
        return utilizationService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtilization(@PathVariable(name = "id") Long id) {
        log.info("Wywołano metodę deleteUtilization");
        utilizationService.delete(id);
    }

}
