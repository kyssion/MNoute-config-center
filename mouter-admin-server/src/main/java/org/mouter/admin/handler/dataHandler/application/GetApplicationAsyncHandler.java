package org.mouter.admin.handler.dataHandler.application;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mouter.admin.code.ErrorCode;
import org.mouter.admin.data.ApplicationInformationData;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.data.answer.ErrorAnser;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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
                //如果 appId或者 group id 没有填写直接返回报错
                if(ObjectUtils.isNullOrEmpty(appdata.getGroupId())){
                    paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"搜索应用请求参数异常，必须要有groupId")));
                    asyncResult.doResult(paramWrapper);
                    return;
                }

                SqlConnection connection = res.result();
                if(ObjectUtils.isNullOrEmpty(appdata.getAppId())){
                    connection.preparedQuery("selsec * from application_information where group_id=?")
                            .execute(Tuple.of(appdata.getGroupId()),(result)->{
                                if(result.succeeded()){
                                    paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, Boolean.TRUE);
                                    paramWrapper.setParam(Answer.createAnswer(200,"success",null));
                                    asyncScheduler.next(paramWrapper,asyncResult);
                                }
                                connection.close();
                            });
                }else{
                    connection.preparedQuery("select * from application_information where group_id=? and app_id =?")
                            .execute(Tuple.of(appdata.getGroupId(),appdata.getAppId()),(result)->{
                                if(result.succeeded()){
                                    List<ApplicationInformationData> applicationInformationData = null;
                                    try {
                                        applicationInformationData =
                                                getDataFrom(result.result());
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                        paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"搜索应用信息，数据合并失败")));
                                        asyncResult.doResult(paramWrapper);
                                        return;
                                    }
                                    paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, applicationInformationData);
                                    paramWrapper.setParam(Answer.createAnswer(200,"success",applicationInformationData));
                                    asyncScheduler.next(paramWrapper,asyncResult);
                                }
                                connection.close();
                            });
                }

            }
        });
    }

    public static List<ApplicationInformationData> getDataFrom(RowSet<Row> rowSet) throws IllegalAccessException {
        List<ApplicationInformationData> appList =
                new ArrayList<>();
        for(Row row:rowSet){
            ApplicationInformationData appData = ObjectUtils.mergeRow(row,new ApplicationInformationData());
            appList.add(appData);
        }
        return appList;
    }
}
