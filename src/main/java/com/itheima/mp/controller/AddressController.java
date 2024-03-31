package com.itheima.mp.controller;


import com.itheima.mp.domain.po.Address;
import com.itheima.mp.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘志平
 * @since 2024-03-15
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService iAddressService;

    @GetMapping
    public Address getTest(){
        Address address = iAddressService.getById(59);


        System.out.println(address);
        return address;





    }

}
