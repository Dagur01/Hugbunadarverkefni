package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.Discount;

import java.util.List;

public interface DiscountService {

    Discount createDiscount(String code, int percentage);

    Discount validateAndUseCode(String code) throws IllegalArgumentException;

    Discount getByCode(String code);

    List<Discount> getAll();

}
