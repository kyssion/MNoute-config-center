package org.mouter.admin.handler.dataHandler.config;

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
import org.mouter.admin.data.PageAble;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.data.answer.ConfigInformationData;
import org.mouter.admin.data.answer.ErrorAnser;
import org.mouter.admin.data.answer.PageAnswer;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@MintFlowHandler
public class GetConfigInformationAsyncHandler extends AsyncSampleFnHandler {

    public static String SQL_DATA_KEY = "data.get.config";
    public static String SQL_DATA_RESULT_KEY = "data.get.config.result";

    public GetConfigInformationAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        MysqlPool.mysql.getConnection((res)->{
            if(res.succeeded()){
                ConfigInformationData appdata = paramWrapper.getContextParam(SQL_DATA_KEY);
                //如果 appId或者 group id 没有填写直接返回报错
                if(ObjectUtils.isNullOrEmpty(appdata.getGroupId(),appdata.getAppId())){
                    paramWrapper.setParam(PageAnswer.createPageAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"搜索配置请求参数异常，必须要有groupId 和 appId"),null));
                    asyncResult.doResult(paramWrapper);
                    return;
                }

                SqlConnection connection = res.result();
                if(ObjectUtils.isNullOrEmpty(appdata.getAppId())){
                    connection.preparedQuery("select * from config_information where group_id=? and app_id=? limit ?,?")
                            .execute(Tuple.of(appdata.getGroupId(),appdata.getAppId(),appdata.getPage().getPage(),appdata.getPage().getSize()),(result)->{
                                if(result.succeeded()){
                                    connection.preparedQuery("select count(id) as num from application_information where group_id=? and app_id=? limit ?,?")
                                            .execute(Tuple.of(appdata.getGroupId(),appdata.getAppId(),appdata.getPage().getPage(),appdata.getPage().getSize()),(r->{
                                        if(r.succeeded()){
                                            List<ConfigInformationData> applicationInformationData = ObjectUtils.getDataFrom(result.result(),ConfigInformationData.class);
                                            RowSet<Row> rows = r.result();
                                            Integer integer = rows.iterator().next().getInteger("num");
                                            PageAble pageAble = new PageAble();
                                            pageAble.setPage(appdata.getPage().getPage());
                                            pageAble.setSize(appdata.getPage().getSize());
                                            pageAble.setAll((int) Math.ceil((double)integer/appdata.getPage().getSize()));
                                            PageAnswer pageAnswer =PageAnswer.createPageAnswer(200,"succuess",applicationInformationData,pageAble);
                                            paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, pageAnswer);
                                            paramWrapper.setParam(pageAnswer);
                                            asyncScheduler.next(paramWrapper,asyncResult);
                                        }
                                        connection.close();
                                    }));
                                }
                            });
                }else{
                    connection.preparedQuery("select * from application_information where group_id=? and app_id =? and config_id=? limit ?,?")
                            .execute(Tuple.of(appdata.getGroupId(),appdata.getAppId(),appdata.getConfigId(),appdata.getPage().getPage(),appdata.getPage().getSize()),(result)->{
                                if(result.succeeded()){
                                    connection.preparedQuery("select count(id) as num from application_information where group_id=? and app_id=? and config_id=? limit ?,?").execute(
                                            Tuple.of(appdata.getGroupId(),appdata.getAppId(),appdata.getConfigId(),appdata.getPage().getPage(),appdata.getPage().getSize()),r->{
                                        if(r.succeeded()){
                                            List<ConfigInformationData> applicationInformationData = ObjectUtils.getDataFrom(result.result(),ConfigInformationData.class);
                                            RowSet<Row> rows = r.result();
                                            Integer integer = rows.iterator().next().getInteger("num");
                                            PageAble pageAble = new PageAble();
                                            pageAble.setPage(appdata.getPage().getPage());
                                            pageAble.setSize(appdata.getPage().getSize());
                                            pageAble.setAll((int) Math.ceil((double)integer/appdata.getPage().getSize()));
                                            PageAnswer pageAnswer =PageAnswer.createPageAnswer(200,"succuess",applicationInformationData,pageAble);
                                            paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, pageAnswer);
                                            paramWrapper.setParam(pageAnswer);
                                            asyncScheduler.next(paramWrapper,asyncResult);
                                        }
                                        connection.close();
                                    });
                                }
                            });
                }

            }
        });
    }
}
