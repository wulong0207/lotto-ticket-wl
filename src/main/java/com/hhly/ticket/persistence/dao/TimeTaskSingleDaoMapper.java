package com.hhly.ticket.persistence.dao;

import org.apache.ibatis.annotations.Param;

public interface TimeTaskSingleDaoMapper {
    /**
     * 修改定时任务执行时间
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年7月18日 下午4:50:47
     * @param project
     * @param name
     * @param serviceIp
     * @return
     */
	int updateExecute(@Param("project")String project, @Param("name")String name,@Param("serviceIp") String serviceIp);
 
}
