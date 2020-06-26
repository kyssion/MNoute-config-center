package org.mouter.admin.data.answer;

import org.mouter.admin.data.PageBaseData;

public class ConfigInformationData extends PageBaseData {
    private Long configId;
    private String configName;
    private Long groupId;
    private Long appId;
    private Long configTemplateId;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getConfigTemplateId() {
        return configTemplateId;
    }

    public void setConfigTemplateId(Long configTemplateId) {
        this.configTemplateId = configTemplateId;
    }
}
