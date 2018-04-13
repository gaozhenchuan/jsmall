package org.jsmall.service.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class CommonPagingService<M extends CommonRecordsForm, S, R>{

	protected void reSettingForm(HttpServletRequest req, M model) throws Exception {
		return;
	}

	/**
	 * <dd>概要：一覧列ヘッダを押下した際の処理を行います。
	 * <dd>詳細：一覧列ヘッダ押下時のソート処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @exception Exception
	 */
	public void sortHeader(HttpServletRequest req, M model) throws Exception {

	}

	/**
	 * <dd>概要：前のページリンクを押下した際の処理を行います。
	 * <dd>詳細：「前のページ」リンク押下時のページング処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @exception Exception
	 */
	public void prevShow(HttpServletRequest req, M model) throws Exception {
	
	}

	/**
	 * <dd>概要：次のページリンクを押下した際の処理を行います。
	 * <dd>詳細：「次のページ」リンク押下時のページング処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @exception Exception
	 */
	public void nextShow(HttpServletRequest req, M model) throws Exception {
	
	}

	/**
	 * <dd>概要：ページ番号リンクを押下した際の処理を行います。
	 * <dd>詳細：ページ番号リンク押下時のページング処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @exception Exception
	 */
	public void pageShow(HttpServletRequest req, M model) throws Exception {
	
	}

	/**
	 * <dd>概要：表示件数ドロップダウンリストを変更した際の処理を行います。
	 * <dd>詳細：「表示件数」ドロップダウンリスト変更時のページング処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @exception Exception
	 */
	public void dispCountChange(HttpServletRequest req, M model) throws Exception {
		
	}

	/**
	 * <dd>概要：検索処理を行います。
	 * <dd>詳細：検索処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @param conditionDto 検索条件DTO
	 * @return 検索結果一覧DTO
	 * @exception Exception
	 */
	protected List<R> search(HttpServletRequest req, M model, S conditionDto) throws Exception {
		return null;
	}

	/**
	 * <dd>概要：検索処理を行います。
	 * <dd>詳細：検索条件に従った検索処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @return 検索結果一覧DTO
	 * @exception Exception
	 */
	protected List<R> search(HttpServletRequest req, M model) throws Exception {
		return null;
	}

	/**
	 * <dd>概要：ページの設定処理を行います。
	 * <dd>詳細：ページの設定処理を行います。
	 * <dd>備考：
	 * @param req リクエスト情報
	 * @param model 画面フォーム
	 * @param cao0001ConditionDto 検索条件DTO
	 * @param pageDto ページング制御DTO
	 * @exception Exception
	 */
	protected void settingPage(HttpServletRequest req, M model, S conditionDto)
			throws Exception {
	
	}

}
