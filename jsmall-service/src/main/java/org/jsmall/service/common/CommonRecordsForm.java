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

package org.jsmall.service.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * <dd>概要：一覧情報の受け渡しを行います。
 * <dd>詳細：一覧画面に表示する値を格納します。
 * <dd>備考：
 * @version V01L01
 * @author 富士通
 */
@Getter
@Setter
public class CommonRecordsForm implements Serializable  {

	/**
	 * serialVersionUID
	 * @see long
	 */
	private static final long serialVersionUID = -1856063754048795060L;

	/**
	 *全体件数
	 * @see int
	 */

	private int allCount;

	/**
	 *表示件数
	 * @see int
	 */
	private int dispNumber;

	/**
	 *表示件数(開始)
	 * @see int
	 */
	private int dispStartNumber;

	/**
	 *表示件数(終了)
	 * @see int
	 */
	private int dispEndNumber;

	/**
	 *総ページ数
	 * @see int
	 */
	private int allPageCount;

	/**
	 *表示ページ数
	 * @see int
	 */
	private int dispPageCount;

	/**
	 *ページIndex
	 * @see int
	 */
	private int pageIndex;

	/**
	 *表示ページ番号リスト
	 * @see int
	 */
	private int dispPageNumber;

	/**
	 *前のページリンク表示フラグ
	 * @see boolean
	 */
	private boolean previousPageLinkFlg;

	/**
	 *次のページリンク表示フラグ
	 * @see boolean
	 */
	private boolean followingPageLinkFlg;

	/**
	 *選択したページ番号
	 * @see int
	 */
	private int selectedPageIndex;

	/**
	 * 並び替え条件
	 * @see Map<String, String>
	 */
	private Map<String, String> sorter;

	/**
	 * 並び替え押下時番号
	 * @see String
	 */
	private String sortHeaderNumber;

	/**
	 *一覧情報DTO（表示用）
	 * @see boolean
	 */
	private List<Object> resultList = new ArrayList<Object>();

	/**
	 *アコーディオン開閉状態フラグ
	 * @see boolean
	 */
	private boolean stateAccordionFlag = false;

	/**
	 *検索結果メッセージ
	 * @see String
	 */
	private String searchMessage;

	/**
	 *0件フラグ
	 * @see boolean
	 */
	private boolean zeroCountFlag = false;

	/**
	 *検索件数条件値超過フラグ
	 * @see boolean
	 */
	private boolean searchCountUpperLimitExcessFlag = false;
}
