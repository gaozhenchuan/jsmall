package org.jsmall.service.common.mail.dto;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailMessageDto implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -9179261449113255169L;

    /**
     * Mail型枚举
     * @see ContentType
     */
    public static enum ContentType {
        /**
         * 文本邮件
         */
        TEXT,

        /**
         * HTML邮件
         */
        HTML
    }

    /**
     * 主题
     * @see String
     */
    private String subject;

    /**
     * 主题参数
     */
    private List<Object> subjectParams = null;

    /**
     * 正文
     * @see String
     */
    private String messageBody;

    /**
     * 正文参数
     */
    private List<Object> messageBodyParams = null;

    /**
     * 消息型
     * @see ContentType
     */
    private ContentType contentType;

    /**
     * 重要度
     * @see int
     */
    private int priority;

    /**
     * TO
     * @see List<MailAddressDto>
     */
    private List<MailAddressDto> toList;

    /**
     * CC
     * @see List<MailAddressDto>
     */
    private List<MailAddressDto> ccList;

    /**
     * BCC
     * @see List<MailAddressDto>
     */
    private List<MailAddressDto> bccList;

    /**
     * FROM
     * @see MailAddressDto
     */
    private MailAddressDto from;

    /**
     * REPLY
     * @see MailAddressDto
     */
    private MailAddressDto reply;

    /**
     * RETURN-PATH
     * @see MailAddressDto
     */
    private MailAddressDto returnPath;

    /**
     * 添付文件
     * @see List<AttachmentDto>
     */
    private List<AttachmentDto> attachmentList;

    /**
     * <dd>概要：取得主题参数拼接后的主题内容
     * <dd>詳細：取得主题参数拼接后的主题内容
     * <dd>备注：
     * @return 拼接后主题
     */
    public String getFormattedSubject() {
        if (subject != null && subjectParams != null && subjectParams.size() > 0) {
            return MessageFormat.format(subject, subjectParams.toArray());
        } else {
            return subject != null ? subject : "";
        }
    }

    /**
     * <dd>概要：取得正文参数拼接后的正文内容
     * <dd>详细：取得正文参数拼接后的正文内容
     * <dd>备注：
     * @return 拼接后正文
     */
    public String getFormattedBody() {
        if (messageBody != null && messageBodyParams != null && messageBodyParams.size() > 0) {
            return MessageFormat.format(messageBody, messageBodyParams.toArray());
        } else {
            return messageBody != null ? messageBody : "";
        }
    }
}
