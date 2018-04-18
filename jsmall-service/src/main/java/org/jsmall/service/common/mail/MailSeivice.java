package org.jsmall.service.common.mail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.jsmall.common.BaseLogger;
import org.jsmall.common.BaseProperties;
import org.jsmall.service.common.mail.dto.AttachmentDto;
import org.jsmall.service.common.mail.dto.MailAddressDto;
import org.jsmall.service.common.mail.dto.MailMessageDto;
import org.jsmall.service.common.mail.entity.MyAuthenticator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MailSeivice implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -1856063754048795060L;

    /** SMTPサーバアドレス */
    public static final String CO_SMTP_SERVER_ADDRESS = "CO.SMTP.Server.Address";
    /** SMTPポート番号 */
    public static final String CO_SMTP_PORT = "CO.SMTP.Port";
    /** SMTP認証ユーザID */
    public static final String CO_SMTP_AUTHENTICATION_USERID = "CO.SMTP.Authentication.UserID";
    /** SMTP認証パスワード */
    public static final String CO_SMTP_AUTHENTICATION_PASSWORD = "CO.SMTP.Authentication.Password";
    /** メール送信文字エンコード */
    public static final String CO_MAIL_SEND_CHARSET_ENCODE = "CO.Mail.Send.Charset.Encode";
    /** メール送信実施フラグ */
    public static final String CO_MAIL_SEND_FLAG = "CO.Mail.Send.Flag";
    /** ローカルホスト */
    public static final String CO_MAIL_SMTP_LOCALHOST = "CO.Mail.Smtp.Localhost";
    /** お問い合わせURL */
    public static final String CO_SYSTEM_ERROR_CONTACT_URL = "CO.SystemErrorContactURL";

    /** 主题 */
    public static final String MAIL_MESSAGE_ERROR_PARAM_SUBJECT = "主题";

    /** 消息 */
    public static final String MAIL_MESSAGE_ERROR_PARAM_MESSAGE_BODY = "消息";
    /** パラメータ：TO */
    public static final String MAIL_MESSAGE_ERROR_PARAM_TO = "TO";
    /** パラメータ：FROM */
    public static final String MAIL_MESSAGE_ERROR_PARAM_FROM = "FROM";
    /** パラメータ：CC */
    public static final String MAIL_MESSAGE_ERROR_PARAM_CC = "CC";
    /** パラメータ：BCC */
    public static final String MAIL_MESSAGE_ERROR_PARAM_BCC = "BCC";
    /** パラメータ：添付ファイル */
    public static final String MAIL_MESSAGE_ERROR_PARAM_ATTACHMENT = "添付ファイル";
    /** パラメータ：区切り文字 */
    public static final String MAIL_MESSAGE_ERROR_PARAM_DELIMITER = "、";
    /** パラメータ：改行コード */
    public static final String MAIL_MESSAGE_ERROR_PARAM_LINE = "\n";
    /** メールアドレス：表示名囲い文字 */
    public static final String MAIL_ADDRESS_DISPLAY_NAME_DOUBLE_QUOTATION = "\"";

    /** 邮件地址开始字符 */
    public static final String MAIL_ADDRESS_DISPLAY_ADDRESS_START = "<";

    /** 邮件地址结束字符 */
    public static final String MAIL_ADDRESS_DISPLAY_ADDRESS_END = ">";

    /** 邮件地址区分字符 */
    public static final String MAIL_ADDRESS_DISPLAY_ADDRESS_DELIMITER = ";";

    /**
     * 添付ファイルの内容のタイプのキー
     */
    private static final String ATTACH_CONTENT_TYPE_KEY = "Content-Type";

    /**
     * 添付ファイルの内容のタイプ
     */
    private static final String ATTACH_CONTENT_TYPE_VALUE = "application/octet-stream";

    /**
     * 添付ファイルの添付方式のキー
     */
    private static final String ATTACH_CONTENT_DISPOSITION_KEY = "Content-Disposition";

    /**
     * 添付ファイルの添付方式
     */
    private static final String ATTACH_CONTENT_DISPOSITION_VALUE = "attachment";

    /**
     * 添付ファイルのエンコーディング方法のキー
     */
    private static final String ATTACH_CONTENT_TRANSFER_ENCODING_KEY = "Content-Transfer-Encoding";

    /**
     * 添付ファイルのエンコーディング方法
     */
    private static final String ATTACH_CONTENT_TRANSFER_ENCODING_VALUE = "base64";

    /**
     * logger
     * @see EnvAnsLogger
     */
    private transient BaseLogger logger = (BaseLogger)BaseLogger.getLogger(MailSeivice.class);

    /**
     * <dd>概要：发送邮件处理
     * <dd>详细：发送邮件处理
     * <dd>备注：
     * @param mailMessage 邮件消息
     * @exception Exception
     */
    public void send(MailMessageDto mailMessage) throws Exception {
        BaseProperties prop = null;
        String errorMessage = null;
        String smtpServerAddress = null;
        String smtpPort = null;
        String smtpAuthUserId = null;
        String smtpAuthPassword = null;
        String mailSendEncode = null;
        String mailSendFlag = null;
        MailAddressDto address = null;
        List<MailAddressDto> addressList = null;
        String localhost = null;
        Properties properties = new Properties();

        Session session = null;
        Authenticator auth = null;

        this.logger.startLog(this.getClass().getName(), "send");

        try {
            // 1. メールメッセージをチェックする。
            // ◆TO、FROM、件名、メッセージがNullでないこと
            // ◆TO、CC、BCC、FROMのオブジェクトが存在する場合、それぞれのオブジェクトのメールアドレスがNull／空文字でないこと
            // ◆添付ファイルが存在する場合、ファイルパスがNull／空文字でないこと
            // errorMessage = this.errorCheck(mailMessage);

            // 1）チェックがNGだった場合、例外をthrowする。【Exception：COME0060 throw】
            // ※引数名にメールメッセージを設定する。
            if (!StringUtils.isEmpty(errorMessage)) {
                this.logger.info(this.editErrorParameter(mailMessage));
                throw new Exception();
            }

            // 2. 構成情報より以下の情報を取得する。
            prop = BaseProperties.getInstance();
            // ◆SMTPサーバアドレス
            smtpServerAddress = prop.getStringProperty(CO_SMTP_SERVER_ADDRESS);
            // ◆SMTPポート番号
            smtpPort = prop.getStringProperty(CO_SMTP_PORT);
            // ◆SMTP認証ユーザID
            smtpAuthUserId = prop.getStringProperty(CO_SMTP_AUTHENTICATION_USERID);
            // ◆SMTP認証パスワード
            smtpAuthPassword = prop.getStringProperty(CO_SMTP_AUTHENTICATION_PASSWORD);
            // ◆メール送信文字エンコード
            mailSendEncode = prop.getStringProperty(CO_MAIL_SEND_CHARSET_ENCODE);
            // ◆メール送信実施フラグ
            mailSendFlag = prop.getStringProperty(CO_MAIL_SEND_FLAG);
            // ◆ローカルホスト
            localhost = prop.getStringProperty(CO_MAIL_SMTP_LOCALHOST);

            if (!"1".equals(mailSendFlag)) {
                this.logger.warn("プロパティファイルの設定(CO.Mail.Send.Flag)よりメール送信未実施。");
                return;
            }

            properties.put("mail.smtp.host", smtpServerAddress);

            // SMTP端口号
            properties.put("mail.smtp.port", smtpPort);
            properties.put("mail.smtp.connectiontimeout", "60000");
            properties.put("mail.smtp.timeout", "60000");
            properties.put("mail.debug", "true");

            // SMTP认证
            if (!StringUtils.isEmpty(smtpAuthUserId)) {
                properties.put("mail.smtp.auth", "true");
                auth = new MyAuthenticator(smtpAuthUserId, smtpAuthPassword);
                session = Session.getDefaultInstance(properties, auth);
            } else {
                properties.put("mail.smtp.auth", "false");
                session = Session.getInstance(properties, null);
            }
            session.setDebug(true);
            //session.setCharset(mailSendEncode);

            // 消息对象
            Message msg = new MimeMessage(session);

            // 主题
            msg.setSubject(mailMessage.getFormattedSubject());

            // FROM
            address = mailMessage.getFrom();
            // 送件人名称显示
            if (StringUtils.isEmpty(address.getName())) {
                // email.setFrom(address.getMailAddress());
                msg.setFrom((Address)(new InternetAddress(address.getMailAddress())));
            } else {
                msg.setFrom((Address)(new InternetAddress(address.getMailAddress(), address.getName(), mailSendEncode)));
            }

            // TO
            addressList = mailMessage.getToList();
            InternetAddress[] toList = new InternetAddress[addressList.size()];
            int i = 0;
            for (MailAddressDto toAddress : addressList) {
                // 收件人名称显示
                if (StringUtils.isEmpty(toAddress.getName())) {
                    toList[i] = new InternetAddress(toAddress.getMailAddress());
                } else {
                    toList[i] = new InternetAddress(toAddress.getMailAddress(), toAddress.getName(), mailSendEncode);
                }
                i++;
            }
            msg.setRecipients(Message.RecipientType.TO, toList);

            // CC
            addressList = mailMessage.getCcList();
            if (addressList != null) {
                InternetAddress[] ccList = new InternetAddress[addressList.size()];
                i = 0;
                for (MailAddressDto ccAddress : addressList) {
                    // 抄送人名称显示
                    if (StringUtils.isEmpty(ccAddress.getName())) {
                        ccList[i] = new InternetAddress(ccAddress.getMailAddress());
                    } else {
                        ccList[i] = new InternetAddress(ccAddress.getMailAddress(), ccAddress.getName(), mailSendEncode);
                    }
                    i++;
                }
                msg.setRecipients(Message.RecipientType.CC, ccList);
            }

            // BCC
            addressList = mailMessage.getBccList();
            if (addressList != null) {
                InternetAddress[] bccList = new InternetAddress[addressList.size()];
                i = 0;
                for (MailAddressDto bccAddress : addressList) {
                    // 密送人名称表示
                    if (StringUtils.isEmpty(bccAddress.getName())) {
                        bccList[i] = new InternetAddress(bccAddress.getMailAddress());
                    } else {
                        bccList[i] = new InternetAddress(bccAddress.getMailAddress(), bccAddress.getName(), mailSendEncode);
                    }
                    i++;
                }
            }

            // REPLY-TO
            address = mailMessage.getReply();
            if (address == null) {
                address = mailMessage.getFrom();
            }

            // 用户名称表示
            if (StringUtils.isEmpty(address.getName())) {
                InternetAddress[] replyToList = new InternetAddress[1];
                replyToList[0] = new InternetAddress(address.getMailAddress());
                msg.setReplyTo(replyToList);
            } else {
                InternetAddress[] replyToList = new InternetAddress[1];
                replyToList[0] = new InternetAddress(address.getMailAddress(), address.getName(), mailSendEncode);
                msg.setReplyTo(replyToList);
            }

            // RETURN-PATH：邮件消息.RETURN-PATH.邮件地址
            if (mailMessage.getReturnPath() != null && !StringUtils.isEmpty(mailMessage.getReturnPath().getMailAddress())) {
                properties.setProperty("mail.smtp.from", mailMessage.getReturnPath().getMailAddress());
            }

            // 重要度
            msg.setHeader("X-Priority", String.valueOf(mailMessage.getPriority()));
            msg.setHeader("X-Mailer", "sacra");

            String body = mailMessage.getFormattedBody();
            if (!body.endsWith("\n")) {
                body += "\n";
            }

            MimeMultipart mp = null;

            msg.setSentDate(new Date());
            // 添付文件是否存在
            if (CollectionUtils.isEmpty(mailMessage.getAttachmentList())) {
                msg.setText(body);
            } else {
                mp = new MimeMultipart();
                MimeBodyPart textBody = new MimeBodyPart();
                textBody.setText(body, mailSendEncode);
                mp.addBodyPart(textBody);
                for (AttachmentDto attachmentDto : mailMessage.getAttachmentList()) {
                    MimeBodyPart attachBody = new MimeBodyPart();
                    ByteArrayDataSource fds = new ByteArrayDataSource(attachmentDto.getFileData(), ATTACH_CONTENT_TYPE_VALUE);
                    attachBody.setDataHandler(new DataHandler(fds));
                    attachBody.setFileName(MimeUtility.encodeText(attachmentDto.getFileName(), mailSendEncode, null));

                    mp.addBodyPart(attachBody);
                }
                msg.setContent(mp);
            }

            properties.setProperty("mail.smtp.localhost", localhost);
            logger.debug("★ localhost: " + localhost);

            // 发送邮件
            Transport.send(msg);

        } catch (Exception e) {
            throw e;

        } finally {
            this.logger.endLog(this.getClass().getName(), "send");
        }
    }
    
    /**
     * <dd>概要：邮件数据校验
     * <dd>详细：邮件数据校验
     * <dd>备注：
     * @param mailMessage 邮件消息
     * @return String    异常信息
     * @exception Exception
     */
    private String errorCheck(MailMessageDto mailMessage) throws Exception {
        StringBuffer errbuf = new StringBuffer();

        try {

            // 主题Check
            logger.debug("主题：" + mailMessage.getFormattedSubject());
            if (StringUtils.isEmpty(mailMessage.getFormattedSubject())) {
                this.addErrorParameter(errbuf, MAIL_MESSAGE_ERROR_PARAM_SUBJECT);
            }

            // 消息Check
            logger.debug("正文：" + mailMessage.getFormattedBody());
            if (StringUtils.isEmpty(mailMessage.getFormattedBody())) {
                this.addErrorParameter(errbuf, MAIL_MESSAGE_ERROR_PARAM_MESSAGE_BODY);
            }

            // 收件人Check
            if (mailMessage.getToList() != null && !mailMessage.getToList().isEmpty()) {
                for (int i = 0; i < mailMessage.getToList().size(); i++) {
                    logger.debug("TOアドレス[" + (i + 1) + "]：" + mailMessage.getToList().get(i).getMailAddress());
                }
            }
            this.addressListCheck(errbuf, mailMessage.getToList(), MAIL_MESSAGE_ERROR_PARAM_TO);

            // 抄送人Check
            if (mailMessage.getCcList() != null && !mailMessage.getCcList().isEmpty()) {
                for (int i = 0; i < mailMessage.getCcList().size(); i++) {
                    logger.debug("CCアドレス[" + (i + 1) + "]：" + mailMessage.getCcList().get(i).getMailAddress());
                }
                this.addressListCheck(errbuf, mailMessage.getCcList(), MAIL_MESSAGE_ERROR_PARAM_CC);
            }

            // 密送人Check
            if (mailMessage.getBccList() != null && !mailMessage.getBccList().isEmpty()) {
                for (int i = 0; i < mailMessage.getBccList().size(); i++) {
                    logger.debug("BCCアドレス[" + (i + 1) + "]：" + mailMessage.getBccList().get(i).getMailAddress());
                }
                this.addressListCheck(errbuf, mailMessage.getBccList(), MAIL_MESSAGE_ERROR_PARAM_BCC);
            }

            // FROM
            if (mailMessage.getFrom() == null) {
                logger.debug("FROMアドレス：" + mailMessage.getFrom());
                this.addErrorParameter(errbuf, MAIL_MESSAGE_ERROR_PARAM_FROM);
            } else {
                logger.debug("FROMアドレス：" + mailMessage.getFrom().getMailAddress());
                if (StringUtils.isEmpty(mailMessage.getFrom().getMailAddress())) {
                    this.addErrorParameter(errbuf, MAIL_MESSAGE_ERROR_PARAM_FROM);
                }
            }

            // 添付文件Check
            if (mailMessage.getAttachmentList() != null && !mailMessage.getAttachmentList().isEmpty()) {
                for (AttachmentDto attachmentDto : mailMessage.getAttachmentList()) {
                    if (StringUtils.isEmpty(attachmentDto.getFileName())) {
                        this.addErrorParameter(errbuf, MAIL_MESSAGE_ERROR_PARAM_ATTACHMENT);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return errbuf.toString();
    }

    /**
     * <dd>概要：パラメータを文字列追加します。
     * <dd>详细：パラメータを文字列追加します。追加先文字列が空でない場合は「、」で区切ります。
     * <dd>备注：
     * @param strbuf 追加先文字列
     * @param paramName エラーパラメータ名
     * @exception Exception
     */
    private void addErrorParameter(StringBuffer strbuf, String paramName) throws Exception {
        if (strbuf.length() != 0) {
            strbuf.append(MAIL_MESSAGE_ERROR_PARAM_DELIMITER);
        }
        strbuf.append(paramName);
    }

    /**
     * <dd>概要：メールアドレスのチェックを行います。
     * <dd>詳細：TO、CC、BCCのメールアドレスをチェックします。
     * <dd>備考：
     * @param errbuf エラーメッセージ文字列
     * @param addressList メールアドレスリスト
     * @param paramName エラーパラメータ名
     * @exception Exception
     */
    private void addressListCheck(StringBuffer errbuf, List<MailAddressDto> addressList, String paramName) throws Exception {
        try {
            if (addressList == null || addressList.isEmpty()) {
                // Nullである場合、チェックエラーとする。
                this.addErrorParameter(errbuf, paramName);
            } else {
                // オブジェクトが存在する場合、オブジェクトのメールアドレスがNull／空文字でないこと
                for (MailAddressDto address : addressList) {
                    if (StringUtils.isEmpty(address.getMailAddress())) {
                        // メールアドレスがNull／空文字である場合、チェックエラーとする。
                        this.addErrorParameter(errbuf, paramName);
                        // チェックエラーとなった場合、ループを抜ける。
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * <dd>概要：メールメッセージ文字列作成です。
     * <dd>詳細：チェックエラー時に表示するメールメッセージの文字列を作成します。
     * <dd>備考：
     * @param mailMessage メールメッセージ
     * @return    String    チェックエラー時のメールメッセージの文字列
     * @exception Exception
     */
    private String editErrorParameter(MailMessageDto mailMessage) throws Exception {
        StringBuffer strbuf = new StringBuffer();
        // 主题
        String subject = null;
        // 消息
        String messageBody = null;
        MailAddressDto addressDto = null;
        List<MailAddressDto> list = null;

        try {

            // 件名
            subject = mailMessage.getSubject();
            if (!StringUtils.isEmpty(subject)) {
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_SUBJECT);
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
                strbuf.append(subject);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
            }

            // 消息
            messageBody = mailMessage.getMessageBody();
            if (!StringUtils.isEmpty(messageBody)) {
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_MESSAGE_BODY);
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
                strbuf.append(messageBody);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
            }

            // TO
            list = mailMessage.getToList();
            if (list != null && !list.isEmpty()) {
                strbuf.append(getAddressListDtoListInfo(list, MAIL_MESSAGE_ERROR_PARAM_TO));
            }

            // CC
            list = mailMessage.getCcList();
            if (list != null && !list.isEmpty()) {
                strbuf.append(getAddressListDtoListInfo(list, MAIL_MESSAGE_ERROR_PARAM_CC));
            }

            // BCC
            list = mailMessage.getBccList();
            if (list != null && !list.isEmpty()) {
                strbuf.append(getAddressListDtoListInfo(list, MAIL_MESSAGE_ERROR_PARAM_BCC));
            }

            // FROM
            addressDto = mailMessage.getFrom();
            if (addressDto != null) {
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_FROM);
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
                strbuf.append(getAddressListDtoInfo(addressDto));
            }

            // REPLY
            addressDto = mailMessage.getReply();
            if (addressDto != null) {
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
                strbuf.append("REPLY");
                strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
                strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
                strbuf.append(getAddressListDtoInfo(addressDto));
            }

            logger.debug(strbuf.toString());
        } catch (Exception e) {
            throw e;
        }

        return strbuf.toString();
    }
    
    /**
     * <dd>概要：メール送信メール宛先リスト情報を取得します。
     * <dd>詳細：メール送信メール宛先リスト情報を取得します。
     * <dd>備考：
     * @param list メール送信メール宛先リストDTOリスト
     * @param itemName 項目名
     * @return String    メール送信メール宛先リストDTOリスト情報
     * @exception Exception
     */
    private String getAddressListDtoListInfo(List<MailAddressDto> list, String itemName) throws Exception {
        StringBuffer strbuf = new StringBuffer();

        logger.startLog(this.getClass().getName(), "getAddressListDtoListInfo");

        strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
        strbuf.append(itemName);
        strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
        strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
        for (MailAddressDto addressDto : list) {
            strbuf.append(getAddressListDtoInfo(addressDto));
        }

        logger.endLog(this.getClass().getName(), "getAddressListDtoListInfo");

        return strbuf.toString();
    }

    /**
     * <dd>概要：メール送信メール宛先情報を取得します。
     * <dd>詳細：メール送信メール宛先情報を取得します。
     * <dd>備考：
     * @param addressDto 邮件地址DTO
     * @return String    邮件地址
     * @exception Exception
     */
    private String getAddressListDtoInfo(MailAddressDto addressDto) throws Exception {
        StringBuffer strbuf = new StringBuffer();

        logger.startLog(this.getClass().getName(), "getAddressListDtoInfo");

        if (!StringUtils.isEmpty(addressDto.getMailAddress())) {
            strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
            strbuf.append("アドレス");
            strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
            strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
            strbuf.append(addressDto.getMailAddress());
            strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
        }
        if (!StringUtils.isEmpty(addressDto.getName())) {
            strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_START);
            strbuf.append("表示名");
            strbuf.append(MAIL_ADDRESS_DISPLAY_ADDRESS_END);
            strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
            strbuf.append(addressDto.getName());
            strbuf.append(MAIL_MESSAGE_ERROR_PARAM_LINE);
        }

        logger.endLog(this.getClass().getName(), "getAddressListDtoInfo");

        return strbuf.toString();
    }
}
