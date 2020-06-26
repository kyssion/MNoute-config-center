package org.mouter.admin.router;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParams;
import org.mintflow.vertx.param.adapter.ResponseParamAdapter;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.util.JsonUtils;

public class SampleResponse implements ResponseParamAdapter<ResponseParams> {

    public static SampleResponse sampleResponse = new SampleResponse();

    @Override
    public ResponseParams createResponseParams(ParamWrapper paramWrapper) {
        Answer answer = paramWrapper.getParam(Answer.class);
        ResponseParams responseParams = new ResponseParams();
        String ans = JsonUtils.encode(answer);
        responseParams.setData(ans);
        return responseParams;
    }
}
