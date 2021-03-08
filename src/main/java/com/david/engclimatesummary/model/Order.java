package com.david.engclimatesummary.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {

    private Integer column;
    private Direction dir;

}
