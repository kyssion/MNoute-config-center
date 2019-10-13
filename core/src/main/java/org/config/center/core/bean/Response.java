package org.config.center.core.bean;

public class Response<T> {
    private T bean;
    private ResponseStatus status;

    public Object getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public static <T> Response<T> getResponse(int code,String desc,T bean){
        Response<T> response = new Response<>();
        ResponseStatus responseStatus= new ResponseStatus();
        responseStatus.setCode(code);
        responseStatus.setDesc(desc);
        response.setBean(bean);
        response.setStatus(responseStatus);
        return response;
    }

}
