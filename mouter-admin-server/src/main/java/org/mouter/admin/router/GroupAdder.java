package org.mouter.admin.router;

import io.vertx.core.MultiMap;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.route.Router;
import org.mouter.admin.data.ApplicationInformationData;
import org.mouter.admin.data.PageAble;
import org.mouter.admin.handler.dataHandler.application.DeleteApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.GetApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.InsertApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.UpdateApplicationAsyncHandler;
import org.mouter.admin.util.JsonUtils;

public class GroupAdder {
    public void add(Router router){
        router.addRoute("/delete_application","application","delete_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ApplicationInformationData applicationInformationData = new ApplicationInformationData();
            String appId = multiMap.get("app_id");
            applicationInformationData.setAppId(appId==null?null:Long.valueOf(appId));
            String groupId = multiMap.get("group_id");
            applicationInformationData.setGroupId(groupId==null?null:Long.valueOf(groupId));
            paramWrapper.setContextParam(DeleteApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
        router.addRoute("/get_application","application","get_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ApplicationInformationData applicationInformationData = new ApplicationInformationData();
            String appId = multiMap.get("app_id");
            applicationInformationData.setAppId(appId==null?null:Long.valueOf(appId));
            String groupId = multiMap.get("group_id");
            applicationInformationData.setGroupId(groupId==null?null:Long.valueOf(groupId));
            PageAble pageAble = new PageAble();
            int page = Integer.parseInt(multiMap.get("page"));
            pageAble.setPage(page*10);
            pageAble.setSize(10);
            applicationInformationData.setPage(pageAble);
            paramWrapper.setContextParam(GetApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SamplePageResponse.samplePageResponse);
        router.addRoute("/insert_application","application","insert_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ApplicationInformationData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ApplicationInformationData.class);
            paramWrapper.setContextParam(InsertApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
        router.addRoute("/update_application","application","update_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ApplicationInformationData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ApplicationInformationData.class);
            paramWrapper.setContextParam(UpdateApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
    }
}
