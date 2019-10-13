package org.config.center.server;

import org.config.center.core.bean.UserBO;
import org.config.center.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserBO logIn(String name, String password) {
        UserBO userBO = userMapper.selectUserByUserId(name,password);
        return userBO;
    }

    public UserBO register(String name, String password, String email) {
        userMapper.insertUser(name,password,email);
        return userMapper.selectUserByUserId(name,password);
    }

    public void addPermission(String name, String namsespace, String cluter, String key) {
        if(key!=null){
            userMapper.isnertPermission(name,"key",key);
        }else if(cluter!=null){
            userMapper.isnertPermission(name,"cluter",cluter);
        }else if (namsespace!=null){
            userMapper.isnertPermission(name,"namsespace",namsespace);
        }
    }

    public void deletePermission(String name, String namsespace, String cluter, String key) {
        if(key!=null){
            userMapper.deletePermission(name,"key",key);
        }else if(cluter!=null){
            userMapper.deletePermission(name,"cluter",cluter);
        }else if (namsespace!=null){
            userMapper.deletePermission(name,"namsespace",namsespace);
        }
    }
}
