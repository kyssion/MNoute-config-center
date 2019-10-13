package org.config.center.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@RestController
public class ConfigController {
    @Autowired
    private ConfigServer configServer;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("config/create")
    public Response<Boolean> configCreate(ConfigBean configBean, HttpServletRequest request) {
        configServer.insertConfig(configBean);
        configBean.setStatus("0");
        return Response.getResponse(200, "ok", true);
    }

    @RequestMapping("config/find")
    public Response<List<ConfigBean>> getConfigList(@RequestParam("page") int page, @RequestParam("page") int pageSize, @RequestParam("key") String keyName) {
        int allSize = configServer.findConfigListSize(keyName);
        int totalSize = allSize % pageSize == 0 ? allSize / page : (allSize / page + 1);
        List<ConfigBean> list = configServer.findConfigList(keyName, page, pageSize);
        return Response.getResponsewithPage(200, "ok", list, page, totalSize);
    }

    @RequestMapping("config/release")
    public Response<Boolean> releaseConfig(@RequestParam("key") String keyName) {
        configServer.updateConfigStatus(keyName, "1");
        return Response.getResponse(200, "ok", true);
    }

    @RequestMapping("config/delete")
    public Response<Boolean> deleteConfig(@RequestParam("key") String name) {
        configServer.delete(name);
        return Response.getResponse(200, "ok", true);
    }

    @RequestMapping("config/update")
    public Response<Boolean> configUpdate(ConfigBean configBean, HttpServletRequest request) {
        configServer.updateConfig(configBean);
        configBean.setStatus("0");
        return Response.getResponse(200, "ok", true);
    }

    @RequestMapping("config/find/all")
    public Response<List<ConfigBean>> findAllConfig() {
        List<ConfigBean> configBeanList = configServer.findAllConfigList();
        return Response.getResponse(200, "ok", configBeanList);
    }

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

    @RequestMapping(value = "config/getfile")
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
