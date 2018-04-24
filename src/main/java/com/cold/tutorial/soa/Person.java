package com.cold.tutorial.soa;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hui.liao
 *         2016/1/18 15:47
 */
@Data
public class Person implements Serializable{

    private int num;
    private String name;
}
