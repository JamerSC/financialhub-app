package com.jamersc.springboot.financialhub.model;

import lombok.*;

//@Entity
//@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String fullName;
    private int cash;
}
