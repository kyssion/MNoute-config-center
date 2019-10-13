package org.config.center.controller;

import org.config.center.bean.UserBO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {



    @RequestMapping("user/login")
    public UserBO Login(String name ,String password) {

        return null;
    }

    @RequestMapping("user/lgout")
    public boolean LgOut() {
        return true;

    }

    @RequestMapping("user/register")
    public boolean register() {
        return true;

    }

    @RequestMapping("user/add/permission")
    public UserBO addPermission() {
        return null;
    }

    @RequestMapping("user/modify/permission")
    public UserBO modifyPermission() {
        return null;

    }
}
