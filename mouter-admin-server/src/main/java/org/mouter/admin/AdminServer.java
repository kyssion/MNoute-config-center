package org.mouter.admin;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.mintflow.MintFlow;
import org.mintflow.handler.MintFlowHandlerMapper;
import org.mintflow.handler.util.MintFlowHandlerMapperFinder;
import org.mintflow.param.ParamWrapper;
import org.mintflow.vertx.param.ResponseParams;
import org.mintflow.vertx.route.Router;
import org.mouter.admin.data.ApplicationInformationData;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.handler.dataHandler.application.DeleteApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.GetApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.InsertApplicationAsyncHandler;
import org.mouter.admin.handler.dataHandler.application.UpdateApplicationAsyncHandler;
import org.mouter.admin.util.JsonUtils;

public class AdminServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        server.exceptionHandler(throwable -> {
            throwable.printStackTrace();
        });
        MintFlowHandlerMapper mapper = MintFlowHandlerMapperFinder.findHandlerDataMap("org.mouter.admin.handler");
        MintFlow mintFlow = MintFlow.newBuilder(mapper).addFnMapper("./fn/application.fn").build();
        Router router = new Router(mintFlow);
       MysqlPool.mysql= MysqlPool.getPool(vertx);
       router.addRoute("/delete_application","application","delete_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ApplicationInformationData applicationInformationData = new ApplicationInformationData();
            applicationInformationData.setAppId(Long.valueOf(multiMap.get("app_id")));
            applicationInformationData.setGroupId(Long.valueOf(multiMap.get("group_id")));
            paramWrapper.setContextParam(DeleteApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        },paramWrapper -> {
            Answer answer = paramWrapper.getParam(Answer.class);
            ResponseParams responseParams = new ResponseParams();
            String ans = JsonUtils.encode(answer);
            responseParams.setData(ans);
            return responseParams;
        });
        router.addRoute("/delete_application","application","delete_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ApplicationInformationData applicationInformationData = new ApplicationInformationData();
            applicationInformationData.setAppId(Long.valueOf(multiMap.get("app_id")));
            applicationInformationData.setGroupId(Long.valueOf(multiMap.get("group_id")));
            paramWrapper.setContextParam(DeleteApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        },paramWrapper -> {
            Answer answer = paramWrapper.getParam(Answer.class);
            ResponseParams responseParams = new ResponseParams();
            String ans = JsonUtils.encode(answer);
            responseParams.setData(ans);
            return responseParams;
        });
        router.addRoute("/get_application","application","get_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ApplicationInformationData applicationInformationData = new ApplicationInformationData();
            applicationInformationData.setAppId(Long.valueOf(multiMap.get("app_id")));
            applicationInformationData.setGroupId(Long.valueOf(multiMap.get("group_id")));
            paramWrapper.setContextParam(GetApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        },paramWrapper -> {
            Answer answer = paramWrapper.getParam(Answer.class);
            ResponseParams responseParams = new ResponseParams();
            String ans = JsonUtils.encode(answer);
            responseParams.setData(ans);
            return responseParams;
        });
        router.addRoute("/insert_application","application","insert_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            ApplicationInformationData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ApplicationInformationData.class);
            paramWrapper.setContextParam(InsertApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        },paramWrapper -> {
            Answer answer = paramWrapper.getParam(Answer.class);
            ResponseParams responseParams = new ResponseParams();
            String ans = JsonUtils.encode(answer);
            responseParams.setData(ans);
            return responseParams;
        });
        router.addRoute("/update_application","application","update_application",requestParam -> {
            ParamWrapper paramWrapper = new ParamWrapper();
            MultiMap multiMap = requestParam.getParams();
            ApplicationInformationData applicationInformationData = JsonUtils.decode(requestParam.getBody(),ApplicationInformationData.class);
            paramWrapper.setContextParam(UpdateApplicationAsyncHandler.SQL_DATA_KEY,applicationInformationData);
            return paramWrapper;
        },paramWrapper -> {
            Answer answer = paramWrapper.getParam(Answer.class);
            ResponseParams responseParams = new ResponseParams();
            String ans = JsonUtils.encode(answer);
            responseParams.setData(ans);
            return responseParams;
        });
        server.requestHandler(router);
        server.listen(8080,  res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }
}
