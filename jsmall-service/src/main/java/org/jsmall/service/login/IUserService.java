package org.jsmall.service.login;

public interface IUserService {

    public boolean getUserCount(int userId);
    
    public boolean isLogin(String userId, String password);
}
