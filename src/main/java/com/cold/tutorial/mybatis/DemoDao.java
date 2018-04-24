package com.cold.tutorial.mybatis;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author liaowenhui
 * @date 2016/6/22 19:50.
 */
@Repository
public interface DemoDao {

    void insert(@Param("topic") String topic, @Param("tag") String tag, @Param("key") String key);
}
