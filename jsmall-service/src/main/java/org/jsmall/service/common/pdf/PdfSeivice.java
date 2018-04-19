package org.jsmall.service.common.pdf;

import java.io.Serializable;

import org.jsmall.common.BaseLogger;
import org.jsmall.service.common.mail.dto.MailMessageDto;
import org.springframework.stereotype.Service;

@Service
public class PdfSeivice implements Serializable {

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

    /**
     * logger
     * @see EnvAnsLogger
     */
    private transient BaseLogger logger = (BaseLogger)BaseLogger.getLogger(PdfSeivice.class);

    /**
     * <dd>概要：发送邮件处理
     * <dd>详细：发送邮件处理
     * <dd>备注：
     * @param mailMessage 邮件消息
     * @exception Exception
     */
    public void exportPdf(MailMessageDto mailMessage) throws Exception {
//    	 Connection connection = DBUtils.getDBInstance().getInitDBConnection();
//         try 
//         {
//             //据据jasper文件生成JasperPrint对象
//             ServletContext context = this.getServletConfig().getServletContext();
//             String fileName = request.getParameter("fileName");//ireport编译文件：*.jasper（由模板文件*.jrxml文件编译生成）
//             File reportFile = new File(context.getRealPath("/WEB-INF/jaspers/"+fileName));
//             HashMap<String, Object> parameters = new HashMap<String, Object>();//给报表模板文件传参
//
//             //得到枚举类型的参数名称，参数名称若有重复的只能得到第一个--获取页面传来的参数，和模板中文件的sql参数名称一一对应
//             Enumeration<?> temp = request.getParameterNames();
//                while (temp.hasMoreElements()) 
//                {
//                 String paramName = (String) temp.nextElement();
//                 String paramValue = request.getParameter(paramName);
//                 parameters.put(paramName, paramValue);
//                }
//             byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters,connection);
//             response.setContentType("application/pdf");
//             response.setContentLength(bytes.length);
//             ServletOutputStream out = response.getOutputStream();
//             out.write(bytes, 0, bytes.length);
//             out.flush();
//             out.close();
//         } 
//         catch (Exception e) 
//         {
//             e.printStackTrace();
//         }
    }
}
