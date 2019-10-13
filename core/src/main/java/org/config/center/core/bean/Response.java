package org.config.center.core.bean;

public class Response<T> {
    private T bean;
    private ResponseStatus status;
    private Page page;
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

    public static <T> Response<T> getResponsewithPage(int code,String desc,T bean,int page,int totalPage){
        Response<T> response = getResponse(code,desc,bean);
        Page page1 = new Page();
        page1.setTotalPage(totalPage);
        page1.setPageSize(page);
        return response;
    }

}
