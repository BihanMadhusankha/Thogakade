package entity;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class ItemEntity {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;
}
