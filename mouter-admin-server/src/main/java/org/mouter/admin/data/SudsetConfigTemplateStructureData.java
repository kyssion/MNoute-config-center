package org.mouter.admin.data;

public class SudsetConfigTemplateStructureData extends PageBaseData{
    private Long sudsetTemplateId;
    private String keyLevel;

    public Long getSudsetTemplateId() {
        return sudsetTemplateId;
    }

    public void setSudsetTemplateId(Long sudsetTemplateId) {
        this.sudsetTemplateId = sudsetTemplateId;
    }

    public String getKeyLevel() {
        return keyLevel;
    }

    public void setKeyLevel(String keyLevel) {
        this.keyLevel = keyLevel;
    }
}
