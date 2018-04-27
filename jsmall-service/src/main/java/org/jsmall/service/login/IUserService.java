package org.jsmall.service.login;

import org.jsmall.dao.master.dto.UserInfoDto;

public interface IUserService {

    public boolean getUserCount(int userId);

    public UserInfoDto getUserInfo(int userId);
    
    public boolean isLogin(String userId, String password);
}
