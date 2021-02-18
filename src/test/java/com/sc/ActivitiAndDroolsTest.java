package com.sc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sc.entity.ActRuTask;
import com.sc.mapper.ActRuTaskEntityMapper;
import com.sc.model.Leave;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhudunfeng
 * @date 2021/2/8 17:05
 */
@SpringBootTest
public class ActivitiAndDroolsTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ActRuTaskEntityMapper actRuTaskEntityMapper;

    @Test
    public void deployProcessAndDrl(){
        /**
         * 注意这里：必须要把drl文件一起deploy
         */
        DeploymentBuilder deploy = repositoryService.createDeployment();
        deploy.addClasspathResource("process/leave.bpmn").addClasspathResource("rules/leave.drl");
        Deployment deploy1 = deploy.deploy();
        System.out.println(deploy1.getId());
    }

    @Test
    public void startProcessAndDrl(){
//        ProcessInstance pi = runtimeService.startProcessInstanceByKey("leave");
        ProcessInstance pi = runtimeService.startProcessInstanceById("leave:2:52505");
        System.out.println(pi.getId());
    }

    @Test
    public void completeTask(){
        String processInstanceId="35001";
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("leave", new Leave("白展堂", 12));
        /**
         * 当前任务
         */
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for(Task task : tasks)
        {
            System.out.println(task.getId() + " , " + task.getName());
            taskService.complete(task.getId(), vars);
        }
    }

    @Test
    public void completeTaskTwo(){
        String processInstanceId="35001";
        /**
         * 下一步任务
         */
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for(Task task : tasks)
        {
            System.out.println(task.getId() + " , " + task.getName());
        }
    }

    @Test
    public void allProcess(){
        String processInstanceId="97501";
        Map<String, Object> vars = new HashMap<String, Object>();
//        Leave leave = new Leave("白展堂", 12);
        Leave leave = new Leave("白展堂", 1);
        vars.put("leave", leave);
        /**
         * 当前任务
         */
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for(Task task : tasks)
        {
            System.out.println(task.getId() + " , " + task.getName());
            taskService.complete(task.getId(), vars);
        }
        System.out.println("Java:"+leave);
        /**
         * 下一步任务
         */
        tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for(Task task : tasks)
        {
            System.out.println(task.getId() + " , " + task.getName());
        }
    }

    @Test
    public void deleteAllProcess(){
//        String processInstanceId="85001";

        //查询
        QueryWrapper<ActRuTask> actRuTaskQueryWrapper = new QueryWrapper<>();
        actRuTaskQueryWrapper.groupBy("PROC_INST_ID_");
        List<ActRuTask> actRuTasks = actRuTaskEntityMapper.selectList(actRuTaskQueryWrapper);
        List<String> proInstanceIdList = actRuTasks.stream().map(actRuTask -> actRuTask.getProcInstId()).collect(Collectors.toList());
        System.out.println(proInstanceIdList);
        System.out.println("===================================");

        proInstanceIdList.forEach(processInstanceId->{
            runtimeService.deleteProcessInstance(processInstanceId,"delete");
            historyService.deleteHistoricProcessInstance(processInstanceId);
        });

    }

}
