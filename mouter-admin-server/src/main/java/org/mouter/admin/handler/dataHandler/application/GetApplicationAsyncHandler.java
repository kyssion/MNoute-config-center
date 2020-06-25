package org.mouter.admin.handler.dataHandler.application;

import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mouter.admin.data.ApplicationInformationData;
import org.mouter.admin.dataBase.MysqlPool;

@MintFlowHandler
public class GetApplicationAsyncHandler extends AsyncSampleFnHandler {


    public static String SQL_DATA_KEY = "data.get.application";
    public static String SQL_DATA_RESULT_KEY = "data.get.application.result";

    public GetApplicationAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        MysqlPool.mysql.getConnection((res)->{
            if(res.succeeded()){
                ApplicationInformationData appdata = paramWrapper.getContextParam(SQL_DATA_KEY);
                SqlConnection connection = res.result();
                connection.preparedQuery("select from application_information where group_id=? and app_id =?")
                        .execute(Tuple.of(appdata.getGroupId(),appdata.getAppId()),(result)->{
                            if(result.succeeded()){
                                paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, Boolean.TRUE);
                                asyncScheduler.next(paramWrapper,asyncResult);
                            }
                        });
            }
        });
    }
}
