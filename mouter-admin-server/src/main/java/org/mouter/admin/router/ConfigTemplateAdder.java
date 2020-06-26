package org.mouter.admin.router;

import io.vertx.core.MultiMap;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.route.Router;
import org.mouter.admin.data.ConfigTemplateData;
import org.mouter.admin.data.ConfigTemplateData;
import org.mouter.admin.data.PageAble;
import org.mouter.admin.handler.dataHandler.configTemplate.DeleteConfigTemplateAsyncHandler;
import org.mouter.admin.handler.dataHandler.configTemplate.GetConfigTemplateAsyncHandler;
import org.mouter.admin.handler.dataHandler.configTemplate.InsertConfigTemplateAsyncHandler;
import org.mouter.admin.handler.dataHandler.configTemplate.UpdateConfigTemplateAsyncHandler;
import org.mouter.admin.util.JsonUtils;

public class ConfigTemplateAdder {
    public void add(Router router){
        router.addRoute("/delete_config_template","application","delete_config_template",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ConfigTemplateData applicationInformationData = new ConfigTemplateData();
            String appId = multiMap.get("app_id");
            applicationInformationData.setAppId(appId==null?null:Long.valueOf(appId));
            String groupId = multiMap.get("group_id");
            applicationInformationData.setGroupId(groupId==null?null:Long.valueOf(groupId));
            String templateId = multiMap.get("template_id");
            applicationInformationData.setTemplateId(groupId==null?null:Long.valueOf(templateId));
            paramWrapper.setContextParam(DeleteConfigTemplateAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
        router.addRoute("/get_config_template","application","get_config_template",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ConfigTemplateData applicationInformationData = new ConfigTemplateData();
            String appId = multiMap.get("app_id");
            applicationInformationData.setAppId(appId==null?null:Long.valueOf(appId));
            String groupId = multiMap.get("group_id");
            applicationInformationData.setGroupId(groupId==null?null:Long.valueOf(groupId));
            String templateId = multiMap.get("template_id");
            applicationInformationData.setTemplateId(groupId==null?null:Long.valueOf(templateId));
            PageAble pageAble = new PageAble();
            int page = Integer.parseInt(multiMap.get("page"));
            pageAble.setPage(page*10);
            pageAble.setSize(10);
            applicationInformationData.setPage(pageAble);
            paramWrapper.setContextParam(GetConfigTemplateAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SamplePageResponse.samplePageResponse);
        router.addRoute("/insert_config_template","application","insert_config_template",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ConfigTemplateData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ConfigTemplateData.class);
            paramWrapper.setContextParam(InsertConfigTemplateAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
        router.addRoute("/update_config_template","application","update_config_template",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ConfigTemplateData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ConfigTemplateData.class);
            paramWrapper.setContextParam(UpdateConfigTemplateAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        }, SampleResponse.sampleResponse);
    }
}
