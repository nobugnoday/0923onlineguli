package com.atguigu.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor   // 有参构造器
@NoArgsConstructor    // 无参构造器
@TableName("user")    // 绑定Javabean和表
public class User {
    /**
     *  主键策略 ：
     *  1  默认是雪花算法 ：IdType.ASSIGN_ID
     *  2 UUID: IdType.ASSIGN_UUID 生成的唯一字符串，表的id字段类型需要是字符串 ， 长度必大于32 位
     *          以后也不会使用UUID 太长无序
     *  3 auto ： 数据库的自增维护id的值
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID )
    private String id;
    @TableField(value = "name")
    private String name1;
    private Integer age;
    private String email;
    // 创建时间  新增user数据列数据库时 ，设置gmtCreate 的值
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    // 修改时间  新增或者更新的时候 设置 gmtModified 的值
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableLogic  // 表示当前字段为逻辑删除字段，查询时会添加逻辑字段的条件
    @TableField(value = "is_deleted")
    private boolean deleted;


    public User(String id, String name1, Integer age, String email) {
        this.id = id;
        this.name1 = name1;
        this.age = age;
        this.email = email;
    }
}
