package org.jsmall.service.common.mail.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailAddressDto implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -6728447489161538900L;

    /**
     * 用户名称
     * @see String
     */
    private String name;

    /**
     * 邮件地址
     * @see String
     */
    private String mailAddress;
}
