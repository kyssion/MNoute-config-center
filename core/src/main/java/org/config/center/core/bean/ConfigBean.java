package org.config.center.core.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("配置信息接收javabean")
public class ConfigBean {
    private long id;

    @ApiModelProperty("命名空间")
    private String namespace;
    @ApiModelProperty("集群信息")
    private String cluter;

    @ApiModelProperty("配置key信息")
    private String key;

    @ApiModelProperty("配置值信息")
    private String value;

    @ApiModelProperty("配置状态")
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getCluter() {
        return cluter;
    }

    public void setCluter(String cluter) {
        this.cluter = cluter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
