package org.config.center.mapper;

import org.apache.ibatis.annotations.*;
import org.config.center.core.bean.ConfigBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ConfigMapper {

    @Insert("insert into config (namespace,cluter,key,value,namespace_token,cluter_token,key_token,status)" +
            " values (#{namespace},#{cluter},#{key},#{value},#{namespace_token},#{cluter_token},#{key_token},#{status}))")
    void insertConfig(ConfigBean configBean);

    @Select("select count(0) from config where key in (#{key} )")
    int findConfigListSize(@Param("key") String keyName);

    @Select("select * from config where key ='%#{key}%' limit #{limit},#[size]")
    List<ConfigBean> findConfigList(@Param("key")String keyName,@Param("limit")int limit,@Param("size")int size);

    @Select("update config set status = #{s} where keyName = #{key}")
    void updateConfigStatus(@Param("key") String keyName, @Param("s") String s);

    @Delete("delete from confign where key = #{key}")
    void delete(@Param("key") String name);

    @Update("update config set value = #{value} where key = #{key}")
    void updateConfig(ConfigBean configBean);

    @Select("select * from config")
    List<ConfigBean> findAllConfigList();
}
