package org.jsmall.dao.common.ulogin;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class IUserMapperImpl extends SqlSessionDaoSupport implements IUserMapper {
	public int getUserCount(int userId) {
		return super.getSqlSession().selectOne("org.jsmall.dao.common.ulogin.IUserMapper.getUserCount", userId); 
	}
}
