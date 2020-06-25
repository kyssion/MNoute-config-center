package org.mouter.admin.data.answer;


public class Answer {
    private int code;
    private String message;
    private Object body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static Answer createAnswer(int code, String message, Object body){
        Answer answer = new Answer();
        answer.setBody(body);
        answer.setCode(code);
        answer.setMessage(message);
        return answer;
    }
}
