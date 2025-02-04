package model.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartTM {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int quantityOnHand;
    private double total;


}
