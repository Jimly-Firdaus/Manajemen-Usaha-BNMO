/* VIP Class */
package com.logic.feature;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class VIP extends Member {
    /* Attributes */
    protected float discountRate;

    /* Constructor */
    public VIP(int id, String name, String phoneNumber) {
        super(id, name, phoneNumber);
        this.discountRate = 10;
    }
}
