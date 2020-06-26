package org.mouter.admin;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.mintflow.MintFlow;
import org.mintflow.handler.MintFlowHandlerMapper;
import org.mintflow.handler.util.MintFlowHandlerMapperFinder;
import org.mintflow.vertx.route.Router;
import org.mouter.admin.dataBase.MysqlPool;
import org.mouter.admin.router.ApplicationRouterAdder;

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
        new ApplicationRouterAdder().add(router);
        MysqlPool.mysql= MysqlPool.getPool(vertx);
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
