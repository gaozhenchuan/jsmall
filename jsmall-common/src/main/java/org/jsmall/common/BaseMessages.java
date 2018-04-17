package org.jsmall.common;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

public class BaseMessages extends AbstractProperties {

	/**
	 * serialVersionUID
	 * @see long
	 */
	private static final long serialVersionUID = -4362210420871365123L;

	/**
	* ロガー
	* @see EnvAnsLogger
	*/
	private static BaseLogger log = (BaseLogger)BaseLogger.getLogger(BaseMessages.class);

	/**
	* メッセージアクセスクラスオブジェクト
	* @see BaseMessages
	*/
	private static BaseMessages messages = new BaseMessages();

	/**
	* メッセージファイル名
	* @see String
	*/
	private static final String MESSAGES_FILENAME = "/pictis_messages.xml";

	/**
	* メッセージIDに対応するメッセージが存在しない場合のメッセージ
	* @see String
	*/
	private static final String MESSAGE_FILE_ERROR = "メッセージリソースにアクセスできませんでした。";

	/**
	* メッセージIDに対応するメッセージレベルが存在しない場合のメッセージ
	* @see String
	*/
	private static final String MESSAGE_NOT_MSGLEBEL = "メッセージID：{0}にメッセージレベルが存在しません。";

	/**
	* メッセージIDに対応するメッセージが存在しない場合のメッセージ
	* @see String
	*/
	private static final String MESSAGE_NOT_FOUND = "メッセージID：{0}に対応するメッセージが存在しません。";

	/**
	* メッセージレベルセパレーター
	* @see String
	*/
	private static final String MESSAGE_SEPARATOR = ",";

	/**
	* <dd>概要：メッセージアクセスクラスコンストラクタです。
	* <dd>詳細：メッセージアクセスクラスコンストラクタです。
	* <dd>備考：
	*/
	private BaseMessages() {
	}

	/**
	* <dd>概要：メッセージアクセスクラスインスタンス取得します。
	* <dd>詳細：メッセージアクセスクラスインスタンス取得します。
	* <dd>備考：
	* @return EnvAnsMessages メッセージアクセスクラスのインスタンス<br>
	*/
	public static BaseMessages getInstance() {
		Logger log = null;
		try {
			if (messages.isEmpty()) {
				messages.load(MESSAGES_FILENAME);
			}
		} catch (Exception ex) {
			log = Logger.getLogger(BaseMessages.class);
			log.error(MESSAGE_FILE_ERROR);
		}
		return messages;
	}

	/**
	* <dd>概要：メッセージアクセスクラスインスタンス取得します。
	* <dd>詳細：メッセージアクセスクラスインスタンス取得します。
	* <dd>備考：
	* @param String メッセージファイル名
	* @return EnvAnsMessages メッセージアクセスクラスのインスタンス<br>
	*/
	public static BaseMessages getInstance(String fileName) {
		Logger log = null;
		try {
			if (messages.isEmpty()) {
				messages.load(fileName);
			}
		} catch (Exception ex) {
			log = Logger.getLogger(BaseMessages.class);
			log.error(MESSAGE_FILE_ERROR);
		}
		return messages;
	}

	/**
	* <dd>概要：メッセージ取得します。
	* <dd>詳細：メッセージ取得します。
	* <dd>備考：
	* @param String メッセージID
	* @return String メッセージ
	*/
	public String getMessage(String messageId) {
		return getMessage(messageId, (Object[])null);
	}

	/**
	* <dd>概要：メッセージ取得します。
	* <dd>詳細：メッセージ取得します。
	* <dd>備考：
	* @param String メッセージID
	* @param Object... メッセージに設定するパラメタ
	* @return String メッセージ
	*/
	public String getMessage(String messageId, Object... param) {
		// 1. メッセージファイル情報よりメッセージIDに対応するメッセージBeanを取得します。
		int separateIndex = 0;
		String message = getProperty(messageId);
		if (message != null) {
			// 2.1. メッセージが存在する場合、パラメタメッセージ取得処理を実行します。
			separateIndex = message.indexOf(MESSAGE_SEPARATOR);
			if (separateIndex > 0) {
				message = message.substring(separateIndex + 1);
			}
			message = getFormatMessage(message, param);
		} else {
			// 2.2. メッセージが存在しなかった場合、トレースログにエラーメッセージを出力します。
			message = getFormatMessage(MESSAGE_NOT_FOUND, messageId);
			log.warn(message);
		}
		return message;
	}

	/**
	* <dd>概要：エラーレベル取得します。
	* <dd>詳細：エラーレベル取得します。
	* <dd>備考：
	* @param String メッセージID
	* @return String エラーレベル
	*/
	public String getLevel(String messageId) {
		String result = null;
		int separateIndex = 0;
		String message = getProperty(messageId);
		if (message != null) {
			separateIndex = message.indexOf(MESSAGE_SEPARATOR);
			if (separateIndex > 0) {
				result = message.substring(0, separateIndex);
			} else {
				message = getFormatMessage(MESSAGE_NOT_MSGLEBEL, messageId);
				log.error(message);
				result = "ERROR";
			}
		} else {
			message = getFormatMessage(MESSAGE_NOT_FOUND, messageId);
			log.warn(message);
		}
		return result;
	}

	/**
	* <dd>概要：パラメタメッセージ取得します。
	* <dd>詳細：パラメタメッセージ取得します。
	* <dd>備考：
	* @param String メッセージ
	* @param Object... パラメタ
	* @return String エラーレベル
	*/
	public String getFormatMessage(String message, Object... param) {
		String result = message;
		if (result != null && param != null) {
			result = MessageFormat.format(result, param);
		}
		return result;
	}

}
