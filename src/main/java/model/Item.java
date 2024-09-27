package model;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;
}
