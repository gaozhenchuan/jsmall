package org.jsmall.service.mail.entity;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -4313168266756007725L;

    /**
     * UserId
     * @see String
     */
    String userId = null;

    /**
     * PassWord
     * @see String
     */
    String password = null;

    /**
     * <dd>概要：设定用户信息
     * <dd>详细：设定用户信息
     * <dd>备注：
     * @param  userId
     * @param  password
     */
    public MyAuthenticator(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /**
     * <dd>概要：取得SMTP认证信息
     * <dd>详细：取得SMTP认证信息
     * <dd>备注：
     * @return  PasswordAuthentication
     */
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userId, password);
    }
}
