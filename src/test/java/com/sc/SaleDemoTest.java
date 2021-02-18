package com.sc;

import com.sc.model.Sale;
import com.sc.model.SaleItem;
import lombok.AllArgsConstructor;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhudunfeng
 * @date 2021/2/18 15:36
 */
@SpringBootTest
public class SaleDemoTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void deploy(){
        //部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("process/Sale.bpmn")
                .addClasspathResource("rules/sale.drl")
                .deploy();
    }
    @Test
    public void testSale() {
        //创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

        //得到流程存储服务组件
        RepositoryService repositoryService = engine.getRepositoryService();

        //得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();

        //得到任务服务组件
        TaskService taskService = engine.getTaskService();

        //部署流程文件
        repositoryService.createDeployment()
                .addClasspathResource("process/Sale.bpmn")
                .addClasspathResource("rules/sale.drl")
                .deploy();

        ProcessInstance pi = runtimeService
                .startProcessInstanceByKey("guize");

        //创建事实实例，符合周六日打九折条件
        Sale s1 = new Sale("001", createDate("2017-07-01"));
        SaleItem s1Item1 = new SaleItem("矿泉水", new BigDecimal(5),
                new BigDecimal(4));

        s1.addItem(s1Item1);

        //满100打八折
        Sale s2 = new Sale("002", createDate("2017-07-03"));
        SaleItem s2Item1 = new SaleItem("爆米花", new BigDecimal(20),
                new BigDecimal(5));
        s2.addItem(s2Item1);

        //满200打七折
        Sale s3 = new Sale("003", createDate("2017-07-03"));
        SaleItem s3Item1 = new SaleItem("可乐一箱", new BigDecimal(70), new BigDecimal(3));
        s3.addItem(s3Item1);

        //星期天满200
        Sale s4 = new Sale("004", createDate("2017-07-02"));
        SaleItem s4Item1 = new SaleItem("爆米花一箱", new BigDecimal(80), new BigDecimal(3));
        s4.addItem(s4Item1);

        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("sale1", s1);
        vars.put("sale2", s2);
        vars.put("sale3", s3);
        vars.put("sale4", s4);

        //查找任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
                .singleResult();

        taskService.complete(task.getId(), vars);
    }

    //早生晚死
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //根据字符串创建日期对象
    static Date createDate(String date) {
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            throw new RuntimeException("parse date error: " + e.getMessage());
        }

    }
}
