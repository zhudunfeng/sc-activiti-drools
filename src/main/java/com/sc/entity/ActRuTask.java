package com.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author zhudunfeng
 * @date 2021/2/9 12:05
 */
@TableName(value = "act_ru_task")
@Data
public class ActRuTask {
    @TableId(value = "ID_")
    private String id;

    @TableField(value = "REV_")
    private int rev;

    @TableField(value = "EXECUTION_ID_")
    private String executionId;

    @TableField(value = "PROC_INST_ID_")
    private String procInstId;

    @TableField(value = "PROC_DEF_ID_")
    private String procDefId;

    @TableField(value = "NAME_")
    private String name;

    @TableField(value = "PARENT_TASK_ID_")
    private String parentTaskId;




}
