package org.jsmall.dao.master;

import java.util.List;

import org.jsmall.dao.entity.User;

public interface IUsersDao {

    /** 
     * 按姓名查询
     * 
     * @param username 用户名称
     * @return list 用户列表
     */
    public List<User> findUsersByName(String username) throws Exception;

    /** 
     * 查询所有用户
     * 
     * @return list 用户列表
     */
    public List<User> findAllUsers() throws Exception;

    /** 
     * 按用户ID查询
     * 
     * @param userId 用户ID
     * @return user 用户信息
     */
    public User findUsersById(int userId)throws Exception;

    /** 
     * 修改操作
     * 
     * @param username 用户ID
     * @return list 用户列表
     */
    public int UsersUpdate(User user)throws Exception;

    /** 
     * 增加操作
     * 
     * @param username 用户ID
     * @return list 用户列表
     */
    public int doInsert(User user)throws Exception;
    //修改操作
    //public boolean doUpdate(Person per)throws Exception;
    //删除操作
    //public boolean doDelete(int id)throws Exception;
    //按id查询
    //public Person findById(int id)throws Exception;
    //查询全部
    //public List<Product> findAllProduct() throws Exception;
    //模糊查询
    //public List<Product> findByLike(String cond)throws Exception;
}
