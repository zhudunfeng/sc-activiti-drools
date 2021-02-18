package com.sc;

/**
 * @author zhudunfeng
 * @date 2021/2/9 12:02
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sc.entity.ActRuTask;
import com.sc.mapper.ActRuTaskEntityMapper;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = ScActivitiDroolsApplication.class)
public class MybatisTest {

    @Autowired
    private ActRuTaskEntityMapper actRuTaskMapper;

    @Test
    public void selectList(){
        List<ActRuTask> actRuTaskEntities = actRuTaskMapper.selectList(null);
        System.out.println(actRuTaskEntities);
    }

    /**
     * 缓存对象的范围-128-127
     * */
    @Test
    public void boolInteger(){
        Integer integer3 = 127;
        Integer integer4 = 127;
        Integer integer5 = 128;
        System.out.println(integer3==integer4);//true
        System.out.println(integer3==integer5);//false


        Integer integer1 = new Integer(128);
        Integer integer2 = new Integer(128);
        System.out.println(integer1==integer2);//false,引用对象，比较的是地址值
        System.out.println(integer1.equals(integer2));
    }

}
