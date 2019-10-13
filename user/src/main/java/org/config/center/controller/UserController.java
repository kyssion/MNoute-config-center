package org.config.center.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.config.center.core.bean.Response;
import org.config.center.core.bean.UserBO;
import org.config.center.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(value = "config controller")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation(value = "登入", notes = "输入帐号密码登入后台系统")
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public Response<UserBO> Login(
            @RequestParam("name") String name,
            @RequestParam("passwd") String password, HttpServletRequest request) throws IOException {
        UserBO userBO = userService.logIn(name, password);
        if (userBO == null) {
            return Response.getResponse(500,"未发现用户",null);
        } else {
            request.getSession().setAttribute("userSession", objectMapper.writeValueAsString(userBO));
            return Response.getResponse(200,"ok",userBO);
        }
    }

    @ApiOperation(value = "校验注册信息", notes = "判断用户是否登入")
    @RequestMapping(value = "isLogin",method = RequestMethod.GET)
    public Response<UserBO> isLogin(HttpServletRequest request) throws IOException {
        String sessionLoginInfor = (String) request.getSession().getAttribute("userSession");
        if (sessionLoginInfor != null) {
            UserBO userBO = objectMapper.readValue(sessionLoginInfor,UserBO.class);
            return Response.getResponse(200,"ok",userBO);
        }else{
            return Response.getResponse(302,"未发现用户",null);
        }
    }

    @ApiOperation(value = "用户登出", notes = "判断用户是否登入")
    @RequestMapping(value = "lgOut",method = RequestMethod.GET)
    public boolean LgOut(HttpServletRequest request) {
        request.getSession().removeAttribute("userSession");
        return true;
    }

    @ApiOperation(value = "用户登出", notes = "判断用户是否登入")
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public Response<UserBO> register(
            @RequestParam("name") String name,
            @RequestParam("passwd") String password,
            @RequestParam("email") String email,
            HttpServletRequest request) throws JsonProcessingException {
        UserBO userBO = userService.register(name,password,email);
        request.getSession().setAttribute("userSession", objectMapper.writeValueAsString(userBO));
        return Response.getResponse(200,"ok",userBO);
    }

//    @RequestMapping("add/permission")
//    public Response<Boolean> addPermission(String name, String namsespace, String cluter, String key) {
//        userService.addPermission(name,namsespace,cluter,key);
//        return Response.getResponse(200,"ok",true);
//    }
//
//    @RequestMapping("delete/permission")
//    public Response<Boolean> deletePermission(String name,String namsespace,String cluter,String key) {
//        userService.deletePermission(name,namsespace,cluter,key);
//        return Response.getResponse(200,"ok",true);
//    }

    @ApiOperation(value = "查看所有的用", notes = "判断用户是否登入 , 返回值 " +
            "{\n" +
            "    \"bean\": [\n" +
            "        {\n" +
            "            \"name\": \"kyssion\",\n" +
            "            \"id\": \"1\",\n" +
            "            \"token\": null\n" +
            "        }\n" +
            "    ],\n" +
            "    \"status\": {\n" +
            "        \"code\": 200,\n" +
            "        \"desc\": \"ok\"\n" +
            "    }\n" +
            "}")
    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    public Response<List<UserBO>> findAllUser(){
        List<UserBO> list = userService.findAllUserList();
        return Response.getResponse(200,"ok",list);
    }
}
