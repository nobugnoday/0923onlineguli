package com.atguigu.mp;

import com.atguigu.mp.entity.Product;
import com.atguigu.mp.entity.User;
import com.atguigu.mp.mapper.ProductMapper;
import com.atguigu.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {
    // 自动装配：必须保证装配的对象已经初始话并保存到spring容器中
    @Autowired
    UserMapper userMapper;


    @Autowired
    ProductMapper productMapper;


    // 模拟并发更新的价格
    @Test
    public void testConcurrent(){
        // A 查询商品信息
        Product aProduct = productMapper.selectById(1);
        // B 查询商品信息
        Product bProduct = productMapper.selectById(1);
        // A 修改   用乐观锁解决版本冲突问题
        aProduct.setPrice(aProduct.getPrice() + 50);
        productMapper.updateById(aProduct);
        // B 修改
        bProduct.setPrice(bProduct.getPrice() - 30);
        int i = productMapper.updateById(bProduct);
        if (i == 0) {
            // 更新失败，数据版本增加了
            bProduct = productMapper.selectById(1);
            bProduct.setPrice(bProduct.getPrice() - 30);
            productMapper.updateById(bProduct);
        }

        // 查询更新的价格是
        Product product = productMapper.selectById(1);
        System.out.println("product = " + product);

    }



    /**
     *  mp 的步骤
     *  1 创建springboot项目引入mp的场景启动器依赖
     *  2 创建Javabean ： 建议javabean的名称以及属性类型属性名 需要和表一致
     *  3 创建操作表Mapper并集成BaseMapper<泛型（要操作表对应的Javabean）>
     *  4 数据库参数配置
     */

    /**
     *  1 查询所有
     *  2 根据id进行查询
     *  3 查询
     *  4 新增
     *  5 删除
     *  6 更新
     */

    //  5 删除
    @Test
    public void testDelete(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","小白3");
        int i = userMapper.delete(queryWrapper);
        System.out.println( i  > 0 ? "删除成功" : "删除失败");

    }

    //  6 更新
    @Test
    public void update(){
//        // 1 根据id 查询记录
//        User user = userMapper.selectById("1352456303703838722");
//        // 2 根据id更新记录
//        user.setName1("小小白");
//        int i = userMapper.updateById(user);
//        System.out.println(i > 0 ? "更新成功" : "更新失败");
        
        // 方法 二


        UpdateWrapper<User> updatewrapper = new UpdateWrapper<>();
        updatewrapper.set("name","老夏123");
        updatewrapper.eq("id","1352580888377991170");
//        User user = new User(null, "小虾米111", 12, "sfdsf");
        userMapper.update(null,updatewrapper);
        
    }



    // 4 新增
    @Test
    void addUser(){
        /**
         *  1 雪花算法 ： 默认
         *  2 UUID  :
         *  3 数据库维护自增
         *  4 不分配策略    默认是雪花算法
         */
        User user = new User();
        user.setName1("小白3");
        user.setAge(17);
        user.setEmail("xiaobai@qq.com");
        int i = userMapper.insert(user);
        System.out.println(i > 0 ? "插入成功" : "插入失败");


    }


    // 带条件的分页查询
    @Test
    public void testQueryPage(){
        // 分页必须配置mp 的分页拦截器拦截到发送到sql请求语句，并添加limit关键字进行拆分
        // 参数1 ： page用来携带查询分页的和每页的size 并按接收 的分页数据集合

        Page<User> page = new Page<>();
        page.setCurrent(2);
        page.setSize(2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age");

       page = userMapper.selectPage(page, queryWrapper);
        System.out.println("page = " + page.getTotal());
        System.out.println("page = " + page.getSize());
        System.out.println("page = " + page.getCurrent());
        System.out.println("page = " + page.getRecords());

    }



    // 查询指定的列
    @Test
    void testQueryColumns(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","email");
        userMapper.selectMaps(queryWrapper);
    }


    // 3 名字中以有T字母开头的用户信息，并且年龄大于18岁 ，或者 id < 3 的用户

    /**
     *  查询封装的条件希望以where 关键字开始 条件都放在where 后  并且对年龄进行降序排序
     *  select * from 表名 where  c1 = XXX
     *  更新条件希望可以set字段 并设置where条件
     *  update 表名 set k=v,k=v where c1 =xx
     */
    @Test
    void testQueryByCondition(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name","o");
        queryWrapper.likeRight("name","J");

        queryWrapper.gt("age",18);
        queryWrapper.or();
        queryWrapper.ge("id",3);
        queryWrapper.orderByDesc("age","id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // 2 根据id进行查询
    @Test
    void testQueryById(){
        User user = userMapper.selectById(1);
        System.out.println("user = " + user);
    }

    // 1 查询所有
    @Test
    void testAllUser() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    @Test
    void contextLoads() {
    }

}
