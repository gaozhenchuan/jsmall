package org.jsmall.service.common.mail.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDto implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = 2631325137635388638L;

    /**
     * 文件名
     */
    private String fileName = "";

    /**
     * 文件
     */
    private byte[] fileData = null;

    /**
     * 构造方法
     */
    public AttachmentDto() {
    }
}
