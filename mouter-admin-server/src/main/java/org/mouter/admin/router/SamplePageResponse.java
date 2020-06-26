package org.mouter.admin.router;

import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParams;
import org.mintflow.vertx.param.adapter.ResponseParamAdapter;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.data.answer.PageAnswer;
import org.mouter.admin.util.JsonUtils;

public class SamplePageResponse implements ResponseParamAdapter<ResponseParams> {

    public static SamplePageResponse samplePageResponse = new SamplePageResponse();

    @Override
    public ResponseParams createResponseParams(ParamWrapper paramWrapper) {
        PageAnswer answer = paramWrapper.getParam(PageAnswer.class);
        ResponseParams responseParams = new ResponseParams();
        String ans = JsonUtils.encode(answer);
        responseParams.setData(ans);
        return responseParams;
    }
}
