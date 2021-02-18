package com.sc.service;

import com.sc.model.Sale;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Collection;

/**
 * @author zhudunfeng
 * @date 2021/2/18 15:30
 */
public class SaleJavaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        Collection sales = (Collection) delegateExecution.getVariable("saleResults");

        System.out.println("输出处理结果：");

        for (Object obj : sales) {
            Sale sale = (Sale) obj;
            System.out.println("销售单：" + sale.getSaleCode() + " 原价："
                    + sale.getTotal() + " 优惠后：" + sale.getDiscountTotal()
                    + " 折扣：" + sale.getDiscount());
        }
    }
}
