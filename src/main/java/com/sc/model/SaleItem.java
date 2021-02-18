package com.sc.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhudunfeng
 * @date 2021/2/18 14:14
 */
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleItem implements Serializable {
    private static final long serialVersionUID = -5603624139268202526L;
    //商品名称
    private String goodsName;


    //商品单价
    private BigDecimal price;


    //数量
    private BigDecimal amount;

}
