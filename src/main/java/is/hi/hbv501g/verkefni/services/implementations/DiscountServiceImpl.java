package is.hi.hbv501g.verkefni.services.implementations;

import is.hi.hbv501g.verkefni.persistence.entities.Discount;
import is.hi.hbv501g.verkefni.services.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final List<Discount> discountCodeRepository = new CopyOnWriteArrayList<>();

    public DiscountServiceImpl() {
        // Pre-populate with default discount codes
        discountCodeRepository.add(new Discount("STUDENT20", 20));
        discountCodeRepository.add(new Discount("FIRST50", 50));
        discountCodeRepository.add(new Discount("WELCOME10", 10));
        discountCodeRepository.add(new Discount("EARLY25", 25));
    }

    @Override
    public List<Discount> getAll() {
        return List.copyOf(discountCodeRepository);
    }

    @Override
    public Discount getByCode(String code) {
        if (code == null || code.isBlank()) {
            return null;
        }

        // Case-insensitive search and trim whitespace
        String normalizedCode = code.trim().toUpperCase();

        return discountCodeRepository.stream()
                .filter(d -> d.getCode().toUpperCase().equals(normalizedCode))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Discount createDiscount(String code, int percentage) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Discount code cannot be empty");
        }

        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }

        // Check if code already exists
        if (getByCode(code) != null) {
            throw new IllegalArgumentException("Discount code already exists: " + code);
        }

        Discount d = new Discount(code.toUpperCase(), percentage);
        discountCodeRepository.add(d);
        return d;
    }

    @Override
    public Discount validateAndUseCode(String code) throws IllegalArgumentException {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Discount code cannot be empty");
        }

        Discount discount = getByCode(code);

        if (discount == null) {
            throw new IllegalArgumentException("Invalid discount code: " + code);
        }
        
        return discount;
    }
}
