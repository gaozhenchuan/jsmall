package org.jsmall.service.common.pdf.dto;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileProperties {

    /**
     * ファイルパス
     */
    private String filePath;

    /**
     * コンテンツタイプ
     * @see String
     */
    private String contentType;

    /**
     * コンテンツレングス
     * @see int
     */
    private int contentLength;

    /**
     * インプットストリーム
     * @see InputStream
     */
    private InputStream inputStream;

    /**
     * バッファサイズ
     * @see int
     */
    private int bufferSize;

    /**
     * 文字コード
     * @see String
     */
    private String contentCharSet;

    /**
     * コンテンツディスポジション
     * @see String
     */
    private String contentDisposition;
}
