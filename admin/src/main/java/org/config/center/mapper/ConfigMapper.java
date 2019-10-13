package org.config.center.mapper;

import org.apache.ibatis.annotations.*;
import org.config.center.core.bean.ConfigBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ConfigMapper {

    @Insert("insert into config (namespace,cluter,`key`,value,status) values (#{namespace},#{cluter},#{key},#{value},#{status})")
    void insertConfig(ConfigBean configBean);

    @Select("select count(0) from config where `key` like #{key}")
    int findConfigListSize(@Param("key") String keyName);

    @Select("select * from config where `key` like #{key} limit #{limit},#{size}")
    List<ConfigBean> findConfigList(@Param("key")String keyName,@Param("limit")int limit,@Param("size")int size);

    @Select("update config set status = #{s} where `key` = #{key}")
    void updateConfigStatus(@Param("key") String keyName, @Param("s") String s);

    @Delete("delete from config where `key` = #{key}")
    void delete(@Param("key") String name);

    @Update("update config set value = #{value} where `key` = #{key}")
    void updateConfig(@Param("key") String key,@Param("value") String value);

    @Select("select * from config")
    List<ConfigBean> findAllConfigList();
}
