package com.atguigu.mp.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private Integer price;
    @Version // 表示当前属性为乐观锁字段值，以后更新时mp会自动检查查询数据的version值和更新时候数据库中该字段的值是否一致
    private Integer version;
}
