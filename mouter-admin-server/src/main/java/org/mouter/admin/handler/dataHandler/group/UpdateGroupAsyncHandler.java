package org.mouter.admin.handler.dataHandler.group;

import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Transaction;
import io.vertx.sqlclient.Tuple;
import org.mintflow.annotation.MintFlowHandler;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mouter.admin.code.ErrorCode;
import org.mouter.admin.data.ConfigTemplateData;
import org.mouter.admin.data.GroupInformationData;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.data.answer.ErrorAnser;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.util.ObjectUtils;

import java.util.List;

@MintFlowHandler
public class UpdateGroupAsyncHandler extends AsyncSampleFnHandler {

    public static String SQL_DATA_KEY = "data.insert.group";
    public static String SQL_DATA_RESULT_KEY = "data.insert.application.group";

    public UpdateGroupAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        MysqlPool.mysql.getConnection((res)->{
            if(res.succeeded()){
                ConfigTemplateData appData = paramWrapper.getContextParam(SQL_DATA_KEY);
                //如果 appId或者 group id 没有填写直接返回报错
                if(ObjectUtils.isNullOrEmpty(appData.getAppId(),appData.getGroupId(),appData.getTemplateId())){
                    paramWrapper.setParam(Answer.createAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"新建应用请求参数异常，需要appId和 groupId")));
                    asyncResult.doResult(paramWrapper);
                    return;
                }
                SqlConnection connection = res.result();
                Transaction tx = connection.begin();
                connection.preparedQuery("select * from group_information where group_id=? ")
                        .execute(Tuple.of(appData.getGroupId()),(result)->{
                            if(result.succeeded()){
                                List<GroupInformationData> configInformationData = null;
                                configInformationData = ObjectUtils.getDataFrom(result.result(),GroupInformationData.class);
                                GroupInformationData appDataUpdate = configInformationData.get(0);
                                ObjectUtils.mergeObject(appDataUpdate, appData);
                                connection.preparedQuery("update group_information set group_name = ?,update_item=?,update_user=?  where group_id=?")
                                        .execute(Tuple.of(appDataUpdate.getGroupName(),System.currentTimeMillis(),appDataUpdate.getUpdateUser(),appDataUpdate.getGroupId()),r->{
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
