package org.mouter.admin.handler.dataHandler.group;

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
import org.mouter.admin.data.ConfigInformationData;
import org.mouter.admin.data.ConfigTemplateData;
import org.mouter.admin.data.GroupInformationData;
import org.mouter.admin.data.PageAble;
import org.mouter.admin.data.answer.ErrorAnser;
import org.mouter.admin.data.answer.PageAnswer;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.util.ObjectUtils;

import java.util.List;

@MintFlowHandler
public class GetGroupAsyncHandler extends AsyncSampleFnHandler {


    public static String SQL_DATA_KEY = "data.get.group";
    public static String SQL_DATA_RESULT_KEY = "data.get.application.group";

    public GetGroupAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        MysqlPool.mysql.getConnection((res)->{
            if(res.succeeded()){
                GroupInformationData appdata = paramWrapper.getContextParam(SQL_DATA_KEY);
                //如果 appId或者 group id 没有填写直接返回报错
                if(ObjectUtils.isNullOrEmpty(appdata.getGroupId())){
                    paramWrapper.setParam(PageAnswer.createPageAnswer(200,"success",new ErrorAnser(ErrorCode.PARAMS_ERROR,"搜索配置请求参数异常，必须要有groupId 和 appId"),null));
                    asyncResult.doResult(paramWrapper);
                    return;
                }

                SqlConnection connection = res.result();
                connection.preparedQuery("select * from group_information where group_id = ? limit ?,?")
                        .execute(Tuple.of(appdata.getGroupId(),appdata.getPage().getPage(),appdata.getPage().getSize()),(result)->{
                            if(result.succeeded()){
                                connection.preparedQuery("select count(id) as num from group_information where group_id = ?")
                                        .execute(Tuple.of(appdata.getGroupId(),appdata.getPage().getPage(),appdata.getPage().getSize()),(r->{
                                            if(r.succeeded()){
                                                List<GroupInformationData> applicationInformationData = ObjectUtils.getDataFrom(result.result(),GroupInformationData.class);
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
                }
        });
    }
}
