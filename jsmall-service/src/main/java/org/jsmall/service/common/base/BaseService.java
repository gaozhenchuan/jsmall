package org.jsmall.service.common.base;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.jsmall.common.BaseLogger;
import org.jsmall.common.BaseMessages;
import org.jsmall.dao.master.CodeMstDao;
import org.jsmall.dao.master.dto.UserInfoDto;
import org.jsmall.dao.master.entity.Code;
import org.jsmall.service.common.DisplayMakeUpInfo;
import org.jsmall.service.common.base.dto.DBCommonColumnDto;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseService implements Serializable {

    /**
     * serialVersionUID
     * @see long
     */
    private static final long serialVersionUID = -8485174899145339062L;

    public static final String KEY_SESSION_PICTIS_POSS_DISPLAY_MAKE_UP = "SESSION_PICTIS_POSS_DISPLAY_MAKE_UP";
    
	public static final String KEY_SESSION_PICTIS_POSS_USER_INFO = "SESSION_PICTIS_POSS_USER_INFO";
    
    /**
     * 区分Dao
     * @see CodeMstMapper
     */
    @Autowired(required = true)
    protected transient CodeMstDao codeMstDao;

//    /**
//     * コードマスタ共通Mapper
//     * @see ICodeMstMapper
//     */
//    @Autowired(required = true)
//    private transient CodeMstDao CodeMstDao;
//
//    /**
//     * 行政庁マスタ共通Mapper
//     * @see IPortalPrefMstMapper
//     */
//    @Autowired(required = true)
//    private transient IPossPrefMstMapper iPossPrefMstMapper;

    /**
     * logger
     * @see BaseLogger
     */
    private transient BaseLogger logger = (BaseLogger)BaseLogger.getLogger(BaseService.class);

    /**
     * <dd>概要：遷移元の画面IDを取得します。
     * <dd>詳細：遷移元の画面IDを取得します。
     * <dd>備考：
     * @param req リクエスト情報
     * @return String 画面ID
     */
    @SuppressWarnings("unchecked")
	public String getPreScreenId(HttpServletRequest req) {
        int i = 1;
        Map<String, DisplayMakeUpInfo> displayMakeUpMap = (LinkedHashMap<String, DisplayMakeUpInfo>)req.getSession()
                .getAttribute(KEY_SESSION_PICTIS_POSS_DISPLAY_MAKE_UP);
        for (Map.Entry<String, DisplayMakeUpInfo> entry : displayMakeUpMap.entrySet()) {
            if ((displayMakeUpMap.size() - 1) == i) {
                return ((DisplayMakeUpInfo)entry.getValue()).getScreenId();
            }
            i++;
        }
        return null;
    }

    /**
     * <dd>概要：２つ前の遷移元の画面IDを取得します。
     * <dd>詳細：２つ前の遷移元の画面IDを取得します。
     * <dd>備考：
     * @param req リクエスト情報
     * @return String 画面ID
     */
    @SuppressWarnings("unchecked")
    public String getTwoBeforeScreenId(HttpServletRequest req) {
        int i = 1;
        Map<String, DisplayMakeUpInfo> displayMakeUpMap = (LinkedHashMap<String, DisplayMakeUpInfo>)req.getSession()
                .getAttribute(KEY_SESSION_PICTIS_POSS_DISPLAY_MAKE_UP);
        for (Map.Entry<String, DisplayMakeUpInfo> entry : displayMakeUpMap.entrySet()) {
            if ((displayMakeUpMap.size() - 2) == i) {
                return ((DisplayMakeUpInfo)entry.getValue()).getScreenId();
            }
            i++;
        }
        return null;
    }

    /**
     * <dd>概要：更新機能IDを取得します。
     * <dd>詳細：更新機能IDを取得します。
     * <dd>備考：
     * @param req リクエスト情報
     * @return 更新機能ID
     */
    @SuppressWarnings("unchecked")
    public String getUpdateFuncId(HttpServletRequest req) {
        int i = 1;
        Map<String, DisplayMakeUpInfo> displayMakeUpMap = (LinkedHashMap<String, DisplayMakeUpInfo>)req.getSession()
                .getAttribute(KEY_SESSION_PICTIS_POSS_DISPLAY_MAKE_UP);
        for (Map.Entry<String, DisplayMakeUpInfo> entry : displayMakeUpMap.entrySet()) {
            if ((displayMakeUpMap.size()) == i) {
                return ((DisplayMakeUpInfo)entry.getValue()).getProgramId();
            }
            i++;
        }
        return null;
    }

    /**
     * <dd>概要：セッションの登録を行います。
     * <dd>詳細：引数にて連携されたオブジェクトをセッションに登録します。
     * <dd>備考：
     * @param req リクエスト情報
     * @param model セッション登録対象オブジェクト
     * @param sessionName セッション名
     * @exception Exception
     */
    public void setModelSession(HttpServletRequest req, Object model, String sessionName) throws Exception {

        logger.startLog(this.getClass().getName(), "setModelSession");

        try {
            // セッションに登録を行う。
            req.getSession().setAttribute(sessionName, model);

        } catch (Exception e) {
            throw e;

        } finally {
            logger.endLog(this.getClass().getName(), "setModelSession");
        }
    }

    /**
     * <dd>概要：セッションの破棄を行います。
     * <dd>詳細：セッション情報から引数にて指定されたセッションを削除します。
     * <dd>備考：
     * @param req リクエスト情報
     * @param sessionName セッション名
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public String clearModelSession(HttpServletRequest req, String sessionName) throws Exception {

        logger.startLog(this.getClass().getName(), "clearModelSession");

        String programId = null;

        try {
            // セッションの破棄を行う。
            req.getSession().removeAttribute(sessionName);

            // 画面構成情報から最終インデックス位置の１つ前のプログラムIDを取得する。
            int i = 1;
            Map<String, DisplayMakeUpInfo> displayMakeUpMap = (LinkedHashMap<String, DisplayMakeUpInfo>)req
                    .getSession().getAttribute(KEY_SESSION_PICTIS_POSS_DISPLAY_MAKE_UP);
            for (Map.Entry<String, DisplayMakeUpInfo> entry : displayMakeUpMap.entrySet()) {
                if ((displayMakeUpMap.size() - 1) == i) {
                    programId = ((DisplayMakeUpInfo)entry.getValue()).getProgramId();
                    break;
                }
                i++;
            }
            return programId;

        } catch (Exception e) {
            throw e;

        } finally {
            logger.endLog(this.getClass().getName(), "clearModelSession");
        }
    }

    /**
     * <dd>概要：エラーメッセージリストにメッセージの追加を行います。
     * <dd>詳細：入力チェックの結果により、メッセージの生成ならびにメッセージの追加を行います。
     * <dd>備考：
     * @param messageId メッセージID
     * @param checkResult 入力チェック結果
     * @param errorMessageList エラーメッセージリスト
     * @param messageArgs メッセージパラメータ
     */
    protected void createMessage(String messageId, boolean checkResult, List<String> errorMessageList,
            Object... messageArgs) {
        if (!checkResult) {
            BaseMessages messages = BaseMessages.getInstance();
            if (messageArgs.length == 0) {
                errorMessageList.add(messages.getMessage(messageId));
            } else {
                errorMessageList.add(messages.getMessage(messageId, messageArgs));
            }
        }
    }

    protected void getCodeName(String codeSetId, Map<String, String> codeNameMap) throws Exception {
        List<Code> codeMstList = null;
        String codeMstKey = new String();

        codeMstList = this.codeMstDao.selectByCodeSetId(codeSetId);

        for (Code codeMst : codeMstList) {
            codeNameMap.put(codeMst.getCodeId(), codeMst.getCodeName());
        }
    }

    /**
     * <dd>概要：コードマスタからコード略称の一覧を取得します。
     * <dd>詳細：コードマスタから指定されたコードセットIDを検索条件として、該当するコード略称の一覧を取得します。
     * <dd>備考：
     * @param codeSetId コードセットID
     * @param codeNameAddrev コード略称マップ
     * @exception Exception
     */
    protected void getCodeAddRev(String codeSetId, Map<String, String> codeAddrevMap) throws Exception {
        List<Code> codeMstList = null;

        codeMstList = this.codeMstDao.selectByCodeSetId(codeSetId);

        for (Code codeMst : codeMstList) {
            codeAddrevMap.put(codeMst.getCodeId(), codeMst.getCodeName());
        }
    }

    /**
     * <dd>概要：コードマスタからコード名称の一覧を取得します。
     * <dd>詳細：コードマスタから指定されたコードセットIDを検索条件として、該当するコード名称の一覧を取得します。
     * <dd>備考：
     * @param codeSetId コードセットID
     * @param pattern パターン(0: 空白行なし、1: 空白行あり)
     * @return Map<String, String> コード名称リスト
     * @exception Exception
     */
    protected Map<String, String> getCodeName(String codeSetId, int pattern) throws Exception {
        List<Code> codeMstList = null;

        Map<String, String> codeNameMap = new LinkedHashMap<String, String>();

        codeMstList = this.codeMstDao.selectByCodeSetId(codeSetId);

        if (pattern == 1) {
            codeNameMap.put("", "");
        }

        for (Code codeMst : codeMstList) {
            codeNameMap.put(codeMst.getCodeId(), codeMst.getCodeName());
        }

        return codeNameMap;
    }

    /**
     * <dd>概要：コードマスタからコード略称の一覧を取得します。
     * <dd>詳細：コードマスタから指定されたコードセットIDを検索条件として、該当するコード略称の一覧を取得します。
     * <dd>備考：
     * @param codeSetId コードセットID
     * @param pattern パターン(0: 空白行なし、1: 空白行あり)
     * @return Map<String, String> コード略称リスト
     * @exception Exception
     */
    protected Map<String, String> getCodeAddRev(String codeSetId, int pattern) throws Exception {
        List<Code> codeMstList = null;
        Map<String, String> codeAddrevMap = new LinkedHashMap<String, String>();

        codeMstList = this.codeMstDao.selectByCodeSetId(codeSetId);

        if (pattern == 1) {
            codeAddrevMap.put("", "");
        }

        for (Code codeMst : codeMstList) {
            codeAddrevMap.put(codeMst.getCodeId(), codeMst.getCodeName());
        }

        return codeAddrevMap;
    }

    /**
     * <dd>概要：コードマスタからコード名称を取得します。
     * <dd>詳細：コードマスタから指定されたコードセットID及びコードIDを検索条件として、該当するコード名称を取得します。
     * <dd>備考：
     * @param codeSetId コードセットID
     * @param codeId コードID
     * @return String コード名称
     * @exception Exception
     */
    protected String getCodeName(String codeSetId, String codeId) throws Exception {
        Code codeMst = null;
        String retStr = null;

        codeMst = this.codeMstDao.selectByPrimaryKey(codeSetId);

        if (codeMst != null) {
            retStr = codeMst.getCodeName();
        }

        return retStr;
    }

    /**
     * <dd>概要：コードマスタからコード略称を取得します。
     * <dd>詳細：コードマスタから指定されたコードセットID及びコードIDを検索条件として、該当するコード略称を取得します。
     * <dd>備考：
     * @param codeSetId コードセットID
     * @param codeId コードID
     * @return String コード略称
     * @exception Exception
     */
    protected String getCodeAddRev(String codeSetId, String codeId) throws Exception {
        Code codeMst = null;
        String retStr = null;

        codeMst = this.codeMstDao.selectByPrimaryKey(codeSetId);

        if (codeMst != null) {
            retStr = codeMst.getCodeName();
        }

        return retStr;
    }

    /**
     * <dd>概要：DTOの共通項目を作成します。
     * <dd>詳細：DTOの共通項目を作成します。
     * <dd>備考：
     * @param <T>
     * @param dto DB登録・更新用DTO
     * @param request リクエスト情報
     * @param reflectFlag（1: 登録、2: 更新）
     * @exception Exception
     */
    protected <T> void createDBCommonColumnDto(T dto, HttpServletRequest request, String... reflectFlag)
            throws Exception {
        UserInfoDto UserInfoDto = null;
        DBCommonColumnDto columnDto = new DBCommonColumnDto();
        Date sysDate = new Date();

        this.logger.startLog(this.getClass().getName(), "createDBCommonColumnDto");

        try {

            UserInfoDto = (UserInfoDto)request.getSession()
                    .getAttribute(KEY_SESSION_PICTIS_POSS_USER_INFO);

//            if ("1".equals(reflectFlag[0])) {
//                columnDto.setCreateUser(UserInfoDto.getStaffUserId());
//                columnDto.setCreateDatetime(sysDate);
//                columnDto.setDeletedFlag("0");
//                columnDto.setUpdateUser(UserInfoDto.getStaffUserId());
//                columnDto.setUpdateDatetime(sysDate);
//                columnDto.setUpdateFuncId(this.getUpdateFuncId(request));
//
//            } else {
//
//                if (reflectFlag.length == 1) {
//                    columnDto.setDeletedFlag("");
//                } else {
//                    columnDto.setDeletedFlag(reflectFlag[1]);
//                }
//                columnDto.setUpdateUser(UserInfoDto.getStaffUserId());
//                columnDto.setUpdateDatetime(sysDate);
//                columnDto.setUpdateFuncId(this.getUpdateFuncId(request));
//            }

        } catch (Exception e) {
            throw e;

        } finally {
            this.logger.endLog(this.getClass().getName(), "createDBCommonColumnDto");

        }

        BeanUtils.copyProperties(dto, columnDto);
    }

}

