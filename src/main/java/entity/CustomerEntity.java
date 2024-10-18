package entity;

import lombok.*;

import java.time.LocalDate;

@ToString
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    private String id;
    private String title;
    private String name;
    private LocalDate dob;
    private Double salary;
    private  String address;

    private String city;
    private String province;
    private String postalCode;
}
