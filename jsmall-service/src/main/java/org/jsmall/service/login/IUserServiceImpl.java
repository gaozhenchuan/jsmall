package org.jsmall.service.login;

import org.jsmall.dao.master.IUserDao;
import org.jsmall.dao.master.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired(required = true)
    private transient IUserDao iUserDao;

    public boolean getUserCount(int userId) {
        return iUserDao.getUserCount(userId) > 0;
    }

	public boolean isLogin(String userId, String password) {
		return false;
	}

	public UserInfoDto getUserInfo(int userId) {
		return new UserInfoDto();
	}
}
