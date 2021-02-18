package com.sc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zhudunfeng
 * @date 2021/2/8 16:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Leave implements Serializable {
    private static final long serialVersionUID = 6396463200111127725L;

    private String name;
    private Integer day;
    private Integer total;

    public Leave(String name, Integer day) {
        this.name = name;
        this.day = day;
    }

    public void say(){
        System.out.println("hello");
    }
}
