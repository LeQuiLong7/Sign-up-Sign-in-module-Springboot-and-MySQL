package com.lql.hellospringsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class  Person {
    @EqualsAndHashCode.Include
    private  int id;
    private  String name;
    private  int age;


}
