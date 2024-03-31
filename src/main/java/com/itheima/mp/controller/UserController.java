package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.query.UserQuery;
import com.itheima.mp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86178
 */

@RestController
@Api(tags = "用户管理接口")
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation("新增用户接口")
    public void saveUser(@RequestBody UserFormDTO userFormDTO){

       User user= BeanUtil.copyProperties(userFormDTO, User.class);
       userService.save(user);


    }

    @ApiOperation("扣减用户余额")
    @PostMapping("/{id}/deduction/{money}")
    public void deductMoneyById(@ApiParam("用户id") @PathVariable Long id,@ApiParam("用户扣减的金额") @PathVariable Integer money){
        userService.deductById(id,money);
    }

    @ApiOperation("根据id查询用户信息及地址信息")
    @GetMapping("/{id}")
    public UserVO getUserAndAddressesById(@PathVariable Long id){
        UserVO userVO=userService.getUserAndAddressesById(id);
        return  userVO;

    }

    @ApiOperation("根据id集合查询用户信息及地址")
    @GetMapping
    public List<UserVO> getUserAndAddressesByIds(@RequestParam("ids") List<Long> ids){

       return userService.getUserAndAddressesByIds(ids);
    }

    @ApiOperation("分页查询用户信息及地址信息")
    @GetMapping("/page")
    public PageDTO<UserVO> getUserAndAddressesByPages( UserQuery query){
       return userService.getUserAndAddressesByPages(query);
    }


}
