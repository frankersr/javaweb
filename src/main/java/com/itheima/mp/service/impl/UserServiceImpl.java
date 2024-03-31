package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.query.UserQuery;
import com.itheima.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 86178
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public void deductById(Long id, Integer money) {
        //1.查找用户
        User user = getById(id);


        //2.判断用户状态（是否存在和账户是否异常）
        if (user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户不存在或状态异常");

        }
        //3.查看用户余额是否小于减去的金额，小于抛出异常
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");

        }
            //4.执行扣钱操作
        baseMapper.deductMoney(id,money);



    }

    @Override
    public UserVO getUserAndAddressesById(Long id) {
        //1.查找用户
        User user = getById(id);
        if(user==null||user.getStatus()==UserStatus.FROZEN){
            throw new RuntimeException("用户状态异常!");

        }

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        //通过静态工具查找用户地址
        List<Address> list = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();

        if(CollUtil.isNotEmpty(list)){
            List<AddressVO> addressVOS = BeanUtil.copyToList(list, AddressVO.class);
            userVO.setAddresses(addressVOS);
        }
        return userVO;
    }

    @Override
    public List<UserVO> getUserAndAddressesByIds(List<Long> ids) {
        //1.查找用户
        List<User> users = listByIds(ids);
        if (CollUtil.isEmpty(users)){
            return  Collections.emptyList();
        }
        //2.获取用户ids
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        //3.根据用户ids获取地址信息
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, ids).list();



        //4.address转为addressvo
        List<AddressVO> addressVOS = BeanUtil.copyToList(addresses, AddressVO.class);

        //5.将地址根据用户id分类
        Map<Long, List<AddressVO>> map=new HashMap<>(0);
        if(CollUtil.isNotEmpty(addressVOS)) {
            map = addressVOS.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }
        List<UserVO> list=new ArrayList<UserVO>(users.size());
        for (User user : users) {
            //将users转为uservo
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            //根据分组加入不同的uservo
            vo.setAddresses(map.get(user.getId()));
            list.add(vo);




        }
        return list;


        }

    @Override
    public PageDTO<UserVO> getUserAndAddressesByPages(UserQuery query) {

        String name=query.getName();
        UserInfo status=query.getStatus();
        //1.分页条件
        Page<User> page=Page.of(query.getPageNo(), query.getPageSize());

        //不为空
        if(StrUtil.isNotBlank(query.getSortBy())) {
            page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        }
        else {
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.开始查询
        Page<User> p = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .ge(status != null, User::getStatus, status)
                .page(page);


        //3.封装类型,vo
        PageDTO<UserVO> pageDTO=new PageDTO<UserVO>();
        pageDTO.setPages(p.getPages());
        pageDTO.setTotal(p.getTotal());
        //获取返回集合
        List<User> list=p.getRecords();

        if (CollUtil.isEmpty(list)){
            pageDTO.setList(Collections.emptyList());
            return pageDTO;
        }


        pageDTO.setList(BeanUtil.copyToList(list,UserVO.class));




        //4.反回
        return pageDTO;




        











    }


}
