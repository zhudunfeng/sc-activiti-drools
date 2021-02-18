package com.sc;

import com.sc.model.Leave;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScActivitiDroolsApplicationTests {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private Leave leave;

    @Test
    public void s(){
        leave.say();
    }

    @Test
    void contextLoads() {
        System.out.println("Hello SpringBoot");
    }

    /**
     * 同时部署流程与规则
     * */
    @Test
    public void deployProcessAndDrl(){
        /**
         * 注意这里：必须要把drl文件一起deploy
         */
        DeploymentBuilder deploy = repositoryService.createDeployment();
        deploy.addClasspathResource("classpath: process/leave.bpmn").addClasspathResource("classpath: rules/leave.drl");
        Deployment deploy1 = deploy.deploy();
        System.out.println(deploy1.getId());

    }


}
