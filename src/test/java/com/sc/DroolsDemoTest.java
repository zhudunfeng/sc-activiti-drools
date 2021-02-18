package com.sc;

import com.sc.model.Leave;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.cdi.KBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhudunfeng
 * @date 2021/2/18 9:55
 */
@SpringBootTest
public class DroolsDemoTest {


    @Autowired
    private KieBase kieBase;

    @Autowired
    private KieContainer kieContainer;


    /**
     * 最原生的demo
     */
    @Test
    public void droolsDemo(){
//        KieServices kieServices = KieServices.Factory.get();
//        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
//        //会话对象，用于和规则引擎交互
//        KieSession kieSession = kieClasspathContainer.newKieSession();
        KieSession kieSession = kieContainer.newKieSession();

        //构建事实对象
        Leave leave = new Leave();
        leave.setDay(5);

        //将数据提供给规则引擎，规则引擎会根据提供的数据进行规则匹配
        kieSession.insert(leave);

        //激活规则引擎，如果匹配成功则执行规则
        kieSession.fireAllRules();
        //关闭当前会话
        kieSession.dispose();

        System.out.println(leave.toString());
    }


    /**
     * SpringBoot demo
     */
    @Test
    public void springBootAndDrools(){
        KieSession kieSession = kieBase.newKieSession();

        Leave leave = new Leave();
        leave.setDay(5);

        kieSession.insert(leave);

        kieSession.fireAllRules();

        kieSession.dispose();

        System.out.println(leave.toString());
    }
}
