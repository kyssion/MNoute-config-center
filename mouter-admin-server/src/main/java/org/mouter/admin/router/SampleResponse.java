package org.mouter.admin.router;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParam;
import org.mintflow.vertx.param.adapter.ResponseParamAdapter;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.util.JsonUtils;

public class SampleResponse implements ResponseParamAdapter<ResponseParam> {

    public static SampleResponse sampleResponse = new SampleResponse();

    @Override
    public ResponseParam createResponseParams(ParamWrapper paramWrapper) {
        Answer answer = paramWrapper.getParam(Answer.class);
        ResponseParam responseParams = new ResponseParam();
        String ans = JsonUtils.encode(answer);
        responseParams.setData(ans);
        return responseParams;
    }
}
