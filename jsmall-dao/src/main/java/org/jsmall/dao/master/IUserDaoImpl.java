package org.jsmall.dao.master;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

public class IUserDaoImpl extends SqlSessionDaoSupport implements IUserDao {

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
       super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    public int getUserCount(int userId) {
        return this.getSqlSession().selectOne("org.jsmall.dao.master.IUserDao.getUserCount", userId);
    }
}
