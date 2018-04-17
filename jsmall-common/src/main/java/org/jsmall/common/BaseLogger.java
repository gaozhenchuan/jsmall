package org.jsmall.common;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BaseLogger extends Logger implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = 2203455531237055478L;

    /**
    * FQCN
    * @see String
    */
    private static final String FQCN = BaseLogger.class.getName();

    /**
    * アプリログ
    * @see BaseLoggerFactory
    */
    private static BaseLoggerFactory factory = new BaseLoggerFactory();

    /**
    * syslog
    * @see Logger
    */
    private static Logger syslog = Logger.getLogger("syslog");

    /**
    * <dd>概要：EnvAnsLoggerを設定します
    * <dd>詳細：EnvAnsLoggerを設定します
    * <dd>備考：
    * @param String ネーム
    */
    public BaseLogger(String name) {
        super(name);
    }

    /**
    * <dd>概要：ロガーを取得します
    * <dd>詳細：ロガーを取得します
    * <dd>備考：
    * @param String ネーム
    * @return Logger ロガー
    */
    public static Logger getLogger(String name) {
        return (BaseLogger)Logger.getLogger(name, factory);
    }

    /**
    * <dd>概要：ロガーを取得します
    * <dd>詳細：ロガーを取得します
    * <dd>備考：
    * @param Class<?> クラス
    * @return Logger ロガー
    */
    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    /**
    * <dd>概要：ログ出力します
    * <dd>詳細：ログ出力します
    * <dd>備考：
    * @param String メッセージID
    * @param Throwable 発生したエラー
    */
    public void log(String messageId, Throwable throwable) {
        log(messageId, throwable, (Object[])null);
    }

    /**
    * <dd>概要：ログ出力します
    * <dd>詳細：ログ出力します
    * <dd>備考：
    * @param String メッセージID
    * @param Object エラー詳細
    */
    public void log(String messageId, Object... param) {
        log(messageId, null, param);
    }

    /**
    * <dd>概要：ログ出力します
    * <dd>詳細：ログ出力します
    * <dd>備考：
    * @param String メッセージID
    * @param Throwable 発生したエラー
    * @param Object エラー詳細
    */
    public void log(String messageId, Throwable throwable, Object... param) {
        BaseMessages messages = BaseMessages.getInstance();
        String message = messages.getMessage(messageId, param);
        String level = messages.getLevel(messageId).toLowerCase();

        if ("fatal".equals(level)) {
            fatal(message);
            syslog.fatal(message);
        } else if ("error".equals(level)) {
            error(message, throwable);
            syslog.error(message);
        } else if ("warn".equals(level)) {
            warn(message, throwable);
            syslog.warn(message);
        } else if ("info".equals(level)) {
            info(message);
        } else if ("trace".equals(level)) {
            // trace(message, throwable);
        } else if ("debug".equals(level)) {
            // debug(message, throwable);
            // trace(message, throwable);
        }
    }

    /**
    * <dd>概要：操作(証跡)ログを出力します
    * <dd>詳細：操作(証跡)ログを出力します
    * <dd>備考：
    * @param HttpServletRequest リクエスト
    */
    public void traceLog(HttpServletRequest request) {
        int iCnt;
        Enumeration<?> e;
        Cookie[] cookies;

        String message = "";
        message = message + "操作(証跡)ログ\n";
        // HTTPヘッダ情報の出力
        e = request.getHeaderNames();
        iCnt = 0;
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            if (0 == iCnt) {
                message = message + "◆HTTPヘッダ情報\n";
                message = message + "\t[" + name + "] = [" + request.getHeader(name) + "]\n";
            } else {
                message = message + "\t[" + name + "] = [" + request.getHeader(name) + "]\n";
            }
            iCnt++;
        }
        // 認証スキームの名前の出力
        if (request.getAuthType() != null) {
            if (!"".equals(request.getAuthType())) {
                message = message + "◆認証スキーム\n";
                message = message + "\t[" + request.getAuthType() + "]\n";
            }
        }
        // コンテキストパスの出力
        if (request.getContextPath() != null) {
            if (!"".equals(request.getContextPath())) {
                message = message + "◆コンテキストパス\n";
                message = message + "\t[" + request.getContextPath() + "]\n";
            }
        }
        // クッキー情報の出力
        try {
            cookies = request.getCookies();
            String coding = "";
            if (cookies != null) {
                message = message + "◆Cookie情報\n";
                iCnt = 0;
                for (Cookie cookie : cookies) {
                    // デコード処理（キー名）
                    String temp = URLDecoder.decode(cookie.getName(), coding);
                    // デコード処理（情報）
                    String decodeData = URLDecoder.decode(cookie.getValue(), coding);

                    // if(0 == iCnt) {
                    message = message + "\t[" + temp + "] = [" + decodeData + "]\n";
                    // } else {
                    // message = message + ",[" + temp + "] = [" + decodeData + "]";
                    // }
                    iCnt++;
                }
            }
        } catch (Exception ex) {
            message = message + "\t[ERROR]Cookie情報取得失敗\n";
        }
        // HTTPメソッドの名前の出力
        if (request.getMethod() != null) {
            if (!"".equals(request.getMethod())) {
                message = message + "◆HTTPメソッド\n";
                message = message + "\t[" + request.getMethod() + "]\n";
            }
        }
        // 拡張パス情報の出力
        if (request.getPathInfo() != null) {
            if (!"".equals(request.getPathInfo())) {
                message = message + "◆拡張パス情報\n";
                message = message + "\t[" + request.getPathInfo() + "]\n";
            }
        }
        // ユーザのログイン名の出力
        if (request.getRemoteUser() != null) {
            if (!"".equals(request.getRemoteUser())) {
                message = message + "◆ユーザのログイン名\n";
                message = message + "\t[" + request.getRemoteUser() + "]\n";
            }
        }
        // セッションIDの出力
        if (request.getRequestedSessionId() != null) {
            if (!"".equals(request.getRequestedSessionId())) {
                message = message + "◆セッションID\n";
                message = message + "\t[" + request.getRequestedSessionId() + "]\n";
            }
        }
        // リクエストURIの出力
        if (request.getRequestURI() != null) {
            if (!"".equals(request.getRequestURI())) {
                message = message + "◆リクエストURI\n";
                message = message + "\t[" + request.getRequestURI() + "]\n";
            }
        }
        // セッション情報の出力
        if (request.getSession() != null) {
            e = request.getSession().getAttributeNames();
            iCnt = 0;
            while (e.hasMoreElements()) {
                String name = (String)e.nextElement();
                if (0 == iCnt) {
                    message = message + "◆Session情報\n";
                    message = message + "\t[" + name + "] = [" + request.getSession().getAttribute(name) + "]\n";
                } else {
                    message = message + "\t[" + name + "] = [" + request.getSession().getAttribute(name) + "]\n";
                }
                iCnt++;
            }
        }
        // リクエストパラメタ情報の出力
        e = request.getParameterNames();
        iCnt = 0;
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            if (0 == iCnt) {
                message = message + "◆リクエストパラメタ情報\n";
                message = message + "\t[" + name + "] = [" + request.getParameter(name) + "]\n";
            } else {
                message = message + "\t[" + name + "] = [" + request.getParameter(name) + "]\n";
            }
            iCnt++;
        }
        info(message);
    }

    /**
    * <dd>概要：開始ログ出力します
    * <dd>詳細：開始ログ出力します
    * <dd>備考：
    * @param String クラス名
    * @param String メソッド名
    */
    public void startLog(String className, String methodName) {
        if (super.isDebugEnabled()) {
            super.log(FQCN, Level.INFO, "▼▼▼ " + className + "." + methodName + " started ▼▼▼", null);
        }
    }

    /**
    * <dd>概要：終了ログ出力します
    * <dd>詳細：終了ログ出力します
    * <dd>備考：
    * @param String クラス名
    * @param String メソッド名
    */
    public void endLog(String className, String methodName) {
        if (super.isDebugEnabled()) {
            super.log(FQCN, Level.INFO, "▲▲▲ " + className + "." + methodName + " ended ▲▲▲", null);
        }
    }
}
