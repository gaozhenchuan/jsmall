package org.jsmall.common;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class BaseLoggerFactory implements LoggerFactory, Serializable {

	/**
	 * serialVersionUID
	 * @see long
	 */
	private static final long serialVersionUID = 510460281957470632L;

	/**
	* <dd>概要：CommonLoggerFactoryのコンストラクタです
	* <dd>詳細：CommonLoggerFactoryのコンストラクタです
	* <dd>備考：
	*/
	public BaseLoggerFactory() {
	}

	/**
	* <dd>概要：CommonLoggerのインスタンスを生成します
	* <dd>詳細：CommonLoggerのインスタンスを生成します
	* <dd>備考：
	* @param String ネーム
	* @return Logger ロガー
	*/
	public Logger makeNewLoggerInstance(String name) {
		return new BaseLogger(name);
	}
}