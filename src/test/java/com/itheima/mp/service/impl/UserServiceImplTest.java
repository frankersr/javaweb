package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void queryTest(){
        User user = new User();
        user.setId(7L);
        user.setUsername("jim");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo(UserInfo.of(23,"英语老师","女"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }

    @Test
    void queryPage(){
        int pageNo=2,pageSize=2;
        //1.分页条件
        Page<User> page=Page.of(pageNo,pageSize);
        //2.排序条件
        page.addOrder(new OrderItem("balance",true));
        page.addOrder(new OrderItem("id",true));
        //3.开始分页查询
        Page<User> p=userService.page(page);

        //4.解析数据
        Long total=p.getTotal();
        Long pages=p.getPages();
        System.out.println("查询总数="+total);
        System.out.println("查询页数="+pages);
        List<User> users=p.getRecords();
        users.stream().forEach(System.out::println);

    }

}