package com.sc.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhudunfeng
 * @date 2021/2/18 14:12
 */
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sale implements Serializable {
    private static final long serialVersionUID = -7042363927341740170L;

    //销售单号
    private String saleCode;

    //销售日期
    private Date date;

    //销售明细
    private List<SaleItem> items;

    //折扣
    private BigDecimal discount = new BigDecimal(1);


    public Sale(String saleCode, Date date) {
        super();
        this.saleCode = saleCode;
        this.date = date;
        this.items = new ArrayList<SaleItem>();
    }

    //返回日期为星期几【第二代日期】
    public int getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.date);
        int dow = c.get(Calendar.DAY_OF_WEEK);
        return dow;
    }

    //返回该销售单的总金额（优惠前）
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        for (SaleItem item : this.items) {
            BigDecimal itemTotal = item.getPrice().multiply(item.getAmount());
            total = total.add(itemTotal);
        }
        total = total.setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数，舍入最近的数
        return total;

    }


    //返回优惠后的总金额
    public BigDecimal getDiscountTotal() {
        BigDecimal total = getTotal();
        total = total.multiply(this.discount).setScale(2, BigDecimal.ROUND_HALF_UP);
        return total;

    }

    public void setDiscount(BigDecimal dicsount) {
        this.discount = dicsount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public BigDecimal getDiscount() {
        return this.discount;
    }

    public void addItem(SaleItem saleItem){
        items.add(saleItem);
    }
}
