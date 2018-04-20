package org.jsmall.dao.master;

import java.util.List;

import org.jsmall.dao.master.entity.Code;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class CodeMstDaoImpl extends SqlSessionDaoSupport implements CodeMstDao {

    public List<Code> selectByCodeSetId(String key) {
        return this.getSqlSession().selectOne("org.jsmall.dao.master.IUserDao.getUserCount", key);
    }

    public int insert(Code paramCodeMst) {
        return 0;
    }
    
    public int update(Code paramCodeMst) {
        return 0;
    }

	public Code selectByPrimaryKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
