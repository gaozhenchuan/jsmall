package org.jsmall.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class BaseProperties extends AbstractProperties {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -2813813343324107689L;

    /**
    * 設定情報アクセスクラスオブジェクト
    * @see BaseProperties
    */
    private static BaseProperties properties = new BaseProperties();

    /**
    * 設定情報（画面構成情報プロパティ）アクセスクラスオブジェクト
    * @see BaseProperties
    */
    private static BaseProperties propertiesDisplayMakeUp = new BaseProperties();

    /**
    * 設定情報ファイル名
    * @see String
    */
    private static final String PROPERTIES_FILENAME = "/pictis.properties.xml";

    /**
    * 設定情報（画面構成情報プロパティ）ファイル名
    * @see String
    */
    private static final String DISPLAYMAKEUP_PROPERTIES_FILENAME = "/displayMakeup.properties.xml";

    /**
    * <dd>概要：設定情報アクセスクラスコンストラクタです。
    * <dd>詳細：設定情報アクセスクラスコンストラクタです。
    * <dd>備考：
    */
    private BaseProperties() {
    }

    /**
    * <dd>概要：設定情報アクセスクラスインスタンスを取得します。
    * <dd>詳細：設定情報アクセスクラスインスタンスを取得します。
    * <dd>備考：
    * @return CommonProperties 設定情報アクセスクラスのインスタンス
    * @throws IOException
    * @throws InvalidPropertiesFormatException
    * @throws FileNotFoundException
    */
    public static synchronized BaseProperties getInstance() throws FileNotFoundException, InvalidPropertiesFormatException, IOException {
        if (properties.isEmpty()) {
            properties.load(PROPERTIES_FILENAME);
        }
        return properties;
    }

    /**
    * <dd>概要：画面構成情報アクセスクラスインスタンスを取得します。
    * <dd>詳細：画面構成情報アクセスクラスインスタンスを取得します。
    * <dd>備考：
    * @return CommonProperties 画面構成情報アクセスクラスのインスタンス
    * @throws IOException
    * @throws InvalidPropertiesFormatException
    * @throws FileNotFoundException
    */
    public static synchronized BaseProperties getInstanceDisplayMakeUp() throws FileNotFoundException, InvalidPropertiesFormatException, IOException {
        if (propertiesDisplayMakeUp.isEmpty()) {
            propertiesDisplayMakeUp.load(DISPLAYMAKEUP_PROPERTIES_FILENAME);
        }
        return propertiesDisplayMakeUp;
    }
}
