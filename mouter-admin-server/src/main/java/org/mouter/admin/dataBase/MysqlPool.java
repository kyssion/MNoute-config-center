package org.mouter.admin.dataBase;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

public class MysqlPool {
    public static MySQLPool mysql;

    public static MySQLPool getPool(Vertx vertx){
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("192.168.43.192")
                .setDatabase("mount-config-admin")
                .setUser("kyssion")
                .setPassword("14159265jkl");

        // Pool options
        PoolOptions poolOptions = new PoolOptions();

        // Create the pooled client
        return MySQLPool.pool(vertx,connectOptions, poolOptions);
    }
}
