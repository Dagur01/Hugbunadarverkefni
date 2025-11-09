package is.hi.hbv501g.verkefni.controllers;

import is.hi.hbv501g.verkefni.persistence.entities.Discount;
import is.hi.hbv501g.verkefni.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService service;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> get(@PathVariable String code) {
        Discount d = service.getByCode(code);
        if (d == null) {
            return ResponseEntity.status(404).body(Map.of("error", "not found"));
        }
        return ResponseEntity.ok(d);
    }
}
