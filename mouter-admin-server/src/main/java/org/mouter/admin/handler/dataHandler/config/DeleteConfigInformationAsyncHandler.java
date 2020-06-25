package org.mouter.admin.handler.dataHandler.config;

import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;

@MintFlowHandler
public class DeleteConfigInformationAsyncHandler extends AsyncSampleFnHandler {
    public DeleteConfigInformationAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {

    }
}
