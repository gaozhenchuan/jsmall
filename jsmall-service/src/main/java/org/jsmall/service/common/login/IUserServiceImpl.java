package org.jsmall.service.common.login;

import org.jsmall.dao.common.ulogin.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService {

//	public boolean getUserCount(int userId) {
//		// TODO Auto-generated method stub
//		return true;
//	}

	
	@Autowired
	private transient IUserMapper iUserMapper;
	

	public boolean getUserCount(int userId) {
		// TODO Auto-generated method stub
		return iUserMapper.getUserCount(userId) > 0;
	}
}
