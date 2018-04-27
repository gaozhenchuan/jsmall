package org.jsmall.dao.master;

import org.jsmall.dao.master.dto.UserInfoDto;

public interface IUserDao {

    /**
     * <dd>概要：
     * <dd>詳細：
     * <dd>備考：
     * @param 
     * @return int 件数
     */
    int getUserCount(int userId);
    
    UserInfoDto getUserInfo(int userId);
}
