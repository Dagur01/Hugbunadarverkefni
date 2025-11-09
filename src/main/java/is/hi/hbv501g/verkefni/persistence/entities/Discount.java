package is.hi.hbv501g.verkefni.persistence.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Discount {
    private String code;
    private Integer percentage;

    public Discount(String code, Integer percentage) {
        this.code = code;
        this.percentage = percentage;
    }
    
}



