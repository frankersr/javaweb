package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.query.UserQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86178
 */
public interface UserService extends IService<User> {
    void deductById(Long id, Integer money);

    UserVO getUserAndAddressesById(Long id);

  List<UserVO> getUserAndAddressesByIds(List<Long> ids);

    PageDTO<UserVO> getUserAndAddressesByPages(UserQuery query);
}
