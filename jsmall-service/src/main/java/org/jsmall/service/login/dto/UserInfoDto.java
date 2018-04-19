package org.jsmall.service.login.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = 5378380990147954883L;

    /**
     * 職員利用者ID
     * @see String
     */
    private String staffUserId;

    /**
     * 職員連番
     * @see int
     */
    private int staffSeq;

    /**
     * 職員名
     * @see String
     */
    private String staffName;

    /**
     * 行政庁コード
     * @see String
     */
    private String prefCode;

    /**
     * 行政庁名
     * @see String
     */
    private String prefName;

    /**
     * 組織コード
     * @see String
     */
    private String deptCode;

    /**
     * 組織名
     * @see String
     */
    private String deptName;

    /**
     * 権限種別
     * @see String
     */
    private String authorityKind;

    /**
     * システム管理者権限フラグ
     * @see String
     */
    private String administratorFlag;

    /**
     * 監督担当者(内閣府)権限フラグ
     * @see String
     */
    private String caoPrivilegeUserFlag;

    /**
     * 行政庁システム管理者権限フラグ
     * @see String
     */
    private String prefAdministratorFlag;

    /**
     * 主担当委員権限フラグ
     * @see String
     */
    private String mainChargeCommitteeUserFlag;

    /**
     * 今回ログイン日時
     * @see Date
     */
    private Date currentLoginDateTime;
}
