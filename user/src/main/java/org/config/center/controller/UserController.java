package org.config.center.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.config.center.core.bean.Response;
import org.config.center.core.bean.UserBO;
import org.config.center.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("login")
    public Response<UserBO> Login(String name, String password, HttpServletRequest request) throws IOException {
        UserBO userBO = userService.logIn(name, password);
        if (userBO == null) {
            return Response.getResponse(500,"未发现用户",null);
        } else {
            request.getSession().setAttribute("userSession", objectMapper.writeValueAsString(userBO));
            return Response.getResponse(200,"ok",userBO);
        }
    }

    @RequestMapping("isLogin")
    public Response<UserBO> isLogin(HttpServletRequest request) throws IOException {
        String sessionLoginInfor = (String) request.getSession().getAttribute("userSession");
        if (sessionLoginInfor != null) {
            UserBO userBO = objectMapper.readValue(sessionLoginInfor,UserBO.class);
            return Response.getResponse(200,"ok",userBO);
        }else{
            return Response.getResponse(302,"未发现用户",null);
        }
    }

    @RequestMapping("lgOut")
    public boolean LgOut(HttpServletRequest request) {
        request.getSession().removeAttribute("userSession");
        return true;
    }

    @RequestMapping("register")
    public Response<UserBO> register(String name,String password,String email,HttpServletRequest request) throws JsonProcessingException {
        UserBO userBO = userService.register(name,password,email);
        request.getSession().setAttribute("userSession", objectMapper.writeValueAsString(userBO));
        return Response.getResponse(200,"ok",userBO);
    }

    @RequestMapping("add/permission")
    public Response<Boolean> addPermission(String name, String namsespace, String cluter, String key) {
        userService.addPermission(name,namsespace,cluter,key);
        return Response.getResponse(200,"ok",true);
    }

    @RequestMapping("delete/permission")
    public Response<Boolean> deletePermission(String name,String namsespace,String cluter,String key) {
        userService.deletePermission(name,namsespace,cluter,key);
        return Response.getResponse(200,"ok",true);
    }
}
