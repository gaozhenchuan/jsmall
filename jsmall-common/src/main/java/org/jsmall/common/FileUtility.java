package org.jsmall.common;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtility implements  Serializable {

	/**
	 * serialVersionUID
	 * @see long
	 */
	private static final long serialVersionUID = 3468410276735554293L;

	/**
	 * 拡張子の開始文字
	 * @see String
	 */
	public static final String EXTENSION_MARK = ".";

	/**
	 * 区切り文字（/）
	 * @see String
	 */
	public static final String SEPARATOR_SLASH = "/";

	/**
	 * 区切り文字（\）
	 * @see String
	 */
	public static final String SEPARATOR_YEN = "\\";

	/**
	 * 登録可能拡張子マップ
	 * @see Map<String, String>
	 */
	private static Map<String, String> upLoadableExtensionMap;

	/**
	 * <dd>概要：リソースを取得します。
	 * <dd>詳細：さまざまな格納場所から汎用的にInputStreamを取得します。
	 * <dd>備考：以下のように指定することでリソースが取得できます。<br>
	 * URL:InputStream inputStream = PtlUtility.getInputStream("http://localhost/test.txt");<br>
	 * ファイルパス:InputStream inputStream = PtlUtility.getInputStream("C:/test.txt");<br>
	 * classpath:InputStream inputStream = PtlUtility.getInputStream("com/fujitsu/test/test.txt");<br>
	 * classpath:InputStream inputStream = PtlUtility.getInputStream("com/fujitsu/test/test.xml");<br>
	 * WEB-INF配下:InputStream inputStream = getInputStream("/WEB-INF/test.xml");<br>
	 * 取得できなかった場合はnullが返却されます。<br>
	 * @param String リソース名
	 * @return InputStream リソースのストリーム
	 */
	public static InputStream getInputStream(String resource) {
		Pattern pattern = null;
		Matcher matcher = null;
		URL sourceUrl = null;
		File file = null;

		// デフォルトのクラスローダを使用してリソースを取得する
		InputStream stream = (new FileUtility()).getClass().getResourceAsStream(resource);
		if (stream == null) {
			// デフォルトのクラスローダでリソースを取得できなかった場合は
			// コンテキスト・クラスローダーを使用してリソースを取得する
			stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
		}
		if (stream == null) {
			// デフォルトのクラスローダでリソースを取得できなかった場合
			// リソース名がURLかどうかを判定する
			pattern = Pattern.compile(".*://.*");
			matcher = pattern.matcher(resource);
			if (matcher.matches()) {
				// リソース名がURLの場合
				sourceUrl = null;
				try {
					sourceUrl = new URL(resource);
				} catch (MalformedURLException e) {
					// 例外が発生したらnullで返却するため何もしない
					sourceUrl = null;
				}
				if (sourceUrl != null) {
					try {
						// URLからストリームを取得する
						stream = sourceUrl.openStream();
					} catch (IOException e) {
						// 例外が発生したらnullで返却するため何もしない
						sourceUrl = null;
					}
				}
			} else {
				file = new File(resource);
				try {
					// ファイルからストリームを取得する
					stream = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// 例外が発生したらnullで返却するため何もしない
					stream = null;
				}
			}
		}
		return stream;
	}

	/**
	 * <dd>概要：フォルダパスを除去します。
	 * <dd>詳細：ファイルパスからフォルダパスを除去したファイル名を返します。
	 * <dd>備考：1. ファイルパスから最後尾の区切り文字("/"または"\")の位置を取得します。<br>
	 *           2. 区切り文字が存在する場合、区切り文字以降のファイル名を取得してリストに格納します。
	 * @param String ファイルパス
	 * @return String フォルダパスを除去したファイル名
	 */
	public static String removeFolderPath(String filePath) {
		String fileName = filePath;

		// 1. ファイルパスから最後尾の区切り文字("/"または"\")の位置を取得します。
		int slashPos = filePath.lastIndexOf(FileUtility.SEPARATOR_SLASH);
		int yenPos = filePath.lastIndexOf(FileUtility.SEPARATOR_YEN);
		int removePos = (slashPos > yenPos) ? slashPos : yenPos;
		if (removePos > 0) {
			// 2. 区切り文字が存在する場合、区切り文字以降のファイル名を取得してリストに格納します。
			fileName = filePath.substring(removePos + 1);
		}
		return fileName;
	}

	/**
	 * <dd>概要：拡張子除去を除去します。
	 * <dd>詳細：ファイル名の拡張子を除去します。
	 * <dd>備考：1. 最後尾にあるドットの位置を取得します。<br>
	 *           2. ドットが存在するかチェックします。<br>
	 *           2.1. 存在する場合は、ドットまでのファイル名を取得します。
	 * @param String ファイル名
	 * @return String 拡張子を除去したファイル名
	 */
	public static String getRemoveExtension(String fileName) {
		String ret = fileName;

		// 1. 最後尾にあるドットの位置を取得します。
		int dot = fileName.lastIndexOf(EXTENSION_MARK);

		// 2. ドットが存在するかチェックします。
		if (dot != -1) {
			// 2.1. 存在する場合は、ドットまでのファイル名を取得します。
			ret = fileName.substring(0, dot);
		}

		return ret;
	}

	/**
	 * <dd>概要：ファイル名にサフィックスを追加します。
	 * <dd>詳細：ファイル名にサフィックスを追加します。
	 * <dd>備考：1. 最後尾にあるドットの位置を取得します。<br>
	 *           2. ドットの前にパラメタの文字を追加した文字列を生成します。
	 * @param String ファイル名
	 * @param String サフィックス文字
	 * @return String サフィックス追加後ファイル名
	 */
	public static String addSuffix(String fileName, String suffix) {
		String ret = "";

		// 1. 最後尾にあるドットの位置を取得します。
		int dot = fileName.lastIndexOf(EXTENSION_MARK);

		// 2. ドットの前にパラメタの文字を追加した文字列を生成します。
		ret = dot >= 0 ? fileName.substring(0, dot) : fileName;
		ret += suffix;
		ret += dot >= 0 ? fileName.substring(dot) : "";

		return ret;
	}

	/**
	 * <dd>概要：ファイル名から拡張子を取得します。
	 * <dd>詳細：ファイル名から拡張子を取得します。
	 * <dd>備考：1. 最後尾にあるドットの位置を取得します。<br>
	 *           2. ドットの移行の文字列を取得します。<br>
	 *           3. 拡張子を小文字に変換します。
	 * @param String ファイル名
	 * @return String 拡張子
	 */
	public static String getExtension(String fileName) {
		// 1. 最後尾にあるドットの位置を取得します。
		int dot = fileName.lastIndexOf(EXTENSION_MARK);

		// 2. ドットの移行の文字列を取得します。
		String ret = dot >= 0 ? fileName.substring(dot + 1) : "";

		// 3. 拡張子を小文字に変換します。
		ret = ret.toLowerCase();

		return ret;
	}

	/**
	 * <dd>概要：資源を削除します。
	 * <dd>詳細：資源を削除します。
	 * <dd>備考：ファイル名が指定された場合はそのファイルを削除します。<br>
	 *           ディレクトリ名が指定された場合は、ディレクトリを削除しますが、空でないと削除されません。
	 * @param String 資源名
	 * @return boolean 削除フラグ(true:資源を削除, false:資源を削除失敗)
	 */
	public static boolean resourceDelete(String resourceName) {
		File objFile = new File(resourceName);
		// 資源を削除
		return objFile.delete();
	}

	/**
	 * <dd>概要：ファイル名を変更します。
	 * <dd>詳細：ファイル名を変更します。
	 * <dd>備考：
	 * @param String 変更前ファイル名
	 * @param String 変更後ファイル名
	 * @return boolean 変更フラグ（true:資源名を変更, false:資源名を変更失敗）
	 */
	public static boolean fileReName(String fromFileName, String toFileName) {
		// 変更前ファイル
		File fromFile = new File(fromFileName);
		// 変更後ファイル
		File toFile = new File(toFileName);

		// ファイル名を変更
		return fromFile.renameTo(toFile);
	}

	/**
	 * <dd>概要：ファイルをコピーします。
	 * <dd>詳細：ファイルをコピーします。
	 * <dd>備考：
	 * @param fromFilePath コピー元ファイル名
	 * @param String コピー先ファイル名
	 * @return boolean コピーフラグ（true:コピー完了, false:コピー失敗）
	 * @throws IOException
	 */
	public static boolean copy(String fromFilePath, String toFilePath) throws IOException {
		FileInputStream fis = new FileInputStream(fromFilePath);
		FileOutputStream fos = new FileOutputStream(toFilePath);
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}

		// 変更後ファイル
		File toFile = new File(toFilePath);
		return toFile.exists();
	}

	/**
	 * <dd>概要：資源の存在チェックします。
	 * <dd>詳細：資源の存在チェックします。
	 * <dd>備考：
	 * @param String 資源名
	 * @return boolean 存在フラグ（true:資源が存在, false:資源が存在しない）
	 */
	public static boolean isExistResource(String resourceName) {
		File objFile = new File(resourceName);
		// 資源の存在チェック
		return objFile.exists();
	}

	/**
	 * <dd>概要：ディレクトリを作成します。
	 * <dd>詳細：ディレクトリを作成します。
	 * <dd>備考：
	 * @param resourceName 資源名<br>
	 * @return boolean 結果（true:ディレクトリを作成, false:ディレクトリを作成失敗）
	 */
	public static boolean makeDirectory(String resourceName) {
		File objFile = new File(resourceName);
		// ディレクトリを作成
		return objFile.mkdir();
	}

	/**
	 * <dd>概要：ディレクトリ（親ディレクトリを含む）を作成します。
	 * <dd>詳細：ディレクトリ（親ディレクトリを含む）を作成します。
	 * <dd>備考：
	 * @param resourceName 資源名<br>
	 * @return boolean 結果（true:ディレクトリを作成, false:ディレクトリを作成失敗）
	 */
	public static boolean makeDirectorys(String resourceName) {
		File objFile = new File(resourceName);
		// ディレクトリを作成
		return objFile.mkdirs();
	}

	/**
	 * <dd>概要：単一ファイルのzip圧縮を行います。
	 * <dd>詳細：単一ファイルのzip圧縮を行います。
	 * <dd>備考：
	 * @param byte[] 入力のバイト配列
	 * @param String 圧縮するファイルのファイル名
	 * @param String zip圧縮後のファイルのファイル名
	 * @return File 出力ファイルオブジェクト
	 * @throws IOException
	 */
	public File zip(byte[] in, String entryFileName, String zipFileName) throws IOException {

		ZipOutputStream zipOutStream = new ZipOutputStream(
				new BufferedOutputStream(new FileOutputStream(new File(zipFileName))),
				Charset.forName("Shift_JIS"));
		File zipFile = null;
		if (in != null) {
			try {
				ZipEntry zipEntry = new ZipEntry(entryFileName);
				zipEntry.setMethod(ZipOutputStream.DEFLATED);
				zipOutStream.putNextEntry(zipEntry);
				zipOutStream.write(in, 0, in.length);
				zipOutStream.closeEntry();
				zipOutStream.flush();

				zipFile = new File(zipFileName);
			} finally {
				zipOutStream.close();
			}
		}

		return zipFile;
	}

	/**
	 * <dd>概要：複数ファイルのzip圧縮を行います。
	 * <dd>詳細：複数ファイルのzip圧縮を行います。
	 * <dd>備考：
	 * @param byte[] 入力のバイト配列
	 * @param String 圧縮するファイルのファイル名
	 * @param String zip圧縮後のファイルのファイル名
	 * @return File 出力ファイルオブジェクト
	 * @throws IOException
	 */
	public File zip(List<byte[]> in, List<String> entryFileName, String zipFileName) throws IOException {
		File zipFile = null;
		if (in != null) {
			ZipOutputStream zipOutStream = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(new File(zipFileName))),
					Charset.forName("Shift_JIS"));
			try {
				int i = 0;
				for (byte[] data : in) {
					ZipEntry zipEntry = new ZipEntry(entryFileName.get(i));
					zipEntry.setMethod(ZipOutputStream.DEFLATED);
					zipOutStream.putNextEntry(zipEntry);
					zipOutStream.write(data, 0, data.length);

					i++;
				}
				zipOutStream.closeEntry();
				zipOutStream.flush();

				zipFile = new File(zipFileName);
			} finally {
				zipOutStream.close();
			}
		}

		return zipFile;
	}

	/**
	 * <dd>概要：ファイルバイナリデータを取得します。
	 * <dd>詳細：ファイルをバイナリデータ（byte[]）型に変換します。
	 * <dd>備考：
	 * @param String ファイル名
	 * @return byte[] バイナリデータ
	 * @throws Exception
	 */
	public static byte[] toBinaryData(String fileName) throws Exception {
		File file = null;
		int len = 0;
		byte[] buf = null;
		InputStream is = null;

		try {
			// ファイル取得
			file = new File(fileName);
			len = (int) file.length();
			buf = new byte[len];

			// ファイルをストリームに変換
			is = new FileInputStream(file);
			// ストリームを読み込み
			is.read(buf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return buf;
	}

	/**
	 * <dd>概要：ファイル名から拡張子を取り除いた名前を返します。
	 * <dd>詳細：ファイル名から拡張子を取り除いた名前を返します。
	 * <dd>備考：
	 * @param String ファイル名
	 * @return String ファイル名
	 */
	public static String getPreffix(String fileName) {
		int point = 0;
		if (fileName == null) {
			return null;
		}
		point = fileName.lastIndexOf(".");
		if (point != -1) {
			return fileName.substring(0, point);
		}
		return fileName;
	}

	/**
	 * <dd>概要：ディレクトリを消します。
	 * <dd>詳細：ディレクトリを消します。
	 * <dd>備考：
	 * @param String ディレクトリ名
	 * @return boolean 結果（true:削除, false:削除失敗）
	 */
	public static boolean deleteDirectory(String directoryName) {
		// 資源を削除
		return delete(new File(directoryName));
	}

	/**
	 * <dd>概要：ファイルを消します。
	 * <dd>詳細：ファイルを消します。
	 * <dd>備考：
	 * @param File ファイルオブジェクト
	 * @return boolean 結果（true:削除, false:削除失敗）
	 */
	private static boolean delete(File file) {
		File[] files = null;
		if (file.exists() == false) {
			return false;
		}

		if (file.isFile()) {
			file.delete();
		}

		if (file.isDirectory()) {
			files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				delete(files[i]);
			}
			file.delete();
		}
		return true;
	}

	/**
	 * <dd>概要：ファイルを作成します。
	 * <dd>詳細：ファイルを作成します。
	 * <dd>備考：
	 * @param String ファイル名
	 * @param byte[] ファイルオブジェクト
	 */
	public static void createNewUtfFile(String fname, byte[] byteData) throws Exception {

		createNewFile(fname, byteData, null);
	}

	/**
	 * <dd>概要：ファイルを作成します。
	 * <dd>詳細：ファイルを作成します。
	 * <dd>備考：文字コードの指定がない場合は、デフォルトでUTF-8とします。
	 * @param fileName ファイル名
	 * @param byteData ファイルオブジェクト
	 * @param charsetName 文字コード
	 * @exception Exception ファイルの作成失敗
	 */
	public static void createNewFile(String fname, byte[] byteData, String charsetName) throws Exception {

		FileOutputStream fos = null;
		OutputStreamWriter fout = null;
		File file = null;
		BufferedWriter buffWriter = null;
		String charCodeName = null;

		// パラメータの文字コードがnullまたは空文字の場合は、デフォルトでUTF-8を設定
		if (null == charsetName || 0 == charsetName.length()) {
			charCodeName = "UTF-8";
		} else {
			charCodeName = charsetName;
		}

		try {
			file = new File(fname);
			// ファイルが存在する場合は削除する。
			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();
			file.deleteOnExit();
			fos = new FileOutputStream(file);
			fout = new OutputStreamWriter(fos, charCodeName);
			buffWriter = new BufferedWriter(fout);
			buffWriter.write(new String(byteData));
			buffWriter.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (buffWriter != null) {
				try {
					buffWriter.close();
					fout.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * <dd>概要：ファイルを作成し、保持します。
	 * <dd>詳細：ファイルを作成し、保持します。。
	 * <dd>備考：文字コードの指定がない場合は、デフォルトでUTF-8とします。
	 * @param fileName ファイル名
	 * @param byteData ファイルオブジェクト
	 * @param charsetName 文字コード
	 * @exception Exception ファイルの作成失敗
	 */
	public static void createNewFileAndSave(String fname, byte[] byteData, String charsetName) throws Exception {

		FileOutputStream fos = null;
		OutputStreamWriter fout = null;
		File file = null;
		BufferedWriter buffWriter = null;
		String charCodeName = null;

		// パラメータの文字コードがnullまたは空文字の場合は、デフォルトでUTF-8を設定
		if (null == charsetName || 0 == charsetName.length()) {
			charCodeName = "UTF-8";
		} else {
			charCodeName = charsetName;
		}

		try {
			file = new File(fname);
			// ファイルが存在する場合は削除する。
			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();
			fos = new FileOutputStream(file);
			fout = new OutputStreamWriter(fos, charCodeName);
			buffWriter = new BufferedWriter(fout);
			buffWriter.write(new String(byteData));
			buffWriter.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (buffWriter != null) {
				try {
					buffWriter.close();
					fout.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * <dd>概要：登録可能な拡張子マップを取得します。
	 * <dd>詳細：登録可能な拡張子マップを取得します。
	 * <dd>備考：構成情報ファイルの読み込みに失敗した場合、IOExceptionをスローします。
	 * @return 登録可能拡張子マップ
	 * @exception IOException 構成情報ファイル読み込み失敗
	 */
	public static Map<String, String> getUpLoadableExtensions() throws IOException {
		// 設定情報アクセスクラス
		BaseProperties properties;
		// 有効ファイル拡張子
		String fileExtensionStr;

		// 登録可能拡張子マップが生成されていない場合、作成する。
		if (upLoadableExtensionMap == null) {
			// 構成情報取得
			properties = BaseProperties.getInstance();
//			// 有効ファイル拡張子取得
//			fileExtensionStr = properties.getProperty(CommonConstInterface.CO_VALIDFILEEXTENSION);
//			// 登録可能拡張子マップ生成
//			upLoadableExtensionMap = new HashMap<String, String>();
//			// マップにつめる。(スラッシュ区切りで設定されている)
//			for (String oneExtension : fileExtensionStr.split(EXTENSION_SEPARATOR)) {
//				upLoadableExtensionMap.put(oneExtension, "");
//			}
		}
		// 拡張子マップを返す
		return upLoadableExtensionMap;
	}

	/**
	 * <dd>概要：バイナリファイルを作成し、保持します。
	 * <dd>詳細：バイナリファイルを作成し、保持します。。
	 * <dd>備考：
	 * @param fileName ファイル名
	 * @param byteData ファイルオブジェクト
	 * @exception Exception ファイルの作成失敗
	 */
	public static void createNewFileAndSaveBynary(String fname, byte[] byteData) throws Exception {

		// ファイルオブジェクト
		File outputFile = null;
		// ファイルストリーム
		FileOutputStream fos = null;
		// バッファストリーム
		BufferedOutputStream bos = null;

		try {

			// ファイルオブジェクトを生成する。
			outputFile = new File(fname);
			// ファイルが存在する場合は削除する。
			if (outputFile.exists()) {
				outputFile.delete();
			}

			// 出力ストリームの生成
			fos = new FileOutputStream(outputFile);
			bos = new BufferedOutputStream(fos);

			// ファイル出力
			bos.write(byteData);
			// 後処理
			bos.flush();
			bos.close();

		} catch (IOException e) {
			throw e;
		}
	}
}
