package org.config.center.controller;

import org.config.center.bean.UserBO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    public UserBO Login(){
        return null;

    }

    public boolean LgOut(){
        return true;

    }

    public boolean register(){
        return true;

    }

    public UserBO addPermission(){
        return null;
    }

    public UserBO modifyPermission(){
        return null;

    }
}
