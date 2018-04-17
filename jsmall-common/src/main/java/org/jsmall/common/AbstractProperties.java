package org.jsmall.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public abstract class AbstractProperties extends Properties {

    /**
    * serialVersionUID
    * @see long
    */
    private static final long serialVersionUID = -4286909550815134891L;

    /**
    * 区切り文字
    * @see String
    */
    public static final String SEPARATOR_PROP_VALUE = ",";

    /**
    * <dd>概要：指定されたファイルを読み込みます。
    * <dd>詳細：指定されたファイルを読み込みます。
    * <dd>備考：
    * @param String リソース名
    * @exception FileNotFoundException
    * @exception InvalidPropertiesFormatException
    * @exception IOException
    */
    protected void load(String resource) throws FileNotFoundException, InvalidPropertiesFormatException, IOException {
        boolean isXml = FileUtility.getExtension(resource).equals("xml");
        InputStream inStream = FileUtility.getInputStream(resource);
        if (inStream != null) {
            try {
                if (isXml) {
                    loadFromXML(inStream);
                } else {
                    load(inStream);
                }
            } finally {
                inStream.close();
                inStream = null;
            }
        }
    }

    /**
    * <dd>概要：プロパティ値を取得します。
    * <dd>詳細：プロパティ値を取得します。
    * <dd>備考：
    * @param String キー
    * @return String キーに対応する値
    */
    public String getStringProperty(String key) {
        return getProperty(key, "");
    }

    /**
    * <dd>概要：プロパティ値を取得します。
    * <dd>詳細：プロパティ値を取得します。
    * <dd>備考：キーに対応する値が見つからない場合、0を返します。
    * @param String キー
    * @return int キーに対応する値
    */
    public int getIntProperty(String key) {
        String result = getProperty(key, "0");
        int intResult = Integer.valueOf(result);
        return intResult;
    }

    /**
    * <dd>概要：プロパティ値を取得します。
    * <dd>詳細：プロパティ値を取得します。
    * <dd>備考：キーに対応する値が見つからない場合、0を返します。
    * @param String キー
    * @return Long キーに対応する値
    */
    public Long getLongProperty(String key) {
        String result = getProperty(key, "0");
        long longResult = Long.valueOf(result);
        return longResult;
    }

    /**
    * <dd>概要：プロパティ値を取得します。
    * <dd>詳細：プロパティ値を取得します。
    * <dd>備考：キーに対応する値が見つからない場合、nullを返します。
    * @param String キー
    * @return List&lt;String&gt; キーに対応する値
    */
    public List<String> getListProperty(String key) {
        List<String> listResult = null;
        String result = getProperty(key);
        String[] splitResult = null;
        if (result != null) {
            splitResult = result.split(SEPARATOR_PROP_VALUE);
            listResult = Arrays.asList(splitResult);
        }
        return listResult;
    }
}
