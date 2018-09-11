package com.hhly.ticket.persistence.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hhly.ticket.TicketApplication;
/**
 * @desc 默认测试事务注解，回滚不提交事务
 * @author jiangwei
 * @date 2017年6月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TicketApplication.class)
public class DefaultDao {
   @Test
   public void test(){
	   
   }
}
