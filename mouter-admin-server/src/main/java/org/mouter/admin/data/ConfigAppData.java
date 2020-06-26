package org.mouter.admin.data;

public class ConfigAppData extends PageBaseData{
    private Long appId;
    private Long groupId;
    private Long appDataId;
    private String levelKey;
    private String value;
    private String valueType;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAppDataId() {
        return appDataId;
    }

    public void setAppDataId(Long appDataId) {
        this.appDataId = appDataId;
    }

    public String getLevelKey() {
        return levelKey;
    }

    public void setLevelKey(String levelKey) {
        this.levelKey = levelKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
