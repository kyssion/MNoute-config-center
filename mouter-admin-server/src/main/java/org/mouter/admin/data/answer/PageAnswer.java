package org.mouter.admin.data.answer;

import org.mouter.admin.data.PageAble;

public class PageAnswer extends Answer{
    private PageAble pageAble;
    public static PageAnswer createPageAnswer(int code, String message, Object body,PageAble pageAble){
        PageAnswer answer = new PageAnswer();
        answer.setBody(body);
        answer.setCode(code);
        answer.setMessage(message);
        answer.setPageAble(pageAble);
        return answer;
    }

    public PageAble getPageAble() {
        return pageAble;
    }

    public void setPageAble(PageAble pageAble) {
        this.pageAble = pageAble;
    }
}
