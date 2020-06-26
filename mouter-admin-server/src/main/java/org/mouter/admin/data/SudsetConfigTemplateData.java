package org.mouter.admin.data;

public class SudsetConfigTemplateData extends PageBaseData{
    private Long groupId;
    private Long appId;
    private Long SudsetId;
    private String subsetName;

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

    public Long getSudsetId() {
        return SudsetId;
    }

    public void setSudsetId(Long sudsetId) {
        SudsetId = sudsetId;
    }

    public String getSubsetName() {
        return subsetName;
    }

    public void setSubsetName(String subsetName) {
        this.subsetName = subsetName;
    }
}
