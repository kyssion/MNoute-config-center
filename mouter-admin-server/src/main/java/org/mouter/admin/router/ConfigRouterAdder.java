package org.mouter.admin.router;

import io.vertx.core.MultiMap;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.route.Router;
import org.mouter.admin.data.ApplicationInformationData;
import org.mouter.admin.data.ConfigInformationData;
import org.mouter.admin.data.PageAble;
import org.mouter.admin.handler.dataHandler.application.DeleteApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.GetApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.InsertApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.UpdateApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.config.DeleteConfigInformationAsyncHandler;
import org.mouter.admin.handler.dataHandler.config.GetConfigInformationAsyncHandler;
import org.mouter.admin.handler.dataHandler.config.InsertConfigInformationAsyncHandler;
import org.mouter.admin.handler.dataHandler.config.UpdateConfigInformationAsyncHandler;
import org.mouter.admin.util.JsonUtils;

public class ConfigRouterAdder {
    public void add(Router router){
        router.addRoute("/delete_config","application","delete_config",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ConfigInformationData applicationInformationData = new ConfigInformationData();
            String appId = multiMap.get("app_id");
            applicationInformationData.setAppId(appId==null?null:Long.valueOf(appId));
            String groupId = multiMap.get("group_id");
            applicationInformationData.setGroupId(groupId==null?null:Long.valueOf(groupId));
            String configId = multiMap.get("config_id");
            applicationInformationData.setConfigId(groupId==null?null:Long.valueOf(configId));
            paramWrapper.setContextParam(DeleteConfigInformationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
        router.addRoute("/get_config","application","get_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ConfigInformationData applicationInformationData = new ConfigInformationData();
            String appId = multiMap.get("app_id");
            applicationInformationData.setAppId(appId==null?null:Long.valueOf(appId));
            String groupId = multiMap.get("group_id");
            applicationInformationData.setGroupId(groupId==null?null:Long.valueOf(groupId));
            String configId = multiMap.get("config_id");
            applicationInformationData.setConfigId(groupId==null?null:Long.valueOf(configId));
            PageAble pageAble = new PageAble();
            int page = Integer.parseInt(multiMap.get("page"));
            pageAble.setPage(page*10);
            pageAble.setSize(10);
            applicationInformationData.setPage(pageAble);
            paramWrapper.setContextParam(GetConfigInformationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SamplePageResponse.samplePageResponse);
        router.addRoute("/insert_config","application","insert_config",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ConfigInformationData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ConfigInformationData.class);
            paramWrapper.setContextParam(InsertConfigInformationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
        router.addRoute("/update_config","application","update_config",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ConfigInformationData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ConfigInformationData.class);
            paramWrapper.setContextParam(UpdateConfigInformationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
    }
}
