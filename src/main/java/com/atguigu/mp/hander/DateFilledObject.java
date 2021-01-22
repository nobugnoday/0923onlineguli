package com.atguigu.mp.hander;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateFilledObject implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 新增填充
        metaObject.setValue("gmtCreate",new Date());
        metaObject.setValue("gmtModified",new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新填充
        metaObject.setValue("gmtModified",new Date());

    }
}
