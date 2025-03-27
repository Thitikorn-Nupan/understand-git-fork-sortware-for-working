package com.ttknp.testspringbootapp.entities.common;

import com.fasterxml.jackson.databind.annotation.NoClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
// generic can have many types and can create generic name as whatever char
// T* can be null by using NoClass class
public class ModelCommon <T1,T2,T3,T4,T5 ,T6>{
    private T1 t1;
    private T2 t2;
    private T3 t3;
    private T4 t4;
    private T5 t5;
    private T6 t6;
}
