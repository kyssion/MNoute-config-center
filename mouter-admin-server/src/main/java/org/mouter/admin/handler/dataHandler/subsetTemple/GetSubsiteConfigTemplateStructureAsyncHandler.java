package org.mouter.admin.handler.dataHandler.subsetTemple;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.async.AsyncSampleFnHandler;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mouter.admin.code.ErrorCode;
import org.mouter.admin.data.PageAble;
import org.mouter.admin.data.SudsetConfigTemplateData;
import org.mouter.admin.data.SudsetConfigTemplateStructureData;
import org.mouter.admin.data.answer.ErrorAnser;
import org.mouter.admin.data.answer.PageAnswer;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.util.ObjectUtils;

import java.util.List;

public class GetSubsiteConfigTemplateStructureAsyncHandler extends AsyncSampleFnHandler {
    public static String SQL_DATA_KEY = "data.get.config";
    public static String SQL_DATA_RESULT_KEY = "data.get.application.config";


    public GetSubsiteConfigTemplateStructureAsyncHandler(String name) {
        super(name);
    }

    @Override
    public void asyncHandle(ParamWrapper paramWrapper, AsyncResult asyncResult, AsyncScheduler asyncScheduler) {
        MysqlPool.mysql.getConnection((res)-> {
            if (res.succeeded()) {
                SudsetConfigTemplateStructureData appdata = paramWrapper.getContextParam(SQL_DATA_KEY);
                //如果 appId或者 group id 没有填写直接返回报错
                if (ObjectUtils.isNullOrEmpty(appdata.getSudsetTemplateId())) {
                    paramWrapper.setParam(PageAnswer.createPageAnswer(200, "success", new ErrorAnser(ErrorCode.PARAMS_ERROR, "搜索配置请求参数异常，必须要有groupId 和 appId"), null));
                    asyncResult.doResult(paramWrapper);
                    return;
                }
                SqlConnection connection = res.result();
                connection.preparedQuery("select * from config_subset_template_structure where subset_template_id=? limit ?,?")
                        .execute(Tuple.of(appdata.getSudsetTemplateId(),appdata.getPage().getPage(), appdata.getPage().getSize()), (result) -> {
                            if (result.succeeded()) {
                                connection.preparedQuery("select count(*) as num from config_subset_template_structure where subset_template_id=? limit ?,?")
                                        .execute(Tuple.of(appdata.getSudsetTemplateId(),appdata.getPage().getPage(), appdata.getPage().getSize()), (r -> {
                                            if (r.succeeded()) {
                                                List<SudsetConfigTemplateStructureData> applicationInformationData = ObjectUtils.getDataFrom(result.result(), SudsetConfigTemplateStructureData.class);
                                                RowSet<Row> rows = r.result();
                                                Integer integer = rows.iterator().next().getInteger("num");
                                                PageAble pageAble = new PageAble();
                                                pageAble.setPage(appdata.getPage().getPage());
                                                pageAble.setSize(appdata.getPage().getSize());
                                                pageAble.setAll((int) Math.ceil((double) integer / appdata.getPage().getSize()));
                                                PageAnswer pageAnswer = PageAnswer.createPageAnswer(200, "succuess", applicationInformationData, pageAble);
                                                paramWrapper.setContextParam(SQL_DATA_RESULT_KEY, pageAnswer);
                                                paramWrapper.setParam(pageAnswer);
                                                asyncScheduler.next(paramWrapper, asyncResult);
                                            }
                                            connection.close();
                                        }));
                            }
                        });
            }
        });
    }
}
