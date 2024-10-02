package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartTM {
    private String itemCode;
    private String description;
    private Integer qty;
    private  Double unitePrice;
    private Double total;
}
