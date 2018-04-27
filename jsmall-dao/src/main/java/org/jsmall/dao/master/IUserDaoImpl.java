package org.jsmall.dao.master;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.jsmall.dao.master.dto.UserInfoDto;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class IUserDaoImpl extends SqlSessionDaoSupport implements IUserDao {

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
       super.setSqlSessionFactory(sqlSessionFactory);
    }

    public int getUserCount(int userId) {
        return this.getSqlSession().selectOne("org.jsmall.dao.master.IUserDao.getUserCount", userId);
    }

    public UserInfoDto getUserInfo(int userId) {
        return this.getSqlSession().selectOne("org.jsmall.dao.master.IUserDao.getUserInfo", userId);
    }
}
