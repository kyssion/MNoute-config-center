package org.mouter.admin.handler.dataHandler.application;

import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Transaction;
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

import java.util.List;

import static org.mouter.admin.handler.dataHandler.application.GetApplicationAsyncHandler.getDataFrom;

@MintFlowHandler
public class UpdateApplicationAsyncHandler extends AsyncSampleFnHandler {


    public static String SQL_DATA_KEY = "data.update.application";
    public static String SQL_DATA_RESULT_KEY = "data.update.application.result";

    public UpdateApplicationAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        MysqlPool.mysql.getConnection((res)->{
            if(res.succeeded()){
                ApplicationInformationData appData = paramWrapper.getContextParam(SQL_DATA_KEY);
                //如果 appId或者 group id 没有填写直接返回报错
                if(ObjectUtils.isNullOrEmpty(appData.getAppId(),appData.getGroupId())){
                    paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"新建应用请求参数异常，需要appId和 groupId")));
                    asyncResult.doResult(paramWrapper);
                    return;
                }
                SqlConnection connection = res.result();
                Transaction tx = connection.begin();
                connection.preparedQuery("select * from application_information where group_id=? and app_id =?")
                        .execute(Tuple.of(appData.getGroupId(),appData.getAppId()),(result)->{
                            if(result.succeeded()){
                                List<ApplicationInformationData> applicationInformationDatas = null;
                                try {
                                    applicationInformationDatas = getDataFrom(result.result());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                    paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"搜索应用信息，数据合并失败")));
                                    asyncResult.doResult(paramWrapper);
                                    return;
                                }
                                ApplicationInformationData appDataUpdate = applicationInformationDatas.get(0);
                                try {
                                    ObjectUtils.mergeObject(appDataUpdate, appData);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                    paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"更新应用信息，数据合并失败")));
                                    asyncResult.doResult(paramWrapper);
                                    return;
                                }
                                connection.preparedQuery("update application_information  set app_name=?,update_item=?,update_user=? where group_id=? and app_id=?")
                                        .execute(Tuple.of("P",System.currentTimeMillis(),appDataUpdate.getUpdateUser(),90000,90000),r->{
                                            if(r.succeeded()){
                                                tx.commit((vo)->{
                                                    if(vo.succeeded()) {
                                                        paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, Boolean.TRUE);
                                                        paramWrapper.setParam(Answer.createAnswer(200, "success", null));
                                                        asyncScheduler.next(paramWrapper, asyncResult);
                                                    }else{
                                                        vo.cause().printStackTrace();
                                                        paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"更新应用信息，数据合并失败")));
                                                        asyncResult.doResult(paramWrapper);
                                                        return;
                                                    }
                                                });
                                            }else{
                                                r.cause().printStackTrace();
                                            }
                                            connection.close();
                                        });
                            }
                        });
            }
        });
    }
}
