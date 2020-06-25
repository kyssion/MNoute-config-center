package org.mouter.admin.handler.dataHandler.group;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

@MintFlowHandler
public class InsertGroupAsyncHandler extends AsyncSampleFnHandler {
    public InsertGroupAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {

    }
}
