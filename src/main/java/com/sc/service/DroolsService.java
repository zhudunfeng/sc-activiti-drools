package com.sc.service;

import com.sc.model.Leave;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.cmd.DelegateTaskCmd;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @author zhudunfeng
 * @date 2021/2/8 16:28
 */
@Service
public class DroolsService implements JavaDelegate {
//    @Override
//    public void notify(DelegateTask delegateTask) {
//        List<Leave> reason = (List) delegateTask.getVariable("reason");
//        System.out.println("reason:"+reason);
//        delegateTask.setVariable("reason", reason);
//    }
    @Override
    public void execute(DelegateExecution delegateExecution) {
        List<Leave> reason = (List) delegateExecution.getVariable("reason");
        System.out.println("reason:"+reason);
//        Leave[] leaves=new Leave[reason.size()];
//        Leave[] leaves1 = reason.toArray(leaves);
//        delegateExecution.setVariable("reason", reason);
    }
}
