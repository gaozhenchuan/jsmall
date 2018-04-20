package org.jsmall.dao.master.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Code  implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = 1133011788093632331L;

    /**
     * 区分ID
     * @see String
     */
    private String codeId;

    /**
     * 子区分ID
     * @see String
     */
    private String subCodeId;

    /**
     * 区分值
     * @see String
     */
    private String codeValue;

    /**
     * 区分名称
     * @see String
     */
    private String codeName;

    /**
     * 创建者
     * @see String
     */
    private String createUser;

    /**
     * 创建时间
     * @see Date
     */
    private Date createTime;

    /**
     * 更新者
     * @see String
     */
    private String updateUser;

    /**
     * 更新时间
     * @see Date
     */
    private Date updateTime;

    /**
     * constructor
     */
    public Code() {

    }
}
