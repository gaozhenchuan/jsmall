package org.jsmall.service.common.base.dto;

import java.io.Serializable;
import java.util.Date;

public class DBCommonColumnDto implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = 8670223418298078656L;

    /**
     * 削除フラグ
     * @see String
     */
    private String deletedFlag;

    /**
     * 作成者
     * @see String
     */
    private String createUser;

    /**
     * 作成日時
     * @see Date
     */
    private Date createDatetime;

    /**
     * 更新者
     * @see String
     */
    private String updateUser;

    /**
     * 更新日時
     * @see Date
     */
    private Date updateDatetime;

    /**
     * 更新機能ID
     * @see String
     */
    private String updateFuncId;
}
