package org.config.center.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.config.center.core.bean.ConfigBean;
import org.config.center.core.bean.Response;
import org.config.center.mapper.ConfigMapper;
import org.config.center.server.ConfigServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Api(value = "config controller")
@RestController
public class ConfigController {
    @Autowired
    private ConfigServer configServer;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation(value = "创建一个配置", notes = "创建一个新的配置")
    @RequestMapping(value = "config/create",method = RequestMethod.POST)
    public Response<Boolean> configCreate(@RequestBody ConfigBean configBean) {
        configBean.setStatus("0");
        configServer.insertConfig(configBean);
        return Response.getResponse(200, "ok", true);
    }
    @ApiOperation(value = "查找一个配置", notes = "注意分页的问题 传入 分页信息和key 获取配置信息")
    @RequestMapping(value = "config/find" ,method = RequestMethod.GET)
    public Response<List<ConfigBean>> getConfigList(
            @ApiParam(name = "page", value = "当前页数", required = true)  @RequestParam("page") int page,
            @ApiParam(name = "pageSize", value = "每一页数据的数量", required = true)@RequestParam("pageSize") int pageSize,
            @ApiParam(name = "key", value = "key的名称", required = true) @RequestParam("key") String keyName) {
        int allSize = configServer.findConfigListSize(keyName);
        int totalSize = allSize % pageSize == 0 ? allSize / pageSize : (allSize / pageSize + 1);
        List<ConfigBean> list = configServer.findConfigList(keyName, page, pageSize);
        return Response.getResponsewithPage(200, "ok", list, page, totalSize);
    }
    @ApiOperation(value = "配置发布", notes = "发布一个全新的配置 , key 的 status 状态 0 表示未发布 1 表示已经发布")
    @RequestMapping(value = "config/release",method = RequestMethod.GET)
    public Response<Boolean> releaseConfig(
            @RequestParam("key") String keyName) {
        configServer.updateConfigStatus(keyName, "1");
        return Response.getResponse(200, "ok", true);
    }
    @ApiOperation(value = "配置删除", notes = "删除一个配置")
    @RequestMapping(value = "config/delete",method = RequestMethod.GET)
    public Response<Boolean> deleteConfig(
            @ApiParam(name = "key", value = "删除的key id", required = true) @RequestParam("key") String name) {
        configServer.delete(name);
        return Response.getResponse(200, "ok", true);
    }
    @ApiOperation(value = "更新配置的信息", notes = "更新一个配置")
    @RequestMapping(value = "config/update",method = RequestMethod.POST)
    public Response<Boolean> configUpdate(
            @ApiParam(name = "key", value = "更新的key id", required = true) @RequestParam("key")String key,
            @ApiParam(name = "value", value = "key 的value", required = true) @RequestParam("value")String value, HttpServletRequest request) {
        configServer.updateConfig(key,value);
        return Response.getResponse(200, "ok", true);
    }

    @ApiOperation(value = "发现一个配置", notes = "发现一个配置")
    @RequestMapping(value = "config/find/all",method = RequestMethod.GET)
    public Response<List<ConfigBean>> findAllConfig() {
        List<ConfigBean> configBeanList = configServer.findAllConfigList();
        return Response.getResponse(200, "ok", configBeanList);
    }

    @ApiOperation(value = "配置上传", notes = "上传一个配置")
    @RequestMapping(value = "config/upload", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> upload(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String info = "";
        while ((info = bufferedReader.readLine()) != null) {
            stringBuilder.append(info);
        }

        ConfigBean configBean = objectMapper.readValue(stringBuilder.toString(), ConfigBean.class);
        configServer.insertConfig(configBean);
        return Response.getResponse(200, "ok", true);
    }

    @ApiOperation(value = "获取一个配置", notes = "获取一个配置")
    @RequestMapping(value = "config/getfile",method = RequestMethod.POST)
    public void getFile(@RequestParam("key") String name, HttpServletResponse response) throws JsonProcessingException {
        List<ConfigBean> configBean = configServer.findConfigList(name, 0, 1);
        String date = "";
        if (configBean != null && configBean.size() > 0) {
            date = objectMapper.writeValueAsString(configBean);
        }

        response.setHeader("Content-Disposition", "attachment;fileName=" + name + ".json");
        OutputStream os = null; //输出流
        BufferedInputStream bis = null;
        try {
            os = response.getOutputStream();
            os.write(date.getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("----------file download" + name + ".json");
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
