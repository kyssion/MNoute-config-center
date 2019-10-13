package org.config.center.server;

import org.config.center.core.bean.ConfigBean;
import org.config.center.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServer {

    @Autowired
    private ConfigMapper configMapper;
    public void insertConfig(ConfigBean configBean){
        configMapper.insertConfig(configBean);
    }

    public int findConfigListSize(String keyName) {
        if(keyName==null){
            keyName = "";
        }
        return configMapper.findConfigListSize(keyName);
    }

    public List<ConfigBean> findConfigList(String keyName, int page, int pageSize) {
        return configMapper.findConfigList(keyName,page*pageSize,pageSize);
    }

    public void updateConfigStatus(String keyName, String s) {
        configMapper.updateConfigStatus(keyName,s);
    }

    public void delete(String name) {
        configMapper.delete(name);
    }

    public void updateConfig(ConfigBean configBean) {
        configMapper.updateConfig(configBean);
    }

    public List<ConfigBean> findAllConfigList() {
        return configMapper.findAllConfigList();
    }
}
