package org.mouter.admin.data;

public class ApplicationInformationData extends BaseData{
    private Long groupId;
    private Long appId;
    private Long appName;

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

    public Long getAppName() {
        return appName;
    }

    public void setAppName(Long appName) {
        this.appName = appName;
    }
}
