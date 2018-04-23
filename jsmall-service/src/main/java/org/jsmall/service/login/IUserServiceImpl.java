package org.jsmall.service.login;

import org.jsmall.dao.master.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired(required = true)
    private transient IUserDao iUserDao;
    
//    public boolean getUserCount(int userId) {
//        // TODO Auto-generated method stub
//        return true;
//    }
//    
//
    public boolean getUserCount(int userId) {
        // TODO Auto-generated method stub
        return iUserDao.getUserCount(userId) > 0;
    }

	public boolean isLogin(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}
}
