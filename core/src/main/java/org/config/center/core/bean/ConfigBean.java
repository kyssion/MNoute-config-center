package org.config.center.core.bean;

public class ConfigBean {
    private long id;
    private String namespace;
    private String cluter;
    private String key;
    private String values;
    private String namespaceToken;
    private String cluterToken;
    private String keyToken;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getNamespaceToken() {
        return namespaceToken;
    }

    public void setNamespaceToken(String namespaceToken) {
        this.namespaceToken = namespaceToken;
    }

    public String getCluterToken() {
        return cluterToken;
    }

    public void setCluterToken(String cluterToken) {
        this.cluterToken = cluterToken;
    }

    public String getKeyToken() {
        return keyToken;
    }

    public void setKeyToken(String keyToken) {
        this.keyToken = keyToken;
    }
}
