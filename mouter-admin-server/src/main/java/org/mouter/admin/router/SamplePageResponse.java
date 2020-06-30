package org.mouter.admin.router;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParam;
import org.mintflow.vertx.param.adapter.ResponseParamAdapter;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.data.answer.PageAnswer;
import org.mouter.admin.util.JsonUtils;

public class SamplePageResponse implements ResponseParamAdapter<ResponseParam> {

    public static SamplePageResponse samplePageResponse = new SamplePageResponse();

    @Override
    public ResponseParam createResponseParams(ParamWrapper paramWrapper) {
        PageAnswer answer = paramWrapper.getParam(PageAnswer.class);
        ResponseParam responseParams = new ResponseParam();
        String ans = JsonUtils.encode(answer);
        responseParams.setData(ans);
        return responseParams;
    }
}
