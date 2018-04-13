/***************************************************************************
 *CommonRecordsForm.java
 ***************************************************************************
 *システム名    公益認定等総合情報システム
 *クラス名      一覧画面共通フォームクラス
 *機能名        共通機能
 ***************************************************************************
 *修正履歴
 *No  日付                Ver         担当者          内容
 *01  2018.01.19          V01L01      定別當          新規作成
 ***************************************************************************/

package org.jsmall.service.mail;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * <dd>概要：一覧情報の受け渡しを行います。
 * <dd>詳細：一覧画面に表示する値を格納します。
 * <dd>備考：
 * @version V01L01
 * @author 富士通
 */
@Service
public class MailSeivice implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -1856063754048795060L;

    private static final String HOST = "smtp.163.com";
    private static final Integer PORT = 25;
    private static final String USERNAME = "*******@163.com";
    private static final String PASSWORD = "*******";
    private static final String EMAILFORM = "*******@163.com";
    private static JavaMailSenderImpl mailSender = createMailSender();
    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param to 接受人
     * @param subject 主题
     * @param html 发送内容
     * @throws MessagingException 异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendHtmlMail(String to, String subject, String html) throws MessagingException,UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(EMAILFORM, "系统名称");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }

}
